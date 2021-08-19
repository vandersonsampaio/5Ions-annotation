package br.ufsc.ine.ppgcc.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mention {

    private String surfaceName;
    private long offset;
    private String type;
    private Sentiment sentiment;
}
