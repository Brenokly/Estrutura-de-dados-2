package cache_eviction_final.structure;

import cache_eviction_final.compression.HuffmanTree;
import cache_eviction_final.compression.Node;
import java.util.Map;

/*
 * A classe Mensagem é responsável por armazenar as informações que devem ser passadas para o servidor.
 * Servidor recebe as informações contidas na mensagme e executa a operação solicitada.
 * A mensagem deve ser enviada comprimida. Então, vamos aplicar o algoritmo de Huffman para comprimir a mensagem.
 * 
 * A classe Mensagem possui os seguintes atributos:
 * - huffman: Instância da árvore de Huffman
 * - huffmanRoot: Raiz da árvore de Huffman
 * - encodedMessage: Mensagem comprimida
 * 
 * Quando eu preciso enviar uma mensagem para o servidor, eu crio uma instância da classe Mensagem e passo a mensagem como parâmetro.
 * A classe Mensagem comprime a mensagem e armazena a mensagem comprimida no atributo encodedMessage.
 * Quando o servidor recebe a mensagem, ele descomprime a mensagem e executa a operação solicitada.
 */

public class Message {
  private HuffmanTree huffman; // Instância da árvore de Huffman
  private Node huffmanRoot; // Raiz da árvore de Huffman
  private String encodedMessage; // Mensagem comprimida

  // Construtor da classe Message
  public Message(String message) {
    this.huffman = new HuffmanTree(); // Inicializa a árvore de Huffman
    this.huffmanRoot = huffman.buildHuffmanTree(message); // Constrói a árvore de Huffman
    this.encodedMessage = compress(message); // Comprime a mensagem
  }

  // ------------------------------------------------------------------------------------------------------------
  // Compressão e descompressão da mensagem

  // Método para comprimir a mensagem
  private String compress(String message) {
    // Se a mensagem tiver apenas um caractere
    if (message.length() == 1) {
      // Retorna um código fixo (ex: "0") para o único caractere
      return Character.toString(huffmanRoot.getCharacter());
    }

    Map<Character, String> codes = huffman.getHuffmanCodes(); // Obtém os códigos de Huffman
    StringBuilder encodedString = new StringBuilder(); // StringBuilder para a mensagem codificada

    for (char c : message.toCharArray()) {
      encodedString.append(codes.get(c)); // Adiciona o código correspondente ao caractere
    }

    return encodedString.toString(); // Retorna a mensagem codificada
  }

  // Método para descomprimir a mensagem
  public String decompress() {
    return huffman.decompress(huffmanRoot, encodedMessage); // Usa a árvore de Huffman para descomprimir
  }

  // ------------------------------------------------------------------------------------------------------------
  // Getters
  public String getEncodedMessage() {
    return encodedMessage; // Retorna a mensagem comprimida
  }
}