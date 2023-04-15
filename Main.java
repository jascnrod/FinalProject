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
            /*
             * There needs to be exception handiling the entry of the wrong numbers
             */
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

//this is the top dog, everything will inherit from this. the Array list will be initialized as a Identity
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
//student specific info Type:grad,undergrad
//possible list of lec/lab attended


}

class TA extends Student {
//TA specific info: 
//List of labs supervised
//Advisor
//Expected degree
//possible list of lec/lab attended


    
}

class Faculty extends Knight {
//Faculty specific:
//Rank
//List of lectures taught

    
}





