package com.example.proyecto.model.bean;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TarjetaDebito extends Tarjeta implements Serializable {
    private double saldo;
}
