import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class HorseTest {

    @Test
    void whenNameParameterIsNullThenGenerateException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    void whenNameParameterIsNullThenGenerateExceptionWithMessage() {
        String expectedMessage = "Name cannot be null.";
        try {
            new Horse(null, 1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"\t", "\n", " ", "", "\f", "\r"})
    void whenNameParameterIsEmptySymbolThenGenerateException(String name) {
        assertThrows(IllegalArgumentException.class, () -> new Horse(name, 1, 1));
    }

    @ParameterizedTest
    @ValueSource(strings = {"\t", "\n", " ", "", "\f", "\r"})
    void whenNameParameterIsEmptySymbolThenGenerateExceptionWithMessage(String name) {
        String expectedMessage = "Name cannot be blank.";
        try {
            new Horse(name, 1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void whenSpeedParameterIsLessZeroThenGenerateException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("name", -1, 1));
    }

    @Test
    void whenSpeedParameterIsLessZeroThenGenerateExceptionWithMessage() {
        String expectedMessage = "Speed cannot be negative.";
        try {
            new Horse("name", -1, 1);
        } catch (IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void whenDistanceParameterIsLessZeroThenGenerateException() {
        assertThrows(IllegalArgumentException.class, () -> new Horse("name", 1, -1));
    }

    @Test
    void whenDistanceParameterIsLessZeroThenGenerateExceptionWithMessage() {
        String expectedMessage = "Distance cannot be negative.";
        try {
            new Horse("name", 1, -1);
        } catch (IllegalArgumentException e) {
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    void mustReturnExpectedName() {
        String expectedName = "name";
        Horse horse = new Horse(expectedName, 50, 100);
        assertEquals(expectedName, horse.getName());
    }

    @Test
    void mustReturnExpectedSpeed() {
        double expectedSpeed = 10.25d;
        Horse horse = new Horse("name", expectedSpeed, 100);
        assertEquals(expectedSpeed, horse.getSpeed());
    }

    @Test
    void mustReturnExpectedDistance() {
        double expectedDistance = 100.2d;
        Horse horse = new Horse("name", 50, expectedDistance);
        assertEquals(expectedDistance, horse.getDistance());
    }

    @Test
    void mustReturnZeroWhenConstructorWithoutDistanceParameter() {
        Horse horse = new Horse("name", 50);
        assertEquals(0, horse.getDistance());
    }

    @Test
    void getRandomDoubleMethodWithGivenParameters() {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            new Horse("name", 1, 1).move();
            horseMockedStatic.verify(() -> Horse.getRandomDouble(0.2, 0.9));
        }
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8})
    void moveWithRandomParameters(double result) {
        try (MockedStatic<Horse> horseMockedStatic = Mockito.mockStatic(Horse.class)) {
            horseMockedStatic.when(() -> Horse.getRandomDouble(0.2, 0.9)).thenReturn(result);
            double horseSpeed = 10.0;
            double horseDistance = 100.0;
            double expected = horseDistance + horseSpeed * result;

            Horse horse = new Horse("name", 10.0, 100.0);
            horse.move();

            assertEquals(expected, horse.getDistance());
        }
    }
}