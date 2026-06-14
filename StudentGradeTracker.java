import java.util.ArrayList;
import java.util.Scanner;

public class StudentGradeTracker {

    // Inner class representing a Student -----------------------------------------------
    static class Student {
        String name;
        ArrayList<Double> grades;
 
        Student(String name) {
            this.name = name;
            this.grades = new ArrayList<>();
        }
 
        void addGrade(double grade) {
            grades.add(grade);
        }
 
        double getAverage() {
            if (grades.isEmpty()) return 0;
            double sum = 0;
            for (double g : grades) sum += g;
            return sum / grades.size();
        }
 
        double getHighest() {
            if (grades.isEmpty()) return 0;
            double max = grades.get(0);
            for (double g : grades) if (g > max) max = g;
            return max;
        }
 
        double getLowest() {
            if (grades.isEmpty()) return 0;
            double min = grades.get(0);
            for (double g : grades) if (g < min) min = g;
            return min;
        }
 
        String getLetterGrade() {
            double avg = getAverage();
            if (avg >= 90) return "A";
            else if (avg >= 80) return "B";
            else if (avg >= 70) return "C";
            else if (avg >= 60) return "D";
            else return "F";
        }
 
        @Override
        public String toString() {
            return String.format("%-20s | Avg: %6.2f | High: %6.2f | Low: %6.2f | Grade: %s",
                    name, getAverage(), getHighest(), getLowest(), getLetterGrade());
        }
    }
 
    // Main Menu ---------------------------------------------------------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
 
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║     Student Grade Tracker — v1.0     ║");
        System.out.println("╚══════════════════════════════════════╝");
 
        boolean running = true;
        while (running) {
            System.out.println("\n┌─ MENU ─────────────────────────────┐");
            System.out.println("│  1. Add Student                    │");
            System.out.println("│  2. Add Grades to Student          │");
            System.out.println("│  3. View All Students (Report)     │");
            System.out.println("│  4. View Class Statistics          │");
            System.out.println("│  5. Exit                           │");
            System.out.println("└────────────────────────────────────┘");
            System.out.print("Choose an option: ");
 
            int choice = getIntInput(sc);
            switch (choice) {
                case 1 -> addStudent(sc, students);
                case 2 -> addGrades(sc, students);
                case 3 -> printReport(students);
                case 4 -> printClassStats(students);
                case 5 -> { running = false; System.out.println("Goodbye!"); }
                default -> System.out.println("Invalid option. Try again.");
            }
        }
        sc.close();
    }
 
    // Add a new student -----------------------------------------------
    static void addStudent(Scanner sc, ArrayList<Student> students) {
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) { System.out.println("Name cannot be empty."); return; }
        students.add(new Student(name));
        System.out.println("✔ Student '" + name + "' added.");
    }
 
    // Add grades to a student ---------------------------------------------------------
    static void addGrades(Scanner sc, ArrayList<Student> students) {
        if (students.isEmpty()) { System.out.println("No students yet. Add a student first."); return; }
 
        System.out.println("Select student:");
        for (int i = 0; i < students.size(); i++)
            System.out.println("  " + (i + 1) + ". " + students.get(i).name);
 
        int idx = getIntInput(sc) - 1;
        if (idx < 0 || idx >= students.size()) { System.out.println("Invalid selection."); return; }
 
        Student student = students.get(idx);
        System.out.print("How many grades to add? ");
        int count = getIntInput(sc);
 
        for (int i = 0; i < count; i++) {
            System.out.print("  Enter grade " + (i + 1) + " (0–100): ");
            double grade = getDoubleInput(sc);
            if (grade < 0 || grade > 100) { System.out.println("  Grade out of range. Skipped."); continue; }
            student.addGrade(grade);
        }
        System.out.println("✔ Grades added for " + student.name);
    }
 
    // Print summary report ---------------------------------------------------------------
    static void printReport(ArrayList<Student> students) {
        if (students.isEmpty()) { System.out.println("No students to display."); return; }
 
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                   STUDENT GRADE REPORT                      ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.printf("║ %-20s | %-10s | %-10s | %-10s | %s%n", "Name", "Average", "Highest", "Lowest", "Grade ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        for (Student s : students)
            System.out.println("║ " + s + " ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }
 
    // Class-wide statistics ---------------------------------------------------------------
    static void printClassStats(ArrayList<Student> students) {
        if (students.isEmpty()) { System.out.println("No students available."); return; }
 
        double classTotal = 0, classHigh = Double.MIN_VALUE, classLow = Double.MAX_VALUE;
        String topStudent = "", bottomStudent = "";
 
        for (Student s : students) {
            if (s.grades.isEmpty()) continue;
            double avg = s.getAverage();
            classTotal += avg;
            if (avg > classHigh) { classHigh = avg; topStudent = s.name; }
            if (avg < classLow)  { classLow  = avg; bottomStudent = s.name; }
        }
 
        System.out.println("\n📊 CLASS STATISTICS");
        System.out.printf("  Class Average  : %.2f%n", classTotal / students.size());
        System.out.printf("  Top Student    : %s (%.2f)%n", topStudent, classHigh);
        System.out.printf("  Lowest Average : %s (%.2f)%n", bottomStudent, classLow);
    }
 
    // Utility helpers -------------------------------------------------------------------
    static int getIntInput(Scanner sc) {
        try { int v = Integer.parseInt(sc.nextLine().trim()); return v; }
        catch (NumberFormatException e) { return -1; }
    }
 
    static double getDoubleInput(Scanner sc) {
        try { return Double.parseDouble(sc.nextLine().trim()); }
        catch (NumberFormatException e) { return -1; }
    }
}
