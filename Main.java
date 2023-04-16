
/* COP3330 Final Project
 * Maria Tran, Gabriel Roca, Jason Rodriguez
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int x = 0, INPUTMAX = 6;
        String path;
        Scanner scanner = new Scanner(System.in);
        List list = new List();
        System.out.println("Enter the absolute path of the file: ");
        path = scanner.nextLine();
        // need to check if the file that has lectures

        while (x != 7) {
            int count = 0, count2 = 0;
            // goes through input array
            Boolean check = true;
            String garbagestr;
            String[] userInputStr = new String[INPUTMAX], userInputStr2 = new String[INPUTMAX];
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
            // There needs to be exception handling the entry of the wrong numbers
            switch (x) {
                case 1:
                    Faculty tempFukalty = new Faculty();
                    TeachingAssistant tempTA = new TeachingAssistant();
                    // there needs to be exception handling for the input of incorrect info
                    System.out.print("Enter UCF id:");
                    userInputStr[count] = scanner.nextLine();
                    check = list.checkId(Integer.parseInt(userInputStr[count]));
                    if (!check) {
                        count++;
                        // this is for if the ucf id is new
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
                        // Crns are seperated by spaces from user
                        userInputStr[count] = scanner.nextLine();
                        //keeps a copy of the the crns 
                        String[] lecturesSplit = (userInputStr[count].split(" "));
                        //the string comes back with null in place of the lecture that is already assigned
                        String[] availiableLectures = list.checkLecture(userInputStr[count].split(" "));
                        // maybe turn this into a exception catch
                        for (int j = 0; j < lecturesSplit.length; j++) {
                            //if these two are different than we gotta get rid of the taken lecture
                            if (!(lecturesSplit[j].equals(availiableLectures[j]))) {
                                userInputStr[count] = null;
                                for (int i = 0; i < lecturesSplit.length; i++) {
                                    if (!lecturesSplit[i].equals(availiableLectures[i]))
                                        System.out.println(
                                                "lecture with crn " + lecturesSplit[i] + " is already taught");
                                }
                                //Outcome of the for loop is a new input with unique lectures or no lectures
                                String temp = null;
                                for (int i = 0; i < lecturesSplit.length; i++) {
                                    temp = null;
                                    if (lecturesSplit[i].equals(availiableLectures[i]))
                                        temp = lecturesSplit[i];
                                    if (temp != null && userInputStr[count] != null)
                                        userInputStr[count] = userInputStr[count].concat(" " + temp);
                                    else if (temp != null)
                                        userInputStr[count] = temp;
                                }
                                if (userInputStr[count] == null)
                                    break;
                            }
                        }
                        //if they were alltaken then we restart the menu
                        if (userInputStr[count] == null)
                                break;
                        
                        // checks crns for a lab
                        if ((new Reader()).labCheck(userInputStr[count].split(" "), path)) {
                            // tempcrn has the lecture with a 0 as the ones with labs
                            String[] tempCrn = (new Reader()).crnReturn(userInputStr[count].split(" "), path);
                            // nolabCrn has the same format as tempcrn but no zeros
                            String[] nolabCrn = userInputStr[count].split(" ");
                            // add the lectures with no labs first
                            for (int i = 0; i < nolabCrn.length; i++) {
                                if (Integer.parseInt(tempCrn[i]) != 0) {
                                    (new Reader()).printClass(tempCrn[i], path);
                                    System.out.print(" Added!\n");
                                }
                            }
                            // add lectures with a lab
                            for (int i = 0; i < nolabCrn.length; i++) {
                                // finds the crn of the lecture with labs
                                if (Integer.parseInt(tempCrn[i]) == 0) {
                                    // returns actuall crns of the lab along with room
                                    String[] labs = (new Reader()).returnLab(nolabCrn[i], path);
                                    // prints out the lecture details
                                    (new Reader()).printClass(nolabCrn[i], path);
                                    System.out.println(" has these labs: ");
                                    // prints all labs
                                    for (int j = 0; j < labs.length; j++) {
                                        System.out.println(labs[j]);
                                    }
                                    // adds a TA to the labs
                                    for (int k = 0; k < labs.length; k++) {
                                        userInputStr2 = new String[INPUTMAX];
                                        count2 = 0;
                                        String[] templabCrn = labs[k].split(",");
                                        System.out.println("Enter TA's id for " + templabCrn[0] + ":");
                                        // check exception they might have inputed to many numbers or just the wrong
                                        // thing
                                        userInputStr2[count2] = scanner.nextLine();

                                        // search for student/TA
                                        check = list.checkId(Integer.parseInt(userInputStr2[count2]));
                                        count2++;

                                        if (!check) {
                                            // if the student is not found then we input a new TA with only the info of
                                            // a TA. which
                                            // doesn't really make sense to me because if a TA is a Student shouldnt we
                                            // have to input
                                            // Student specific info. The example code doesn't do it so i won't.
                                            System.out.print("Name of TA: ");
                                            userInputStr2[count2] = scanner.nextLine();
                                            count2++;
                                            System.out.println("TA's supervisor's name: ");
                                            userInputStr2[count2] = scanner.nextLine();
                                            count2++;
                                            System.out.println("Degree Seeking: ");
                                            userInputStr2[count2] = scanner.nextLine();
                                            count2++;
                                            userInputStr2[count2] = templabCrn[0];
                                            tempTA.addTA(userInputStr2);
                                            list.teachingassistantAdd(tempTA);

                                        }
                                    }
                                }
                            }

                        }
                        else {
                            String [] temp = userInputStr[count].split(" ");
                            for (int i = 0; i < temp.length; i++) {
                                (new Reader()).printClass(temp[i], path);
                                System.out.print(" Added!\n");
                            }

                        }
                        
                        tempFukalty.add(userInputStr, count);
                        list.facultyAdd(tempFukalty);
                    } else {
                        tempFukalty = list.returnFaculty(Integer.parseInt(userInputStr[count]));
                        int tracksnumlectures = 0;
                        System.out.print("Enter how many lectures: ");
                        userInputStr[count] = scanner.nextLine();
                        count++;
                        System.out.print("Enter the crns of the lectures to assign to this faculty: ");
                        // need to check if crn is assigned, if assigned change num of lectures
                        userInputStr[count] = scanner.nextLine();
                
                        String[] lecturesSplit = (userInputStr[count].split(" "));
                        //the string comes back with null in place of the lecture that is already assigned
                        String[] availiableLectures = list.checkLecture(userInputStr[count].split(" "));
                        // maybe turn this into a exception catch
                        for (int j = 0; j < lecturesSplit.length; j++) {
                            //if these two are different than we gotta get rid of the taken lecture
                            if (!(lecturesSplit[j].equals(availiableLectures[j]))) {
                                userInputStr[count] = null;
                                for (int i = 0; i < lecturesSplit.length; i++) {
                                    if (!lecturesSplit[i].equals(availiableLectures[i]))
                                        System.out.println(
                                                "lecture with crn " + lecturesSplit[i] + " is already taught");
                                }
                                //Outcome of the for loop is a new input with unique lectures or no lectures
                                String temp = null;
                                for (int i = 0; i < lecturesSplit.length; i++) {
                                    temp = null;
                                    if (lecturesSplit[i].equals(availiableLectures[i]))
                                        temp = lecturesSplit[i];
                                    if (temp != null && userInputStr[count] != null) {
                                        
                                        userInputStr[count] = userInputStr[count].concat(" " + temp);
                                        tracksnumlectures++;
                                    }
                                        else if (temp != null)
                                        userInputStr[count] = temp;
                                }
                                if (userInputStr[count] == null)
                                    break;
                            }
                        }
                        //if they were alltaken then we restart the menu
                        if (userInputStr[count] == null)
                                break;
                        
                        // checks crns for a lab
                        if ((new Reader()).labCheck(userInputStr[count].split(" "), path)) {
                            // tempcrn has the lecture with a 0 as the ones with labs
                            String[] tempCrn = (new Reader()).crnReturn(userInputStr[count].split(" "), path);
                            // nolabCrn has the same format as tempcrn but no zeros
                            String[] nolabCrn = userInputStr[count].split(" ");
                            // add the lectures with no labs first
                            for (int i = 0; i < nolabCrn.length; i++) {
                                if (Integer.parseInt(tempCrn[i]) != 0) {
                                    (new Reader()).printClass(tempCrn[i], path);
                                    System.out.print(" Added!\n");
                                }
                            }
                            // add lectures with a lab
                            for (int i = 0; i < nolabCrn.length; i++) {
                                // finds the crn of the lecture with labs
                                if (Integer.parseInt(tempCrn[i]) == 0) {
                                    // returns actuall crns of the lab along with room
                                    String[] labs = (new Reader()).returnLab(nolabCrn[i], path);
                                    // prints out the lecture details
                                    (new Reader()).printClass(nolabCrn[i], path);
                                    System.out.println(" has these labs: ");
                                    // prints all labs
                                    for (int j = 0; j < labs.length; j++) {
                                        System.out.println(labs[j]);
                                    }
                                    // adds a TA to the labs
                                    for (int k = 0; k < labs.length; k++) {
                                        userInputStr2 = new String[INPUTMAX];
                                        count2 = 0;
                                        String[] templabCrn = labs[k].split(",");
                                        System.out.println("Enter TA's id for " + templabCrn[0] + ":");
                                        // check exception they might have inputed to many numbers or just the wrong
                                        // thing
                                        userInputStr2[count2] = scanner.nextLine();
    
                                        // search for student/TA
                                        check = list.checkId(Integer.parseInt(userInputStr2[count2]));
                                        count2++;
    
                                        if (!check) {
                                            // if the student is not found then we input a new TA with only the info of
                                            // a TA. which
                                            // doesn't really make sense to me because if a TA is a Student shouldnt we
                                            // have to input
                                            // Student specific info. The example code doesn't do it so i won't.
                                            System.out.print("Name of TA: ");
                                            userInputStr2[count2] = scanner.nextLine();
                                            count2++;
                                            System.out.println("TA's supervisor's name: ");
                                            userInputStr2[count2] = scanner.nextLine();
                                            count2++;
                                            System.out.println("Degree Seeking: ");
                                            userInputStr2[count2] = scanner.nextLine();
                                            count2++;
                                            userInputStr2[count2] = templabCrn[0];
                                            tempTA.addTA(userInputStr2);
                                            list.teachingassistantAdd(tempTA);
    
                                        }
                                    }
                                }
                            }
    
                        }
                        else {
                            String [] temp = userInputStr[count].split(" ");
                            for (int i = 0; i < temp.length; i++) {
                                (new Reader()).printClass(temp[i], path);
                                System.out.print(" Added!\n");
                            }
    
                        }
                        tempFukalty.changeLectureArr(userInputStr[count].split(" "));
                        list.replaceFaculty(Integer.parseInt(userInputStr[0]), tempFukalty);
                        
                    }
                    
                    // If a lecture has labs, ask to enter the UCF id of the TA for each lab (a TA
                    // may do more than one Lab). This may require entering a new TA to the system,
                    // and in this case, you need to ask for the remaining information of the TA.
                    // Keep in mind that a TA can be a student.

                    break;
                case 2:
                    Random rand = new Random();
                    count = 0;// goes through input array
                    userInputStr = new String[INPUTMAX];
                    System.out.print("Enter UCF id:");
                    userInputStr[count] = scanner.nextLine();
                    check = list.checkId(Integer.parseInt(userInputStr[count]));
                    if (!check) {
                        count++;
                    System.out.print("Enter name:");
                    userInputStr[count] = scanner.nextLine();
                    System.out.println("Is ["+userInputStr[count]+"] undergraduate or graduate");
                    count++;
                    userInputStr[count] = scanner.nextLine();
                    System.out.print("Which lecture to enroll [" + userInputStr[count-2] + "] in?");
                    // Crns are seperated by spaces from user
                    userInputStr[count] = scanner.nextLine();
                      // checks crns for a lab
                      if ((new Reader()).labCheck(userInputStr[count].split(" "), path)) {
                          // tempcrn has the lecture with a 0 as the ones with labs
                          String[] tempCrn = (new Reader()).crnReturn(userInputStr[count].split(" "), path);
                          // nolabCrn has the same format as tempcrn but no zeros
                          String[] nolabCrn = userInputStr[count].split(" ");
                          // add the lectures with no labs first
                          userInputStr[count] = null;
                          for (int i = 0; i < nolabCrn.length; i++) {
                              if (Integer.parseInt(tempCrn[i]) != 0) {
                                  (new Reader()).printClass(tempCrn[i], path);
                                  System.out.print(" Added!\n");
                                  if(userInputStr[count]== null) {
                                    userInputStr[count] = tempCrn[i];
                                  }
                                  else {
                                    userInputStr[count] = userInputStr[count].concat(" " +tempCrn[i]);
                                  }
                              }
                          }
                          // add lectures with a lab
                          for (int i = 0; i < nolabCrn.length; i++) {
                              // finds the crn of the lecture with labs
                              if (Integer.parseInt(tempCrn[i]) == 0) {
                                  // returns actuall crns of the lab along with room
                                  String[] labs = (new Reader()).returnLab(nolabCrn[i], path);
                                  // prints out the lecture details
                                  (new Reader()).printClass(nolabCrn[i], path);
                                  System.out.println(" has these labs: ");
                                  // prints all labs
                                  for (int j = 0; j < labs.length; j++) {
                                      System.out.println(labs[j]);
                                  }
                                  int randint = rand.nextInt(labs.length-1);
                                  String[] templabCrn = labs[randint].split(",");
                                  if(userInputStr[count]== null) {
                                    userInputStr[count] = nolabCrn[i];
                                    userInputStr[count+1] = templabCrn[0];
                                  }
                                  else {
                                    userInputStr[count] = userInputStr[count].concat(" " +tempCrn[i]);
                                  }



                              }
                          }
                      }

                    // if a lecture requires a lab, randomly pick a lab for the student (no caps on
                    // how many students to enroll in a lab) (Use the built in Java random
                    // generation of a number)

                    }
                    else {

                    }
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
    private String[] lecAttended,labAttended;
    private String type;

    public String[] getLecAttended() {
        return lecAttended;
    }

    public String[] getLabAttended() {
        return labAttended;
    }

    public void setLabAttended(String[] labAttended) {
        this.labAttended = labAttended;
    }

    public void setLecAttended(String[] lecLabAttended) {
        this.lecAttended = lecLabAttended;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

class TeachingAssistant extends Student {
    // might need to write a method to append labs
    private String labs;
    private String advisor, degree;
    // might have to code in something for the input from a student to a TA

    public void addTA(String[] bonk) {
        int count = 0;
        setUcfid(Integer.parseInt(bonk[count]));
        count++;
        setName(bonk[count]);
        count++;
        setAdvisor(bonk[count]);
        count++;
        setDegree(bonk[count]);
        count++;
        setLabs(bonk[count]);

    }


    public String getLabs() {
        return labs;
    }

    public void setLabs(String bonk) {
        this.labs = bonk;
    }

    public String getAdvisor() {
        return advisor;
    }

    public void setAdvisor(String advisor) {
        this.advisor = advisor;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

}

class Faculty extends Knight {
    // Faculty specific:
    // Rank
    // List of lectures taught
    private String rank, office;
    private String[] lectureArr;
    private int numLectures;

    public void changeLectureArr(String[] bonk) {
       this.numLectures = (numLectures+bonk.length);
       String[] temp = new String[numLectures];
       for(int i = 0; i<(numLectures-bonk.length);i++) {
        temp[i] =lectureArr[i];
       }
       for(int i= (numLectures-bonk.length); i<numLectures;i++) {
        temp[i] =bonk[i-(numLectures-bonk.length)];
       }
       this.lectureArr = temp;

    }

    public void add(String[] bonk, int length) {
        int count = 0; 

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
    private static ArrayList<Knight> list = new ArrayList<Knight>();// Array list adds objects to it infinitely no need
    // to update length

    public boolean checkId(int id) {

        for (Knight e : list) {
            if (e.getUcfid() == id) {
                return true;
            }
        }
        return false;
    }
    
    public String[] checkLecture ( String[] bonk) {

        for (Knight e : list) {
            int numlect = ((Faculty)e).getNumLectures();
            if (numlect != 0) {
                String checklect[] = ((Faculty)e).getLectureArr();
                //going through the facult's 
                for (int i=0;i<numlect;i++) {
                    for (int j = 0; j< bonk.length; j++){

                        if (checklect[i].equals(bonk[j]))
                        bonk[j] = null;

                    }
                }
            }
        }

      return bonk;  
    }

    public void replaceFaculty(int id, Faculty bonk) {
        for (Knight e : list) {
            if (e.getUcfid() == id) {
                id =list.indexOf(e);
                list.add(id, e);
                System.out.println("");
            }   
        }
    }

    public Faculty returnFaculty(int id) {
        Faculty T =null;
        for (Knight e : list) {
            if (e.getUcfid() == id) {
                id =list.indexOf(e);
                T = (Faculty)list.get(id);
            }   
        }
       
        return T;
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

class Reader {
    private Scanner scanner = null;

    public boolean labCheck(String user[], String path) {

        String line;
        String[] arr = null;
        for (int i = 0; i < user.length; i++) {
            try {
                scanner = new Scanner(new File(path));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                // skips the commma and adds the string to arr
                arr = line.split(",");
                // looks for lectures with labs and then checks if they are tied to the crn
                // given
                if (arr.length > 5 && arr[6].equalsIgnoreCase("yes")) {
                    if (arr[0].equals(user[i])) {
                        return true;
                    }
                }

            }
        }
        scanner.close();
        return false;
    }

    public String[] crnReturn(String user[], String path) {
        String line;
        String[] arr = null, crn = user;

        for (int i = 0; i < user.length; i++) {
            try {
                scanner = new Scanner(new File(path));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                // skips the commma and adds the string to arr
                arr = line.split(",");
                // the crns with a lab are getting sent back with zeroes in the spot of the crn
                if (arr.length > 5 && arr[6].equalsIgnoreCase("yes")) {
                    if (arr[0].equals(user[i]))
                        crn[i] = "0";
                }
            }
        }
        scanner.close();

        return crn;
    }

    public void printClass(String crn, String path) {

        String line;
        String[] arr = null;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            arr = line.split(",");
            // looks for the room in the designated spot
            if (arr.length > 2 && arr[0].equalsIgnoreCase(crn)) {
                // prints out the the class
                System.out.print("[" + arr[0] + "/" + arr[1] + "/" + arr[2] + "]");
            } else if (arr.length < 3 && arr[0].equalsIgnoreCase(crn)) {
                System.out.println(line);

            }

        }

    }

    public String[] returnLab(String crn, String path) {
        String line;
        String[] arr = null, lab = null;
        int counter = 0;
        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            arr = line.split(",");

            while (scanner.hasNextLine()) {
                if (arr.length > 5 && arr[6].equalsIgnoreCase("yes")) {
                    if (arr[0].equals(crn)) {
                        line = scanner.nextLine();
                        arr = line.split(",");
                        while (arr.length < 3) {
                            counter++;
                            line = scanner.nextLine();
                            arr = line.split(",");
                        }
                    }
                }
                line = scanner.nextLine();
                arr = line.split(",");
            }

        }
        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        lab = new String[counter];
        counter = 0;
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            arr = line.split(",");

            while (scanner.hasNextLine()) {
                if (arr.length > 5 && arr[6].equalsIgnoreCase("yes")) {
                    if (arr[0].equals(crn)) {
                        line = scanner.nextLine();
                        arr = line.split(",");
                        while (arr.length < 3) {
                            lab[counter] = line;
                            counter++;
                            line = scanner.nextLine();
                            arr = line.split(",");
                        }
                    }
                }
                line = scanner.nextLine();
                arr = line.split(",");
            }

        }

        scanner.close();

        return lab;
    }
}