package org.sixdouglas.reactive.microservices.notation.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class Note {
    @Id
    private Long id;
    private Long devoirId;
    private BigDecimal note;
    private String remarque;
    private String eleveId;
}
