package br.ufsc.ine.ppgcc.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Entity {

    private Long id;
    private String source;
    private String name;
    private String type;
    private SentimentSummary sentimentSummary;
    private List<Mention> mentions;

}
