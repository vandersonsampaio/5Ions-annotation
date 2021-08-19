package br.ufsc.ine.ppgcc.service.interfaces;

import br.ufsc.ine.ppgcc.model.Document;
import br.ufsc.ine.ppgcc.model.response.EntityCoreResponse;

import java.util.List;

public interface IEntityService {

    List<Document> entityAnnotation(List<Document> documents) throws Exception;

    List<EntityCoreResponse> toDTO(List<Document> documents);
}
