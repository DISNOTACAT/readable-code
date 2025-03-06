package cleancode.minesweeper.tobe.cell;

public abstract class Cell {

  protected static final String FLAG_SIGN = "⚑";
  protected static final String UNCHECKED_SIGN = "□";

  protected boolean isFlaged;
  protected boolean isOpened;

  public abstract boolean isLandMine();

  public abstract boolean hasLandMineCount();

  public abstract String getSign();


  public void flag() {
    this.isFlaged = true;
  }

  public boolean isChecked() {
    return isFlaged || isOpened;
  }


  public void open() {
    this.isOpened = true;
  }

  public boolean isOpened() {
    return isOpened;
  }



}
