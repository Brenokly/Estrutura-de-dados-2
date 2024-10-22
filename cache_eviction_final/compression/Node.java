package cache_eviction_final.compression;

/*
 * Representação de um nó da árvore de Huffman.
*/

public class Node {
  char character; // Caractere armazenado (ou '\0' para nós internos)
  int frequency; // Frequência do caractere
  Node left, right; // Filhos esquerdo e direito

  public Node(char character, int frequency) {
    this.character = character;
    this.frequency = frequency;
    this.left = this.right = null;
  }

  public char getCharacter() {
    return character;
  }

  public int getFrequency() {
    return frequency;
  }
}
