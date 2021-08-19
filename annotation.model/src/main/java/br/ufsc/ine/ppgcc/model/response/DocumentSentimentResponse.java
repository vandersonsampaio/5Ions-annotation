package br.ufsc.ine.ppgcc.model.response;

import br.ufsc.ine.ppgcc.model.Sentence;
import br.ufsc.ine.ppgcc.model.Sentiment;
import lombok.*;

import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSentimentResponse {

    private String id;
    private Sentiment sentiment;
    private List<Sentence> sentences;
}
