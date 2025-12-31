package controle.gastos.demo.repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import controle.gastos.demo.model.Expenses;

public interface ExpenseRepository extends JpaRepository<Expenses, Long> {
    List<Expenses> findByDataVencimentoBetween(
            LocalDateTime inicio,
            LocalDateTime fim
    );

    boolean existsByDescricaoAndValorAndDataVencimento(
            String descricao,
            BigDecimal valor,
            LocalDateTime dataVencimento);
}   
