package cleancode.minesweeper.tobe.io;

import cleancode.minesweeper.tobe.BoardIndexConverter;
import cleancode.minesweeper.tobe.position.CellPosition;
import cleancode.minesweeper.tobe.user.UserAction;
import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler {

  public static final Scanner SCANNER = new Scanner(System.in);
  private final BoardIndexConverter converter = new BoardIndexConverter();

  @Override
  public CellPosition getCellPositionFromUser() {
    String userInput = SCANNER.nextLine();

    int colIndex = converter.getSelectedColIndex(userInput);
    int rowIndex = converter.getSelectedRowIndex(userInput);
    return CellPosition.of(colIndex, rowIndex);
  }

  @Override
  public UserAction getUserActionFromUser() {
    String userInput = SCANNER.nextLine();

    if("1".equals(userInput)) {
      return UserAction.OPEN;
    }

    if("2".equals(userInput)) {
      return UserAction.FLAG;
    }

    return UserAction.UNKNOWN;
  }

}
