package com.example.proyecto.model.bean;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InformacionBancaria implements Serializable {
    private String numeroCuenta;
    private ArrayList<Tarjeta> tarjetas;
}
