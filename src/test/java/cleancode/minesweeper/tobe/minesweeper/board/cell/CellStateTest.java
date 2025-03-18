package cleancode.minesweeper.tobe.minesweeper.board.cell;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CellStateTest {

  @DisplayName("[CellState] CellState 초기화시 flag와 open은 false 상태이다.")
  @Test
  void test() {
    // given & when
    CellState cellState = CellState.initialize();

    // then
    assertThat(cellState.isFlaged()).isFalse();
    assertThat(cellState.isOpened()).isFalse();

  }

}