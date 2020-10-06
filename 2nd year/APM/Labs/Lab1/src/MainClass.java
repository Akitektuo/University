public class MainClass {

    public static void main(String[] args) {
        float sum = 0;
        int count = 0;

        for (String argument : args) {
            try {
                float number = Float.parseFloat(argument);
                sum += number;
                count++;
            } catch (Exception exception) {
                System.out.println("Error parsing '" + argument + "' to int, exception:\n" + exception.toString() + "\n");
            }
        }

        System.out.println("The average is: " + sum / count);
    }
}
