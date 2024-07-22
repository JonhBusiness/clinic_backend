package org.example.clinic.service;

import jakarta.transaction.Transactional;
import org.example.clinic.domain.paciente.DTO.ActulizaPacienteDTO;
import org.example.clinic.domain.paciente.DTO.ListaPacienteDTO;
import org.example.clinic.domain.paciente.DTO.PacienteResponse;
import org.example.clinic.domain.paciente.DTO.RegistraPacienteDTO;
import org.example.clinic.domain.paciente.Paciente;
import org.example.clinic.domain.paciente.PacienteRepository;
import org.example.clinic.service.interfaces.IGenericService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Service
public class PacienteService implements IGenericService<ListaPacienteDTO> {
    private final PacienteRepository pacienteRepository;

    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public Page<ListaPacienteDTO> getAll(Pageable pageable) {
        return pacienteRepository.findAll(pageable)
                .map(paciente -> new ListaPacienteDTO(paciente));
    }

    public ResponseEntity<?> registrar(RegistraPacienteDTO paciente, UriComponentsBuilder uriBuilder) {
        if (pacienteRepository.existsByNombre(paciente.nombre())) {
            return ResponseEntity.badRequest().body("Nombre ya existe");
        }
        Paciente newPaciente = pacienteRepository.save(new Paciente(paciente));
        URI uriComponents = uriBuilder.path("/{id}").build(newPaciente.getId());
        return ResponseEntity.created(uriComponents).body(new PacienteResponse(newPaciente));
    }

@Transactional
    public ResponseEntity<?> actualizar(ActulizaPacienteDTO paciente, UriComponentsBuilder uriBuilder) {

        Optional<Paciente> pacienteEncontrado=pacienteRepository.findById(paciente.id());
        if(pacienteEncontrado.isEmpty()){
            return new ResponseEntity<>("Paciente no Encontrado", HttpStatus.NOT_FOUND);
        }
        Paciente pacienteActual=pacienteEncontrado.get();
        pacienteActual.setNombre(paciente.nombre());
        pacienteActual.setTelefono(paciente.telefono());
        pacienteActual.setDireccion(paciente.direccion());
        URI uriComponents = uriBuilder.path("/{id}").build(pacienteActual.getId());
        return ResponseEntity.created(uriComponents).body(new PacienteResponse(pacienteActual));

    }
}
