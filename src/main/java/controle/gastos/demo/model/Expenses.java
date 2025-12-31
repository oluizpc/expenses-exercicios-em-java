package controle.gastos.demo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import controle.gastos.demo.enums.FormaPagamento;
import controle.gastos.demo.enums.StatusExpenses;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "expenses")
@Getter
@Setter
@Builder
@AllArgsConstructor   // <-- construtor com todos os campos
@NoArgsConstructor    // <-- construtor vazio exigido pelo JPA
public class Expenses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String descricao;

    @Column
    private String categoria;

    @Column(length = 500)
    private String observacoes;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private LocalDateTime dataVencimento;

    @Column
    private LocalDateTime dataPagamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private StatusExpenses status;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull
    private FormaPagamento formaPagamento;

    @PrePersist
    public void prePersist() {
        if (dataHora == null) {
            dataHora = LocalDateTime.now();
        }
    }
}
