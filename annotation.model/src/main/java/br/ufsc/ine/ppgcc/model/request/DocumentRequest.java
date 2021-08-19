package br.ufsc.ine.ppgcc.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentRequest {

    private String id;
    private String content;
    private String language;
}
