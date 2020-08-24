package project0.utilities;

/**
 * This class provides static methods to facilitate
 */
public class MethodLibrary {

    /**
     * converts a double to a String formatted for currency
     * @param num
     * @return
     */
    public static String doubleToUSDFormat(double num) {

        boolean isNegative = false;
        if(num < 0) {
            num *= -1;
            isNegative = true;
        }

        int intNum = (int)(num * 100);
        StringBuilder sb = new StringBuilder();
        int count = 0;

        while(intNum > 0) {
            if(count == 2) {
                sb.append(".");
            }
            sb.append(intNum % 10);
            intNum /= 10;
            count++;
        }
        sb.append("$");

        if(isNegative) {
            sb.append("-");
        }

        sb.reverse();

        return sb.toString();
    }
}
