# Projeto de Simulação de Cache Eviction

## Sobre o Projeto

Este repositório contém os projetos desenvolvidos durante a disciplina de **Estruturas de Dados II (ED2)** do curso de **Ciências da Computação** da **UFERSA (Universidade Federal Rural do Semi-Árido)**. Ao longo da disciplina, exploramos diversas estruturas de dados essenciais, aplicadas em três projetos distintos, cada um focando na implementação de uma simulação de **cache eviction** entre um cliente e um servidor. O objetivo foi aprimorar a solução a cada unidade utilizando novas estruturas de dados aprendidas.

## Estruturas de Dados Utilizadas

Durante a disciplina, trabalhamos com as seguintes estruturas de dados:

- **Árvores Binárias**
- **Árvores Rubros-Negras**
- **Árvores Balanceadas**
- **Árvores B**
- **Tabelas Hash**
- **Heaps**
- **Listas Autoajustáveis**
- **Listas de Prioridades**
- **Processamento de String**
- **Compressão de Dados com Algoritmo de Huffman**

## Projetos

Os projetos foram desenvolvidos em três unidades, cada um focando em melhorias na simulação de cache eviction:

1. **Unidade 1 - `cache_eviction`**: 
   - Implementação inicial do sistema de cache utilizando uma árvore balanceada como base de dados e uma fila normal com tamanho 20 e política FIFO como cache. O servidor mantinha um log das operações feitas pelo cliente, que podia buscar, cadastrar, remover, alterar e ver a quantidade de registros na base de dados.

2. **Unidade 2 - `hashtable`**:
   - A base de dados do servidor foi melhorada para ser uma tabela hash redimensionável, com tratamento de colisão por encadeamento exterior e função hash de divisão. Assim como na primeira unidade, o servidor e a cache mantinham um log das operações do cliente, que continuava a ter as mesmas funcionalidades.

3. **Unidade 3 - `cache_eviction_final`**:
   - Nesta unidade, a base de dados permaneceu uma tabela hash redimensionável, mas cada índice da tabela era uma lista autoajustável, assim como a cache. A comunicação entre cliente e servidor foi aprimorada para utilizar mensagens comprimidas com o algoritmo de Huffman, permitindo buscas no log do servidor e da cache, com suporte a processamento de strings.

## Objetivo do Projeto

O principal objetivo deste projeto é simular como um cliente realiza requisições a um servidor, utilizando uma cache intermediária. A cada nova unidade, o projeto foi aprimorado para demonstrar o uso eficiente das estruturas de dados aprendidas, visando otimizar a operação de cache e o desempenho geral da aplicação.

## Instalação e Uso

Para rodar os projetos, siga as instruções abaixo:

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/Estrutura-de-dados-2.git
   ```
2. Navegue até o diretório do projeto:
   ```bash
   cd Estrutura-de-dados-2
   ```
3. Escolha qual parte (unidade) você deseja executar. O projeto é dividido em três, então, dependendo da unidade que você escolher, utilize a IDE de sua preferência para compilar e executar o projeto específico:
   - Para **Unidade 1**: abra o arquivo `cache_eviction`.
   - Para **Unidade 2**: abra o arquivo `hashtable`.
   - Para **Unidade 3**: abra o arquivo `cache_eviction_final`.

## Agradecimentos

Agradeço ao professor [@Paulo Henrique Lopes Silva] e aos colegas da UFERSA pela colaboração e aprendizado durante esta disciplina!
