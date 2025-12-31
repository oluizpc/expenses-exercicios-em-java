package controle.gastos.demo.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExpenseRequestDTO(
    @NotBlank(message = "A descrição é obrigatória")
    String descricao,
    
    String categoria,

    @NotNull(message = "O valor é obrigatório")
    BigDecimal valor,

    @NotBlank(message = "A data de vencimento é obrigatória")
    String dataVencimento,

    @NotBlank(message = "A forma de pagamento é obrigatória")
    String formaPagamento,

    @NotBlank(message = "O status é obrigatório")   
    String status,
    String observacoes
) {
}
