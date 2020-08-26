package project0.utilities;

import org.junit.Test;

/**
 * Tests the methods in the method library class
 */
public class MethodLibraryTest {

    /**
     * test double 352.728 converts to $352.72
     */
    @Test
    public void doubleToUSDFormatTest1() {
        String output = MethodLibrary.doubleToUSDFormat(352.728);
        String expectedOutput = "$352.72";
        assert(output.equals(expectedOutput));
    }

    /**
     * test double 52.1 converts to $52.10
     */
    @Test
    public void doubleToUSDFormatTest2() {
        String output = MethodLibrary.doubleToUSDFormat(52.1);
        String expectedOutput = "$52.10";
        assert(output.equals(expectedOutput));
    }

}
