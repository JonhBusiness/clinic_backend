package org.example.clinic.domain.consulta;

import org.example.clinic.domain.medico.Estado;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

//    @Query("SELECT c FROM Consulta c WHERE c.estado = true ORDER BY c.fecha")
    Page<Consulta> findByEstadoTrue(Pageable pageable);

    Boolean existsByPacienteNombreAndFechaBetween(String nombrePaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);

    Boolean existsByMedicoNombreAndFecha(String nombreMedico, LocalDateTime fecha);
}
