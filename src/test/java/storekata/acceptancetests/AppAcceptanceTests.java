package storekata.acceptancetests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import storekata.App;
import storekata.testdoubles.PrintStreamSpy;

import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppAcceptanceTests {
    private PrintStreamSpy printStreamSpy;
    private PrintStream console;

    @BeforeEach
    public void BeforeEach(){
        console = System.out;
        printStreamSpy = new PrintStreamSpy();
        System.setOut(printStreamSpy);
    }

    @Test
    public void OrderWithMultipleOfAnItemButNoDeal(){
        App.main(new String[]{"2 tins of soup, bought today"});

        assertEquals("1.30", printStreamSpy.lastPrint);
    }

    @AfterEach
    public void AfterEach(){
        System.setOut(console);
    }
}
