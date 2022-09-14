package com.example.proyecto.repository.dao;

import com.example.proyecto.configuration.OracleConfiguration;
import com.example.proyecto.model.bean.UsuarioBean;
import com.example.proyecto.model.bean.UsuarioBeanMapper;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
@Log4j
public class UsuarioDao {

    @Autowired // Realizar la inyeccion de dependencias, no crear varios objetos del mismo tipo.
    private OracleConfiguration dataSource;

    public List<UsuarioBean> getAllUser () throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource.dataSource());

        String sql = "SELECT * FROM \"Usuario\"";
        List<UsuarioBean> usuarioBeans = jdbcTemplate.query(sql,new BeanPropertyRowMapper(UsuarioBean.class));
        log.info("Usuarios: "+ usuarioBeans.toString());
        return usuarioBeans;
    }

    public UsuarioBean getUserName(String userName) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource.dataSource());
        String sqlName = "SELECT * FROM \"Usuario\" where \"usuario\" = ?";
        UsuarioBean usuarioBean = jdbcTemplate.queryForObject (sqlName,new Object[]{userName}, new UsuarioBeanMapper());
        log.info("Usuario Name: "+usuarioBean.toString());
        return usuarioBean;
    }

    public boolean registrarUsuario(UsuarioBean usuarioBean) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource.dataSource());
        String sqlInsert = "INSERT INTO \"Usuario\" VALUES (?,?,?,?)";
        int registrarUsuario=0;
        System.out.println("sqlInsert: "+sqlInsert);
        try{
            registrarUsuario = jdbcTemplate.update(sqlInsert,usuarioBean.getIdUsuario(), usuarioBean.getUsuario(), usuarioBean.getPass(), usuarioBean.getFechaAlta());
        }catch(Exception e){
           System.out.println(e.getMessage());
        }
        return registrarUsuario == 1 ? true : false;
    }

    public boolean updateUsuario(UsuarioBean usuarioBean) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource.dataSource());
        String sqlUpdate = "UPDATE \"Usuario\" SET \"usuario\"=?, \"pass\"=? where \"idUsuario\"=?";
        int updateUsuario=0;
        try{
            updateUsuario = jdbcTemplate.update(sqlUpdate, usuarioBean.getUsuario(), usuarioBean.getPass(), usuarioBean.getIdUsuario());
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return updateUsuario ==1 ? true: false;
    }

    public boolean deteleUsuario(int idUsuario) throws SQLException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource.dataSource());
        String sqlDelete = "DELETE FROM \"Usuario\" WHERE \"idUsuario\"=?";
        int deleteUsuario=0;
        try{
            deleteUsuario = jdbcTemplate.update(sqlDelete, idUsuario);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return deleteUsuario == 1 ? true : false;
    }
}