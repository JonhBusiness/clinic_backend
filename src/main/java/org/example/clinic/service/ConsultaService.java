package org.example.clinic.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import lombok.Getter;
import lombok.Setter;
import org.example.clinic.domain.consulta.Consulta;
import org.example.clinic.domain.consulta.ConsultaRepository;
import org.example.clinic.domain.consulta.DTO.AgendaConsultaDTO;
import org.example.clinic.domain.consulta.DTO.CancelamientoConsultaDTO;
import org.example.clinic.domain.consulta.DTO.ConsultaResponse;
import org.example.clinic.domain.medico.Medico;
import org.example.clinic.domain.medico.MedicoRepository;
import org.example.clinic.domain.paciente.Paciente;
import org.example.clinic.domain.paciente.PacienteRepository;
import org.example.clinic.service.interfaces.IGenericService;
import org.example.clinic.service.interfaces.IValidaConsultas;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService implements IGenericService {


    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    @Autowired
    List<IValidaConsultas> validadores;

    public ConsultaService(ConsultaRepository consultaRepository, PacienteRepository pacienteRepository, MedicoRepository medicoRepository) {
        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @Override
    public Page<?> getAll(Pageable pageable) {

        return consultaRepository.findByEstadoTrue(pageable).map(c -> new ConsultaResponse(c));
    }

    public ResponseEntity<?> agendar(AgendaConsultaDTO consulta) {
        Optional<Medico> medicoEncontrado = medicoRepository.findByNombre(consulta.nombreMedico());
        if (medicoEncontrado.isEmpty()) {
            return new ResponseEntity<>("Médico no encontrado", HttpStatus.NOT_FOUND);
        }
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findByNombre(consulta.nombrePaciente());
        if (pacienteEncontrado.isEmpty()) {
            return new ResponseEntity<>("Paciente no encontrado", HttpStatus.NOT_FOUND);
        }
        validadores.forEach(valida ->valida.validar(consulta));

//        Paciente paciente = pacienteRepository.findByNombre(consulta.nombrePaciente()).get();
//        Medico medico = medicoRepository.findByNombre(consulta.nombreMedico()).get();

        var newConsulta = new Consulta(medicoEncontrado.get(), pacienteEncontrado.get(), consulta.fecha());
        System.out.println("fecha a retornar: " + newConsulta.getFecha());
        consultaRepository.save(newConsulta);

        return new ResponseEntity<>(new ConsultaResponse(newConsulta), HttpStatus.CREATED);
    }

    public Page<?> obtenerMedicosActivos(Pageable pageable) {
        return medicoRepository.findByActivoTrue(pageable).map(m -> new MedicoActivoDTO(m));
    }

    @Transactional
    public ResponseEntity<?> cancelarCita(CancelamientoConsultaDTO cita) {
        Consulta consulta = consultaRepository.findById(cita.idConsulta()).get();
        var ahora = LocalDateTime.now();
        var diferenciaEnHoras = Duration.between(ahora, consulta.getFecha()).toHours();

        if (diferenciaEnHoras < 24) {
            throw new ValidationException("Consulta solamente puede ser cancelada con antecedencia mínima de 24h!");
        }
        consulta.setMotivo_cancelamiento(cita.motivo());
        consulta.setEstado(false);
        return ResponseEntity.ok("Consulta cancelada");
    }
@Transactional
    public ResponseEntity<?> actulizarAgenda(AgendaConsultaDTO consulta) {
        Optional<Medico> medicoEncontrado = medicoRepository.findByNombre(consulta.nombreMedico());
        if (medicoEncontrado.isEmpty()) {
            return new ResponseEntity<>("Médico no encontrado", HttpStatus.NOT_FOUND);
        }
        Optional<Paciente> pacienteEncontrado = pacienteRepository.findByNombre(consulta.nombrePaciente());
        if (pacienteEncontrado.isEmpty()) {
            return new ResponseEntity<>("Paciente no encontrado", HttpStatus.NOT_FOUND);
        }
    Optional<Consulta> consultaEncontrada = consultaRepository.findById(consulta.id());
    if (consultaEncontrada.isEmpty()) {
        return new ResponseEntity<>("Consulta no encontrado", HttpStatus.NOT_FOUND);
    }

        validadores.forEach(valida ->valida.validar(consulta));
    Consulta consultaExistente= consultaEncontrada.get();
    consultaExistente.setMedico(medicoEncontrado.get());
    consultaExistente.setPaciente(pacienteEncontrado.get());
    consultaExistente.setFecha(consulta.fecha());

//
//        var newConsulta = new Consulta(medicoEncontrado.get(), pacienteEncontrado.get(), consulta.fecha());

        return new ResponseEntity<>(new ConsultaResponse(consultaExistente) , HttpStatus.CREATED);
    }

    @Getter
    @Setter
    private static class MedicoActivoDTO {
        private Long id;
        private String nombre;

        public MedicoActivoDTO(Medico m) {
            this.id = m.getId();
            this.nombre = m.getNombre();
        }
    }
}
/**
 * public ResponseEntity<?> agendar(AgendaConsultaDTO consulta) {
 * if (pacienteRepository.findById(consulta.idPaciente()).isEmpty()) {
 * return new ResponseEntity<>("El id " + consulta.idPaciente() + " del paciente no fue encontrado", HttpStatus.NOT_FOUND);
 * }
 * if (medicoRepository.findById(consulta.idMedico()).isEmpty()) {
 * return new ResponseEntity<>("El id " + consulta.idMedico() + " del medico no fue encotrado", HttpStatus.NOT_FOUND);
 * }
 * <p>
 * validadores.forEach(valida ->valida.validar(consulta));
 * <p>
 * Paciente paciente = pacienteRepository.findById(consulta.idPaciente()).get();
 * Medico medico = medicoRepository.findById(consulta.idMedico()).get();
 * medico.setDisponibilidad(false);
 * var newConsulta = new Consulta(medico,paciente,consulta.fecha());
 * <p>
 * consultaRepository.save(newConsulta);
 * <p>
 * return new ResponseEntity<>(newConsulta, HttpStatus.CREATED);
 * }
 */