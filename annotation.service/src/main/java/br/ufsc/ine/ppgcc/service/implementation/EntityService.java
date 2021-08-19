package br.ufsc.ine.ppgcc.service.implementation;

import br.ufsc.ine.ppgcc.mapper.EntityMapper;
import br.ufsc.ine.ppgcc.model.Entity;
import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.response.DocumentMentionResponse;
import br.ufsc.ine.ppgcc.model.response.EntityCoreResponse;
import br.ufsc.ine.ppgcc.service.interfaces.IEntityService;
import com.google.cloud.language.v1.LanguageServiceClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EntityService implements IEntityService {

    private final EntityMapper mapper;

    public EntityService(EntityMapper mapper) {
        this.mapper = mapper;
    }

    public List<Document> entityAnnotation(List<Document> documents) throws Exception {
        LanguageServiceClient language = LanguageServiceClient.create();
        for (Document document : documents) {
            com.google.cloud.language.v1.Document doc = com.google.cloud.language.v1.Document
                    .newBuilder().setContent(document.getContent())
                    .setType(com.google.cloud.language.v1.Document.Type.PLAIN_TEXT)
                    .build();

            List<Entity> entities = mapper.toEntityList(language.analyzeEntities(doc).getEntitiesList());
            document.setEntities(entities);
        }

        return documents;
    }

    public List<EntityCoreResponse> toDTO(List<Document> documents) {
        List<EntityCoreResponse> dto = new ArrayList<>();

        for(Document doc : documents) {
            for(Entity entity : doc.getEntities()) {
                EntityCoreResponse entityDTO = dto.stream().filter(d -> d.getName().equals(entity.getName()) && d.getSource().equals(entity.getSource()))
                        .findFirst().orElse(EntityCoreResponse.builder()
                                .name(entity.getName())
                                .salience(entity.getSalience())
                                .source(entity.getSource())
                                .type(entity.getType())
                                .documentMentions(new ArrayList<>())
                                .build());

                if(entityDTO.getDocumentMentions().size() == 0){
                    dto.add(entityDTO);
                }

                DocumentMentionResponse documentDTO = DocumentMentionResponse.builder()
                        .id(doc.getId())
                        .content(doc.getContent())
                        .language(doc.getLanguage())
                        .mentions(entity.getMentions())
                        .build();

                entityDTO.getDocumentMentions().add(documentDTO);
            }
        }

        return dto;
    }
}
