package org.example.clinic.domain.consulta;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.clinic.domain.medico.Medico;
import org.example.clinic.domain.paciente.Paciente;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Consulta")
@Table(name = "tbl_consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    @Enumerated(EnumType.STRING)
    private MotivoCancelamiento motivo_cancelamiento;

    private boolean estado;

    @ManyToOne
    private Medico medico;

    @ManyToOne
    private Paciente paciente;


    public Consulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        this.medico = medico;
        this.paciente = paciente;
        this.fecha = fecha;
        this.estado = true;
    }
}