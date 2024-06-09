
# luizaLabs

# File Converter

## Descrição

O **File Converter** é um sistema que permite a conversão de arquivos de pedidos em diferentes formatos para JSON. Este projeto também inclui a integração com um banco de dados MongoDB para armazenar e consultar pedidos.

## Funcionalidades

- Conversão de arquivo .txt de pedidos para JSON.
- Armazenamento de pedidos em um banco de dados MongoDB.
- Consulta de pedidos pelo ID.
- Consulta de pedidos por data de início e fim.
- Consulta de todos os pedidos.
- Tratamento de exceções e mensagens de erro claras.
- Testes de integração para verificar a funcionalidade do sistema.
- Testes unitários para verificar uma função ou método específico em uma classe para assegurar que a lógica de negócio está correta.
- Gerar arquivo de cobertura de testes (FileConverter/target/site/jacoco/index.html).
- Executar testes e gerar o relatório (FileConverter/target/site/surefire-report.html).
- Gerar um Swagger com a documentação.

## Requisitos

- Java 17 ou superior
- Maven 3.6.3 ou superior
- MongoDB 4.4 ou superior
- Docker 25.0.3 ou superior
- docker-compose 1.29.2 ou superior

## Descrição de Pastas e Arquivos do Repositório

- **docker-compose.yml**: Arquivo de configuração usado pelo Docker Compose para definir e gerenciar multi-contêineres Docker.
- **FileConverter**: Pasta principal do projeto File Converter.

## Arquitetura do Projeto

### Arquitetura Limpa (Clean Architecture)

Este projeto segue os princípios da Arquitetura Limpa, também conhecida como Clean Architecture. A Arquitetura Limpa visa manter a independência das regras de negócio em relação a frameworks, bancos de dados, interfaces de usuário e quaisquer outros elementos externos.

## Instalação

1. Clone o repositório:

    ```sh
    git clone https://github.com/ochannel/luizaLabs.git
    cd luizaLabs
    ```

2. Configure o MongoDB para rodar localmente:

    ```sh 
    docker-compose up 
    ```

3. Compile e execute o projeto:

    ```sh
    cd FileConverter
    mvn clean install
    mvn spring-boot:run
    ```

## Documentação da API

Esta aplicação utiliza Swagger para fornecer a documentação interativa da API. Para acessar a interface do Swagger, siga as etapas abaixo:

1. Certifique-se de que a aplicação está em execução. Caso contrário, volte ao passo de Instalação deste documento.

2. Abra seu navegador e vá para a seguinte URL para acessar a interface do Swagger:
    ```sh
    http://localhost:8080/swagger-ui/index.html
    ```
