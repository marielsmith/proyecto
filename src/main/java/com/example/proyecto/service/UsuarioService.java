package com.example.proyecto.service;

import com.example.proyecto.model.bean.UsuarioBean;
import com.example.proyecto.repository.dao.UsuarioDao;
import com.example.proyecto.service.interfac.UsuarioServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class UsuarioService implements UsuarioServiceInterface {

    @Autowired
    UsuarioDao usuarioDao;

    @Override
    public List<UsuarioBean> getAllUser() throws SQLException {
        return usuarioDao.getAllUser();
    }

    @Override
    public UsuarioBean getUserName(String username) throws SQLException {
        return usuarioDao.getUserName(username);
    }

    @Override
    public boolean validaUsuario(UsuarioBean usuarioBean) throws SQLException {
        boolean validaUsuario = false;

        List<UsuarioBean> listaUsuarios= getAllUser();
        boolean validaUsuario2 = listaUsuarios.stream().anyMatch(s -> s.getIdUsuario()==usuarioBean.getIdUsuario());
        System.out.println("Valida Usuario 2: "+ validaUsuario2);
        for(int i=0; listaUsuarios.size()>i; i++){
            if(listaUsuarios.get(i).getIdUsuario() == usuarioBean.getIdUsuario() && listaUsuarios.get(i).getUsuario().equals(usuarioBean.getUsuario()) && listaUsuarios.get(i).getPass().equals(usuarioBean.getPass())  ) {
                validaUsuario=true;
                break;
            }
        }
        System.out.println("Valida Usuario: "+ validaUsuario);

        return validaUsuario;
    }
    @Override
    public List<UsuarioBean> getUserRegistradosNDias (int day) throws SQLException {
        List<UsuarioBean> listaFiltrados = new ArrayList<>();
        List<UsuarioBean> listaInicial = usuarioDao.getAllUser();

        for(int i=0; listaInicial.size()>i; i++){
            if (validaRegistroDias(day, listaInicial.get(i))){
                listaFiltrados.add(listaInicial.get(i));
            }
        }
        return listaFiltrados;
    }

    public boolean validaRegistroDias(int day, UsuarioBean usuarioBean){
        boolean validaRegistro = true;

       // Date hoy= new Date();
        // Date fechaValida = hoy.before()
        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        c.add(Calendar.DATE, - day);
        Date fechaValida = c.getTime();

        System.out.println("FECHA DE HOY: " + now + "  FECHA valida:  "+fechaValida);
        if(usuarioBean.getFechaAlta().compareTo(fechaValida) > 0){
            validaRegistro=false;
        }
        return validaRegistro;
    }

    @Override
    public boolean registrarUsuario(UsuarioBean usuarioBean) throws SQLException {
      return usuarioDao.registrarUsuario(usuarioBean);
    }

    @Override
    public boolean updateUsuario(UsuarioBean usuarioBean) throws SQLException {
        return usuarioDao.updateUsuario(usuarioBean);
    }

    @Override
    public  boolean deleteUsuario(int idUsuario) throws SQLException {
        return usuarioDao.deteleUsuario(idUsuario);
    }
}
