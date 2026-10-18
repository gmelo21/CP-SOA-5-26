package br.com.fiap3esph.autoescola3esph.adapter.out.repository.entity;

import br.com.fiap3esph.autoescola3esph.shared.vo.enumeration.MotivoCancelamento;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity(name = "Instrucao")
@Table(name = "instrucoes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(of = "id")
public class InstrucaoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aluno_id")
    private AlunoEntity aluno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "instrutor_id")
    private InstrutorEntity instrutor;

    @Column(name = "data_hora")
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "motivo_cancelamento")
    private MotivoCancelamento motivoCancelamento;

    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;
}
