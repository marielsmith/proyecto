package com.example.proyecto.controller;

import com.example.proyecto.model.MensajeRespuesta;
import com.example.proyecto.model.bean.UsuarioBean;
import com.example.proyecto.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;
    //@RequestMapping(value = "getAllUser", method =RequestMethod.GET)
    @GetMapping("getAllUser")
    public ResponseEntity<List<UsuarioBean>> getAllUser() throws SQLException {
        return new ResponseEntity<>(usuarioService.getAllUser(), HttpStatus.OK);
    }

    @GetMapping("getUserName/{userName}")
    public ResponseEntity<UsuarioBean> getUserName(@PathVariable("userName") String userName) throws SQLException {
        return new ResponseEntity<>(usuarioService.getUserName(userName), HttpStatus.OK);
    }

    @PostMapping("validaUsuario")
    public ResponseEntity<String> validaUsuario(@RequestBody UsuarioBean usuarioBean) throws SQLException {
        return new ResponseEntity<>(usuarioService.validaUsuario(usuarioBean) ? "Ok" : "No se encontro", HttpStatus.OK);
    }

    @GetMapping("getUserRegistradosNDias/{day}")
    public ResponseEntity<List<UsuarioBean>> getUserRegistradosNDias(@PathVariable("day") int day) throws SQLException {
        return new ResponseEntity<>(usuarioService.getUserRegistradosNDias(day),HttpStatus.OK);
    }

    @PostMapping("registrarUsuario")
    public ResponseEntity<MensajeRespuesta> registrarUsuario(@RequestBody UsuarioBean usuarioBean) throws SQLException {
        MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();

        if(usuarioService.registrarUsuario(usuarioBean)){
            mensajeRespuesta.setStatus("success");
            mensajeRespuesta.setMensaje("Registro creado");
            return new ResponseEntity<>(mensajeRespuesta,HttpStatus.CREATED);
        }else{
            mensajeRespuesta.setStatus("error");
            mensajeRespuesta.setMensaje("Ocurrio un problema");
            return new ResponseEntity<>(mensajeRespuesta,HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/updateUsuario")
    public ResponseEntity<MensajeRespuesta> updateUsuario(@RequestBody UsuarioBean usuarioBean) throws SQLException {
        MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
        if(usuarioService.updateUsuario(usuarioBean)){
            mensajeRespuesta.setStatus(("success"));
            mensajeRespuesta.setMensaje("Registro actualizado");
            return new ResponseEntity<>(mensajeRespuesta, HttpStatus.CREATED);
        }else{
            mensajeRespuesta.setStatus("error");
            mensajeRespuesta.setMensaje("Ocurrio un problema");
            return new ResponseEntity<>(mensajeRespuesta, HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("deleteUsuario/{idUsuario}")
    public ResponseEntity<MensajeRespuesta> deleteUsuario(@PathVariable int idUsuario) throws SQLException {
        MensajeRespuesta mensajeRespuesta = new MensajeRespuesta();
        if(usuarioService.deleteUsuario(idUsuario)){
            mensajeRespuesta.setStatus("success");
            mensajeRespuesta.setMensaje("Registro Eliminado");
            return new ResponseEntity<>(mensajeRespuesta, HttpStatus.ACCEPTED);
        }else {
            mensajeRespuesta.setStatus("error");
            mensajeRespuesta.setMensaje("Ocurrio un error");
            return new ResponseEntity<>(mensajeRespuesta, HttpStatus.CONFLICT);
        }
    }
}
