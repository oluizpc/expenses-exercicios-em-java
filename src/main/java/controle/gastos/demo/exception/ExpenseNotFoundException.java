package controle.gastos.demo.exception;

public class ExpenseNotFoundException extends RuntimeException{

    public ExpenseNotFoundException() {
        super("Tarefa não encontrada");
    }

    public ExpenseNotFoundException(String mensagem) {
        super(mensagem);
    }
}

