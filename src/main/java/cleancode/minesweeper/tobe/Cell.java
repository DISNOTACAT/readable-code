package cleancode.minesweeper.tobe;

public class Cell {

  private static final String FLAG_SIGN = "⚑";
  private static final String LAND_MINE_SIGN = "☼";
  private static final String UNCHECKED_SIGN = "□";
  private static final String EMPTY_SIGN = "■";

  private int nearbyLandMineCount;
  private boolean isLandMine;
  private boolean isFlaged;
  private boolean isOpened;


  private Cell(int nearbyLandMineCount, boolean isLandMine, boolean isFlaged, boolean isOpened) {
    this.nearbyLandMineCount = nearbyLandMineCount;
    this.isLandMine = isLandMine;
    this.isFlaged = isFlaged;
    this.isOpened = isOpened;
  }

  public static Cell of(int nearbyLandMineCount, boolean isLandMine, boolean isFlaged, boolean isOpened) {
    return new Cell(nearbyLandMineCount, isLandMine, isFlaged, isOpened);
  }

  public static Cell create() {
    return of(0, false, false, false);
  }

  public void turnOnLandMine() {
    this.isLandMine = true;
  }

  public void flag() {
    this.isFlaged = true;
  }

  public boolean isChecked() {
    return isFlaged || isOpened;
  }

  public boolean isLandMine() {
    return isLandMine;
  }

  public void open() {
    this.isOpened = true;
  }

  public boolean isOpened() {
    return isOpened;
  }

  public void updateNearbyLandMineCount(int count) {
    this.nearbyLandMineCount = count;
  }

  public boolean hasLandMineCount() {
    return nearbyLandMineCount > 0;
  }

  public String getSign() {

    if(isOpened) {
      if(isLandMine) {
        return LAND_MINE_SIGN;
      }

      if(hasLandMineCount()) {
        return String.valueOf(nearbyLandMineCount);
      }

      return EMPTY_SIGN;
    }

    if(isFlaged) {
      return FLAG_SIGN;
    }

    return UNCHECKED_SIGN;
  }
}
