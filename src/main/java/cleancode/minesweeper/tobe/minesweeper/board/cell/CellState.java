package cleancode.minesweeper.tobe.minesweeper.board.cell;

public class CellState {

  private boolean isFlaged;
  private boolean isOpened;

  public CellState(boolean isFlaged, boolean isOpened) {
    this.isFlaged = isFlaged;
    this.isOpened = isOpened;
  }

  public static CellState initialize() {
    return new CellState(false, false);
  }

  public void flag() {
    this.isFlaged = true;
  }

  public boolean isFlaged() {
    return isFlaged;
  }

  public void open() {
    this.isOpened = true;
  }

  public boolean isOpened() {
    return isOpened;
  }
}
