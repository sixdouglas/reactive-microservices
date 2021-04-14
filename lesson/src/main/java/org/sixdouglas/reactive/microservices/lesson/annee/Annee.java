package org.sixdouglas.reactive.microservices.lesson.annee;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
class Annee {
    @Id
    private Long id;
    private String name;
    private Long diplomeId;
    private Integer ordre;
}
