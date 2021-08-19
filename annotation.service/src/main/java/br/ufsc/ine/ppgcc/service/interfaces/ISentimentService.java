package br.ufsc.ine.ppgcc.service.interfaces;

import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.Entity;

import java.util.List;
import java.util.Map;

public interface ISentimentService {

    List<Document> sentimentAnnotation(List<Document> documents) throws Exception;

    List<Document> sentimentEntityAnnotation(List<Document> documents) throws Exception;
}
