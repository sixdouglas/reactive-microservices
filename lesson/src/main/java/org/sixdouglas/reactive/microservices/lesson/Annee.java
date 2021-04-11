package org.sixdouglas.reactive.microservices.lesson;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Annee {
    @Id
    private Long id;
    private String name;
    private Long diplomeId;
    private Integer ordre;
}
