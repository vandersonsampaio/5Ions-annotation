package br.ufsc.ine.ppgcc.model;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sentence {

    private int order;
    private long offset;
    private Sentiment sentiment;
}
