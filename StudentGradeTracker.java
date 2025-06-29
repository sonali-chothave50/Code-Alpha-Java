import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeTracker {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine(); // consume newline

        // Input student details
        for (int i = 0; i < n; i++) {
            System.out.print("\nEnter name of student " + (i + 1) + ": ");
            String name = sc.nextLine();

            System.out.print("Enter grade of " + name + ": ");
            double grade = sc.nextDouble();
            sc.nextLine(); // consume newline

            students.add(new Student(name, grade));
        }

        // Process grades
        double total = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;
        String topStudent = "", bottomStudent = "";

        for (Student s : students) {
            total += s.grade;

            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s.name;
            }

            if (s.grade < lowest) {
                lowest = s.grade;
                bottomStudent = s.name;
            }
        }

        double average = total / students.size();

        // Summary Report
        System.out.println("\n--- STUDENT GRADE SUMMARY REPORT ---");
        for (Student s : students) {
            System.out.println("Name: " + s.name + " | Grade: " + s.grade);
        }

        System.out.println("\nAverage Grade: " + average);
        System.out.println("Highest Grade: " + highest + " (Student: " + topStudent + ")");
        System.out.println("Lowest Grade : " + lowest + " (Student: " + bottomStudent + ")");

        sc.close();
    }
}
