package dev.app.test.utility;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilityTests {
    @Test
    public void verifyExtractAskResult() {
        String input = "1-10 of 5,480,000,000 results";
        Long actualValue = Utility.extractAskResult(input);
        assertEquals(5480000000L, actualValue);
    }

    @Test
    public void verifyExtractSogouResult(){
        String input = "搜狗已为您找到约1,652,402条相关结果";
        Long actualValue = Utility.extractSogouResult(input);
        assertEquals(1652402L, actualValue);
    }

    @Test
    public void verifyExtractAskResultWhenInputNotCorrect() {
        String input = "1-10";
        Long actualValue = Utility.extractAskResult(input);
        assertEquals(0L, actualValue);
    }

    @Test
    public void verifyExtractSogouResultWhenInputNotCorrect() {
        String input = "搜狗已为您找到约";
        Long actualValue = Utility.extractSogouResult(input);
        assertEquals(0L, actualValue);
    }
}
