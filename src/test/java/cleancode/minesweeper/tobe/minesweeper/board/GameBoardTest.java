package cleancode.minesweeper.tobe.minesweeper.board;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import cleancode.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;
import cleancode.minesweeper.tobe.minesweeper.board.position.CellPosition;
import cleancode.minesweeper.tobe.minesweeper.gameLevel.Beginner;
import cleancode.minesweeper.tobe.minesweeper.gameLevel.GameLevel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameBoardTest {
  @DisplayName("[GameBoard] 깃발 액션 시, 동일한 위치의 셀 상태는 FLAG로 변경된다.")
  @Test
  void doesSnapshotChangedToFlag() {
    GameBoard gameBoard = gameInitWithBeginnerLevel();
    // given
    CellPosition cellPosition = CellPosition.of(0,0);

    // when
    CellSnapshot snapshot = gameBoard.getSnapshot(cellPosition);
    gameBoard.flagAt(cellPosition);
    CellSnapshot resultSnapshot = gameBoard.getSnapshot(cellPosition);

    // then
    assertThat(resultSnapshot.getStatus()).isNotEqualTo(snapshot.getStatus());
    assertThat(resultSnapshot.getStatus()).isEqualTo(CellSnapshotStatus.FLAG);
  }

  @DisplayName("[GameBoard] 모든 셀에 깃발 액션 시, 게임은 진행 중 상태를 유지한다.")
  @Test
  void isProgressingWhenflagAtAllCell() {
    // given
    GameBoard gameBoard = gameInitWithBeginnerLevel();

    boolean progressGameStatus = gameBoard.isInProgress();

    // when
    for(int i = 0; i < gameBoard.getRowSize(); i++) {
      for(int j = 0; j < gameBoard.getColSize(); j++) {
        CellPosition cellPosition = CellPosition.of(i,j);
        gameBoard.flagAt(cellPosition);
      }
    }
    boolean resultGameStatus = gameBoard.isInProgress();

    // then
    assertThat(resultGameStatus).isEqualTo(progressGameStatus);
    assertThat(gameBoard.isWinStatus()).isFalse();
  }

  @DisplayName("[GameBoard] 보드 범위 밖의 인덱스를 open 할 경우, 의도하지 않은 예외를 발생시킨다.")
  @Test
  void getExceptionFromOutOfBoardSize() {
    // given
    GameBoard gameBoard = gameInitWithBeginnerLevel();
    int outOfRowSize = new Beginner().getRowSize();
    CellPosition cellPosition = CellPosition.of(outOfRowSize, 0);

    // when & then
    assertThatThrownBy(() -> gameBoard.openAt(cellPosition))
        .isInstanceOf(Exception.class);
  }

  @DisplayName("[GameBoard] 지뢰인 셀을 열면 게임 상태는 실패, 지뢰가 아니면 실패하지 않음")
  @Test
  void isLoseWhenOpenCell() {
    // given
    GameBoard gameBoard = gameInitWithBeginnerLevel();
    CellPosition cellPosition = CellPosition.of(0, 0);
    CellSnapshotStatus cellState = gameBoard.getSnapshot(cellPosition).getStatus();

    // when
    gameBoard.openAt(cellPosition);
    boolean isLose = gameBoard.isLoseStatus();

    // then
    if(cellState != CellSnapshotStatus.LAND_MINE) {
      assertThat(isLose).isFalse();
    } else {
      assertThat(isLose).isTrue();
    }
  }

  private static GameBoard gameInitWithBeginnerLevel() {
    GameLevel gameLevel = new Beginner();
    GameBoard gameBoard = new GameBoard(gameLevel);
    gameBoard.initializeGame();
    return gameBoard;
  }

}