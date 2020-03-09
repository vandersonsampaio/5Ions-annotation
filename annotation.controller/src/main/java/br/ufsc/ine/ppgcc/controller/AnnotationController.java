package br.ufsc.ine.ppgcc.controller;

import br.ufsc.ine.ppgcc.model.Entity;
import br.ufsc.ine.ppgcc.service.EntityAnnotationService;
import br.ufsc.ine.ppgcc.service.SentimentAnalysisService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/annotations")
public class AnnotationController {

    private final EntityAnnotationService entityAnnotationService;
    private final SentimentAnalysisService sentimentAnalysisService;

    public AnnotationController(EntityAnnotationService entityAnnotationService, SentimentAnalysisService sentimentAnalysisService){
        this.entityAnnotationService = entityAnnotationService;
        this.sentimentAnalysisService = sentimentAnalysisService;
    }

    @PostMapping(value = "entities", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Long, List<Entity>> entity(Map<Long, String> mapDocuments){
        return entityAnnotationService.entityAnnotation(mapDocuments);
    }

    @PostMapping(value = "sentiments", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Long, Map<Long, Double>> sentiment(Map<Long, String> mapDocuments){
        return sentimentAnalysisService.sentimentAnnotation(mapDocuments);
    }

    @PostMapping(value = "sentimentEntities", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<Long, List<Entity>> sentimentEntity(Map<Long, String> mapDocuments){
        return sentimentAnalysisService.sentimentEntityAnnotation(mapDocuments);
    }
}

