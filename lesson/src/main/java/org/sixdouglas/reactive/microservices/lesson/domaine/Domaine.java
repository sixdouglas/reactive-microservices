package org.sixdouglas.reactive.microservices.lesson.domaine;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Data
class Domaine {
    @Id
    private Long id;
    private String name;
    private Integer ordre;
}
