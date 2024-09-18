package hashtable.structure;

import java.time.LocalTime;

public class OrderService {
  private static int counterCode = 0; // Contador de códigos
  private int code; // Código do serviço
  private String name; // name do serviço
  private String description; // Descrição do serviço
  private LocalTime requestTime; // Hora da solicitação

  public OrderService(String name, String description) {
    this.code = counterCode++;
    this.name = name;
    this.description = description;
    this.requestTime = LocalTime.now().withSecond(0).withNano(0);
  }

  public OrderService(int code, String name, String description) {
    if (code > counterCode) {
      counterCode = code;
    }
    this.code = code;
    this.name = name;
    this.description = description;
    this.requestTime = LocalTime.now().withSecond(0).withNano(0);
  }

  /**
   * @return int return the code
   */
  public int getContadorCode() {
    return counterCode;
  }

  /**
   * @return int return the code
   */
  public int getCode() {
    return code;
  }

  /**
   * @return String return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return String return the description
   */
  public String getDescription() {
    return description;
  }

  /**
   * @param description the description to set
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @return LocalDateTime return the requestTime
   */
  public LocalTime getRequestTime() {
    return requestTime;
  }

  /**
   * @param requestTime the requestTime to set
   */
  public void setRequestTime(LocalTime requestTime) {
    this.requestTime = requestTime;
  }
}