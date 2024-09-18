package hashtable.structure;

public class HashFunctions {

  // Constantes para o método da multiplicação
  private static final double A = 0.6180339887; // Uma constante irracional

  // Método de hash por divisão
  public static int divisionHash(int key, int size) {
    return key % size;
  }

  // Método de hash por multiplicação
  public static int multiplicationHash(int key, int size) {
    double fractionalPart = (key * A) % 1;
    return (int) (fractionalPart * size);
  }

  // Método de hash por dobra (implementação básica)
  public static int foldingHash(int key, int size) {
    // Converte o key para uma string binária
    String binaryString = Integer.toBinaryString(key);
    int length = binaryString.length();
    int numParts = (int) Math.ceil(length / 8.0); // Dividir em partes de 8 bits

    int hashValue = 0;
    for (int i = 0; i < numParts; i++) {
      int start = length - (i + 1) * 8;
      int end = length - i * 8;
      if (start < 0)
        start = 0;
      String part = binaryString.substring(start, end);
      hashValue ^= Integer.parseInt(part, 2);
    }

    return hashValue % size;
  }

  public static int analysisHash(int key, int size) {
    String keyString = Integer.toString(key);
    int length = keyString.length();

    // Assegura que há pelo menos um dígito para usar
    if (length == 0) {
      return 0;
    }

    // Calcula os índices de início e fim para os dígitos centrais
    int start = Math.max(0, length / 3);
    int end = Math.min(length, 2 * (length / 3));

    // Verifica se o intervalo é válido e não vazio
    if (start >= end || start >= length || end > length) {
      return 0;
    }

    // Extrai os dígitos centrais
    String middleDigits = keyString.substring(start, end);

    // Verifica se a string resultante não está vazia
    if (middleDigits.isEmpty()) {
      return 0;
    }

    // Converte para um inteiro e aplica o módulo
    int middleValue;
    try {
      middleValue = Integer.parseInt(middleDigits);
    } catch (NumberFormatException e) {
      return 0; // Retorna 0 se a conversão falhar
    }

    return middleValue % size;
  }
}