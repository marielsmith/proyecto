package com.example.proyecto.service;

import com.example.proyecto.model.bean.*;
import com.example.proyecto.repository.dao.UsuarioDao;
import com.example.proyecto.service.interfac.ConsumoApiServiceInterface;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class ConsumoApiService implements ConsumoApiServiceInterface {
    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UsuarioService usuarioService;

    @Value("${pokemon.url}")
    String urlPokemon;

    @Value("${banco.url}")
    String urlBanco;
    @Override
    public String getInformacionPokemon(String nombre) throws JSONException {
        //ResponseEntity<String> response = restTemplate.getForEntity(url+"/"+nombre, String.class);
//        System.out.println("getBody: "+ response.getBody());
//        System.out.println("getToString: "+ response.toString());
//        System.out.println("getStatusCode: "+ response.getStatusCode());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> request = new HttpEntity<>(httpHeaders);

        String respuesta = "{\n" +
                "    \"idUsuario\": 5,\n" +
                "    \"usuario\": \"user1\",\n" +
                "    \"pass\": \"hola\"\n" +
                "}";

        UsuarioBean USR = new Gson().fromJson(respuesta, UsuarioBean.class);
        USR.setPass(USR.getPass()+"extra");

        System.out.println("Usuario JSON A OBJECT" + USR.toString());

        ResponseEntity<String> response = restTemplate.exchange(urlPokemon + "/" + nombre, HttpMethod.GET, request, String.class);

        return response.getBody();
    }

    @Override
    public List<Persona> getAllInformacionPersona() {
        HttpHeaders httpHeaders = new HttpHeaders();
        //httpHeaders.add("Content-Type","application/json");
        //httpHeaders.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<RespuestaPersona> request = new HttpEntity<>(httpHeaders);
        ResponseEntity<RespuestaPersona> response = restTemplate.exchange(urlBanco, HttpMethod.GET, request,RespuestaPersona.class);
        return response.getBody().getPersonas();
    }

    @Override
    public List<Persona> getCumpleanioMes(int mes){
        List<Persona> listaInicial = getAllInformacionPersona();
        List<Persona> listaCumpleanioMes = new ArrayList<>();
        for(int i=0; listaInicial.size()>i; i++){
            if(listaInicial.get(i).getFechaNacimiento().getMonthValue() == mes){
                listaCumpleanioMes.add(listaInicial.get(i));
            }
        }
        return listaCumpleanioMes;
    }

    @Override
    public Persona getFechaProxCumpleanio(){
        List<Persona> listaInicial = getAllInformacionPersona();
        Persona cumpleanioMes = new Persona();
        long numeroDíasMinimo = 0;
        numeroDíasMinimo = getDiasParaCumpleanio(listaInicial.get(0));

        for(int i=1; listaInicial.size()>i; i++){
          long  numeroDía = getDiasParaCumpleanio(listaInicial.get(i));
           if(numeroDía<numeroDíasMinimo){
               numeroDíasMinimo=numeroDía;
               cumpleanioMes=listaInicial.get(i);
           }
        }

        return cumpleanioMes;

    }

    private long getDiasParaCumpleanio(Persona persona){
        LocalDate hoy= LocalDate.now();
        LocalDate proxCumpleanio= LocalDate.of(hoy.getYear(), persona.getFechaNacimiento().getMonthValue(), persona.getFechaNacimiento().getDayOfMonth());
        long numeroDias = DAYS.between(hoy, proxCumpleanio);
        if(numeroDias<0){
            proxCumpleanio= LocalDate.of(hoy.getYear()+1, persona.getFechaNacimiento().getMonthValue(), persona.getFechaNacimiento().getDayOfMonth());
            numeroDias = DAYS.between(hoy, proxCumpleanio);
        }
        return numeroDias;
    }

    @Override
    public boolean capacidadPago(int idPersona, double costoProducto) throws SQLException {
        List<Persona> listaInicial = getAllInformacionPersona();
        Persona personaAnalizar = new Persona();
        boolean registrarUsuarioConCapacidad = false;
        for(int i=0; listaInicial.size()>i;i++){
            if(listaInicial.get(i).getId() == idPersona){
                personaAnalizar = listaInicial.get(i);
            }
        }
        if(capacidadPagoTotal(personaAnalizar) >= costoProducto){
            int idUsuarioProx=usuarioService.getAllUser().size()+1;
            UsuarioBean usuarioBean = new UsuarioBean();
            usuarioBean.setIdUsuario(idUsuarioProx);
            usuarioBean.setUsuario(personaAnalizar.getNombreCompleto());
            usuarioBean.setPass(personaAnalizar.getProfesion());
            usuarioBean.setFechaAlta(new Date());
            registrarUsuarioConCapacidad = usuarioService.registrarUsuario(usuarioBean);
        }

        return registrarUsuarioConCapacidad;
    }

    private double capacidadPagoTotal (Persona personaAnalizar){
        ArrayList<Tarjeta> tarjetas = personaAnalizar.getInformacionBancaria().getTarjetas();
        double capacidadPagoTotal =0;
        for(int i=0; tarjetas.size()>i; i++){
            if(tarjetas.get(i) instanceof TarjetaDebito){
                capacidadPagoTotal += ((TarjetaDebito) tarjetas.get(i)).getSaldo();
            } else {
                capacidadPagoTotal += (((TarjetaCredito) tarjetas.get(i)).getLineaCredito()-((TarjetaCredito) tarjetas.get(i)).getCreditoUsado());
            }
        }

        return capacidadPagoTotal;
    }
}
