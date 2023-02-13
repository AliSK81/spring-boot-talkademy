package view;

import controller.MySQLManager;
import model.Course;
import model.Professor;
import model.Student;

import java.util.Scanner;

public class CommandRunner {
    private static CommandRunner instance;

    private final Scanner sc = new Scanner(System.in);
    private final MySQLManager dbMgr;

    private CommandRunner() {
        dbMgr = MySQLManager.getInstance();
    }

    public static CommandRunner getInstance() {
        if (instance == null) {
            instance = new CommandRunner();
        }
        return instance;
    }

    public void run() {
        System.out.println("Connecting...");
        dbMgr.startConnection(
                "jdbc:mysql://localhost/CourseManagement",
                "alisk",
                "aliskalisk"
        );
        System.out.println("Connected! Enter help or exit.");
        String command = "";
        while (!command.equals("exit")) {
            System.out.print("> ");
            execute(command = sc.nextLine());
        }
        dbMgr.closeConnection();
    }

    private void execute(String command) {

        String[] args = command.split("\\s+");

        switch (args[0]) {
            case "help":
                showHelp();
                break;
            case "add": {
                switch (args[1]) {
                    case "student":
                        addStudent(args[2], args[3]);
                        break;
                    case "course":
                        addCourse(args[2], Integer.parseInt(args[3]));
                        break;
                    case "professor":
                        addProfessor(args[2], args[3]);
                        break;
                }
                break;
            }
            case "accept":
                acceptCourse(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                break;
            case "register":
                registerCourse(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                break;
            case "delCourse":
                deleteCourse(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                break;
            case "changeFav":
                changeStudentFav(Integer.parseInt(args[1]), Integer.parseInt(args[2]));
                break;
            case "score":
                scoreStudent(Integer.parseInt(args[1]), Integer.parseInt(args[2]), Double.parseDouble(args[3]));
                break;
            case "view": {
                switch (args[1]) {
                    case "average":
                        viewAverageScore(Integer.parseInt(args[2]));
                        break;
                    case "students":
                        viewStudentsGPA(Double.parseDouble(args[2]));
                        break;
                    case "favorites":
                        viewStudentCountForEachFavCourse();
                        break;
                }
                break;
            }
        }
    }

    private void addCourse(String name, int capacity) {
        dbMgr.insertCourse(Course.builder()
                .name(name).capacity(capacity).build());
    }

    private void addStudent(String firstName, String lastName) {
        dbMgr.insertStudent(Student.builder()
                .fName(firstName).lName(lastName).build());
    }

    private void addProfessor(String firstName, String lastName) {
        dbMgr.insertProfessor(Professor.builder()
                .fName(firstName).lName(lastName).build());
    }

    private void acceptCourse(int professorId, int courseId) {
        dbMgr.insertSection(professorId, courseId);
    }

    private void registerCourse(int studentId, int courseId) {
        dbMgr.insertStudentCourse(studentId, courseId);
    }

    private void deleteCourse(int studentId, int courseId) {
        dbMgr.deleteStudentCourse(studentId, courseId);
    }

    private void changeStudentFav(int studentId, int courseId) {
        dbMgr.updateStudentFavCourse(studentId, courseId);
    }

    private void scoreStudent(int professorId, int studentId, double score) {
        dbMgr.updateStudentCourseScore(studentId, professorId, score);
        dbMgr.updateGPA(studentId);
    }

    private void viewAverageScore(int courseId) {
        System.out.println("average: " +
                dbMgr.selectCourseAverageScore(courseId));
    }

    private void viewStudentsGPA(double minGPA) {
        dbMgr.selectStudentsByGPA(minGPA).forEach(System.out::println);
    }

    private void viewStudentCountForEachFavCourse() {
        dbMgr.selectStudentCountForFavCourses().forEach((courseId, count) ->
                System.out.printf("courseId: %d, favCount: %d\n", courseId, count));
    }

    private void showHelp() {
        System.out.println("add student <FIRST_NAME> <LAST_NAME>");
        System.out.println("add professor <FIRST_NAME> <LAST_NAME>");
        System.out.println("add course <NAME> <CAPACITY>");
        System.out.println("accept <PROFESSOR_ID> <COURSE_ID>");
        System.out.println("register <STUDENT_ID> <COURSE_ID>");
        System.out.println("delCourse <COURSE_ID>");
        System.out.println("changeFav <STUDENT_ID> <COURSE_ID>");
        System.out.println("score <PROFESSOR_ID> <COURSE_ID>");
        System.out.println("view average <COURSE_ID>");
        System.out.println("view students <MIN_GPA>");
        System.out.println("view favorites");
    }

}
