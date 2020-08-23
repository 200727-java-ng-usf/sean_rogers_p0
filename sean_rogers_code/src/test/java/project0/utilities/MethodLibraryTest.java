package project0.utilities;

import org.junit.Test;

public class MethodLibraryTest {

    @Test
    public void doubleToUSDFormatTest1() {
        String output = MethodLibrary.doubleToUSDFormat(352.728);
        String expectedOutput = "$352.72";
        assert(output.equals(expectedOutput));
    }

    @Test
    public void doubleToUSDFormatTest2() {
        String output = MethodLibrary.doubleToUSDFormat(52.1);
        String expectedOutput = "$52.10";
        assert(output.equals(expectedOutput));
    }

}
