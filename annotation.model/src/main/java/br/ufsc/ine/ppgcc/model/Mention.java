package br.ufsc.ine.ppgcc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mention {

    private String surfaceName;
    private Long sentenceNumber;
    private Sentiment sentiment;
}
