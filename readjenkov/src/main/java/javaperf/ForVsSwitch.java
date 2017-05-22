package javaperf;

/**
 * @Author Sanmu
 * @Date 2017/5/19 0019.
 */
public class ForVsSwitch {

    public static void main(String[] args) {
        byte[] bytes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int iterations = 8;
        System.out.println(sumWithSwitch(bytes, iterations));
    }

    private static int sumWithSwitch(byte[] bytes, int iterations) {
        int result = 0;
        int index = 0;

        switch (iterations) {
            case 16:
                result += bytes[index++];
            case 15:
                result += bytes[index++];
            case 14:
                result += bytes[index++];
            case 13:
                result += bytes[index++];
            case 12:
                result += bytes[index++];
            case 11:
                result += bytes[index++];
            case 10:
                result += bytes[index++];
            case 9:
                result += bytes[index++];
            case 8:
                result += bytes[index++];
            case 7:
                result += bytes[index++];
            case 6:
                result += bytes[index++];
            case 5:
                result += bytes[index++];
            case 4:
                result += bytes[index++];
            case 3:
                result += bytes[index++];
            case 2:
                result += bytes[index++];
            case 1:
                result += bytes[index];
        }
        return result;
    }

}
