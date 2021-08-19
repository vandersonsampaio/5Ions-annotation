package br.ufsc.ine.ppgcc.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    private String id;
    private String content;
    private String language;

    private List<Entity> entities;
    private List<Sentence> sentences;
    private Sentiment sentiment;
}
