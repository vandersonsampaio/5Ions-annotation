package br.ufsc.ine.ppgcc.model.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityCoreResponse {

    private String source;
    private String name;
    private String type;
    private double salience;

    private List<DocumentMentionResponse> documentMentions;
}
