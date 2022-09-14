package com.example.proyecto.service.interfac;

import com.example.proyecto.model.bean.Persona;
import org.springframework.boot.configurationprocessor.json.JSONException;

import java.sql.SQLException;
import java.util.List;

public interface ConsumoApiServiceInterface {

    public String getInformacionPokemon(String nombre) throws JSONException;
    public List<Persona> getAllInformacionPersona();

    public List<Persona> getCumpleanioMes(int mes);
    public Persona getFechaProxCumpleanio();

    public boolean capacidadPago(int idPersona, double costoProducto) throws SQLException;
}
