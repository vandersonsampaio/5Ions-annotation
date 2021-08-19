package br.ufsc.ine.ppgcc.mapper;

import br.ufsc.ine.ppgcc.model.Entity;
import br.ufsc.ine.ppgcc.model.Mention;
import br.ufsc.ine.ppgcc.model.Sentence;
import br.ufsc.ine.ppgcc.model.Sentiment;
import com.google.cloud.language.v1.EntityMention;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SentimentMapper {

    Sentiment toModel(com.google.cloud.language.v1.Sentiment dto);

    @Mappings({
            @Mapping(target = "order", ignore = true),
            @Mapping(target = "offset", source = "text.beginOffset"),
            @Mapping(target = "sentiment", source = "sentiment"),
    })
    Sentence toModel(com.google.cloud.language.v1.Sentence dto);
    List<Sentence> toSentenceList(List<com.google.cloud.language.v1.Sentence> dto);

    @Mappings({
            @Mapping(target = "surfaceName", source = "text.content"),
            @Mapping(target = "offset", source = "text.beginOffset"),
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "sentiment", source = "sentiment"),
    })
    Mention toModel(EntityMention dto);
    List<Mention> toMentionList(List<EntityMention> dto);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "type", source = "type"),
            @Mapping(target = "salience", source = "salience"),
            @Mapping(target = "source", expression = "java(dto.getMetadataOrDefault(\"source\", null))"),
            @Mapping(target = "sentiment", source = "sentiment"),
            @Mapping(target = "mentions", source = "mentionsList"),
    })
    Entity toModel(com.google.cloud.language.v1.Entity dto);
    List<Entity> toEntityList(List<com.google.cloud.language.v1.Entity> dto);
}
