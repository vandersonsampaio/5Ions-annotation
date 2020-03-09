package br.ufsc.ine.ppgcc.service;

import br.ufsc.ine.ppgcc.model.Entity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SentimentAnalysisService {

    public Map<Long, Map<Long, Double>> sentimentAnnotation(Map<Long, String> mapDocuments){
        return null;
    }

    public Map<Long, List<Entity>> sentimentEntityAnnotation(Map<Long, String> mapDocuments){
        return null;
    }

    private String identifyLanguage(String document){
        return "";
    }
}
