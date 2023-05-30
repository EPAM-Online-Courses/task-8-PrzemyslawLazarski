package efs.task.unittests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.*;


import static org.junit.jupiter.api.Assertions.*;

class FitCalculatorTest {

    @Test
    void shouldReturnTrue_whenDietRecommended() {
        //given
        double weight = 89.2;
        double height = 1.72;

        //when
        boolean recommended = FitCalculator.isBMICorrect(weight, height);

        //then
        assertTrue(recommended);
    }
    @Test
    void shouldReturnFalse_whenBMIIsNotCorrect() {
        //given
        double weight = 69.5;
        double height = 1.72;
        //when
        boolean state = FitCalculator.isBMICorrect(weight, height);
        //then
        assertFalse(state);
    }

    @Test
    void shouldExcept_IllegalArgumentException() {
        //given
        double weight = 69.5;
        double height = 0;
        //then
        assertThrows(IllegalArgumentException.class, () -> FitCalculator.isBMICorrect(weight, height));
    }

    @ParameterizedTest(name = "waga: {0}")
    @ValueSource(doubles = {84.2, 86.2, 88.2})
    void shouldReturnTrue_whenBMIIsCorrect(double weight) {
        //given
        double height = 1.73;
        //when
        boolean state = FitCalculator.isBMICorrect(weight, height);
        //then
        assertTrue(state);
    }

    @ParameterizedTest(name = "waga: {0}, wzrost: {1}")
    @CsvSource({"60.6,2.05", "50.5,1.89", "40.4,1.99"})
    void shouldReturnFalse_ForAllCsvSource(double weight, double height) {
        //when
        boolean state = FitCalculator.isBMICorrect(weight, height);
        //then
        assertFalse(state);
    }

    @ParameterizedTest(name = "waga: {0}, wzrost: {1}")
    @CsvFileSource(resources = "/data.csv", numLinesToSkip = 1)
    void shouldReturnFalse_ForDataFromCSV(double weight, double height) {
        //when
        boolean fromCSV = FitCalculator.isBMICorrect(weight, height);
        //then
        assertFalse(fromCSV);
    }

    @Test
    void shouldReturnUserWithTheWorstBMI() {
        //given
        double weight = 97.3;
        double height = 1.79;
        //when
        User user = FitCalculator.findUserWithTheWorstBMI(TestConstants.TEST_USERS_LIST);
        //then
        assertAll("Should return user with the worst BMI",
                () -> assertEquals(weight, user.getWeight()),
                () -> assertEquals(height, user.getHeight()
                ));
    }

    @Test
    void shouldReturnNullForEmptyUsersList() {
        //when
        User user = FitCalculator.findUserWithTheWorstBMI(Collections.emptyList());
        //then
        assertNull(user);
    }

    @Test
    void shouldReturnTestUsersBMIScore() {
        //given
        double[] BMIScore = new double[TestConstants.TEST_USERS_LIST.size()];
        //when
        BMIScore = FitCalculator.calculateBMIScore(TestConstants.TEST_USERS_LIST);
        //then
        assertArrayEquals(TestConstants.TEST_USERS_BMI_SCORE, BMIScore);
    }

}