package com.example.proyecto.model.bean;

import lombok.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TarjetaCredito extends Tarjeta implements Serializable {
    private double lineaCredito;
    private LocalDate fechaCorte;
    private double creditoUsado;
}
