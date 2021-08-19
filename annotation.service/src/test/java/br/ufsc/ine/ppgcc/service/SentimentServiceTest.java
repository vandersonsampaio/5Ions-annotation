package br.ufsc.ine.ppgcc.service;

import br.ufsc.ine.ppgcc.helper.ServiceHelper;
import br.ufsc.ine.ppgcc.mapper.SentimentMapper;
import br.ufsc.ine.ppgcc.mapper.SentimentMapperImpl;
import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.service.implementation.SentimentService;
import com.google.cloud.language.v1.AnalyzeEntitySentimentResponse;
import com.google.cloud.language.v1.AnalyzeSentimentResponse;
import com.google.cloud.language.v1.LanguageServiceClient;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LanguageServiceClient.class)
public class SentimentServiceTest {

    private SentimentService service;
    private LanguageServiceClient serviceClientMock;

    @Before
    public void setUp() throws IOException {
        SentimentMapper mapper = new SentimentMapperImpl();
        service = new SentimentService(mapper);

        serviceClientMock = PowerMockito.mock(LanguageServiceClient.class);
        PowerMockito.mockStatic(LanguageServiceClient.class);
        PowerMockito.when(LanguageServiceClient.create()).thenReturn(serviceClientMock);
    }

    @Test
    public void sentimentAnnotationTest() throws Exception {
        List<Document> documentEmpty = Collections.emptyList();
        List<Document> document = Collections.singletonList(ServiceHelper.buildDocument());

        com.google.cloud.language.v1.Document request = ServiceHelper.buildGoogleDocument();
        AnalyzeSentimentResponse response = ServiceHelper.buildAnalyzeSentimentResponse();

        PowerMockito.when(serviceClientMock.analyzeSentiment(request)).thenReturn(response);

        List<Document> actualEmpty = service.sentimentAnnotation(documentEmpty);
        List<Document> actual = service.sentimentAnnotation(document);

        Assert.assertTrue(actualEmpty.isEmpty());

        Assert.assertNotNull(actual.get(0).getSentiment());
        Assert.assertEquals(.3, actual.get(0).getSentiment().getScore(), .001);
        Assert.assertEquals(.5, actual.get(0).getSentiment().getMagnitude(), .001);

        Assert.assertNotNull(actual.get(0).getSentences());
        Assert.assertTrue(actual.get(0).getSentences().size() > 0);

        Assert.assertEquals(1, actual.get(0).getSentences().get(0).getOrder());
        Assert.assertEquals(0, actual.get(0).getSentences().get(0).getOffset());
        Assert.assertEquals(.8, actual.get(0).getSentences().get(0).getSentiment().getScore(), .001);
        Assert.assertEquals(.3, actual.get(0).getSentences().get(0).getSentiment().getMagnitude(), .001);

        Assert.assertEquals(2, actual.get(0).getSentences().get(1).getOrder());
        Assert.assertEquals(255, actual.get(0).getSentences().get(1).getOffset());
        Assert.assertEquals(.2, actual.get(0).getSentences().get(1).getSentiment().getScore(), .001);
        Assert.assertEquals(.2, actual.get(0).getSentences().get(1).getSentiment().getMagnitude(), .001);
    }

    @Test
    public void sentimentEntityAnnotationTest() throws Exception {
        List<Document> documentEmpty = Collections.emptyList();
        List<Document> documents = Collections.singletonList(ServiceHelper.buildDocumentNotEnglish());
        List<Document> documentsEnglish = Collections.singletonList(ServiceHelper.buildDocument());

        com.google.cloud.language.v1.Document request = ServiceHelper.buildGoogleDocument();
        AnalyzeEntitySentimentResponse response = ServiceHelper.buildAnalyzeEntitySentimentResponse();

        PowerMockito.when(serviceClientMock.analyzeEntitySentiment(request)).thenReturn(response);

        List<Document> actualEmpty = service.sentimentEntityAnnotation(documentEmpty);
        List<Document> actual = service.sentimentEntityAnnotation(documents);
        List<Document> actualEnglish = service.sentimentEntityAnnotation(documentsEnglish);

        Assert.assertTrue(actualEmpty.isEmpty());

        Assert.assertNotNull(actualEnglish.get(0).getEntities());
        Assert.assertTrue(actualEnglish.get(0).getEntities().size() > 0);
        Assert.assertEquals("source_url", actualEnglish.get(0).getEntities().get(0).getSource());
        Assert.assertNotNull(actualEnglish.get(0).getEntities().get(0).getSentiment());
        Assert.assertEquals(.3, actualEnglish.get(0).getEntities().get(0).getSentiment().getScore(), .001);
        Assert.assertEquals(.2, actualEnglish.get(0).getEntities().get(0).getSentiment().getMagnitude(), .001);
        Assert.assertTrue(actualEnglish.get(0).getEntities().get(0).getMentions().size() > 0);

        Assert.assertNotNull(actual.get(0).getEntities().get(0).getSentiment());
        Assert.assertEquals(.333, actual.get(0).getEntities().get(0).getSentiment().getScore(), .001);
        Assert.assertNotNull(actual.get(0).getEntities().get(0).getMentions().get(0).getSentiment());
        Assert.assertEquals(.4, actual.get(0).getEntities().get(0).getMentions().get(0).getSentiment().getScore(), .001);
        Assert.assertEquals(.2, actual.get(0).getEntities().get(0).getMentions().get(1).getSentiment().getScore(), .001);
    }
}
