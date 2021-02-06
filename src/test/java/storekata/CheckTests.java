package storekata;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class CheckTests {
    @Test
    public void CheckTestsRun(){
        assertFalse(Check.giveMeFalse());
    }
}
