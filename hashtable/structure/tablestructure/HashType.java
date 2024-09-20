package hashtable.structure.tablestructure;

/*
 * Enumeração que define os tipos de função hash possíveis em uma tabela hash.
*/

public enum HashType {

  // --------------------------------------------------------------------------------

  DIVISION,

  /*
   * Nesse método, a chave é dividida por um número primo, e o resto da divisão
   * é utilizado como índice na tabela hash. Ou seja, a função hash é definida
   * 
   * como: h(k)=k mod(m)
   * 
   * Onde k é a chave e m é o tamanho da tabela (geralmente um número primo).
   * 
   * Quando usar:
   * 1 - Quando o tamanho da tabela hash (m) pode ser escolhido adequadamente.
   * 2 - Para casos em que as chaves são distribuídas de maneira aparentemente aleatória.
   * 3 - Funciona bem quando o tamanho da tabela m é um número primo, o que ajuda a minimizar colisões.
   * 
   * Cenário ideal:
   * Quando você tem controle sobre o tamanho da tabela hash e quer uma função
   * simples e rápida. Funciona bem para números inteiros e em cenários onde a
   * distribuição das chaves não tem um padrão específico.
   */

  // --------------------------------------------------------------------------------

  MULTIPLICATION,

  /*
   * No método da multiplicação, a chave é multiplicada por uma constante fracionária A, 
   * onde 0 < A < 1. A parte fracionária do resultado é multiplicada pelo tamanho da tabela
   * e arredondada para baixo para gerar o índice. A função hash é dada por:
   * 
   * h(k) = ⌊m((k*A) mod 1)⌋
   * 
   * Onde k é a chave, A é a constante fracionária (geralmente relacionada à razão áurea
   * ou outro número irracional), e m é o tamanho da tabela.
   * 
   * Quando usar:
   * 1 - Quando o tamanho da tabela hash não pode ser escolhido como um número primo.
   * 2 - Quando é necessário evitar padrões regulares nas chaves, e a distribuição precisa ser bastante uniforme.
   * 3 - Quando a eficiência e a distribuição uniforme das chaves são prioridades.
   * 
   * Cenário ideal:
   * Esse método é bom para situações onde o tamanho da tabela não pode ser controlado
   * e você precisa de uma boa distribuição de chaves, sem precisar escolher um número
   * primo como tamanho da tabela. É especialmente útil em sistemas com tabelas de hash
   * de tamanho fixo.
   */

  // --------------------------------------------------------------------------------

  FOLDING,

  /*
  * O método da dobra divide a chave em partes, e realiza operações específicas, como 
  * soma ou XOR, entre as partes da chave para gerar o índice da tabela hash. Esse processo 
  * é repetido até que a chave seja reduzida ao tamanho desejado.
  * 
  * Funcionamento:
  * 1. Considere a chave com dígitos d₁, d₂, ..., dJ.
  * 2. A dobra é realizada em torno do k-ésimo dígito à esquerda.
  * 3. O tamanho da chave é reduzido em k dígitos, e uma operação (como XOR) é aplicada
  * entre as partes.
  * 4. As dobras são feitas sucessivamente até obter o endereço-base de k dígitos.
  * 
  * Exemplo:
  * - Dimensão da tabela = 25.
  * - Endereço-base de 5 bits, chaves de 10 bits e operação XOR.
  * 
  * Chave 1: 71 = 00010 00111
  * Chave 2: 46 = 00001 01110
  * 
  * Endereço-base de 71: (00010) XOR (00111) = 00101 = 5
  * Endereço-base de 46: (00001) XOR (01110) = 01111 = 15
  * 
  * Quando usar:
  * 1. Quando as chaves têm muitos dígitos e você deseja combinar diferentes partes
  *    da chave para obter o índice.

  * 2. Para chaves grandes e numéricas, como números de cartão de crédito ou CPF, 
  * onde a soma ou operações como XOR entre as partes podem distribuir as chaves de maneira uniforme.
  * 
  * Cenário ideal:
  * Esse método é ideal para sistemas com chaves longas e padronizadas, especialmente quando 
  * a chave pode ser dividida em partes e manipulada por operações bit a bit, como XOR, para 
  * garantir uma boa dispersão na tabela hash.
  */

  // --------------------------------------------------------------------------------

  ANALYSIS,

  /*
   * O método da análise de dígitos utiliza partes específicas da chave
   * (geralmente dígitos)
   * para construir o índice. Isso é feito selecionando e reorganizando os dígitos
   * da chave.
   * 
   * Quando usar:
   * 1 - Quando as chaves têm muitos dígitos e é mais eficiente usar apenas alguns deles para gerar o índice.
   * 
   * 2 - Quando os dígitos mais significativos ou menos significativos da chave não variam muito
   * e é mais vantajoso usar dígitos do meio.
   * 
   * 3 - Quando a chave possui padrões regulares e uma análise dos dígitos pode fornecer melhor dispersão.
   * 
   * Cenário ideal:
   * Esse método é adequado para sistemas que utilizam chaves numéricas longas e
   * padronizadas, como números de cartão, números de série ou matrículas. Pode ser útil quando
   * certos dígitos da chave são mais informativos ou variáveis do que outros.
   */

  // --------------------------------------------------------------------------------

  DOUBLEHASH;

  /*
   * O método de hash duplo é uma técnica que usa duas funções hash diferentes para
   * calcular o índice da tabela hash. Se h1(k) é a primeira função hash e h2(k) é a
   * segunda função hash, o índice é calculado como:
   * 
   * h(k, i) = (h1(k) + i * h2(k)) mod m
   * 
   * Onde k é a chave, i é o número de tentativas, m é o tamanho da tabela hash e mod
   * é o operador módulo.
   * 
   * Quando usar:
   * 1 - Quando a função hash primária não é suficiente para evitar colisões.
   * 
   * 2 - Quando é necessário garantir uma boa dispersão das chaves, mesmo quando a
   * tabela hash é grande.
   * 
   * 3 - Quando a função hash primária não é uniforme e a segunda função pode ajudar
   * a distribuir as chaves de maneira mais uniforme.
   * 
   * Cenário ideal:
   * Esse método é útil quando a dispersão das chaves é crítica e a função hash primária
   * não é suficiente para evitar colisões e você precisa testar diferentes posições válidas.
   * Assim você garante que para cada chave tenha M posições para ser inserida. Pode ser usado 
   * em conjunto com outras técnicas de tratamento de colisões para garantir uma boa distribuição das chaves.
   */

  // Essas informações acimas foram tiradas de pesquisas realizadas por mim, então podem estar erradas!

  // método para retornar em string o tipo de hash
  public String toString() {
    switch (this) {
      case DIVISION:
        return "Divisão";
      case MULTIPLICATION:
        return "Multiplicação";
      case FOLDING:
        return "Dobra";
      case ANALYSIS:
        return "Análise de dígitos";
      case DOUBLEHASH:
        return "Hash duplo";
      default:
        return "Tipo de hash inválido";
    }
  }
}