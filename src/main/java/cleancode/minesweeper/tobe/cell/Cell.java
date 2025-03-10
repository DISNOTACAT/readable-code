package cleancode.minesweeper.tobe.cell;

public interface Cell {

  boolean isLandMine();
  boolean hasLandMineCount();

  CellSnapshot getSnapshot();

  public void flag();

  public boolean isChecked();


  public void open();

  public boolean isOpened();

}
