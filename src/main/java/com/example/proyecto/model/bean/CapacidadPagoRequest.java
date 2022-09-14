package com.example.proyecto.model.bean;


import lombok.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CapacidadPagoRequest implements Serializable {
    int idPersona;
    double costoProducto;
}
