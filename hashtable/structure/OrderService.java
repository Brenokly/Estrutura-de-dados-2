package hashtable.structure;

import java.time.LocalTime;

public class OrderService {
  private static int contadorCodigo = 1;  // Contador de códigos
  private int codigo;                     // Código do serviço
  private String nome;                    // Nome do serviço
  private String descricao;               // Descrição do serviço
  private LocalTime horaSolicitacao;      // Hora da solicitação

  public OrderService(String nome, String descricao) {
    this.codigo = ++contadorCodigo;
    this.nome = nome;
    this.descricao = descricao;
    this.horaSolicitacao = LocalTime.now().withSecond(0).withNano(0);
  }

  public OrderService(int codigo, String nome, String descricao) {
    if (codigo > contadorCodigo) {
      contadorCodigo = codigo;
    }
    this.codigo = codigo;
    this.nome = nome;
    this.descricao = descricao;
    this.horaSolicitacao = LocalTime.now().withSecond(0).withNano(0);
  }

  /**
   * @return int return the codigo
   */
  public int getContador() {
    return contadorCodigo;
  }

  /**
   * @return int return the codigo
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * @return String return the nome
   */
  public String getNome() {
    return nome;
  }

  /**
   * @param nome the nome to set
   */
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * @return String return the descricao
   */
  public String getDescricao() {
    return descricao;
  }

  /**
   * @param descricao the descricao to set
   */
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  /**
   * @return LocalDateTime return the horaSolicitacao
   */
  public LocalTime getHoraSolicitacao() {
    return horaSolicitacao;
  }

  /**
   * @param horaSolicitacao the horaSolicitacao to set
   */
  public void setHoraSolicitacao(LocalTime horaSolicitacao) {
    this.horaSolicitacao = horaSolicitacao;
  }
}