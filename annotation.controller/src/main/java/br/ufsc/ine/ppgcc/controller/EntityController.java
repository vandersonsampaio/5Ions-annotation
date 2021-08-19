package br.ufsc.ine.ppgcc.controller;

import br.ufsc.ine.ppgcc.mapper.DocumentMapper;
import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.request.DocumentRequest;
import br.ufsc.ine.ppgcc.model.response.EntityAnnotatedResponse;
import br.ufsc.ine.ppgcc.model.response.EntityCoreResponse;
import br.ufsc.ine.ppgcc.service.interfaces.IEntityService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/entity-annotation", produces = MediaType.APPLICATION_JSON_VALUE)
public class EntityController {

    private final IEntityService service;
    private final DocumentMapper mapper;

    public EntityController(IEntityService service, DocumentMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(value = "mentions")
    public ResponseEntity<EntityAnnotatedResponse> entity(@RequestBody List<DocumentRequest> request) throws Exception {
        List<Document> documents = mapper.toDocumentList(request);

        List<Document> annotated = service.entityAnnotation(documents);
        List<EntityCoreResponse> entitiesAnnotated = service.toDTO(annotated);
        EntityAnnotatedResponse response = EntityAnnotatedResponse.builder()
                .documents(annotated)
                .entities(entitiesAnnotated)
                .build();

        return ResponseEntity.ok(response);
    }
}

