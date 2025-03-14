# Sistema-de-Coowking

**Sistema de Reservas para Espaços de Coworking**
Este projeto é um sistema de gerenciamento de reservas para espaços de coworking, que permite o cadastro de usuários com diferentes perfis, gestão de salas e estações de trabalho, e um controle de reservas eficiente. O sistema foi desenvolvido utilizando Spring Boot, Spring Security, Spring Data JPA, PostgreSQL e JWT para autenticação. A aplicação também conta com Swagger/OpenAPI para documentação da API e validações com Bean Validation.

# Funcionalidades
🔑 **Cadastro de Usuários**
Perfis de Usuários: Administrador, Funcionário e Cliente.
Permissões específicas: Apenas administradores podem cadastrar novas salas.
Autenticação via JWT: Garantindo segurança no acesso.

🏢 **Gestão de Salas e Estações de Trabalho**
CRUD Completo: Possibilidade de criar, ler, atualizar e deletar salas e estações de trabalho.
Definições de salas: Capacidade, equipamentos disponíveis e horários de funcionamento.

📅 **Sistema de Reservas**
Reserva de espaços: Clientes podem reservar salas ou estações de trabalho para datas e horários específicos.
Controle de disponibilidade: Verificação em tempo real da disponibilidade dos espaços.
Cancelamento e reagendamento: Funcionalidade para gestão de reservas.
Atualização automática: A cada 1 minuto, o sistema verifica a disponibilidade e finaliza agendamentos de forma automática.

# Tecnologias Utilizadas
**Backend**
Spring Boot
Spring Security (Roles: Admin, Funcionário, Cliente)
Spring Data JPA
PostgreSQL

**Autenticação:**
JWT (JSON Web Token)

**Documentação:**
JavadocSpring
Swagger/OpenAPI

**Validações:**
Bean Validation para entradas de dados
Como Rodar o Projeto

# Endpoints Principais
**Usuários**
POST /auth/register – Registra um novo usuário.

POST /auth/login – Realiza o login e gera o JWT.

**Salas**

POST /room/create - Criação de uma nova Sala

PUT /room/delete - Remoção de uma sala existente

PUT /room/update - Atualização de uma sala existente

PUT /room/enable - Reativação de uma sala desativada ( deletada )

GET /room/list - Listagem de todas as salas ativas

GET /room/horarioFuncionamento - Verifica o horário de funcionamento

POST /room/agendamento - Realiza o agendamento de uma sala

GET /room/disponibilidade - Verifica a disponibilidade das salas ativas

PUT /room/cancelamento - Realiza o cancelamento de um agendamento

**Reservas**

POST /station/create - Criação de uma nova Sala

PUT /station/delete - Remoção de uma sala existente

PUT /station/update - Atualização de uma sala existente

PUT /station/enable - Reativação de uma sala desativada ( deletada )

GET /station/list - Listagem de todas as salas ativas

GET /station/horarioFuncionamento - Verifica o horário de funcionamento

POST /station/agendamento - Realiza o agendamento de uma sala

GET /station/disponibilidade - Verifica a disponibilidade das salas ativas

PUT /station/cancelamento - Realiza o cancelamento de um agendamento
