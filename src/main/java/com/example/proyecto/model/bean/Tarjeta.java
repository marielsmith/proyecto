package com.example.proyecto.model.bean;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Tarjeta implements Serializable {
    private String bancoEmisor;
    private String numeroTarjeta;
    private LocalDate fechaVencimiento;

}
