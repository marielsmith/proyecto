package com.example.proyecto.model.bean;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioBean {
    private Integer idUsuario;
    private String usuario;
    private String pass;
    private Date fechaAlta;
}
