package br.ufsc.ine.ppgcc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SentimentSummary {

    private double valuePositive;
    private double valueNegative;
}
