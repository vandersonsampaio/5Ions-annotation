package br.ufsc.ine.ppgcc.mapper;

import br.ufsc.ine.ppgcc.helper.MapperHelper;
import br.ufsc.ine.ppgcc.model.Entity;
import br.ufsc.ine.ppgcc.model.Mention;
import br.ufsc.ine.ppgcc.model.Sentence;
import br.ufsc.ine.ppgcc.model.Sentiment;
import com.google.cloud.language.v1.EntityMention;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SentimentMapperTest {

    private SentimentMapper mapper;

    @Before
    public void setUp(){
        mapper = new SentimentMapperImpl();
    }

    @Test
    public void toModelSentenceTest(){
        com.google.cloud.language.v1.Sentence dto = MapperHelper.buildGoogleSentence();

        Sentence actualNull = mapper.toModel((com.google.cloud.language.v1.Sentence) null);
        Sentence actual = mapper.toModel(dto);

        Assert.assertNull(actualNull);

        assertEquals(dto.getText().getBeginOffset(), actual.getOffset());
        assertEquals(dto.getSentiment().getMagnitude(), actual.getSentiment().getMagnitude(), .0001);
        assertEquals(dto.getSentiment().getMagnitude(), actual.getSentiment().getMagnitude(), .0001);
    }

    @Test
    public void toModelListSentenceTest(){
        List<com.google.cloud.language.v1.Sentence> listEmpty = new ArrayList<>();
        List<com.google.cloud.language.v1.Sentence> list = Collections.singletonList(MapperHelper.buildGoogleSentence());

        List<Sentence> actualNull = mapper.toSentenceList(null);
        List<Sentence> actualEmpty = mapper.toSentenceList(listEmpty);
        List<Sentence> actual = mapper.toSentenceList(list);

        Assert.assertNull(actualNull);
        Assert.assertEquals(Collections.emptyList(), actualEmpty);

        Assert.assertNotNull(actual);
        Assert.assertEquals(list.size(), actual.size());
        Assert.assertEquals(list.stream().findFirst().get().getText().getBeginOffset(), actual.get(0).getOffset());
    }

    @Test
    public void toModelEntityMentionTest(){
        EntityMention dto = MapperHelper.buildGoogleEntityMention();
        EntityMention dtoEmpty = MapperHelper.buildGoogleEntityMentionEmpty();

        Mention actualNull = mapper.toModel((EntityMention) null);
        Mention actualEmpty = mapper.toModel(dtoEmpty);
        Mention actual = mapper.toModel(dto);

        Assert.assertNull(actualNull);

        Assert.assertNotNull(actualEmpty);
        Assert.assertEquals(0, actualEmpty.getOffset());
        Assert.assertNull(actualEmpty.getSentiment());
        Assert.assertEquals("TYPE_UNKNOWN", actualEmpty.getType());
        Assert.assertNull(actualEmpty.getSurfaceName());

        Assert.assertNotNull(actual.getSentiment());
        Assert.assertEquals(dto.getSentiment().getScore(), actual.getSentiment().getScore(), .001);
        Assert.assertEquals(dto.getSentiment().getMagnitude(), actual.getSentiment().getMagnitude(), .001);
        Assert.assertEquals(dto.getType().toString(), actual.getType());
        Assert.assertEquals(dto.getText().getBeginOffset(), actual.getOffset());
        Assert.assertEquals(dto.getText().getContent(), actual.getSurfaceName());
    }

    @Test
    public void toModelListEntityMentionTest(){
        List<EntityMention> listEmpty = new ArrayList<>();
        List<EntityMention> list = Collections.singletonList(MapperHelper.buildGoogleEntityMention());

        List<Mention> actualNull = mapper.toMentionList(null);
        List<Mention> actualEmpty = mapper.toMentionList(listEmpty);
        List<Mention> actual = mapper.toMentionList(list);

        Assert.assertNull(actualNull);
        Assert.assertEquals(Collections.emptyList(), actualEmpty);

        Assert.assertNotNull(actual);
        Assert.assertEquals(list.size(), actual.size());
        Assert.assertEquals(list.stream().findFirst().get().getText().getBeginOffset(), actual.get(0).getOffset());
    }

    @Test
    public void toModelEntityTest(){
        com.google.cloud.language.v1.Entity dto = MapperHelper.buildGoogleEntity();
        com.google.cloud.language.v1.Entity dtoEmpty = MapperHelper.buildGoogleEntityEmpty();

        Entity actualNull = mapper.toModel((com.google.cloud.language.v1.Entity) null);
        Entity actualEmpty = mapper.toModel(dtoEmpty);
        Entity actual = mapper.toModel(dto);

        Assert.assertNull(actualNull);

        Assert.assertEquals(dtoEmpty.getMetadataOrDefault("source", null), actualEmpty.getSource());
        Assert.assertNull(actualEmpty.getSentiment());
        Assert.assertEquals(dtoEmpty.getMentionsList().size(), actualEmpty.getMentions().size());

        Assert.assertEquals(dto.getName(), actual.getName());
        Assert.assertEquals(dto.getSalience(), actual.getSalience(), .001);
        Assert.assertEquals(dto.getType().toString(), actual.getType());
        Assert.assertEquals(dto.getMetadataOrDefault("source", null), actual.getSource());
        Assert.assertEquals(dto.getSentiment().getScore(), actual.getSentiment().getScore(), .001);
        Assert.assertEquals(dto.getSentiment().getMagnitude(), actual.getSentiment().getMagnitude(), .001);
        Assert.assertEquals(dto.getMentionsList().size(), actual.getMentions().size());
    }

    @Test
    public void toModelListEntityTest(){
        List<com.google.cloud.language.v1.Entity> listEmpty = new ArrayList<>();
        List<com.google.cloud.language.v1.Entity> list = Collections.singletonList(MapperHelper.buildGoogleEntity());

        List<Entity> actualNull = mapper.toEntityList(null);
        List<Entity> actualEmpty = mapper.toEntityList(listEmpty);
        List<Entity> actual = mapper.toEntityList(list);

        Assert.assertNull(actualNull);
        Assert.assertEquals(Collections.emptyList(), actualEmpty);

        Assert.assertNotNull(actual);
        Assert.assertEquals(list.size(), actual.size());
        Assert.assertEquals(list.stream().findFirst().get().getName(), actual.get(0).getName());
    }

    @Test
    public void toModelSentimentTest(){
        com.google.cloud.language.v1.Sentiment dto = MapperHelper.buildGoogleSentiment();

        Sentiment actualNull = mapper.toModel((com.google.cloud.language.v1.Sentiment) null);
        Sentiment actual = mapper.toModel(dto);

        Assert.assertNull(actualNull);

        Assert.assertEquals(dto.getMagnitude(), actual.getMagnitude(), .001);
        Assert.assertEquals(dto.getScore(), actual.getScore(), .001);
    }
}
