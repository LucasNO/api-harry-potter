# Desafio de backend

Esse projeto tem a intenção de criar uma rest api para cadastrar, editar, buscar, listar e deletar
personagens de Harry Potter, fazendo uma integração com a api https://www.potterapi.com/, para pegar as informações da casa de Hogwarts que o 
personagem pertence.

### Tecnologias Usadas
Projeto feito em Java com Spring boot utilizando as seguintes tecnologias:

* Docker
* Lombok (Biblioteca utilizada para redução e produtividade do código)
* Feign (Utilizado para fazer a comunicação com a potter api)
* Swagger (Documentação)
* Mapstruct (Biblioteca utilizada para mapear um entidade em dto e dto em entidade)
* Postgresql (Banco de Dados)
* Resilience4j (Circuit Breaker e Retry)
* Redis (Cache)
* Junit (Testes) e H2 (Banco de dados de teste)

### Como Rodar
Para rodar o projeto é bem simples, basta ter o docker instalado na sua maquina e executar o seguinte comando na raiz do projeto: 

$ docker-compose up --build

Esse comando acimas sobe imagens do postgre e do redis.

Depois é só rodar o seguinte comando para subir a aplicação em si.

* Linux/ Mac

$ ./mvnw spring-boot:run

* Windows

$ ./mvnw.cmd spring-boot:run

### Documentação da api
http://localhost:9100/api/swagger-ui.html