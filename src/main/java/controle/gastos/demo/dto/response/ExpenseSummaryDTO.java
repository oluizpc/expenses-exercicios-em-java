package controle.gastos.demo.dto.response;

import java.math.BigDecimal;

public record ExpenseSummaryDTO(
        String mesAno,
        BigDecimal total,
        BigDecimal totalPago,
        BigDecimal totalPendente,
        BigDecimal totalVencido,
        long quantidade
) {}
