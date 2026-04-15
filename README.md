# Library System Java

Sistema de biblioteca desenvolvido em Java puro, simulando empréstimo e devolução de livros com fila de espera, exceções de negócio e menu interativo no console.


Projeto desenvolvido como parte do meu portfólio de estudos em Java, com foco em praticar conceitos fundamentais.


---

## Tecnologias

- Java 25

---

## Estrutura do projeto
```
library-system-java/
  src/main/java/
    Main.java
    model/
      Livro.java
      Revista.java
      ReciboEmprestimo.java
    contract/
      Emprestavel.java
    exception/
      LivroNaoEncontradoException.java
      LivroIndisponivelException.java
    service/
      BibliotecaService.java
```
---

## Funcionalidades

- Listar livros disponíveis
- Buscar livro por título
- Emprestar livro com geração de recibo
- Devolver livro com aviso automático para fila de espera
- Adicionar novos livros ao catálogo
- Fila de espera por livro indisponível

---

## Conceitos praticados

- Interfaces e polimorfismo
- HashMap e List na prática
- Exceções de negócio customizadas
- Record do Java 16+
- Stream API e Optional
- Separação de responsabilidades em camadas

---

## Como rodar

Pré-requisito: Java 25 instalado.

1. Clone o repositório
2. Abra na IntelliJ IDEA
3. Execute a classe Main.java

---
