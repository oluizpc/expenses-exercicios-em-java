# Controle de Despesas - Java Spring Boot

Um sistema simples para gerenciar despesas pessoais ou empresariais, permitindo cadastrar, listar, deletar e pagar despesas.

---

## Funcionalidades

- Criar despesas com descrição, valor, data de vencimento e categoria.
- Listar todas as despesas cadastradas.
- Deletar despesas por ID.
- Pagar despesas e registrar a forma de pagamento.
- Resumo mensal com total pago, total pendente e total vencido.

---

## Tecnologias

- Java 17
- Spring Boot
- H2 (banco em memória) / PostgreSQL
- Maven
- Lombok

---

## Como rodar

1. Clonar o repositório:
   ```bash
   git clone https://github.com/oluizpc/expenses-exercicios-em-java.git

2. Entrar na pasta do projeto:
    ```bash
    cd spring.boot.controle.gastos
3. Rodar a aplicação:
   ```bash
   ./mvnw spring-boot:run
   ou, se tiver Maven instalado:
   mvn spring-boot:run


## EndPoints principais
Método	Rota	                Descrição
GET	    /expenses	              Listar todas as despesas
GET	    /expenses/{id}	        Buscar despesa por ID
POST	  /expenses	              Criar nova despesa
PATCH	  /expenses/{id}/pay	    Pagar despesa existente
DELETE	/expenses/{id}	        Deletar despesa

## Estrutura dos JSON para testes
-Criar despesa
POST /expenses
  
    ```bash
    {
      "descricao": "Aluguel mensal 2026-01",
      "categoria": "Moradia",
      "valor": 1332.50,
      "dataVencimento": "2026-01-15",
      "formaPagamento": "NAO_INFORMADO",
      "status": "PENDENTE",
      "observacoes": "Referente ao mês de janeiro"
    }


-Pagar despesa
PATCH /expenses/{id}/pay

    ```bash
    {
      "formaPagamento": "PIX"
    }
Obs: As opções válidas para formaPagamento são: DINHEIRO, CARTAO_DEBITO, CARTAO_CREDITO, PIX, NAO_INFORMADO

-Exemplo de retorno de despesa
GET /expenses/{id}

    ```bash
      {
        "id": 1,
        "descricao": "Aluguel mensal 2026-01",
        "categoria": "Moradia",
        "valor": 1332.50,
        "dataVencimento": "2026-01-15T00:00",
        "formaPagamento": "PIX",
        "status": "PAGO",
        "observacoes": "Referente ao mês de janeiro"
      }
      
Status e formas de pagamento
Status:     PENDENTE, PAGO,CANCELADO, ATRASADO
Formas de pagamento: DINHEIRO, CARTAO_DEBITO, CARTAO_CREDITO, PIX, DINHEIRO
Categorias: ALUGUEL, AGUA, ENERGIA, INTERNET, TELEFONE, MANUTENCAO, MATERIAL_ESCRITORIO, IMPOSTOS, SALARIOS, TRANSPORTE, COMPRAS, SEGURO, SAUDE, EDUCACAO, LAZER, OUTROS;

Autor -
Luiz Paullo

