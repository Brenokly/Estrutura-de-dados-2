package cache_eviction_final.compression;

import java.util.HashMap;
import java.util.Map;
import cache_eviction_final.structure.listPstructure.MinHeap; 

// Classe responsável por construir e manipular a árvore de Huffman
public class HuffmanTree {
  // Mapa que armazena os códigos de Huffman para cada caractere
  private Map<Character, String> huffmanCodes;

  // Construtor da árvore de Huffman
  public HuffmanTree() {
    this.huffmanCodes = new HashMap<>(); // Inicializa o mapa de códigos
  }

  // Método que constrói a árvore de Huffman a partir do texto fornecido
  public Node buildHuffmanTree(String text) {
    // Mapa para contar a frequência de cada caractere
    Map<Character, Integer> frequencyMap = new HashMap<>();
    for (char c : text.toCharArray()) {
      // Atualiza a frequência do caractere
      frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
    }

    // Cria uma MinHeap para armazenar os nós da árvore
    MinHeap minHeap = new MinHeap();

    // Adiciona um nó para cada caractere na MinHeap
    for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
      minHeap.insert(new Node(entry.getKey(), entry.getValue()));
    }

    // Enquanto houver mais de um nó na MinHeap, continue construindo a árvore
    while (minHeap.size() > 1) {
      // Remove os dois nós com as menores frequências
      Node left = minHeap.extractMin(); // Menor nó
      Node right = minHeap.extractMin(); // Segundo menor nó

      // Cria um novo nó pai com a soma das frequências dos nós filhos
      // O caractere "\0" é usado para indicar que o nó não é um caractere
      Node parent = new Node('\0', left.frequency + right.frequency);
      parent.left = left; // Define o filho esquerdo
      parent.right = right; // Define o filho direito

      // Insere o nó pai de volta na MinHeap
      minHeap.insert(parent);
    }

    // O único nó restante é a raiz da árvore de Huffman
    Node root = minHeap.extractMin(); // Extraí a raiz
    generateHuffmanCodes(root, ""); // Gera os códigos de Huffman a partir da raiz
    return root; // Retorna a raiz da árvore
  }

  // Método que gera os códigos de Huffman usando uma travessia pré-ordem
  private void generateHuffmanCodes(Node root, String code) {
    if (root == null) // Se o nó não existe, não faz nada
      return;

    // Se o nó é uma folha (caractere), armazena seu código
    if (root.character != '\0') {
      huffmanCodes.put(root.character, code); // Armazena o código para o caractere
    }

    // Chama recursivamente para os filhos esquerdo e direito, adicionando '0' ou
    // '1' ao código
    generateHuffmanCodes(root.left, code + "0"); // Para o filho esquerdo, adiciona '0'
    generateHuffmanCodes(root.right, code + "1"); // Para o filho direito, adiciona '1'
  }

  // Método que imprime a árvore de Huffman em pré-ordem
  public void printHuffmanTree(Node root, String prefix) {
    if (root != null) { // Se o nó existe
      // Se o nó é uma folha, imprime o caractere e sua frequência
      if (root.character != '\0') {
        System.out.println(prefix + root.character + ": " + root.frequency);
      }

      // Chama recursivamente para os filhos esquerdo e direito
      printHuffmanTree(root.left, prefix + "0"); // Adiciona '0' ao prefixo para o filho esquerdo
      printHuffmanTree(root.right, prefix + "1"); // Adiciona '1' ao prefixo para o filho direito
    }
  }

  // Método para descomprimir a mensagem usando a árvore de Huffman
  public String decompress(Node root, String encodedString) {
    StringBuilder decodedString = new StringBuilder(); // StringBuilder para construir a string decodificada
    Node currentNode = root; // Começa pela raiz da árvore

    // Para cada bit no string codificado, navega pela árvore
    for (char bit : encodedString.toCharArray()) {
      // Navega para a esquerda ou direita baseado no bit
      currentNode = (bit == '0') ? currentNode.left : currentNode.right;

      // Se chega a um nó folha, adiciona o caractere à string decodificada
      if (currentNode.left == null && currentNode.right == null) {
        decodedString.append(currentNode.character); // Adiciona o caractere decodificado
        currentNode = root; // Retorna para a raiz para decodificar o próximo caractere
      }
    }
    return decodedString.toString(); // Retorna a string decodificada
  }

  // Método para obter os códigos de Huffman
  public Map<Character, String> getHuffmanCodes() {
    return huffmanCodes; // Retorna o mapa de códigos de Huffman
  }
}