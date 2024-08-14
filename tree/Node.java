package tree;

public class Node {
  int key;
  String codigo;
  Node left, right;

  public Node(int key) {
    this.key = key;
    left = right = null;
  }

  public Node(int key, String codigo) {
    this.key = key;
    this.codigo = codigo;
    left = right = null;
  }

  public Node(int key, String codigo, Node right, Node left) {
    this.key = key;
    this.codigo = codigo;
    this.right = right;
    this.left = left;
  }

  public Node() {
  }

  /**
   * @return int return the key
   */
  public int getKey() {
    return key;
  }

  /**
   * @return String return the codigo
   */
  public String getCodigo() {
    return codigo;
  }
}