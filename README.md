# Kafka Client — Produtores, Consumidores e Paralelização 🛠️🚀

Este projeto tem como objetivo fortalecer os conceitos fundamentais do **Apache Kafka** na prática, explorando:  
✅ **Produtores e Consumidores**  
✅ **Paralelização entre microsserviços**  
✅ **Desacoplamento temporal** entre aplicações  

A proposta é simular eventos entre módulos da aplicação como se fossem microsserviços independentes, eliminando o acoplamento direto e promovendo:  
🔗 **Comunicação assíncrona**  
🕒 **Liberdade temporal entre as aplicações**

---

## 📌 Por que Kafka?

Em sistemas distribuídos, o **acoplamento temporal** pode gerar falhas e gargalos quando um serviço depende da disponibilidade imediata de outro.

Com o **Kafka**:
- Os serviços não precisam estar ativos ao mesmo tempo.
- Eventos podem ser processados de forma independente e paralela.
- O sistema ganha robustez, flexibilidade e tolerância a falhas.

---
## 🔧 Configurações de Resiliência no Cluster Kafka

Para garantir que o ambiente seja resiliente a falhas e que as mensagens não sejam perdidas nem duplicadas, foram aplicadas as seguintes configurações:

| Configuração | Objetivo |
|---------------|----------|
| `num.partitions=3` | Cria tópicos com **3 partições** por padrão, permitindo **paralelismo e escalabilidade**. |
| `offsets.topic.replication.factor=3` | Garante que o **tópico interno de offsets dos consumidores** tenha 3 réplicas, evitando perda de posição de leitura em caso de falha. |
| `transaction.state.log.replication.factor=3` | Mantém 3 cópias do **log de transações** (usado em transações exactly-once), garantindo disponibilidade e consistência. |
| `acks=all` | Produtores confirmam o envio **somente após todas as réplicas sincronizadas** confirmarem o recebimento. |


Essas configurações aumentam a segurança, a confiabilidade e a resiliência do cluster Kafka, permitindo que o sistema continue funcionando mesmo diante de falhas ou indisponibilidades pontuais.

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot
- Apache Kafka - rodando localmente
---

