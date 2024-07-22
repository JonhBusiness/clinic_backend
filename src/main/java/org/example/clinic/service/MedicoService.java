package org.example.clinic.service;

import jakarta.transaction.Transactional;
import org.example.clinic.domain.medico.DTO.ActulizaMedicoDTO;
import org.example.clinic.domain.medico.DTO.ListaMedicosDTO;
import org.example.clinic.domain.medico.DTO.MedicoResponse;
import org.example.clinic.domain.medico.DTO.RegistraMedicoDTO;
import org.example.clinic.domain.medico.Medico;
import org.example.clinic.domain.medico.MedicoRepository;
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
public class MedicoService implements IGenericService<ListaMedicosDTO> {

    private final MedicoRepository medicoRepository;

    public MedicoService(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }

    @Override
    public Page<ListaMedicosDTO> getAll(Pageable pageable) {
        return medicoRepository.findByActivoTrue(pageable).map(medico -> new ListaMedicosDTO(medico));
    }

    public ResponseEntity<?> registrar(RegistraMedicoDTO medico, UriComponentsBuilder ucBuilder) {
        Optional<Medico> existingMedico = medicoRepository.findByNombre(medico.nombre());
        if (existingMedico.isPresent()) {
            return ResponseEntity.badRequest().body("El medico Ya existe");
        }
        Medico newMedico = medicoRepository.save(new Medico(medico));
        URI url = ucBuilder.path("/medicos/{id}").buildAndExpand(newMedico.getId()).toUri();
        return ResponseEntity.created(url).body(new MedicoResponse(newMedico));
    }

@Transactional
    public ResponseEntity<?> actualizar(ActulizaMedicoDTO medico, UriComponentsBuilder ucBuilder) {

    Optional<Medico> medicoEncontrado = medicoRepository.findById(medico.id());
    if (medicoEncontrado.isEmpty()) {
        return new ResponseEntity<>("Médico no encontrado", HttpStatus.NOT_FOUND);
    }
        Medico medicoExistente = medicoEncontrado.get();

        medicoExistente.setNombre(medico.nombre());
        medicoExistente.setDireccion(medico.direccion());
        medicoExistente.setTelefono(medico.telefono());
        medicoExistente.setEspecialidad(medico.especialidad());

        URI url = ucBuilder.path("/medicos/{id}").buildAndExpand(medicoExistente.getId()).toUri();

        return ResponseEntity.created(url).body(new MedicoResponse(medicoExistente));
    }
    @Transactional
    public ResponseEntity<?> desactivar(Long idMedico) {
        Medico medicoExistente = medicoRepository.findById(idMedico).get();
        medicoExistente.Desactivar();
//        medicoExistente.setActivo(false);
        System.out.println("Medico desactivado: " + medicoExistente.getNombre());
        System.out.println("Medico estado: " + medicoExistente.getActivo());
        return new ResponseEntity<>("Medico Desactivado", HttpStatus.NO_CONTENT);
    }
}
