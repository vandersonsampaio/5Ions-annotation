package br.ufsc.ine.ppgcc.controller;

import br.ufsc.ine.ppgcc.helper.ControllerHelper;
import br.ufsc.ine.ppgcc.mapper.DocumentMapper;
import br.ufsc.ine.ppgcc.mapper.DocumentMapperImpl;
import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.request.DocumentRequest;
import br.ufsc.ine.ppgcc.model.response.DocumentSentimentResponse;
import br.ufsc.ine.ppgcc.service.interfaces.ISentimentService;
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

public class SentimentControllerTest {

    private final double delta = .0001;
    private SentimentController controller;
    private ISentimentService service;

    @Before
    public void Setup(){
        service = mock(ISentimentService.class);
        DocumentMapper mapper = new DocumentMapperImpl();

        controller = new SentimentController(service, mapper);
    }

    @Test
    public void sentimentTest_Success() throws Exception {
        List<DocumentRequest> input = ControllerHelper.buildListDocumentRequest();
        List<Document> documentAnnotated = ControllerHelper.buildListDocumentWithSentenceAndSentiment();

        when(service.sentimentAnnotation(any())).thenReturn(documentAnnotated);

        ResponseEntity<List<DocumentSentimentResponse>> response = controller.sentiment(input);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(documentAnnotated.get(0).getId(), Objects.requireNonNull(response.getBody()).get(0).getId());

        Assert.assertEquals(documentAnnotated.get(0).getSentences().size(), Objects.requireNonNull(response.getBody()).get(0).getSentences().size());
        Assert.assertEquals(documentAnnotated.get(0).getSentences().get(0).getOrder(), Objects.requireNonNull(response.getBody()).get(0).getSentences().get(0).getOrder());
        Assert.assertEquals(documentAnnotated.get(0).getSentences().get(0).getOffset(), Objects.requireNonNull(response.getBody()).get(0).getSentences().get(0).getOffset());

        Assert.assertEquals(documentAnnotated.get(0).getSentences().get(0).getSentiment().getScore(), Objects.requireNonNull(response.getBody()).get(0).getSentences().get(0).getSentiment().getScore(), delta);
        Assert.assertEquals(documentAnnotated.get(0).getSentences().get(0).getSentiment().getMagnitude(), Objects.requireNonNull(response.getBody()).get(0).getSentences().get(0).getSentiment().getMagnitude(), delta);
    }

    @Test
    public void sentimentEntityTest_Success() throws Exception {
        List<DocumentRequest> input = ControllerHelper.buildListDocumentRequest();
        List<Document> documentAnnotated = ControllerHelper.buildListDocumentWithEntities();

        when(service.sentimentEntityAnnotation(any())).thenReturn(documentAnnotated);

        ResponseEntity<List<Document>> response = controller.sentimentEntity(input);

        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(documentAnnotated.get(0).getId(), Objects.requireNonNull(response.getBody()).get(0).getId());

        Assert.assertEquals(documentAnnotated.get(0).getEntities().size(), Objects.requireNonNull(response.getBody()).get(0).getEntities().size());
        Assert.assertEquals(documentAnnotated.get(0).getEntities().get(0).getName(), Objects.requireNonNull(response.getBody()).get(0).getEntities().get(0).getName());
        Assert.assertEquals(documentAnnotated.get(0).getEntities().get(0).getType(), Objects.requireNonNull(response.getBody()).get(0).getEntities().get(0).getType());

        Assert.assertEquals(documentAnnotated.get(0).getEntities().get(0).getSentiment().getScore(), Objects.requireNonNull(response.getBody()).get(0).getEntities().get(0).getSentiment().getScore(), delta);
        Assert.assertEquals(documentAnnotated.get(0).getEntities().get(0).getSentiment().getMagnitude(), Objects.requireNonNull(response.getBody()).get(0).getEntities().get(0).getSentiment().getMagnitude(), delta);
    }
}
