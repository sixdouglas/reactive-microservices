package org.sixdouglas.reactive.microservices.lesson.diplome;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@AllArgsConstructor
@Data
class Diplome {
    @Id
    private Long id;
    private String name;
    private Long domaineId;
    private Integer ordre;
}
