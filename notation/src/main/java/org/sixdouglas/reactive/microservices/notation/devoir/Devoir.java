package org.sixdouglas.reactive.microservices.notation.devoir;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Devoir {
    @Id
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private Long matiereId;
}
