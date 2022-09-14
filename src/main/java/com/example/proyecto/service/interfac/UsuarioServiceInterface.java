package com.example.proyecto.service.interfac;

import com.example.proyecto.model.bean.UsuarioBean;

import java.sql.SQLException;
import java.util.List;

public interface UsuarioServiceInterface {

    public List<UsuarioBean> getAllUser () throws SQLException;
    public UsuarioBean getUserName(String username) throws SQLException;
    public boolean validaUsuario (UsuarioBean usuarioBean) throws SQLException;
    public List<UsuarioBean> getUserRegistradosNDias (int day) throws SQLException;
    public boolean registrarUsuario(UsuarioBean usuarioBean) throws SQLException;
    public boolean updateUsuario(UsuarioBean usuarioBean) throws SQLException;
    public boolean deleteUsuario(int idUsuario) throws SQLException;
}
