package br.ufsc.ine.ppgcc.model.response;

import br.ufsc.ine.ppgcc.model.Mention;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentMentionResponse {

    private String id;
    private String content;
    private String language;

    private List<Mention> mentions;
}
