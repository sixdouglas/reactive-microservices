package org.sixdouglas.reactive.microservices.lesson;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Semestre {
    @Id
    private Long id;
    private String name;
    private Long anneeId;
    private Integer ordre;
}
