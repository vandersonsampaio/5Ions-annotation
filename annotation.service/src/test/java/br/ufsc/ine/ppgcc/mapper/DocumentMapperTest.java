package br.ufsc.ine.ppgcc.mapper;

import br.ufsc.ine.ppgcc.helper.MapperHelper;
import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.request.DocumentRequest;
import br.ufsc.ine.ppgcc.model.response.DocumentSentimentResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class DocumentMapperTest {

    private DocumentMapper mapper;

    @Before
    public void setUp(){
        mapper = new DocumentMapperImpl();
    }

    @Test
    public void toModelTest(){
        DocumentRequest dto = MapperHelper.buildDocumentRequest();

        Document actual = mapper.toModel(dto);
        Document actualNull = mapper.toModel(null);

        Assert.assertNull(actualNull);
        assertion(dto, actual);
    }

    @Test
    public void toModelList(){
        List<DocumentRequest> dto = Collections.singletonList(MapperHelper.buildDocumentRequest());

        List<Document> actual = mapper.toDocumentList(dto);
        List<Document> actualNull = mapper.toDocumentList(null);

        Assert.assertNull(actualNull);
        Assert.assertEquals(dto.size(), actual.size());
        Assert.assertTrue(actual.size() > 0);

        assertion(dto.get(0), actual.get(0));
    }

    private void assertion(DocumentRequest excepted, Document actual){
        Assert.assertEquals(excepted.getId(), actual.getId());
        Assert.assertEquals(excepted.getContent(), actual.getContent());
        Assert.assertEquals(excepted.getLanguage(), actual.getLanguage());
    }

    @Test
    public void toDTOTest(){
        Document model = MapperHelper.buildDocument();

        DocumentSentimentResponse actual = mapper.toDTO(model);
        DocumentSentimentResponse actualNull = mapper.toDTO(null);

        Assert.assertNull(actualNull);
        assertion(model, actual);
    }

    @Test
    public void toDTOList(){
        List<Document> modelList = Collections.singletonList(MapperHelper.buildDocument());
        List<Document> modelListWithoutSentences = Collections.singletonList(MapperHelper.buildDocumentWithoutSentence());

        List<DocumentSentimentResponse> actual = mapper.toDTOList(modelList);
        List<DocumentSentimentResponse> actualNull = mapper.toDTOList(null);
        List<DocumentSentimentResponse> actualSentencesNull = mapper.toDTOList(modelListWithoutSentences);

        Assert.assertNull(actualNull);

        Assert.assertEquals(modelList.size(), actual.size());
        Assert.assertEquals(modelListWithoutSentences.size(), actualSentencesNull.size());

        Assert.assertTrue(actual.size() > 0);
        Assert.assertTrue(actualSentencesNull.size() > 0);

        assertion(modelList.get(0), actual.get(0));
        assertion(modelListWithoutSentences.get(0), actualSentencesNull.get(0));
    }

    private void assertion(Document actual, DocumentSentimentResponse excepted){
        Assert.assertEquals(excepted.getId(), actual.getId());
        Assert.assertEquals(excepted.getSentences(), actual.getSentences());
        Assert.assertEquals(excepted.getSentiment(), actual.getSentiment());
    }
}
