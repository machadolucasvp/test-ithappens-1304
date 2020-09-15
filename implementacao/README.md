## SISTEMA DE ESTOQUE ITHAPPENS

### Sistema para realizar opeações básicas no estoque ItHappens

#### :books: Linguagens,dependências e libs utilizadas
- Java
- Spring
- Hibernate
- Flyway
- Swagger
- Lombok

#### :zap: Pré-requisitos para rodar o sistema
1. Crie um banco local na sua máquina chamado estoque;
2. Verifique no arquivo application.properties, no caminho `estoque/src/main/resources`, se as configurações de username e password configuradas são as mesmas utilizadas na porta 5432 da sua máquina. Caso não seja, mude as informações para as que estão configuradas na sua máquina;
3. Rode o projeto.

#### :computer: Como testar a api usando o Swagger
1. Rode o projeto
2. No seu navegador acesse: http://localhost:8080/swagger-ui.html
