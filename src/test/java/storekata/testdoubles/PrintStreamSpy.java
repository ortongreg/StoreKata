package storekata.testdoubles;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PrintStreamSpy extends PrintStream {
    public String lastPrint;

    public PrintStreamSpy() {
        super(new ByteArrayOutputStream());
    }

    @Override
    public void println(String message) {
        lastPrint = message;
    }
}
