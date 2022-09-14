package com.example.proyecto.model.bean;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioBeanMapper implements RowMapper<UsuarioBean> {

    @Override
    public UsuarioBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        UsuarioBean usuarioBean = new UsuarioBean();
        usuarioBean.setIdUsuario(rs.getInt("idUsuario"));
        usuarioBean.setUsuario(rs.getString("usuario"));
        usuarioBean.setPass(rs.getString("pass"));
        usuarioBean.setFechaAlta(rs.getDate("fechaAlta"));
        return usuarioBean;
    }
}
