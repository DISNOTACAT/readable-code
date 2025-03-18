package cleancode.minesweeper.tobe.minesweeper.io;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cleancode.minesweeper.tobe.minesweeper.exception.GameException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BoardIndexConverterTest {

  @DisplayName("[BoardIndexConverter] 입력값을 행 인덱스로 변환하여 반환한다.")
  @Test
  void getRowIndexFromUserInput() {
    // given
    BoardIndexConverter converter = new BoardIndexConverter();
    String cellInput = "a1";

    // when
    int rowIndex = converter.getSelectedRowIndex(cellInput);

    // then
    assertThat(rowIndex).isZero();
  }

  @DisplayName("[BoardIndexConverter] 음수를 포함한 입력값은 예외를 반환한다.")
  @Test
  void getRowIndexFromNegativeUserInput() {
    // given
    BoardIndexConverter converter = new BoardIndexConverter();
    String cellInput = "a0";

    // then
    assertThatThrownBy(() -> converter.getSelectedRowIndex(cellInput))
        .isInstanceOf(GameException.class)
        .hasMessageContaining("잘못된 입력입니다.");
  }
}