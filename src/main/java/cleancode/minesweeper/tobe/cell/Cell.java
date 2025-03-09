package cleancode.minesweeper.tobe.cell;

public interface Cell {

  String FLAG_SIGN = "⚑";
  String UNCHECKED_SIGN = "□";

  boolean isLandMine();
  boolean hasLandMineCount();

  String getSign();


  public void flag();

  public boolean isChecked();


  public void open();

  public boolean isOpened();

}
