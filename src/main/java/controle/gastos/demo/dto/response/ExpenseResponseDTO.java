package controle.gastos.demo.dto.response;

import java.math.BigDecimal;

public record ExpenseResponseDTO(
    Long id,
    String descricao,
    String categoria,
    BigDecimal valor,
    String dataVencimento,
    String formaPagamento,
    String status,
    String observacoes
) {

}
