# Projeto Kafka de Processamento de Produtos

Este projeto consiste em dois serviços: um desenvolvido em Java responsável por criar produtos e outro em Go responsável por processar esses produtos. A comunicação entre esses serviços é feita por meio do Apache Kafka, um sistema de mensageria distribuída.

## Serviço Java (Criação de Produtos)

O serviço em Java é responsável por criar produtos. Quando um novo produto é criado, ele é enviado para um tópico no Kafka, onde o serviço em Go o consumirá e realizará o processamento necessário.

### Tecnologias Utilizadas:
- Java
- Kafka

## Serviço Go (Processamento de Produtos)

O serviço em Go consome o tópico do Kafka onde os produtos são publicados pelo serviço Java. Ele processa esses produtos de acordo com a lógica de negócios definida.

### Tecnologias Utilizadas:
- Go
- Kafka
