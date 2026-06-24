# 📔 Diário Cultural

Aplicação desktop para catalogar e organizar obras culturais — filmes, séries e livros — com interface gráfica desenvolvida em JavaFX.

## Funcionalidades

- **Cadastro** de filmes, séries e livros com informações detalhadas (título, ano, gênero, nota, etc.)
- **Busca** por título, autor, diretor, elenco, ISBN e outros campos
- **Listagem** das obras ordenadas por nota
- **Detalhes** completos de cada obra cadastrada
- **Persistência** automática dos dados em arquivo `save.json` local

## Tecnologias

- Java 21
- JavaFX 21 (interface gráfica + FXML)
- Maven (gerenciamento de dependências)
- org.json (persistência em JSON)
- JUnit 5 + Mockito (testes)

## Pré-requisitos

- JDK 21 instalado ([download](https://adoptium.net/))
- IntelliJ IDEA (recomendado)

## Como executar

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/diario-cultural.git
   ```

2. Abra o projeto no IntelliJ IDEA

3. Na aba **Maven** (lado direito), navegue até:
   ```
   Plugins → javafx → javafx:run
   ```
   E dê dois cliques em **javafx:run**

> O Maven baixará todas as dependências automaticamente na primeira execução.

## Estrutura do projeto

```
src/
├── main/
│   ├── java/diariocultural/
│   │   ├── Main.java
│   │   ├── controller/     # Controladores das telas
│   │   └── model/          # Modelos (Obra, Filme, Série, Livro, Acervo)
│   └── resources/diariocultural/view/
│       ├── *.fxml          # Telas da aplicação
│       └── *.css           # Estilos
```

## Dados salvos

Os dados são salvos automaticamente no arquivo `save.json` na raiz do projeto ao encerrar a aplicação.
