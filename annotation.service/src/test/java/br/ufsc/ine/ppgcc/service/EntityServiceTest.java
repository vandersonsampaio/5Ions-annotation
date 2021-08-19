package br.ufsc.ine.ppgcc.service;

import br.ufsc.ine.ppgcc.helper.ServiceHelper;
import br.ufsc.ine.ppgcc.mapper.EntityMapper;
import br.ufsc.ine.ppgcc.mapper.EntityMapperImpl;
import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.Entity;
import br.ufsc.ine.ppgcc.model.response.EntityCoreResponse;
import br.ufsc.ine.ppgcc.service.implementation.EntityService;
import com.google.cloud.language.v1.AnalyzeEntitiesResponse;
import com.google.cloud.language.v1.LanguageServiceClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LanguageServiceClient.class)
public class EntityServiceTest {

    private EntityService service;

    @Before
    public void setUp(){
        EntityMapper mapper = new EntityMapperImpl();
        service = new EntityService(mapper);
    }

    @Test
    public void entityAnnotationTest_DocumentEmpty() throws Exception {
        List<Document> documentEmpty = Collections.emptyList();

        PowerMockito.mockStatic(LanguageServiceClient.class);
        PowerMockito.when(LanguageServiceClient.create()).thenReturn(null);

        List<Document> actualEmpty = service.entityAnnotation(documentEmpty);

        Assert.assertTrue(actualEmpty.isEmpty());
    }

    @Test
    public void entityAnnotationTest_HasDocument() throws Exception {
        List<Document> documents = Collections.singletonList(ServiceHelper.buildDocument());

        com.google.cloud.language.v1.Document request = ServiceHelper.buildGoogleDocument();
        AnalyzeEntitiesResponse response = ServiceHelper.buildAnalyzeEntitiesResponse();

        LanguageServiceClient serviceClientMock = PowerMockito.mock(LanguageServiceClient.class);
        PowerMockito.mockStatic(LanguageServiceClient.class);
        PowerMockito.when(LanguageServiceClient.create()).thenReturn(serviceClientMock);
        PowerMockito.when(serviceClientMock.analyzeEntities(request)).thenReturn(response);

        List<Document> actual = service.entityAnnotation(documents);

        Assert.assertNotNull(actual.get(0).getEntities());
        Assert.assertTrue(actual.get(0).getEntities().size() > 0);
        Assert.assertEquals("source_url", actual.get(0).getEntities().get(0).getSource());
        Assert.assertTrue(actual.get(0).getEntities().get(0).getMentions().size() > 0);
    }

    @Test
    public void toDTOTest_DocumentEmpty() {
        List<Document> documents = Collections.emptyList();

        List<EntityCoreResponse> actual = service.toDTO(documents);

        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void toDTOTest_HasDocument_EntitiesEmpty() {
        List<Document> documents = Collections.singletonList(ServiceHelper.buildDocument());

        List<EntityCoreResponse> actual = service.toDTO(documents);

        Assert.assertTrue(actual.isEmpty());
    }

    @Test
    public void toDTOTest_HasDocument_HasEntities() {
        Document document1 = ServiceHelper.buildDocument();
        Entity entity1 = ServiceHelper.buildEntity();
        Entity entity2 = ServiceHelper.buildEntity();
        Entity entity3 = ServiceHelper.buildEntity();
        entity2.setSource("source_url2");
        entity3.setName("JOAO");

        document1.setEntities(Arrays.asList(entity1, entity2, entity3));

        List<Document> documents = Arrays.asList(document1, document1);

        List<EntityCoreResponse> actual = service.toDTO(documents);

        Assert.assertFalse(actual.isEmpty());
    }
}
