package baseball;

import baseball.util.exception.DuplicateInputNumberException;
import baseball.util.exception.InputOutOfRangeException;
import baseball.util.exception.InvalidInputException;
import baseball.util.validator.UserInputValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static baseball.util.constant.ExceptionMessageConstant.*;
import static org.assertj.core.api.Assertions.*;

public class UserTest {

    private UserInputValidator userInputValidator;

    @BeforeEach
    public void setUp() {
        userInputValidator = new UserInputValidator();
    }

    @ParameterizedTest
    @DisplayName("숫자 외 다른 문자가 포함된 입력 예외처리")
    @ValueSource(strings = {"a31", "!?a", "인풋", "92a", "-13"})
    void notNumber(String input) {
        validateInputWithExceptionAndMessage(input, InvalidInputException.class, INVALID_INPUT_EXCEPTION);
    }

    @ParameterizedTest
    @DisplayName("중복된 숫자가 입력되었을 때 예외처리")
    @ValueSource(strings = {"445", "141", "633"})
    void duplicateNumber(String input) {
        validateInputWithExceptionAndMessage(input, DuplicateInputNumberException.class, DUPLICATED_NUMBER_EXCEPTION);
    }

    @ParameterizedTest
    @DisplayName("숫자에 0이 포함되었을 때 예외처리")
    @ValueSource(strings = {"031", "029", "490"})
    void hasZeroNumber(String input) {
        validateInputWithExceptionAndMessage(input, InvalidInputException.class, INVALID_INPUT_EXCEPTION);
    }

    @ParameterizedTest
    @DisplayName("범위 밖 숫자에 대한 예외처리")
    @ValueSource(strings = {"13", "48912", "2359"})
    void outOfRangeNumber(String input) {
        validateInputWithExceptionAndMessage(input, InputOutOfRangeException.class, OUT_OF_RANGE_EXCEPTION);
    }

    private void validateInputWithExceptionAndMessage(String input, Class<? extends Exception> e, String message) {
        assertThatThrownBy(() -> userInputValidator.validate(input))
                .isInstanceOf(e)
                .hasMessageMatching(message);
    }
}
