# Auto Escola 3ESPH — API ReST

API ReST para gerenciamento de uma auto-escola: cadastro de **instrutores**, **alunos** e
**usuários**, e **agendamento/cancelamento de instruções**, com autenticação por token JWT,
documentação automática (Swagger/OpenAPI), CORS configurado e consumo de um WebService
externo (ViaCEP).

- **Disciplina:** Arquitetura SOA e Web Services
- **Professor:** Carlos Eduardo Machado de Oliveira
- **Turma:** 3ESPH

## Integrantes do grupo

| Nome | RM |
| --- | --- |
| Alice Santos Bulhões | RM554499 |
| Eduardo Oliveira Cardoso Madid | RM556349 |
| Nicolas Haubricht Hainfellner | RM556259 |
| Guilherme da Cunha Melo | RM555310 |

## Stack

| Item | Versão |
| --- | --- |
| Java | 21 |
| Spring Boot | 4.0.5 |
| Banco de dados | MySQL 8 (H2 em memória nos testes) |
| Migrations | Flyway |
| Segurança | Spring Security + JWT (java-jwt) |
| Documentação | springdoc-openapi (Swagger UI) |
| Testes | JUnit 5, Mockito, AssertJ, MockMvc |

## Como executar

1. Suba um MySQL e crie a base:

```sql
create database autoescola3esph;
```

2. Ajuste, se necessário, as credenciais em `src/main/resources/application.properties`
   (`spring.datasource.username` / `spring.datasource.password`).

3. Execute a aplicação — o Flyway cria as tabelas e a carga inicial de usuários:

```bash
./mvnw spring-boot:run
```

A API sobe em `http://localhost:8085`.

4. Rode os testes automatizados (não precisam de MySQL, usam H2 em memória):

```bash
./mvnw test
```

## Documentação (Swagger)

- Swagger UI: <http://localhost:8085/swagger-ui.html>
- Contrato OpenAPI: <http://localhost:8085/v3/api-docs>

Ambos os endereços são públicos. Para testar os endpoints protegidos, clique em
**Authorize** e informe o token obtido em `POST /login`.

## Autenticação

Usuários criados pela migration `V10` (senha em texto puro de ambos: `123456`; apenas o
hash BCrypt é armazenado):

| Login | Senha | Perfil |
| --- | --- | --- |
| `admin` | `123456` | `ADMIN` |
| `usuario` | `123456` | `USER` |

```http
POST /login
Content-Type: application/json

{ "login": "admin", "senha": "123456" }
```

A resposta traz o `tokenJWT`, que deve ser enviado nas demais requisições:

```http
Authorization: Bearer <tokenJWT>
```

Requisição sem token devolve **401**; com token, mas sem o perfil exigido, devolve **403**.

## Endpoints

### Autenticação e saúde

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| `POST` | `/login` | público | Devolve o token JWT |
| `GET` | `/health-check` | público | Verifica se a API está no ar |

### Instrutores

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| `POST` | `/instrutores` | `ADMIN` | Cadastra um instrutor |
| `GET` | `/instrutores` | `ADMIN`, `USER` | Lista ativos, ordenados por nome, 10 por página |
| `GET` | `/instrutores/{id}` | `ADMIN`, `USER` | Detalha um instrutor |
| `PUT` | `/instrutores` | `ADMIN` | Atualiza nome, telefone e endereço |
| `DELETE` | `/instrutores/{id}` | `ADMIN` | Exclusão lógica (inativa) |

### Alunos

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| `POST` | `/alunos` | `ADMIN` | Cadastra um aluno |
| `GET` | `/alunos` | `ADMIN`, `USER` | Lista ativos, ordenados por nome, 10 por página |
| `GET` | `/alunos/{id}` | `ADMIN`, `USER` | Detalha um aluno |
| `PUT` | `/alunos` | `ADMIN` | Atualiza nome, telefone e endereço |
| `DELETE` | `/alunos/{id}` | `ADMIN` | Exclusão lógica (inativa) |

### Usuários

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| `POST` | `/usuarios` | `ADMIN` | Cadastra usuário (senha gravada como hash BCrypt) |
| `GET` | `/usuarios` | `ADMIN` | Lista usuários, 10 por página |
| `GET` | `/usuarios/{id}` | `ADMIN` | Detalha um usuário |
| `PUT` | `/usuarios` | `ADMIN` | Atualiza o perfil (`ADMIN`/`USER`) |
| `DELETE` | `/usuarios/{id}` | `ADMIN` | Exclui um usuário |
| `PATCH` | `/usuarios/senha` | autenticado | O próprio usuário troca a sua senha |

### Instruções

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| `POST` | `/instrucoes` | `ADMIN`, `USER` | Agenda uma instrução |
| `GET` | `/instrucoes` | `ADMIN`, `USER` | Lista instruções, 10 por página |
| `GET` | `/instrucoes/{id}` | `ADMIN`, `USER` | Detalha uma instrução |
| `DELETE` | `/instrucoes` | `ADMIN`, `USER` | Cancela uma instrução (corpo com motivo) |

### Endereços (WebService externo)

| Método | Rota | Perfil | Descrição |
| --- | --- | --- | --- |
| `GET` | `/enderecos/{cep}` | autenticado | Consulta um CEP no ViaCEP |

## Exemplos

Agendamento — o instrutor é opcional; se omitido, informe a `especialidade` e o sistema
sorteia um instrutor disponível:

```http
POST /instrucoes
Authorization: Bearer <tokenJWT>

{
  "id_aluno": 1,
  "id_instrutor": 2,
  "data_hora": "20/10/2026 - 10:00"
}
```

