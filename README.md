# API de desafio Técnico Supera
## A API de Transferências Bancárias permite gerenciar contas e transferências entre elas. Ela fornece endpoints para criar, editar, listar e excluir contas, bem como realizar transferências entre contas e recuperar transferências com base em datas.

# Configuração do Projeto
## Siga as etapas abaixo para configurar e executar o projeto:

1. Clone o repositório do projeto para o seu ambiente local.
2. Certifique-se de ter o Java JDK (versão 11 ou superior) instalado em seu sistema (Utilizada 19 para o desenvolvimento).
3. Abra o projeto em sua IDE de preferência.
4. Sincronize as dependências do Maven para garantir que todas as bibliotecas necessárias estejam instaladas corretamente.
5. O projeto utiliza um banco de dados H2 embutido por padrão. Certifique-se de ter o driver H2 DB instalado em seu ambiente.
6. Execute o script de criação do banco de dados data.sql localizado na pasta src/main/resources para criar a estrutura do banco de dados.
7. Inicie o projeto executando a classe BancoApplication.java como uma aplicação Spring Boot. (Via IDE, ou jar gerado em Target/Classes)

# Endpoints Disponíveis
## A API possui os seguintes endpoints:

### Contas
- 1. GET /contas: Retorna todas as contas cadastradas.

- 2. GET /contas/{id}: Retorna uma conta com base no ID fornecido.

- 3. POST /contas: Cria uma nova conta. Requer um corpo JSON contendo o nome do responsável pela conta.

```
{
  "nomeResponsavel": "Denis Zickhur"
}
```


###  Transferências
- 1. GET /transferencias: Retorna todas as transferências cadastradas.

- 2. GET /transferencias/between-dates?startDateTime=2019-04-28T15:23&endDateTime=&nomeResponsavel=Fulano: Retorna as transferências realizadas entre as datas especificadas. Os parâmetros startDateTime e endDateTime devem estar no formato ISO 8601 (por exemplo, 2023-07-14T14:30:00).

- 3. GET /transferencias/count: retorna o valor composto de todas as transferências cadastradas

- 4. POST /transferencias: Cria uma nova transferência. Requer um corpo JSON contendo os detalhes da transferência, como data, valor, tipo, nome do operador de transação e ID da conta de origem.

```
{
  "dataTransferencia": "2023-07-14T14:30:00",
  "valor": 100.00,
  "tipo": "DEBIT",
  "nomeOperadorTransacao": "Denis Zickhur",
  "conta": {
    "id": 1
  }
}
```