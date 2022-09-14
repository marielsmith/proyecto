package com.example.proyecto.model;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MensajeRespuesta {
    String status;
    String mensaje;
}
