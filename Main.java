import java.util.Scanner;

public class Main {
    public static void main ( String []  args) {
       int x = 0 ;
       Scanner scanner = new Scanner(System.in);
       while(x != 7){
            System.out.println(
            "1: Add a new faculty to the schedule.\n" +
            "2: Enroll a student to a lecture (and to of of its labs if applicable)\n"+
            "3: Print the Schedule of a faculty.\n" +
            "4: Print the schedule of an TA.\n" +
            "5: Print the schedule of a student.\n" +
            "6: Delete a scheduled lecture\n" +
            "7: Exit Program");
            System.out.println("Type the number for what you want to do.");
            x = scanner.nextInt();
            switch (x) {
                case 1:
                break;
                case 2:
                break;
                case 3:
                break;
                case 4:
                break;
                case 5:
                break;
                case 6:
                break;
                case 7:
                break;
                
            }
            
        }
        scanner.close();
    }
}

