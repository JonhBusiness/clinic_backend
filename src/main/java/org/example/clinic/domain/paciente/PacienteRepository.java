package org.example.clinic.domain.paciente;

import org.example.clinic.domain.medico.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
//    Page<Paciente> findAllByestadoCitaTrue(Pageable paginacion);
Optional<Paciente> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
}
