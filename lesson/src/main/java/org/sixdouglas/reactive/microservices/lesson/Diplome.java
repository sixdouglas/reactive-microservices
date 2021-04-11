package org.sixdouglas.reactive.microservices.lesson;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Diplome {
    @Id
    private Long id;
    private String name;
    private Long domaineId;
    private Integer ordre;
}
