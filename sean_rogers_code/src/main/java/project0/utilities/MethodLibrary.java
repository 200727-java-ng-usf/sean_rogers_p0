package project0.utilities;

public class MethodLibrary {

    public static String doubleToUSDFormat(double num) {
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
        sb.reverse();

        return sb.toString();
    }
}
