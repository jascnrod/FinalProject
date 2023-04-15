import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int x = 0, INPUTMAX = 5;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Knight> list = new ArrayList<Knight>();// Array list adds objects to it infinitely no need to
        while (x != 7) {
            int count = 0;// goes through input array
            String[] userInputStr = new String[INPUTMAX];
            System.out.println(
                "1: Add a new faculty to the schedule.\n" +
                "2: Enroll a student to a lecture (and to of of its labs if applicable)\n" +
                "3: Print the Schedule of a faculty.\n" +
                "4: Print the schedule of an TA.\n" +
                "5: Print the schedule of a student.\n" +
                "6: Delete a scheduled lecture\n" +
                "7: Exit Program");
                System.out.println("Type the number for what you want to do.");
                x = scanner.nextInt();
            
            //There needs to be exception handling the entry of the wrong numbers 
            switch (x) {
                case 1:
                    System.out.print("Enter UCF id:");
                    userInputStr[count] = scanner.nextLine();
                    count++;
                    // needs to check if id already exists
                    System.out.print("Enter rank:");
                    userInputStr[count] = scanner.nextLine();
                    count++;
                    System.out.print("Enter rank");
                    userInputStr[count] = scanner.nextLine();
                    count++;
                    System.out.print("Enter office location:");
                    userInputStr[count] = scanner.nextLine();
                    count++;
                    System.out.print("Enter how many lectures");
                    userInputStr[count] = scanner.nextLine();
                    count++;
                    System.out.print("Enter the crns of the lectures to assign to this faculty");
                    userInputStr[count] = scanner.nextLine();

                    //   If a lecture has labs, ask to enter the UCF id of the TA for each lab (a TA
                    //   may do more than one Lab). This may require entering a new TA to the system,
                    //   and in this case, you need to ask for the remaining information of the TA.
                    //   Keep in mind that a TA can be a student.
                    
                    break;
                case 2:
                    count = 0;// goes through input array
                    userInputStr = new String[INPUTMAX];
                    System.out.print("Enter UCF id:");
                    userInputStr[count] = scanner.nextLine();
                    count++;
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

// this is the top dog, everything will inherit from this. the Array list will
// be initialized as a Identity
abstract class Knight {
    private int ucfid = 0;
    private String name;

    public int getUcfid() {
        return ucfid;
    }

    public void setUcfid(int ucfid) {
        this.ucfid = ucfid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

class Student extends Knight {
    // student specific info Type:grad,undergrad
    // possible list of lec/lab attended

}

class TA extends Student {
    // TA specific info:
    // List of labs supervised
    // Advisor
    // Expected degree
    // possible list of lec/lab attended

}

class Faculty extends Knight {
    // Faculty specific:
    // Rank
    // List of lectures taught
    private String rank;
    private String[] lectureArr;

}