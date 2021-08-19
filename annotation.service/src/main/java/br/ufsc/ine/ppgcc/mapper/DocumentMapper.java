package br.ufsc.ine.ppgcc.mapper;

import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.request.DocumentRequest;
import br.ufsc.ine.ppgcc.model.response.DocumentSentimentResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mappings({
            @Mapping(target = "entities", ignore = true),
            @Mapping(target = "sentiment", ignore = true),
            @Mapping(target = "sentences", ignore = true),
    })
    Document toModel(DocumentRequest dto);

    List<Document> toDocumentList(List<DocumentRequest> dto);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "sentiment", source = "sentiment"),
            @Mapping(target = "sentences", source = "sentences"),
    })
    DocumentSentimentResponse toDTO(Document model);
    List<DocumentSentimentResponse> toDTOList(List<Document> model);
}
