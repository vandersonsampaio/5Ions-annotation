package br.ufsc.ine.ppgcc.model.response;

import br.ufsc.ine.ppgcc.model.Document;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityAnnotatedResponse {

    private List<Document> documents;
    private List<EntityCoreResponse> entities;
}
