package org.sixdouglas.reactive.microservices.notation;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
public class Note {
    @Id
    private Long id;
    private BigDecimal note;
    private String remarque;
    private String eleveId;
}
