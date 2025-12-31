package controle.gastos.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import controle.gastos.demo.dto.request.ExpenseRequestDTO;
import controle.gastos.demo.dto.request.PagarDespesaDTO;
import controle.gastos.demo.dto.response.ExpenseResponseDTO;
import controle.gastos.demo.dto.response.ExpenseSummaryDTO;
import controle.gastos.demo.service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.time.YearMonth;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/expenses")
@RestController
@RequiredArgsConstructor
public class ExpenseController {


    private final ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<ExpenseResponseDTO> criarDespesa(
        @RequestBody @Valid ExpenseRequestDTO dto) {
        ExpenseResponseDTO responseDTO = expenseService.criarDespesa(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ExpenseResponseDTO>> listarTodasDespesas() {
        List<ExpenseResponseDTO> despesas = expenseService.listarTodasDespesas();
        return ResponseEntity.ok(despesas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseResponseDTO> buscarDespesaPorId(
            @PathVariable Long id) {

        ExpenseResponseDTO despesa = expenseService.buscarPorID(id);
        return ResponseEntity.ok(despesa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarDespesa(@PathVariable Long id) {
        expenseService.deletarDespesa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    public ResponseEntity<ExpenseSummaryDTO> obterResumoDespesas(
            @RequestParam int ano,
            @RequestParam int mes) {

        YearMonth yearMonth = YearMonth.of(ano, mes);

        ExpenseSummaryDTO resumo = expenseService.resumoMensal(yearMonth);

        return ResponseEntity.ok(resumo);
    }

    @PatchMapping("/{id}/pay")
    public ResponseEntity<ExpenseResponseDTO> pagarDespesa(
            @PathVariable Long id,
            @RequestBody PagarDespesaDTO dto) {

        ExpenseResponseDTO response = expenseService.pagarDespesa(id, dto.formaPagamento());
        return ResponseEntity.ok(response);
    }

}