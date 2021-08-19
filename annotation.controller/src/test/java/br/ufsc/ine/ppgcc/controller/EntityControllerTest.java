package br.ufsc.ine.ppgcc.controller;

import br.ufsc.ine.ppgcc.helper.ControllerHelper;
import br.ufsc.ine.ppgcc.mapper.DocumentMapper;
import br.ufsc.ine.ppgcc.mapper.DocumentMapperImpl;
import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.request.DocumentRequest;
import br.ufsc.ine.ppgcc.model.response.EntityAnnotatedResponse;
import br.ufsc.ine.ppgcc.model.response.EntityCoreResponse;
import br.ufsc.ine.ppgcc.service.interfaces.IEntityService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EntityControllerTest {

    private EntityController controller;
    private IEntityService service;

    @Before
    public void Setup(){
        service = mock(IEntityService.class);
        DocumentMapper mapper = new DocumentMapperImpl();

        controller = new EntityController(service, mapper);
    }

    @Test
    public void entityTest_Success() throws Exception {
        List<DocumentRequest> input = ControllerHelper.buildListDocumentRequest();
        List<Document> documentAnnotated = ControllerHelper.buildListDocument();
        List<EntityCoreResponse> entityCore = ControllerHelper.buildListEntityCoreResponse();

        when(service.entityAnnotation(any())).thenReturn(documentAnnotated);
        when(service.toDTO(any())).thenReturn(entityCore);

        ResponseEntity<EntityAnnotatedResponse> response = controller.entity(input);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(documentAnnotated, Objects.requireNonNull(response.getBody()).getDocuments());
        Assert.assertEquals(entityCore, response.getBody().getEntities());
    }
}
