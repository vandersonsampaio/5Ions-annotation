package br.ufsc.ine.ppgcc.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Entity {

    private String source;
    private String name;
    private String type;
    private double salience;
    private Sentiment sentiment;
    private List<Mention> mentions;

}
