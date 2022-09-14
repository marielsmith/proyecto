package com.example.proyecto.model.bean;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Persona implements Serializable {
    private int id;
    private LocalDate fechaNacimiento;
    private String nombreCompleto;
    private char sexo;
    private String profesion;
    private InformacionBancaria informacionBancaria;
}
