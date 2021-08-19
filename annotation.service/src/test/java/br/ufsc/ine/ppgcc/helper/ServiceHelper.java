package br.ufsc.ine.ppgcc.helper;

import br.ufsc.ine.ppgcc.model.*;
import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.Entity;
import br.ufsc.ine.ppgcc.model.Sentence;
import br.ufsc.ine.ppgcc.model.Sentiment;
import com.google.cloud.language.v1.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ServiceHelper {

    public static Document buildDocument(){
        return Document.builder()
                .id("Id 1")
                .content("Content 1")
                .language("en")
                .entities(Collections.emptyList())
                .build();
    }

    public static com.google.cloud.language.v1.Document buildGoogleDocument(){
        return com.google.cloud.language.v1.Document
                .newBuilder().setContent(buildDocument().getContent())
                .setType(com.google.cloud.language.v1.Document.Type.PLAIN_TEXT)
                .build();
    }

    public static Document buildDocumentNotEnglish(){
        return Document.builder()
                .id("5be9edaa67e400216cefe2c6")
                .content("Alckmin se diz ‘confiante’ sobre licitação suspensa de linhas do Metrô")
                .language("pt")
                .entities(Collections.singletonList(buildEntity()))
                .sentences(buildSentences())
                .build();
    }

    public static Entity buildEntity(){
        return Entity.builder()
                .type("PERSON")
                .name("GERALDO ALCKMIN")
                .source("https://en.wikipedia.org/wiki/Geraldo_Alckmin")
                .salience(1)
                .mentions(buildMentions())
                .build();
    }

    public static List<Mention> buildMentions(){
        Mention mention1 = buildMention(41, "Geraldo Alckmin");
        Mention mention2 = buildMention(533, "Alckmin");
        return Arrays.asList(mention1, mention2);
    }

    private static Mention buildMention(long offset, String surfaceName) {
        return Mention.builder()
                .offset(offset)
                .surfaceName(surfaceName)
                .type("PROPER")
                .build();
    }

    public static List<Sentence> buildSentences() {
        Sentence sentence1 = buildSentence(1, .4, .4, 0);
        Sentence sentence2 = buildSentence(2, .3, .3, 199);
        Sentence sentence3 = buildSentence(3, .2, .2, 395);
        Sentence sentence4 = buildSentence(4, .5, .5, 595);
        Sentence sentence5 = buildSentence(5, 0, 0, 719);
        Sentence sentence6 = buildSentence(6, .2, .2, 755);
        Sentence sentence7 = buildSentence(7, .69, .69, 892);
        Sentence sentence8 = buildSentence(8, .2, .2, 954);

        return Arrays.asList(sentence1, sentence2, sentence3, sentence4,
                sentence5, sentence6, sentence7, sentence8);
    }

    private static Sentence buildSentence(int order, double score, double magnitude, long offset){
        return Sentence.builder()
                .order(order)
                .offset(offset)
                .sentiment(Sentiment.builder()
                        .magnitude(magnitude)
                        .score(score)
                        .build())
                .build();
    }

    public static AnalyzeSentimentResponse buildAnalyzeSentimentResponse() {
        return AnalyzeSentimentResponse.newBuilder()
                .setDocumentSentiment(buildSentiment(.3f, .5f))
                .addSentences(buildSentence(0, .8f, .3f))
                .addSentences(buildSentence(255, .2f, .2f))
                .build();
    }

    public static AnalyzeEntitySentimentResponse buildAnalyzeEntitySentimentResponse() {
        return AnalyzeEntitySentimentResponse.newBuilder()
                .addEntities(buildGoogleEntity())
                .build();
    }

    public static com.google.cloud.language.v1.Entity buildGoogleEntity(){
        return com.google.cloud.language.v1.Entity.newBuilder()
                .setType(com.google.cloud.language.v1.Entity.Type.PERSON)
                .setName("NAME LAST_NAME")
                .setSalience(.2f)
                .putMetadata("source", "source_url")
                .setSentiment(buildSentiment(.3f, .2f))
                .addMentions(buildEntityMention("LAST_NAME", 5, .1f, .1f))
                .addMentions(buildEntityMention("FIRST_NAME", 965, -.5f, .4f))
                .build();
    }

    public static EntityMention buildEntityMention(String surfaceName, int beginOffset, float score, float magnitude) {
        return EntityMention.newBuilder()
                .setType(EntityMention.Type.PROPER)
                .setText(TextSpan.newBuilder()
                        .setBeginOffset(beginOffset)
                        .setContent(surfaceName)
                        .build())
                .setSentiment(buildSentiment(score, magnitude))
                .build();
    }

    public static com.google.cloud.language.v1.Sentiment buildSentiment(float score, float magnitude){
        return com.google.cloud.language.v1.Sentiment.newBuilder()
                .setMagnitude(magnitude)
                .setScore(score)
                .build();
    }

    public static com.google.cloud.language.v1.Sentence buildSentence(int beginOffset, float score, float magnitude){
        return com.google.cloud.language.v1.Sentence.newBuilder()
                .setSentiment(buildSentiment(score, magnitude))
                .setText(TextSpan.newBuilder()
                        .setBeginOffset(beginOffset)
                        .build())
                .build();
    }

    public static AnalyzeEntitiesResponse buildAnalyzeEntitiesResponse() {
        return AnalyzeEntitiesResponse.newBuilder()
                .addEntities(buildGoogleEntity())
                .build();
    }
}
