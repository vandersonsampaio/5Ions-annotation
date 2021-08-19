package br.ufsc.ine.ppgcc.helper;

import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.Entity;
import br.ufsc.ine.ppgcc.model.Sentence;
import br.ufsc.ine.ppgcc.model.Sentiment;
import br.ufsc.ine.ppgcc.model.request.DocumentRequest;
import br.ufsc.ine.ppgcc.model.response.EntityCoreResponse;

import java.util.Collections;
import java.util.List;

public class ControllerHelper {

    private final static String ID = "Id 1";
    private final static String CONTENT = "Content";
    private final static String LANGUAGE = "pt";

    private final static String NAME_ENTITY = "Name Entity";
    private final static String TYPE_ENTITY = "PERSON";
    private final static double SALIENCE = .3;
    private final static float SCORE_ENTITY = -.6f;
    private final static float MAGNITUDE_ENTITY = .1f;

    private final static float SCORE_TEXT = -.4f;
    private final static float MAGNITUDE_TEXT = .2f;

    private final static int ORDER = 1;
    private final static long OFFSET = 0;
    private final static float SCORE_SENTENCE = -.2f;
    private final static float MAGNITUDE_SENTENCE = .8f;

    public static List<DocumentRequest> buildListDocumentRequest(){
        return Collections.singletonList(DocumentRequest.builder()
                .id(ID)
                .content(CONTENT)
                .language(LANGUAGE)
                .build());
    }

    public static List<Document> buildListDocument(){
        return Collections.singletonList(Document.builder()
                .id(ID)
                .content(CONTENT)
                .language(LANGUAGE)
                .build());
    }

    public static List<EntityCoreResponse> buildListEntityCoreResponse(){
        return Collections.singletonList(EntityCoreResponse.builder()
                .name(NAME_ENTITY)
                .type(TYPE_ENTITY)
                .salience(SALIENCE)
                .build());
    }

    public static List<Document> buildListDocumentWithSentenceAndSentiment(){
        return Collections.singletonList(Document.builder()
                .id(ID)
                .sentiment(buildSentiment(SCORE_TEXT, MAGNITUDE_TEXT))
                .sentences(buildListSentence())
                .build());
    }

    public static List<Document> buildListDocumentWithEntities(){
        return Collections.singletonList(Document.builder()
                .id(ID)
                .entities(buildListEntity())
                .build());
    }

    private static List<Entity> buildListEntity(){
        return Collections.singletonList(Entity.builder()
                .name(NAME_ENTITY)
                .type(TYPE_ENTITY)
                .sentiment(buildSentiment(SCORE_ENTITY, MAGNITUDE_ENTITY))
                .build());
    }

    private static List<Sentence> buildListSentence() {
        return Collections.singletonList(Sentence.builder()
                .order(ORDER)
                .offset(OFFSET)
                .sentiment(buildSentiment(SCORE_SENTENCE, MAGNITUDE_SENTENCE))
                .build());
    }

    private static Sentiment buildSentiment(float score, float magnitude) {
        return Sentiment.builder()
                .score(score)
                .magnitude(magnitude)
                .build();
    }
}
