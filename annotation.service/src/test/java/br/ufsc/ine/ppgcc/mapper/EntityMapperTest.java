package br.ufsc.ine.ppgcc.mapper;

import br.ufsc.ine.ppgcc.helper.MapperHelper;
import br.ufsc.ine.ppgcc.model.Entity;
import br.ufsc.ine.ppgcc.model.Mention;
import com.google.cloud.language.v1.EntityMention;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityMapperTest {

    private EntityMapper mapper;

    @Before
    public void setUp(){
        mapper = new EntityMapperImpl();
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
        Assert.assertEquals("TYPE_UNKNOWN", actualEmpty.getType());
        Assert.assertNull(actualEmpty.getSurfaceName());

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
        Assert.assertEquals(dtoEmpty.getMentionsList().size(), actualEmpty.getMentions().size());

        Assert.assertEquals(dto.getName(), actual.getName());
        Assert.assertEquals(dto.getSalience(), actual.getSalience(), .001);
        Assert.assertEquals(dto.getType().toString(), actual.getType());
        Assert.assertEquals(dto.getMetadataOrDefault("source", null), actual.getSource());
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
}
