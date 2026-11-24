# Agendador de Tarefas

API REST para cadastrar e consultar tarefas e eventos agendados. A aplicação usa Spring Boot, armazena os dados no MongoDB e protege as rotas com tokens JWT.

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Web MVC e Spring Security
- Spring Data MongoDB
- Spring Cloud OpenFeign
- Gradle Wrapper

## Pré-requisitos

- JDK 21
- MongoDB em execução
- Serviço de usuários acessível para validar os usuários dos tokens JWT

## Configuração

As configurações padrão estão em `agendador-tarefas/src/main/resources/application.properties`:

| Propriedade | Valor padrão | Descrição |
|---|---|---|
| `spring.mongodb.uri` | `mongodb://localhost:27017/db_agendador` | URI do MongoDB |
| `usuario.url` | `localhost:8080` | Endereço do serviço de usuários |
| `server.port` | `8081` | Porta da API |

O serviço de usuários deve disponibilizar `GET /usuario?email={email}` e aceitar o token no header `Authorization`.

## Executar

Na raiz do repositório, entre no diretório da aplicação:

```shell
cd agendador-tarefas
```

No Windows:

```powershell
.\gradlew.bat bootRun
```

No Linux ou macOS:

```shell
./gradlew bootRun
```

A API ficará disponível em `http://localhost:8081`. Para executar os testes:

```shell
.\gradlew.bat test
```

## Autenticação

Todas as rotas exigem um JWT válido. Envie o token no formato `Bearer`:

```http
Authorization: Bearer <seu-token-jwt>
```

O serviço de usuários configurado em `usuario.url` é consultado para carregar os dados do usuário associado ao token.

## Endpoints

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/tarefas` | Cria uma tarefa para o usuário do token |
| `GET` | `/tarefas` | Lista as tarefas do usuário |
| `GET` | `/tarefas/eventos?dataInicial={dataInicial}&dataFinal={dataFinal}` | Lista tarefas dentro do período informado |
| `PUT` | `/tarefas?id={id}` | Atualiza os campos informados de uma tarefa |
| `PATCH` | `/tarefas?id={id}&status={status}` | Altera o status da notificação |
| `DELETE` | `/tarefas?id={id}` | Exclui uma tarefa |

Os valores aceitos para `status` são `PENDENTE`, `NOTIFICADO` e `CANCELADO`.

### Criar tarefa

```http
POST /tarefas
Authorization: Bearer <seu-token-jwt>
Content-Type: application/json
```

```json
{
  "nomeDaTarefa": "Reunião de planejamento",
  "descricao": "Revisar as próximas entregas",
  "dataEvento": "30-10-2026 14:00:00"
}
```

O identificador do usuário, a data de criação e o status inicial (`PENDENTE`) são definidos pela aplicação. As datas no corpo da tarefa usam o formato `dd-MM-yyyy HH:mm:ss`.

### Consultar eventos por período

```http
GET /tarefas/eventos?dataInicial=2026-10-30T00:00:00&dataFinal=2026-10-31T00:00:00
Authorization: Bearer <seu-token-jwt>
```

Os parâmetros de período usam o formato ISO `yyyy-MM-dd'T'HH:mm:ss`.

> **Observação:** atualmente, o endpoint `GET /tarefas` também declara o header `Autthorizaion` (com erro de digitação) no controlador. Além do `Authorization` exigido pela autenticação, esse endpoint pode exigir esse header adicional até que o código seja corrigido.
