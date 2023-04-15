/* COP3330 Final Project
 * Maria Tran, Gabriel Roca, Jason Rodriguez
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int x = 0, INPUTMAX = 6;
        String path;
        Scanner scanner = new Scanner(System.in);
        List list = new List();
        System.out.println("Enter the absolute path of the file: ");
        path = scanner.nextLine();
        //need to check if the file that has lectures
        
        while (x != 7) {
            int count = 0;// goes through input array
            Boolean check = true;
            String garbagestr;
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
                garbagestr = scanner.nextLine();
            //There needs to be exception handling the entry of the wrong numbers 
            switch (x) {
                case 1:
                    Faculty tempFukalty = new Faculty();
                    //there needs to be exception handling for the input of incorrect info
                    System.out.print("Enter UCF id:");
                    userInputStr[count] = scanner.nextLine();
                    check = list.checkId(Integer.parseInt(userInputStr[count]));
                    count++;
                    if (check){
                        // needs to check if id already exists
                        System.out.print("Enter name: ");
                        userInputStr[count] = scanner.nextLine();
                        count++;
                        System.out.print("Enter rank: ");
                        userInputStr[count] = scanner.nextLine();
                        count++;
                        System.out.print("Enter office location: ");
                        userInputStr[count] = scanner.nextLine();
                        count++;
                        System.out.print("Enter how many lectures: ");
                        userInputStr[count] = scanner.nextLine();
                        count++;
                        System.out.print("Enter the crns of the lectures to assign to this faculty: ");
                        //Crns are seperated by spaces from user
                        userInputStr[count] = scanner.nextLine();
                        tempFukalty.add(userInputStr,count);
                        list.facultyAdd(tempFukalty);
                    }
                    else {
                        System.out.print("Enter how many lectures: ");
                        userInputStr[count] = scanner.nextLine();
                        count++;
                        System.out.print("Enter the crns of the lectures to assign to this faculty: ");
                        //Crns are seperated by spaces from user
                        //need to check if crn is assigned, if assigned change num of lectures
                        userInputStr[count] = scanner.nextLine();
                        tempFukalty.add(userInputStr,count);
                        list.facultyAdd(tempFukalty);

                    }
                    

                    


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
                    System.out.print("Enter name:");
                    userInputStr[count] = scanner.nextLine();
                    count++;
                    System.out.print("Enter the crns of the lectures to assign to this faculty");
                    // Crns are seperated by spaces from user
                    userInputStr[count] = scanner.nextLine();
                    // if a lecture requires a lab, randomly pick a lab for the student (no caps on
                    // how many students to enroll in a lab) (Use the built in Java random
                    // generation of a number)
                    break;

                case 3:
                    count = 0;// goes through input array
                    userInputStr = new String[INPUTMAX];
                    System.out.print("Enter UCF id:");
                    userInputStr[count] = scanner.nextLine();
                    count++;
                    // Then Print the UCF id, name and the crns of the lectures taught by the
                    // faculty (No need to display anything else).

                    break;
                case 4:
                    count = 0;// goes through input array
                    userInputStr = new String[INPUTMAX];
                    System.out.print("Enter UCF id:");
                    userInputStr[count] = scanner.nextLine();
                    count++;
                    // Then Print the UCF id, name and the crns of the lectures taught by the
                    // faculty (No need to display anything else).
                    break;
                case 5:
                    count = 0;// goes through input array
                    userInputStr = new String[INPUTMAX];
                    System.out.print("Enter UCF id:");
                    userInputStr[count] = scanner.nextLine();
                    count++;
                    // Then Print the UCF id, name and the crns of the lectures taught by the
                    // faculty (No need to display anything else).
                    break;
                case 6:
                    // Deleting a lecture requires deleting its labs as well and updating any
                    // faculty/student record who is teaching/taking that lecture
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

class TeachingAssistant extends Student {
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
    private String rank,office;
    private String[] lectureArr;
    private int numLectures;

    public void add (String[] bonk , int length) {
        int count = 0;
        switch (length) {
            case 5:
                setUcfid(Integer.parseInt(bonk[count]));
                count++;
                setName(bonk[count]);
                count++;
                setRank(bonk[count]);
                count++;
                setOffice(bonk[count]);
                count++;
                setNumLectures(Integer.parseInt(bonk[count]));
                count++;
                setLectureArr((bonk[count]).split(" "));
                break;
            case 2:
                //if the faculty already exists
                break;
            
        
        }

    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public int getNumLectures() {
        return numLectures;
    }

    public void setNumLectures(int numLectures) {
        this.numLectures = numLectures;

    }

    public String getRank() {
        return rank;
    }
    public void setRank(String rank) {
        this.rank = rank;
    }
    public String[] getLectureArr() {
        return lectureArr;
    }
    public void setLectureArr(String[] lectureArr) {
        this.lectureArr = lectureArr;
    }
    
}
class List {
    private static ArrayList<Knight> list = new ArrayList<Knight>();// Array list adds objects to it infinitely no need to update length
    
    public boolean checkId (int id) {

        for (Knight e : list) {
            if (e.getUcfid() != id) {    
                return true;
            }
        }

        return false;
    }
    public void facultyAdd(Faculty tempFaculty) {
        list.add(tempFaculty);

    }
    
    public void studentAdd(Student tempStudent) {
        list.add(tempStudent);

    }
    public void teachingassistantAdd(TeachingAssistant tempTA) {
        list.add(tempTA);

    }

    public ArrayList<Knight> getList() {
        return list;
    }

    public void setList(ArrayList<Knight> list) {
        this.list = list;
    }

}