package br.ufsc.ine.ppgcc.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sentiment {

    private double score;
    private double magnitude;
}
