package cleancode.minesweeper.tobe.cell;

public class NumberCell extends cleancode.minesweeper.tobe.cell.Cell {

  private final int nearbyLandMineCount;

  public NumberCell(int count) {
    this.nearbyLandMineCount = count;
  }

  @Override
  public boolean isLandMine() {
    return false;
  }

  @Override
  public boolean hasLandMineCount() {
    return true;
  }

  @Override
  public String getSign() {
    if(isOpened()) {
      return String.valueOf(nearbyLandMineCount);
    }

    if(isFlaged) {
      return FLAG_SIGN;
    }
    return UNCHECKED_SIGN;
  }
}
