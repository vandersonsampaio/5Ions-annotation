package br.ufsc.ine.ppgcc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sentiment {

    private double value;
    private double magnitude;
}
