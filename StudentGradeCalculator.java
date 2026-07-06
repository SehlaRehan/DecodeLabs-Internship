package com.mycompany.studentgradecalculator;

    import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentGradeCalculator {

    Scanner sc = new Scanner(System.in);

    int numberOfSubjects;
    String[] subjectNames;
    int[] marks;

    int totalMarks = 0;
    int maximumMarks;

    double average;
    double percentage;

    String grade;
    String status;

    
    public void inputData() {

        while (true) {
            try {
                System.out.print("Enter Number of Subjects: ");
                numberOfSubjects = sc.nextInt();

                if (numberOfSubjects <= 0) {
                    System.out.println("Number of subjects must be greater than 0.");
                    continue;
                }

                break;

            } catch (InputMismatchException e) {
                System.out.println("Invalid Input! Please enter an integer.");
                sc.nextLine();
            }
        }

        sc.nextLine(); 

        subjectNames = new String[numberOfSubjects];
        marks = new int[numberOfSubjects];

        for (int i = 0; i < numberOfSubjects; i++) {

            System.out.print("\nEnter Subject Name " + (i + 1) + ": ");
            subjectNames[i] = sc.nextLine();

            while (true) {

                try {

                    System.out.print("Enter Marks for " + subjectNames[i] + " (0-100): ");
                    marks[i] = sc.nextInt();

                    if (marks[i] < 0 || marks[i] > 100) {
                        System.out.println("Marks should be between 0 and 100.");
                        continue;
                    }

                    totalMarks += marks[i];
                    sc.nextLine(); 
                    break;

                } catch (InputMismatchException e) {

                    System.out.println("Invalid Input! Enter numeric marks only.");
                    sc.nextLine();

                }
            }
        }
    }

    
    public void calculateResult() {

        maximumMarks = numberOfSubjects * 100;

        average = (double) totalMarks / numberOfSubjects;

        percentage = ((double) totalMarks / maximumMarks) * 100;

        if (percentage >= 90) {
            grade = "A+";
            status = "PASS";
        }
        else if (percentage >= 80) {
            grade = "A";
            status = "PASS";
        }
        else if (percentage >= 70) {
            grade = "B";
            status = "PASS";
        }
        else if (percentage >= 60) {
            grade = "C";
            status = "PASS";
        }
        else if (percentage >= 50) {
            grade = "D";
            status = "PASS";
        }
        else {
            grade = "F";
            status = "FAIL";
        }
    }

    
    public void displayResult() {

       
        System.out.println("  STUDENT GRADE REPORT");
        

        for (int i = 0; i < numberOfSubjects; i++) {
            System.out.println(subjectNames[i] + " : " + marks[i]);
        }

        System.out.println("------------------------------------");
        System.out.println("Total Marks      : " + totalMarks + "/" + maximumMarks);
        System.out.printf("Average Marks    : %.2f%n", average);
        System.out.printf("Percentage       : %.2f%%%n", percentage);
        System.out.println("Grade            : " + grade);
        System.out.println("Status           : " + status);
        System.out.println("====================================");
    }

    
    public static void main(String[] args) {

        StudentGradeCalculator student = new StudentGradeCalculator();

        student.calculateResult();
        student.displayResult();
    }
}

   