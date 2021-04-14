package org.sixdouglas.reactive.microservices.lesson.semestre;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Data
class Semestre {
    @Id
    private Long id;
    private String name;
    private Long anneeId;
    private Integer ordre;
}
