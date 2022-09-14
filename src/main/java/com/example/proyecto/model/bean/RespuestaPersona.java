package com.example.proyecto.model.bean;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaPersona implements Serializable {
    List<Persona> personas;
}
