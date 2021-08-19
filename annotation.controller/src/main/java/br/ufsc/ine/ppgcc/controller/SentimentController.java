package br.ufsc.ine.ppgcc.controller;

import br.ufsc.ine.ppgcc.mapper.DocumentMapper;
import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.request.DocumentRequest;
import br.ufsc.ine.ppgcc.model.response.DocumentSentimentResponse;
import br.ufsc.ine.ppgcc.service.interfaces.ISentimentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/sentiment-annotation", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
public class SentimentController {

    private final ISentimentService service;
    private final DocumentMapper mapper;

    public SentimentController(ISentimentService service, DocumentMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(value = "document")
    public ResponseEntity<List<DocumentSentimentResponse>> sentiment(@RequestBody List<DocumentRequest> documentsRequest) throws Exception {
        List<Document> documents = mapper.toDocumentList(documentsRequest);

        List<DocumentSentimentResponse> response = mapper.toDTOList(service.sentimentAnnotation(documents));
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "entities")
    public ResponseEntity<List<Document>> sentimentEntity(@RequestBody List<DocumentRequest> documentsRequest) throws Exception {

        List<Document> documents = mapper.toDocumentList(documentsRequest);
        return ResponseEntity.ok(service.sentimentEntityAnnotation(documents));
    }
}
