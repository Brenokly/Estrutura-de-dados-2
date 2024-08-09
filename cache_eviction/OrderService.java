package cache_eviction;

import java.time.LocalTime;

public class OrderService {
  private static int contadorCodigo = 0;
  private int codigo;
  private String nome;
  private String descricao;
  private LocalTime horaSolicitacao;

  public OrderService(String nome, String descricao) {
    this.codigo = ++contadorCodigo;
    this.nome = nome;
    this.descricao = descricao;
    this.horaSolicitacao = LocalTime.now().withSecond(0).withNano(0);
  }

  // um construtor que recebe o id também, porém só cria com o id recebido se ele
  // for maior que o contador
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