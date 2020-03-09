package br.ufsc.ine.ppgcc.client;

import br.ufsc.ine.ppgcc.model.Entity;
import org.springframework.http.MediaType;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.Map;

@FeignClient
public interface AnnotationClient {

    @PostMapping(value = "annotations/entities", produces = MediaType.APPLICATION_JSON_VALUE)
    Map<Long, List<Entity>> entity(Map<Long, String> mapDocuments);

    @PostMapping(value = "annotations/sentiments", produces = MediaType.APPLICATION_JSON_VALUE)
    Map<Long, Map<Long, Double>> sentiment(Map<Long, String> mapDocuments);

    @PostMapping(value = "annotations/sentimentEntities", produces = MediaType.APPLICATION_JSON_VALUE)
    Map<Long, List<Entity>> sentimentEntity(Map<Long, String> mapDocuments);
}