Cancelamento — motivos aceitos: `ALUNO_DESISTIU`, `INSTRUTOR_CANCELOU`, `OUTROS`:

```http
DELETE /instrucoes
Authorization: Bearer <tokenJWT>

{
  "id_instrucao": 10,
  "motivo": "ALUNO_DESISTIU"
}
```

Cadastro de aluno — no endereço basta o CEP, o restante é completado pelo ViaCEP:

```http
POST /alunos
Authorization: Bearer <tokenJWT>

{
  "nome": "Maria Souza",
  "email": "maria.souza@email.com.br",
  "telefone": "(11) 98765-4321",
  "cpf": "12345678901",
  "endereco": { "cep": "01311-000", "numero": "1106" }
}
```

## Regras de negócio implementadas

**Instrutores e alunos**

- Todos os campos são obrigatórios, exceto número e complemento do endereço.
- Listagens ordenadas por nome, de forma crescente, paginadas de 10 em 10.
- Na atualização, só nome, telefone e endereço podem mudar: o e-mail e a CNH/especialidade
  do instrutor, e o e-mail e o CPF do aluno, são imutáveis — não existem no contrato de
  atualização.
- A exclusão não apaga os dados: marca o registro como inativo.

**Agendamento de instruções**

- Funcionamento de segunda a sábado, das 06:00 às 21:00.
- Duração fixa de 1 hora, portanto o último horário de início é 20:00 e só horas cheias
  são aceitas.
- Antecedência mínima de 30 minutos.
- Não é permitido agendar com aluno ou instrutor inativo.
- Máximo de duas instruções por dia para o mesmo aluno.
- Um instrutor não pode ter duas instruções na mesma data/hora.
- O instrutor é opcional: sem ele, o sistema sorteia um instrutor ativo e livre no horário,
  dentro da especialidade informada.

**Cancelamento de instruções**

- O motivo é obrigatório: `ALUNO_DESISTIU`, `INSTRUTOR_CANCELOU` ou `OUTROS`.
- Antecedência mínima de 24 horas.
- Uma instrução já cancelada não pode ser cancelada novamente.
- O cancelamento libera o horário do instrutor e não conta no limite diário do aluno.

## Consumo de WebService externo (ViaCEP)

O adaptador `ViaCepEnderecoGateway` consome `https://viacep.com.br/ws/{cep}/json/` com o
`RestClient` do Spring. Ele é usado em dois pontos:

- no cadastro de alunos e instrutores, completando logradouro, bairro, cidade e UF a partir
  do CEP (o que o usuário informar manualmente tem precedência);
- no endpoint `GET /enderecos/{cep}`, que expõe a consulta diretamente.

A URL base é configurável por `api.integracao.viacep.url`.

## CORS

Configurado em `config/web/ConfiguracaoDeCors`, com origens, métodos e cabeçalhos definidos pelas
chaves `api.cors.*` do `application.properties` e aplicados pelo Spring Security a todas as
rotas (`/**`).

## Testes automatizados

Seis testes, um para cada forma de o projeto quebrar em silêncio, cobrindo as quatro
entidades da API:

| Teste | Entidade | Risco que cobre |
| --- | --- | --- |
| `AutoEscola3EsphApplicationTests` | — | O contexto não sobe: mapeamento JPA, bean faltando, migration incompatível |
| `InstrutorControllerTest` (201) | Instrutor | Rota, desserialização, Bean Validation e `Location` de uma requisição válida |
| `InstrutorControllerTest` (403) | Instrutor | Perfil `USER` acessando operação restrita a `ADMIN` |
| `AlunoControllerTest` | Aluno | Validação de entrada afrouxada (CPF fora do formato) |
| `UsuarioServiceTest` | Usuário | Senha persistida em texto puro em vez do hash BCrypt |
| `AgendaDeInstrucoesTest` | Instrução | Regra de negócio não aplicada (cancelamento com menos de 24 horas) |

Os testes rodam com H2 em memória (perfil `test`), sem precisar de um MySQL no ar:

```bash
./mvnw test
```

## Arquitetura

Arquitetura hexagonal (ports & adapters):

```
application/core/domain ....... entidades de domínio, sem dependência de framework
application/core/specification  validadores das regras de negócio
application/port/in|out ....... contratos de entrada e saída
application/service ........... casos de uso
adapter/in/controller ......... controllers ReST, DTOs e mappers
adapter/out/repository ........ entidades JPA, repositórios e mappers
adapter/out/integration ....... consumo de WebServices externos (ViaCEP)
adapter/out/security .......... UserDetailsService
config ........................ segurança, CORS, Swagger e cliente HTTP
```

## Entregas por checkpoint

| Checkpoint | Conteúdo | Onde está |
| --- | --- | --- |
| CP2 | API ReST com Spring Boot, JPA, Flyway e validação | Todo o projeto |
| CP3 | CRUD completo de instrutores e alunos | `*Controller`, `*Service`, `*Repository` |
| CP4 | Cadastro de usuários com senha criptografada, restrições por perfil, troca da própria senha e cancelamento de instruções | `UsuarioController`, `ConfiguracaoDeSeguranca`, `AgendaDeInstrucoes#cancelar` |
| CP5 | Consumo de WebService externo, Swagger, CORS e testes automatizados | `adapter/out/integration`, `config/documentation`, `config/web`, `src/test` |

> O Checkpoint 1 (WebService SOAP — projeto *Winery*: publisher e consumers) é um projeto
> separado deste e não faz parte deste repositório.
