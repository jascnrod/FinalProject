/* COP3330 Final Project
 * Maria Tran, Gabriel Roca, Jason Rodriguez
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int x = 0, INPUTMAX = 6, countdel = 0;
        Boolean check = true,issue = false;
        String path, garbagestr;;
        Scanner scanner = new Scanner(System.in);
        Reader reader = new Reader();
        List list = new List();
        System.out.println("Enter the absolute path of the file: ");
        path = scanner.nextLine();
        while (check){
            try {
                scanner = new Scanner(new File(path));
                check = false;
            } catch (Exception e) {
                System.out.print("Sorry no such file.\nTry again: ");
                path = scanner.nextLine();
                
            }
        }
        scanner = new Scanner(System.in);
        check = true;

        // need to check if the file that has lectures

        while (x != 7) {
            int count = 0, count2 = 0;         
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
            try {
                x = scanner.nextInt();
                
            } catch (InputMismatchException e) {
                System.out.println("The options are 1 through 7. Try again");
            }
            garbagestr = scanner.nextLine();
            // There needs to be exception handling the entry of the wrong numbers
            switch (x) {
                case 1:
                int tryParse=0;
                    Faculty tempfaculty1 = new Faculty();
                    // there needs to be exception handling for the input of incorrect info
                    userInputStr[count] = tempfaculty1.idHandling();
                    check = list.checkId(Integer.parseInt(userInputStr[count]));
                   
                    // this is for if the ucf id is new
                    if (!check) {
                        count++;
                        System.out.print("Enter name: ");
                        //scanner.nextLine();
                        //names can be whatever. Who am I to put rules on it
                        userInputStr[count] = scanner.nextLine();
                        count++;
                        //Rank is never specified in rules besides saying upper case and lowercase mix is allowed.
                        //So I'm converting all to lowercase.
                   
                            System.out.print("Enter rank: ");
                            userInputStr[count] = scanner.nextLine();
                           
                        
                        userInputStr[count] = userInputStr[count].toLowerCase();
                        count++;
                        System.out.print("Enter office location: ");
                        userInputStr[count] = scanner.nextLine();
                        count++;
                        while(true) {
                            System.out.print("Enter how many lectures: ");
                            userInputStr[count] = scanner.nextLine();
                            try {
                                tryParse = Integer.parseInt(userInputStr[count]);
                            } catch (Exception e) {
                                System.out.println("Enter a number.");
                                continue;
                            }
                            break;
                        }
                        count++;
                        System.out.print("Enter the crns of the lectures to assign to this faculty: ");
                        // Crns are seperated by spaces from user
                        userInputStr[count] = scanner.nextLine();
                        // keeps a copy of the the crns
                        String[] lecturesSplit = (userInputStr[count].split(" "));
                        // the string comes back with null in place of the lecture that is already
                        // assigned
                        if(lecturesSplit.length != tryParse) {
                            System.out.println("The amount of lectures specified is not the same as what was given. Going back to menu.");
                            break;
                        }
                        String[] availiableLectures = list.checkLecture(userInputStr[count].split(" "));
                        
                        for (int j = 0; j < lecturesSplit.length; j++) {
                            // if these two are different than we gotta get rid of the taken lecture
                            if (!(lecturesSplit[j].equals(availiableLectures[j]))) {
                                userInputStr[count] = null;
                                for (int i = 0; i < lecturesSplit.length; i++) {
                                    if (!lecturesSplit[i].equals(availiableLectures[i]))
                                        System.out.println(
                                                "lecture with crn " + lecturesSplit[i] + " is already taught");
                                }
                                // Outcome of the for loop is a new input with unique lectures or no lectures
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
                        // if they were alltaken then we restart the menu
                        if (userInputStr[count] == null) {
                            System.out.println("all lectures are taken");
                            break;
                        }

                        // checks crns for a lab
                        if (reader.labCheck(userInputStr[count].split(" "), path)) {
                            // tempcrn has the lecture with a 0 as the ones with labs
                            String[] tempCrn = reader.crnReturn(userInputStr[count].split(" "), path);
                            // nolabCrn has the same format as tempcrn but no zeros
                            String[] nolabCrn = userInputStr[count].split(" ");
                            // add the lectures with no labs first
                            for (int i = 0; i < nolabCrn.length; i++) {
                                if (Integer.parseInt(tempCrn[i]) != 0) {
                                    reader.printClass(tempCrn[i], path);
                                    System.out.print(" Added!\n");
                                }
                            }
                            // add lectures with a lab
                            for (int i = 0; i < nolabCrn.length; i++) {
                                // finds the crn of the lecture with labs
                                if (Integer.parseInt(tempCrn[i]) == 0) {
                                    // returns actuall crns of the lab along with room
                                    String[] labs = reader.returnLab(nolabCrn[i], path);
                                    // prints out the lecture details
                                    reader.printClass(nolabCrn[i], path);
                                    System.out.println(" has these labs: ");
                                    // prints all labs
                                    for (int j = 0; j < labs.length; j++) {
                                        System.out.println(labs[j]);
                                    }
                                    // adds a TA to the labs
                                    for (int k = 0; k < labs.length; k++) {
                                        TeachingAssistant tempTA = new Student();
                                        userInputStr2 = new String[INPUTMAX];
                                        count2 = 0;
                                        String[] templabCrn = labs[k].split(",");
                                        while (!issue) {
                                            System.out.println("Enter TA's id for " + templabCrn[0] + ":");
                                            userInputStr2[count2] = scanner.nextLine();
                                            try {
                                                tryParse = Integer.parseInt(userInputStr[count]);
                                                issue=false;
                                            }catch (Exception e){
                                                System.err.println(e.getMessage());
                                                issue = true;
                                            }
                                            if (!issue) {
                                                try {
                                                    if( String.valueOf(tryParse).length()<7)
                                                    throw new idException();
                                                    break;
                                                } catch (idException e) {
                                                    e.getMessage();
                                                }
                                            }
                                        }
                                        // search for student/TA
                                        check = list.checkId(Integer.parseInt(userInputStr2[count2]));

                                        if (!check) {
                                            count2++;
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
                                            ((TeachingAssistant) tempTA).addTA(userInputStr2);
                                            list.teachingassistantAdd((TeachingAssistant) tempTA);

                                        } else {
                                            // might have to actually check if its a student or not. right now
                                            // this techinically only checks if the id has been used
                                            tempTA = (TeachingAssistant) list
                                                    .returnStudent(Integer.parseInt(userInputStr2[count2]));
                                            count2++;
                                            if (((TeachingAssistant) tempTA).getLabs() == null) {

                                                System.out.println("TA found as a student: " + tempTA.getName());
                                                userInputStr2[count2] = tempTA.getName();
                                                count2++;
                                                System.out.println("TA's supervisor's name: ");
                                                userInputStr2[count2] = scanner.nextLine();
                                                count2++;
                                                // when inputting this into the object write code to update the type of
                                                // student
                                    
                                                    System.out.println("Degree Seeking: ");
                                                    
                                                count2++;
                                                userInputStr2[count2] = templabCrn[0];
                                                ((TeachingAssistant) tempTA).addTA(userInputStr2);
                                                // causing unnecessary poutpu

                                            } else {
                                                ((TeachingAssistant) tempTA).addLabs(templabCrn[0]);
                                                // causing unnecessary poutpu

                                            }
                                        }
                                    }
                                    reader.printClass(nolabCrn[i], path);
                                    System.out.print(" Added!\n");
                                }

                            }

                        } else {
                            String[] temp = userInputStr[count].split(" ");
                            for (int i = 0; i < temp.length; i++) {
                                reader.printClass(temp[i], path);
                                System.out.print(" Added!\n");
                            }

                        }

                        tempfaculty1.add(userInputStr, count);
                        list.facultyAdd(tempfaculty1);
                    } else {
                        tempfaculty1 = list.returnFaculty(Integer.parseInt(userInputStr[count]));
                        while(true) {
                            System.out.print("Enter how many lectures: ");
                            userInputStr[count] = scanner.nextLine();
                            try {
                                tryParse = Integer.parseInt(userInputStr[count]);
                            } catch (Exception e) {
                                System.out.println("Enter a number.");
                                continue;
                            }
                            break;
                        }
                        count++;
                        System.out.print("Enter the crns of the lectures to assign to this faculty: ");
                        // need to check if crn is assigned, if assigned change num of lectures
                        userInputStr[count] = scanner.nextLine();

                        String[] lecturesSplit = (userInputStr[count].split(" "));
                        // the string comes back with null in place of the lecture that is already
                        // assigned
                        if(lecturesSplit.length != tryParse) {
                            System.out.println("The amount of lectures specified is not the same as what was given. Going back to menu.");
                            break;
                        }
                        String[] availiableLectures = list.checkLecture(userInputStr[count].split(" "));
                        for (int j = 0; j < lecturesSplit.length; j++) {
                            // if these two are different than we gotta get rid of the taken lecture
                            if (!(lecturesSplit[j].equals(availiableLectures[j]))) {
                                userInputStr[count] = null;
                                for (int i = 0; i < lecturesSplit.length; i++) {
                                    if (!lecturesSplit[i].equals(availiableLectures[i]))
                                        System.out.println(
                                                "lecture with crn " + lecturesSplit[i] + " is already taught");
                                }
                                // Outcome of the for loop is a new input with unique lectures or no lectures
                                String temp = null;
                                for (int i = 0; i < lecturesSplit.length; i++) {
                                    temp = null;
                                    if (lecturesSplit[i].equals(availiableLectures[i]))
                                        temp = lecturesSplit[i];
                                    if (temp != null && userInputStr[count] != null) {
                                        userInputStr[count] = userInputStr[count].concat(" " + temp);
                                    } else if (temp != null)
                                        userInputStr[count] = temp;
                                }
                                if (userInputStr[count] == null)
                                    break;
                            }
                        }
                        // if they were alltaken then we restart the menu
                        if (userInputStr[count] == null)
                            break;

                        // checks crns for a lab
                        if (reader.labCheck(userInputStr[count].split(" "), path)) {
                            // tempcrn has the lecture with a 0 as the ones with labs
                            String[] tempCrn = reader.crnReturn(userInputStr[count].split(" "), path);
                            // nolabCrn has the same format as tempcrn but no zeros
                            String[] nolabCrn = userInputStr[count].split(" ");
                            // add the lectures with no labs first
                            for (int i = 0; i < nolabCrn.length; i++) {
                                if (Integer.parseInt(tempCrn[i]) != 0) {
                                    reader.printClass(tempCrn[i], path);
                                    System.out.print(" Added!\n");
                                }
                            }
                            // add lectures with a lab
                            for (int i = 0; i < nolabCrn.length; i++) {
                                // finds the crn of the lecture with labs
                                if (Integer.parseInt(tempCrn[i]) == 0) {
                                    // returns actuall crns of the lab along with room
                                    String[] labs = reader.returnLab(nolabCrn[i], path);
                                    // prints out the lecture details
                                    reader.printClass(nolabCrn[i], path);
                                    System.out.println(" has these labs: ");
                                    // prints all labs
                                    for (int j = 0; j < labs.length; j++) {
                                        System.out.println(labs[j]);
                                    }
                                    // adds a TA to the labs
                                    for (int k = 0; k < labs.length; k++) {
                                        Knight tempTA = new TeachingAssistant();
                                        userInputStr2 = new String[INPUTMAX];
                                        count2 = 0;
                                        String[] templabCrn = labs[k].split(",");
                                        while (!issue) {
                                            System.out.println("Enter TA's id for " + templabCrn[0] + ":");
                                            userInputStr2[count2] = scanner.nextLine();
                                            try {
                                                tryParse = Integer.parseInt(userInputStr[count]);
                                                issue=false;
                                            }catch (Exception e){
                                                System.err.println(e.getMessage());
                                                issue = true;
                                            }
                                            if (!issue) {
                                                try {
                                                    if( String.valueOf(tryParse).length()<7)
                                                    throw new idException();
                                                    break;
                                                } catch (idException e) {
                                                    System.out.println(e.getMessage());
                                                }
                                            }
                                        }
                                        // search for student/TA
                                        check = list.checkId(Integer.parseInt(userInputStr2[count2]));

                                        if (!check) {
                                            count2++;
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
                                            ((TeachingAssistant) tempTA).addTA(userInputStr2);
                                            list.teachingassistantAdd((TeachingAssistant) tempTA);

                                        } else {
                                            tempTA = (TeachingAssistant) list.returnStudent(Integer.parseInt(userInputStr2[count2]));
                                            count2++;
                                            if (((TeachingAssistant) tempTA).getLabs() == null) {

                                                System.out.println("TA found as a student: " + tempTA.getName());
                                                userInputStr2[count2] = tempTA.getName();
                                                count2++;
                                                System.out.println("TA's supervisor's name: ");
                                                userInputStr2[count2] = scanner.nextLine();
                                                count2++;
                                                System.out.println("Degree Seeking: ");
                                                userInputStr2[count2] = scanner.nextLine();
                                                count2++;
                                                userInputStr2[count2] = templabCrn[0];
                                                ((TeachingAssistant) tempTA).addTA(userInputStr2);

                                            } else {
                                                ((TeachingAssistant) tempTA).addLabs(templabCrn[0]);

                                            }
                                        }
                                    }
                                }
                                reader.printClass(nolabCrn[i], path);
                                System.out.print(" Added!\n");
                            }
                            for (int i = 0; i < nolabCrn.length; i++) {
                                if (Integer.parseInt(tempCrn[i]) == 0) {
                                    reader.printClass(nolabCrn[i], path);
                                    System.out.print(" Added!\n");
                                }
                            }

                        } else {
                            String[] temp = userInputStr[count].split(" ");
                            // i think i need to check if there is crns being using already
                            for (int i = 0; i < temp.length; i++) {
                                reader.printClass(temp[i], path);
                                System.out.print(" Added!\n");
                            }

                        }
                        tempfaculty1.changeLectureArr(userInputStr[count].split(" "));

                    }

                    // If a lecture has labs, ask to enter the UCF id of the TA for each lab (a TA
                    // may do more than one Lab). This may require entering a new TA to the system,
                    // and in this case, you need to ask for the remaining information of the TA.
                    // Keep in mind that a TA can be a student.

                    break;
                case 2:
                    TeachingAssistant tempStudent = new Student();
                    Random rand = new Random();
                    int tryParse2 = 0;
                    count = 0;// goes through input array
                    userInputStr = new String[INPUTMAX];
                    userInputStr[count] = tempStudent.idHandling();
                    // check if there is already a student
                    check = list.checkId(Integer.parseInt(userInputStr[count]));
                    if (!check) {
                        count++;
                        System.out.print("Enter name:");
                        userInputStr[count] = scanner.nextLine();
                        System.out.println("Is [" + userInputStr[count] + "] undergraduate or graduate");
                        count++;
                        userInputStr[count] = scanner.nextLine();
                        System.out.print("Which lecture to enroll [" + userInputStr[count - 1] + "] in?");
                        count++;
                        // Crns are seperated by spaces from user
                        userInputStr[count] = scanner.nextLine();
                        // checks crns for a lab
                        if (reader.labCheck(userInputStr[count].split(" "), path)) {
                            // tempcrn has the lecture with a 0 as the ones with labs
                            String[] tempCrn = reader.crnReturn(userInputStr[count].split(" "), path);
                            // nolabCrn has the same format as tempcrn but no zeros
                            String[] nolabCrn = userInputStr[count].split(" ");
                            // add the lectures with no labs first
                            userInputStr[count] = null;
                            for (int i = 0; i < nolabCrn.length; i++) {
                                if (Integer.parseInt(tempCrn[i]) != 0) {
                                    reader.printClass(tempCrn[i], path);
                                    System.out.print(" Added!\n");
                                    if (userInputStr[count] == null) {
                                        userInputStr[count] = tempCrn[i];
                                    } else {
                                        userInputStr[count] = userInputStr[count].concat(" " + tempCrn[i]);
                                    }
                                }
                            }
                            // add lectures with a lab
                            for (int i = 0; i < nolabCrn.length; i++) {
                                // finds the crn of the lecture with labs
                                if (Integer.parseInt(tempCrn[i]) == 0) {
                                    // returns actuall crns of the lab along with room
                                    String[] labs = reader.returnLab(nolabCrn[i], path);
                                    // prints out the lecture details
                                    reader.printClass(nolabCrn[i], path);
                                    System.out.println(" has these labs: ");
                                    // prints all labs
                                    for (int j = 0; j < labs.length; j++) {
                                        System.out.println(labs[j]);
                                    }
                                    int randint = rand.nextInt(labs.length - 1);
                                    String[] templabCrn = labs[randint].split(",");
                                    System.out.println("[" + userInputStr[1] + "] is add to lab : " + templabCrn[0]);
                                    if (userInputStr[count] == null) {
                                        userInputStr[count] = nolabCrn[i];
                                        userInputStr[count] = userInputStr[count].concat(" " + templabCrn[0]);
                                    } else {
                                        userInputStr[count] = userInputStr[count].concat(" " + nolabCrn[i]);
                                        userInputStr[count] = userInputStr[count].concat(" " + templabCrn[0]);
                                    }
                                }
                            }
                            ((Student) tempStudent).addStudent(userInputStr);
                            list.studentAdd((Student) tempStudent);
                        } else {
                            // this is if there is no lab contained in the crns inputed
                            // needs to be edited, this only works for if there is one crnn i think
                            // not smart code
                            String[] temp = userInputStr[count].split(" ");

                            for (int i = 0; i < temp.length; i++) {
                                if (Integer.parseInt(temp[i]) == 0) {
                                    // idk need to add check if crn is on the .txt
                                    System.out.println("forgot what to add here ");
                                } else if (temp[i] != null) {
                                    // userInputStr[count] = userInputStr[count].concat(" " +temp[i]);
                                    reader.printClass(temp[i], path);
                                    System.out.println("student enrolled!");
                                }

                            }

                            ((Student) tempStudent).addStudent(userInputStr);
                            list.studentAdd((Student) tempStudent);

                        }

                        // if a lecture requires a lab, randomly pick a lab for the student (no caps on
                        // how many students to enroll in a lab) (Use the built in Java random
                        // generation of a number)

                    } else {
                        tempStudent = (Student) list.returnStudent(Integer.parseInt(userInputStr[count]));
                        System.out.println("Record found/Name: " + tempStudent.getName());
                        count++;
                        System.out.print("Which lecture to enroll [" + tempStudent.getName() + "] in? ");
                        // Crns are seperated by spaces from user
                        userInputStr[count] = scanner.nextLine();

                        // if a lecture requires a lab, randomly pick a lab for the student (no caps on
                        // how many students to enroll in a lab) (Use the built in Java random
                        // generation of a number)
                        if (reader.labCheck(userInputStr[count].split(" "), path)) {
                            // tempcrn has the lecture with a 0 as the ones with labs
                            String[] tempCrn = reader.crnReturn(userInputStr[count].split(" "), path);
                            // nolabCrn has the same format as tempcrn but no zeros
                            String[] nolabCrn = userInputStr[count].split(" ");
                            // add the lectures with no labs first
                            userInputStr[count] = null;
                            for (int i = 0; i < nolabCrn.length; i++) {
                                if (Integer.parseInt(tempCrn[i]) != 0) {
                                    reader.printClass(tempCrn[i], path);
                                    System.out.print(" Added!\n");
                                    if (userInputStr[count] != null) {
                                        userInputStr[count] = userInputStr[count].concat(" " + tempCrn[i]);
                                    } else {
                                        userInputStr[count] = tempCrn[i];
                                    }
                                }
                            }
                            // add lectures with a lab
                            for (int i = 0; i < nolabCrn.length; i++) {
                                // finds the crn of the lecture with labs
                                if (Integer.parseInt(tempCrn[i]) == 0) {
                                    // returns actuall crns of the lab along with room
                                    String[] labs = reader.returnLab(nolabCrn[i], path);
                                    // prints out the lecture details
                                    reader.printClass(nolabCrn[i], path);
                                    System.out.println(" has these labs: ");
                                    // prints all labs
                                    for (int j = 0; j < labs.length; j++) {
                                        System.out.println(labs[j]);
                                    }
                                    int randint = rand.nextInt(labs.length - 1);
                                    String[] templabCrn = labs[randint].split(",");
                                    System.out.println(
                                            "[" + tempStudent.getName() + "] is add to lab : " + templabCrn[0]);
                                    if (userInputStr[count] == null) {
                                        userInputStr[count] = nolabCrn[i].concat(" " + templabCrn[0]);
                                    } else {
                                        userInputStr[count] = userInputStr[count].concat(" " + nolabCrn[i]);
                                        userInputStr[count] = userInputStr[count].concat(" " + templabCrn[0]);
                                    }
                                }
                            }
                            ((Student) tempStudent).setLeclabAttended(userInputStr[count].split(" "));
                        } else {
                            // this is if there is no lab contained in the crns inputed
                            String[] temp = userInputStr[count].split(" ");
                            String[] temp2 = ((Student) tempStudent).getLeclabAttended();
                            // checks if there is crns being using already
                            // might turn this into a exception handle
                            userInputStr[count] = null;
                            for (int i = 0; i < temp.length; i++) {
                                for (int k = 0; k < temp2.length; k++) {
                                    if (temp[i].equals(temp2[k])) {
                                        temp[i] = null;
                                        if (userInputStr[count] == null) {
                                            userInputStr[count] = temp2[k];
                                        } else {
                                            userInputStr[count] = userInputStr[count].concat(" " + temp2[k]);
                                        }
                                    }
                                }
                            }

                            for (int i = 0; i < temp.length; i++) {
                                if (temp[i] != null) {
                                    userInputStr[count] = userInputStr[count].concat(" " + temp[i]);
                                    reader.printClass(temp[i], path);
                                    System.out.println("student enrolled!");
                                }
                            }

                            ((Student) tempStudent).setLeclabAttended(userInputStr[count].split(" "));

                        }

                    }

                    break;

                case 3:
                    count = 0;// goes through input array
                    userInputStr = new String[INPUTMAX];
                    Faculty tempFaculty = new Faculty();
                    
                    userInputStr[count] = tempFaculty.idHandling();
                    tempFaculty = list.returnFaculty(Integer.parseInt(userInputStr[count]));
                    if (tempFaculty != null)
                        tempFaculty.printInfo(tempFaculty, path);
                    else
                        System.out.println("Faculty not found");
                    // Then Print the UCF id, name and the crns of the lectures taught by the
                    // faculty (No need to display anything else).

                    break;
                case 4:
                TeachingAssistant tempTA = new TeachingAssistant();
                    count = 0;// goes through input array
                    userInputStr = new String[INPUTMAX];
                    userInputStr[count] = tempTA.idHandling();
                    tempTA = (TeachingAssistant) (list.returnStudent(Integer.parseInt(userInputStr[count])));
                    if (tempTA == null) {
                        System.out.println("Sorry no TA found.");
                        break;
                    }
                    if (tempTA.getLabs() != null) {
                        String[] lab = (tempTA.getLabs()).split(" ");
                        for (int i = 0; i < lab.length; i++) {
                            check = reader.doesCrnExist(lab[i], path);
                            if (!check) {
                                lab[i] = null;
                                count++;
                            }
                        }
                        if (count == lab.length) {
                            System.out.println("Sorry no TA with this id");
                        } else {
                            System.out.println("Record Found: \n" + tempTA.getName());
                            System.out.println("Assisting in lab/labs:");
                            for (int i = 0; i < lab.length; i++) {
                                check = reader.doesCrnExist(lab[i], path);
                                if (!check) {
                                    lab[i] = null;
                                    count++;
                                }
                            }
                            for (int i = 0; i < lab.length; i++) {
                                reader.printClass(lab[i], path);
                            }

                        }
                    }
                    break;
                case 5:
                Student tempStudent2 = new Student();
                    count = 0;// goes through input array
                    userInputStr = new String[INPUTMAX];
                    
                    userInputStr[count] = tempStudent2.idHandling();
                    tempStudent2 = (Student) (list.returnStudent(Integer.parseInt(userInputStr[count])));
                    if (tempStudent2 != null) {
                        if(tempStudent2.getLeclabAttended() != null){
                            tempStudent2.printInfo(tempStudent2, path);

                        }   
                        else
                        System.out.println("Student not found");

                    } else {
                        System.out.println("Student not found");
                    }

                    // Then Print the UCF id, name and the crns of the lectures taught by the
                    // faculty (No need to display anything else).
                    break;
                case 6:
                    // print out deleted class
                    userInputStr = new String[INPUTMAX];

                    System.out.println("Enter the crn of the lecture to delete: ");
                    userInputStr[0] = scanner.nextLine();
                    countdel++;
                    try {
                        reader.deleteCrn(userInputStr[count], path);
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    // Deleting a lecture requires deleting its labs as well and updating any
                    // faculty/student record who is teaching/taking that lecture
                    break;
                case 7:
                userInputStr = new String[INPUTMAX];
                    if (countdel != 0) {
                        System.out.println("You have made a deletion of at least one lecture. Would you like to" +
                                "print the copy of lec.txt? Enter y/Y for Yes or n/N for No:");
                        userInputStr[0] = scanner.nextLine();
                        userInputStr[0] = userInputStr[0].toUpperCase();
                        while (!userInputStr[0].equals("Y"))
                            if (userInputStr[0].equals("Y")) {
                                reader.printEverything(path);
                                System.out.println("Bye!");
                                break;
                            } else if (userInputStr[0].equals("N")) {

                                System.out.println("Bye!");
                                break;
                            } else {
                                System.out.println("Is that a yes or no? Enter y/Y for Yes or n/N for No:");
                                userInputStr[0] = scanner.nextLine();
                                userInputStr[0] = userInputStr[0].toUpperCase();
                            }

                    }
                    if (userInputStr[0].equals("Y") && (userInputStr[0] != null)) {

                        reader.printEverything(path);
                        System.out.println("Bye!");
                        break;
                    }
                    else {

                        System.out.println("Bye!");
                        break;
                    }
            }
        }
        scanner.close();
    }
}

// this is the top dog, everything will inherit from this. the Array list will
// be initialized as a Identity
abstract class Knight {
    Reader reader = new Reader();
    private int ucfid = 0;
    private String name;
    
    public int getUcfid() {
        return ucfid;
    }
    
    public String idHandling() {
        String userInputStr=null;
        Scanner scanner = new Scanner(System.in);
      
            int tryParse = 0;
            boolean issue = true;
            while (true) {
                System.out.print("Enter UCF id:");
                userInputStr = scanner.nextLine();
                try {
                    tryParse = Integer.parseInt(userInputStr);
                    issue = false;
                }catch (Exception e){
                    System.out.print("Enter a 7 digit number.\n");
                    issue = true;
                }
                if (!issue) {
                    try {
                        if( String.valueOf(tryParse).length()< 7 || String.valueOf(tryParse).length() > 7)
                        throw new idException();
                        break;
                    } catch (idException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }
            //scanner.close();
        
        return userInputStr;
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

class Student extends TeachingAssistant {
    // student specific info Type:grad,undergrad
    // possible list of lec/lab attended
    private String[] leclabAttended;
    private String type;
//changing exporting matching test outoput wednesday 152
    // need to check if the crn exists.
 
    public void printInfo(Student bonk, String path) {
        String[] leclab = bonk.getLeclabAttended().clone();
        // checking if any of the crns have labs tied to them.
        if (reader.labCheck(leclab, path)) {
            System.out.println("Record Found: ");
            System.out.println(bonk.getName() + "\n Enrolled in the following lectures");
            String[] nolab = leclab.clone();
            reader.crnReturn(nolab, path);// nolab now has zeros where labs go
            for (int i = 0; i < leclab.length; i++) {
                if (Integer.parseInt(nolab[i]) == 0) {
                    reader.printNecessary2(leclab[i], path);
                    i++;
                    reader.printNecessary2(leclab[i], path);
                } else {
                    reader.printNecessary2(leclab[i], path);
                }
            }

        }
        // runs with no labs. so only need to check if the crn exists
        else {
            int counter =0;
            for (int i = 0; i < leclab.length; i++) {
                if (reader.doesCrnExist(leclab[i], path)) {
                    counter++;
                }
            }
            if(counter != 0){
                
                System.out.println("Record Found: ");
                System.out.println(bonk.getName() + "\n Enrolled in the following lectures");
                for (int i = 0; i < leclab.length; i++) {
                    if (reader.doesCrnExist(leclab[i], path)) {
                        reader.printNecessary2(leclab[i], path);
                    }
                }
            
            }
            else {
                System.out.println("Student not found");
            }
        }
    }

    public void addStudent(String[] bonk) {
        int count = 0;
        setUcfid(Integer.parseInt(bonk[count]));
        count++;
        setName(bonk[count]);
        count++;
        setType(bonk[count]);
        count++;
        setLeclabAttended(bonk[count].split(" "));

    }

    public String[] getLeclabAttended() {
        return leclabAttended;
    }

    public void setLeclabAttended(String[] lecLabAttended) {
        this.leclabAttended = lecLabAttended;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

class TeachingAssistant extends Knight {
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

    public void addLabs(String bonk) {
        this.labs = (this.labs).concat(" " + bonk);
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
        this.numLectures = (numLectures + bonk.length);
        String[] temp = new String[numLectures];
        for (int i = 0; i < (numLectures - bonk.length); i++) {
            temp[i] = lectureArr[i];
        }
        for (int i = (numLectures - bonk.length); i < numLectures; i++) {
            temp[i] = bonk[i - (numLectures - bonk.length)];
        }
        this.lectureArr = temp;

    }

    public void printInfo(Faculty bonk, String path) {
        String Crn[] = (bonk.getLectureArr()).clone();
        boolean check = false;
        int count = 0;

        for (int i = 0; i < Crn.length; i++) {
            if (!reader.doesCrnExist(Crn[i], path))
                count++;
        }
        if (count == Crn.length) {
            System.out.println("Factulty not found");
            return;
        }
        System.out.print(bonk.getName() + " is teaching the following lectures:");
        check = reader.labCheck(Crn, path);
        if (check) {
            String[] nolabcrn = Crn.clone();
            reader.crnReturn(Crn, path);
            for (int i = 0; i < Crn.length; i++) {
                if (Integer.parseInt(Crn[i]) != 0) {
                    reader.printNecessary(nolabcrn[i], path);
                }
            }
            for (int i = 0; i < Crn.length; i++) {
                if (Integer.parseInt(Crn[i]) == 0) {
                    reader.printClass(nolabcrn[i], path);
                    System.out.print(" with Labs: \n");
                    String[] labs = reader.returnLab(nolabcrn[i], path);
                    for (int j = 0; j < labs.length; j++) {
                        String[] temp = labs[j].split(",");
                        System.out.println("[" + temp[0] + "/" + temp[1] + "]");
                    }
                }
            }
        } else {
            for (int i = 0; i < Crn.length; i++) {
                reader.printNecessary(Crn[i], path);
            }
        }

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

    public Knight returnStudent(int id) {
        TeachingAssistant T = null;
        for (Knight e : list) {
            if (e.getUcfid() == id) {
                id = list.indexOf(e);
                T = (Student) list.get(id);
            }
        }

        return T;
    }

    public boolean checkId(int id) {

        for (Knight e : list) {
            if (e.getUcfid() == id) {
                return true;
            }
        }
        return false;
    }

    public String[] checkLecture(String[] bonk) {
        Faculty comp = new Faculty();
        for (Knight e : list) {
            if (e.getClass() == comp.getClass()) {

                int numlect = ((Faculty) e).getNumLectures();
                if (numlect != 0) {
                    String checklect[] = ((Faculty) e).getLectureArr();
                    // going through the facult's
                    for (int i = 0; i < numlect; i++) {
                        for (int j = 0; j < bonk.length; j++) {

                            if (checklect[i].equals(bonk[j]))
                                bonk[j] = null;

                        }
                    }
                }
            }
        }

        return bonk;
    }

    /*
     * public void replaceFaculty(int id, Faculty bonk) {
     * for (Knight e : list) {
     * if (e.getUcfid() == id) {
     * id =list.indexOf(e);
     * list.add(id, e);
     * System.out.println("");
     * }
     * }
     * }
     */

    public Faculty returnFaculty(int id) {
        Faculty T = null;
        for (Knight e : list) {
            if (e.getUcfid() == id) {
                id = list.indexOf(e);
                T = (Faculty) list.get(id);
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

    public static void setList(ArrayList<Knight> list) {
        List.list = list;
    }

   
}

class Reader {
    private Scanner scanner = null;
    // need to add a check for if a crn doesnt exist

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

    public boolean doesCrnExist(String crn, String path) {
        boolean check = false;
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
            if (arr[0].equalsIgnoreCase(crn)) {
                check = true;
                return check;
            }

        }
        return check;

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
                System.out.print("\n[" + arr[0] + "/" + arr[1] + "/" + arr[2] + "]");
            } else if (arr.length < 3 && arr[0].equalsIgnoreCase(crn)) {
                System.out.println(line);

            }

        }

    }

    public void printNecessary2(String crn, String path) {
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
            if (arr.length > 4 && arr[0].equalsIgnoreCase(crn)) {
                // prints out the the class
                System.out.print("\n[" + arr[1] + "/" + arr[2] + "]");
            } else if (arr.length < 3 && arr[0].equalsIgnoreCase(crn)) {
                System.out.println("/[Lab: " + arr[0] + "]");
            }
        }
    }

    public void printNecessary(String crn, String path) {
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
            if (arr.length > 4 && arr[0].equalsIgnoreCase(crn)) {
                // prints out the the class
                System.out.print("\n[" + arr[1] + "/" + arr[2] + "][" + arr[4] + "]");
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
                        while (arr.length < 3 && scanner.hasNextLine()) {
                            counter++;
                            line = scanner.nextLine();
                            arr = line.split(",");
                        }
                        if (!(scanner.hasNextLine()))
                            counter++;
                        break;
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
                        while (arr.length < 3 && scanner.hasNextLine()) {
                            lab[counter] = line;
                            counter++;
                            line = scanner.nextLine();
                            arr = line.split(",");
                        }
                        if (!scanner.hasNextLine())
                            lab[counter++] = line;
                        break;
                    }
                }
                line = scanner.nextLine();
                arr = line.split(",");
            }

        }

        scanner.close();

        return lab;
    }

    public void deleteCrn(String crn, String path) throws IOException {
        File inFile = new File(path);
        File tempFile = new File(inFile.getAbsolutePath() + ".txt");

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(tempFile))) {
                String line = null;
                String[] arr = null;
                while ((line = br.readLine()) != null) {
                    arr = line.split(",");
                    // looks for no in the designated spot
                    if (!(arr.length > 1 && arr[0].equalsIgnoreCase(crn))) {
                        pw.println(line);
                        pw.flush();
                    } else if (arr.length > 5 && arr[6].equalsIgnoreCase("yes")) {

                        line = br.readLine();
                        arr = line.split(",");
                        while (arr.length < 3 && (line != null)) {

                            line = br.readLine();
                            if (line != null)
                                arr = line.split(",");
                        }
                        if (line == null)
                            break;
                        pw.println(line);
                        pw.flush();

                    }
                }
            }
        }

        if (!inFile.delete()) {
            System.out.println("Could not delete file");
            return;
        }

        // Rename the new file to the filename the original file had.
        if (!tempFile.renameTo(inFile))
            System.out.println("Could not rename file");

    }

    public void printEverything(String path) {
        String line;
        

        Scanner scanner = null;
        try {
            scanner = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            System.out.println(line);

        }
    }
}

class idException extends Exception {
   
    @Override
    public String getMessage() {
        return ">>>>>>>>>>>Sorry incorrect format. (Ids are 7 digits)";
        
    }


}