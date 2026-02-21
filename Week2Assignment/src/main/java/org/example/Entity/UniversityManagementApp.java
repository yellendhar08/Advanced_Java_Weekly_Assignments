package org.example.Entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UniversityManagementApp {
    public static void main( String[] args ) {
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        Session session =factory.openSession();
        Transaction trx = session.beginTransaction();
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Enter Department Name: ");
            String deptname = sc.nextLine();
            Department dept = new Department(deptname);

            System.out.print("Enter number of studens: ");
            int noOfStudents = sc.nextInt();
            sc.nextLine();

            List<Student> stuList = new ArrayList<>();
            for(int i=1; i<=noOfStudents; i++){
                System.out.print("Enter "+i+" Student name: ");
                String stuName = sc.nextLine();

                Student student = new Student(stuName);
                student.setDepartment(dept);

                System.out.println("Enter ID Card number for "+stuName+": ");
                String cardNumber = sc.nextLine();

                IDCard idCard = new IDCard(cardNumber);
                student.setIdcard(idCard);

                System.out.println("Enter how many Courses for Student: ");
                int courseCount = sc.nextInt();
                sc.nextLine();
                List<Course> courseList = new ArrayList<>();
                for(int j=1; j<=courseCount; j++){
                    System.out.println("Enter course name "+j+" : ");
                    String courseName = sc.nextLine();
                    Course course = new Course(courseName);
                    courseList.add(course);
                }
                student.setCourseList(courseList);
                stuList.add(student);
            }
            dept.setStudentList(stuList);
            session.persist(dept);
            trx.commit();
            System.out.println("Data Saved");
        }catch (Exception e){
            trx.rollback();
            e.printStackTrace();
        }finally {
            session.close();
            factory.close();
            sc.close();
        }
    }
}
