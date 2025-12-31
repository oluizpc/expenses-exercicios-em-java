package controle.gastos.demo.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

import org.springframework.stereotype.Service;

import controle.gastos.demo.dto.request.ExpenseRequestDTO;
import controle.gastos.demo.dto.response.ExpenseResponseDTO;
import controle.gastos.demo.dto.response.ExpenseSummaryDTO;
import controle.gastos.demo.enums.FormaPagamento;
import controle.gastos.demo.enums.StatusExpenses;
import controle.gastos.demo.exception.ExpenseNotFoundException;
import controle.gastos.demo.model.Expenses;
import controle.gastos.demo.repository.ExpenseRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepository expenseRepository;


    public ExpenseResponseDTO criarDespesa(ExpenseRequestDTO dto) {

    if (dto.valor() == null || dto.valor().compareTo(BigDecimal.ZERO) <= 0)
        throw new IllegalArgumentException("O valor deve ser maior que zero.");

    LocalDate vencimento = LocalDate.parse(dto.dataVencimento());

    if (vencimento.getYear() < 2000 || vencimento.getYear() > 2100)
        throw new IllegalArgumentException("Data de vencimento inválida.");

    if (vencimento.isBefore(LocalDate.now()))
        throw new IllegalArgumentException("Data de vencimento não pode ser anterior a hoje.");

    StatusExpenses status = StatusExpenses.valueOf(dto.status());
    FormaPagamento forma = FormaPagamento.valueOf(dto.formaPagamento());

    if (status == StatusExpenses.PAGO &&
            dto.formaPagamento().equalsIgnoreCase("NAO_INFORMADO"))
        throw new IllegalArgumentException("Despesas pagas precisam de forma de pagamento.");

    if (expenseRepository.existsByDescricaoAndValorAndDataVencimento(
            dto.descricao(), dto.valor(), vencimento.atStartOfDay()))
        throw new IllegalArgumentException("Já existe uma despesa igual cadastrada.");

    Expenses expenses = Expenses.builder()
            .descricao(dto.descricao())
            .categoria(dto.categoria())
            .valor(dto.valor())
            .dataVencimento(vencimento.atStartOfDay())
            .formaPagamento(forma)
            .status(status)
            .observacoes(dto.observacoes())
            .build();

    if (status == StatusExpenses.PAGO)
        expenses.setDataPagamento(LocalDateTime.now());

    return toResponse(expenseRepository.save(expenses));
    }

        public ExpenseResponseDTO toResponse(Expenses e) {
            return new ExpenseResponseDTO(
                    e.getId(),
                    e.getDescricao(),
                    e.getCategoria(),
                    e.getValor(),
                    e.getDataVencimento().toString(),
                    e.getFormaPagamento().name(),
                    e.getStatus().name(),
                    e.getObservacoes()
            );
        }

        //Lista todas as despesas
        public List<ExpenseResponseDTO> listarTodasDespesas () {
            return expenseRepository.findAll().stream()
                    .map(this::toResponse)
                    .toList();
        }


        //Buscar despesas por ID
        public ExpenseResponseDTO buscarPorID (long id) {
            Expenses expenses = expenseRepository.findById(id)
                    .orElseThrow(() -> new ExpenseNotFoundException ("Despesa não encontrada com esse id: " + id));
            return toResponse(expenses);
        }

        //Deletar despesa por ID
        public void deletarDespesa (long id) {
            Expenses expenses = expenseRepository.findById(id)
                    .orElseThrow(() -> new ExpenseNotFoundException ("Despesa não encontrada com esse id: " + id));
            expenseRepository.delete(expenses);
        }

        public ExpenseResponseDTO pagarDespesa(Long id, String formaPagamento) {
        Expenses despesa = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(
                        "Despesa não encontrada com id: " + id
                ));

        if (despesa.getStatus() == StatusExpenses.PAGO)
                throw new IllegalArgumentException("Essa despesa já está paga.");

        // Convertendo corretamente
        FormaPagamento forma;
        try {
                forma = FormaPagamento.valueOf(formaPagamento.toUpperCase());
        } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Forma de pagamento inválida: " + formaPagamento);
        }

        despesa.setStatus(StatusExpenses.PAGO);
        despesa.setFormaPagamento(forma);
        despesa.setDataPagamento(LocalDateTime.now());

        expenseRepository.save(despesa);

        return toResponse(despesa);
        }


        //Implementação feita por AI 
        public ExpenseSummaryDTO resumoMensal(YearMonth mesAno) {

            LocalDateTime inicio = mesAno.atDay(1).atStartOfDay();
            LocalDateTime fim = mesAno.atEndOfMonth().atTime(23, 59, 59);

            List<Expenses> despesas = expenseRepository
                    .findByDataVencimentoBetween(inicio, fim);

            BigDecimal total = despesas.stream()
                    .map(Expenses::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalPago = despesas.stream()
                    .filter(d -> d.getStatus() == StatusExpenses.PAGO)
                    .map(Expenses::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalPendente = despesas.stream()
                    .filter(d -> d.getStatus() == StatusExpenses.PENDENTE)
                    .map(Expenses::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal totalVencido = despesas.stream()
                    .filter(d ->
                            d.getStatus() != StatusExpenses.PAGO &&
                            d.getDataVencimento().isBefore(LocalDate.now().atStartOfDay())
                    )
                    .map(Expenses::getValor)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new ExpenseSummaryDTO(
                    mesAno.toString(),          // ex: 2025-03
                    total,
                    totalPago,
                    totalPendente,
                    totalVencido,
                    despesas.size()
            );
        }
    }