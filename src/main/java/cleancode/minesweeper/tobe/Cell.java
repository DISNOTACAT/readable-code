package cleancode.minesweeper.tobe;

public class Cell {

  private final String sign;

  private Cell(String sign) {
    this.sign = sign;
  }

  public static Cell of(String sign) {
    return new Cell(sign);
  }
}
