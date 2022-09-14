package com.example.proyecto.controller;

import com.example.proyecto.model.bean.CapacidadPagoRequest;
import com.example.proyecto.model.bean.Persona;
import com.example.proyecto.service.ConsumoApiService;
import com.sun.org.apache.xpath.internal.operations.Variable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/ConsumoApi")
public class ConsumoApiController {
    @Autowired
    ConsumoApiService consumoApiService;

    @GetMapping("InformacionPokemon/{nombrePokemon}")
    public ResponseEntity<String> getInformacionPokemon(@PathVariable("nombrePokemon") String nombrePokemon) throws JSONException {
        return new ResponseEntity<>(consumoApiService.getInformacionPokemon(nombrePokemon), HttpStatus.OK);
    }

    @GetMapping("InformacionBancoPersona")
    public ResponseEntity<List<Persona>> getAllInformacionPersona(){
        return new ResponseEntity<List<Persona>>(consumoApiService.getAllInformacionPersona(), HttpStatus.OK);
    }

    @GetMapping("getCumpleanioMes/{numMes}")
    public ResponseEntity<List<Persona>> getCumpleanioMes (@PathVariable("numMes") int numMes){
        return new ResponseEntity<>(consumoApiService.getCumpleanioMes(numMes),HttpStatus.OK);
    }

    @GetMapping("getFechaProxCumpleanio")
    public ResponseEntity<Persona> getFechaProxCumpleanio(){
        return  new ResponseEntity<>(consumoApiService.getFechaProxCumpleanio(), HttpStatus.OK);
    }

    @PostMapping("/capacidadPago")
    public ResponseEntity<String> capacidadPago (@RequestBody CapacidadPagoRequest capacidadPagoRequest) throws SQLException {
        return new ResponseEntity<>(consumoApiService.capacidadPago(capacidadPagoRequest.getIdPersona(), capacidadPagoRequest.getCostoProducto()) ? "OK":"Fallo el servicio", HttpStatus.OK);
    }
}
