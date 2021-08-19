package br.ufsc.ine.ppgcc.service.implementation;

import br.ufsc.ine.ppgcc.mapper.SentimentMapper;
import br.ufsc.ine.ppgcc.model.*;
import br.ufsc.ine.ppgcc.service.interfaces.ISentimentService;
import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.LanguageServiceClient;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.IntStream;

@Service
public class SentimentService implements ISentimentService {

    private final SentimentMapper mapper;

    public SentimentService(SentimentMapper mapper) {
        this.mapper = mapper;
    }

    public List<Document> sentimentAnnotation(List<Document> documents) throws Exception {
        LanguageServiceClient language = LanguageServiceClient.create();
        for (Document document : documents) {
            com.google.cloud.language.v1.Document doc = buildDocument(document.getContent());

            AnalyzeSentimentResponse responseAnalysis = language.analyzeSentiment(doc);

            Sentiment sentiment = mapper.toModel(responseAnalysis.getDocumentSentiment());
            List<Sentence> sentences = mapper.toSentenceList(responseAnalysis.getSentencesList());
            IntStream.range(0, sentences.size()).forEach(idx -> sentences.get(idx).setOrder(idx + 1));

            document.setSentiment(sentiment);
            document.setSentences(sentences);
        }

        return documents;
    }

    public List<Document> sentimentEntityAnnotation(List<Document> documents) throws Exception {
        for(Document document : documents){
            if(document.getLanguage().toLowerCase(Locale.ROOT).equals("en")){
                sentimentEntityEnglishDocument(document);
            } else {
                sentimentEntityAnyLanguage(document);
            }
        }
        return documents;
    }

    private void sentimentEntityEnglishDocument(Document document) throws Exception {
        LanguageServiceClient language = LanguageServiceClient.create();
        com.google.cloud.language.v1.Document doc = buildDocument(document.getContent());

        List<com.google.cloud.language.v1.Entity> entitiesResponse = language.analyzeEntitySentiment(doc).getEntitiesList();

        document.setEntities(mapper.toEntityList(entitiesResponse));
    }

    private com.google.cloud.language.v1.Document buildDocument(String content) {
        return com.google.cloud.language.v1.Document
                .newBuilder().setContent(content)
                .setType(com.google.cloud.language.v1.Document.Type.PLAIN_TEXT)
                .build();
    }

    private void sentimentEntityAnyLanguage(Document document) throws Exception {
        for(Entity entity : document.getEntities()) {
            double scoreSentiment = 0;
            double magnitudeSentiment = 0;

            for(Mention mention : entity.getMentions()) {
                long offsetMention = mention.getOffset();

                Sentiment sentiment = document.getSentences().stream()
                        .filter(s -> s.getOffset() <= offsetMention)
                        .max(Comparator.comparing(Sentence::getOffset))
                        .orElseThrow(Exception::new).getSentiment();

                magnitudeSentiment += sentiment.getMagnitude();
                scoreSentiment += (sentiment.getScore() * sentiment.getMagnitude());

                mention.setSentiment(sentiment);
            }

            double scoreFinal = scoreSentiment / magnitudeSentiment;
            Sentiment sentimentEntity = Sentiment.builder()
                    .magnitude(magnitudeSentiment)
                    .score(scoreFinal)
                    .build();

            entity.setSentiment(sentimentEntity);
        }
    }
}
