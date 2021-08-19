package br.ufsc.ine.ppgcc.helper;

import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.Sentence;
import br.ufsc.ine.ppgcc.model.Sentiment;
import br.ufsc.ine.ppgcc.model.request.DocumentRequest;
import com.google.cloud.language.v1.Entity;
import com.google.cloud.language.v1.EntityMention;
import com.google.cloud.language.v1.TextSpan;

import java.util.Collections;

public class MapperHelper {

    public static DocumentRequest buildDocumentRequest(){
        return DocumentRequest.builder()
                .id("Id 1")
                .content("Content 1")
                .language("en")
                .build();
    }

    public static Document buildDocument(){
        return Document.builder()
                .id("Id 1")
                .content("Content 1")
                .language("en")
                .sentences(Collections.singletonList(buildSentence()))
                .sentiment(buildSentiment())
                .build();
    }

    public static Document buildDocumentWithoutSentence(){
        return Document.builder()
                .id("Id 1")
                .content("Content 1")
                .language("en")
                .sentences(null)
                .sentiment(buildSentiment())
                .build();
    }

    public static Sentence buildSentence(){
        return Sentence.builder()
                .offset(0)
                .order(1)
                .sentiment(buildSentiment())
                .build();
    }

    public static com.google.cloud.language.v1.Sentence buildGoogleSentence(){
        return com.google.cloud.language.v1.Sentence.newBuilder()
                .setText(buildGoogleTextSpan())
                .setSentiment(buildGoogleSentiment())
                .build();
    }

    public static Sentiment buildSentiment(){
        return Sentiment.builder()
                .score(.5)
                .magnitude(1)
                .build();
    }

    public static com.google.cloud.language.v1.Sentiment buildGoogleSentiment(){
        return com.google.cloud.language.v1.Sentiment.newBuilder()
                .setScore(1)
                .setMagnitude(1)
                .build();
    }

    public static EntityMention buildGoogleEntityMention(){
        return EntityMention.newBuilder()
                .setSentiment(buildGoogleSentiment())
                .setText(buildGoogleTextSpan())
                .setType(EntityMention.Type.PROPER)
                .build();
    }

    public static EntityMention buildGoogleEntityMentionEmpty(){
        return EntityMention.newBuilder()
                .build();
    }

    public static TextSpan buildGoogleTextSpan(){
        return TextSpan.newBuilder().setContent("Content").setBeginOffset(0).build();
    }

    public static com.google.cloud.language.v1.Entity buildGoogleEntity(){
        return com.google.cloud.language.v1.Entity.newBuilder()
                .setName("Entity")
                .setSalience(.5f)
                .setSentiment(buildGoogleSentiment())
                .setType(Entity.Type.EVENT)
                .addMentions(buildGoogleEntityMention())
                .putMetadata("source", "source")
                .build();
    }

    public static com.google.cloud.language.v1.Entity buildGoogleEntityEmpty(){
        return com.google.cloud.language.v1.Entity.newBuilder()
                .setName("Entity")
                .setSalience(.5f)
                .setType(Entity.Type.EVENT)
                .build();
    }
}
