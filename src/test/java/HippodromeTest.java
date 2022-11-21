import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HippodromeTest {

    @Test
    void whenParameterIsNullThenThrowException() {
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
    }

    @Test
    void whenParameterIsNullThenThrowExceptionWithMessage() {
        String exceptedMessage = "Horses cannot be null.";
        try {
            new Hippodrome(null);
        } catch (IllegalArgumentException e) {
            assertEquals(exceptedMessage, e.getMessage());
        }
    }

    @Test
    void whenParameterIsEmptyThenThrowException() {
        List<Horse> emptyList = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, () -> new Hippodrome(emptyList));
    }

    @Test
    void whenParameterIsEmptyThenThrowExceptionWithMessage() {
        String exceptedMessage = "Horses cannot be empty.";
        try {
            new Hippodrome(new ArrayList<>());
        } catch (IllegalArgumentException e) {
            assertEquals(exceptedMessage, e.getMessage());
        }
    }


    @Test
    void mustReturnExpectedList() {
        List<Horse> expectedList = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            expectedList.add(new Horse("name " + i, i, i));
        }
        Hippodrome hippodrome = new Hippodrome(expectedList);
        assertArrayEquals(expectedList.toArray(), hippodrome.getHorses().toArray());
    }

    @Test
    void mustCallMoveMethodForEachHorse() {
            List<Horse> mockedHorses = new ArrayList<>();
            for (int i = 0; i < 50; i++) {
                mockedHorses.add(Mockito.mock(Horse.class));
            }

            Hippodrome hippodrome = new Hippodrome(mockedHorses);
            hippodrome.move();

            for (Horse horse : hippodrome.getHorses()) {
                Mockito.verify(horse).move();
            }
    }

    @Test
    void mustReturnHorseWithMaxDistance() {
        Horse horse1 = new Horse("name1", 1, 2);
        Horse horse2 = new Horse("name2", 1, 5);
        Horse horse3 = new Horse("name3", 1, 4);
        Horse horse4 = new Horse("name4", 1, 3);
        Horse horse5 = new Horse("name5", 1, 1);

        Hippodrome hippodrome = new Hippodrome(List.of(horse1, horse2, horse3, horse4));

        assertEquals(horse2, hippodrome.getWinner());
    }
}