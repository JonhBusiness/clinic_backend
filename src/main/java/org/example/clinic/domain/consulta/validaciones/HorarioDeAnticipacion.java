package org.example.clinic.domain.consulta.validaciones;

import jakarta.validation.ValidationException;
import org.example.clinic.domain.consulta.DTO.AgendaConsultaDTO;
import org.example.clinic.infra.exceptions.ValidacionException;
import org.example.clinic.service.interfaces.IValidaConsultas;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class HorarioDeAnticipacion implements IValidaConsultas {
    @Override
    public void validar(AgendaConsultaDTO datos) throws ValidacionException {
        var ahora = LocalDateTime.now();
        var horaDeConsulta= datos.fecha();

        var diferenciaDe30Min= Duration.between(ahora,horaDeConsulta).toMinutes()<30;
        if(diferenciaDe30Min){
            throw new ValidacionException("Las consultas deben programarse con al menos 30 minutos de anticipación");
        }
    }
}
