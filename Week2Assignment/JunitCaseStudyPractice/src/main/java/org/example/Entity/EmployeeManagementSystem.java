package org.example.Entity;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Scanner;

public class EmployeeManagementSystem {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        EmployeeDAO employeeDAO = new EmployeeDAO(factory);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1: Update");
            System.out.println("2: View Employee");
            System.out.println("3: Update Employee Salary");
            System.out.println("4: Delete Employee");
            System.out.println("5: Close");

            System.out.print("Enter Choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice){
                case 1:
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Department: ");
                    String department = sc.nextLine();

                    System.out.print("Enter Salary: ");
                    double salary = sc.nextDouble();

                    Employee employee = new Employee(name, department, salary);
                    employeeDAO.addEmployee(employee);
                    break;
                case 2:
                    System.out.print("Enter Employee ID: ");
                    int id = sc.nextInt();
                    Employee emp = employeeDAO.getEmployeeDetailsById(id);

                    if(emp != null){
                        System.out.println("Employee Found: " + emp);
                    }else{
                        System.out.println("Employee not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Employee ID: ");
                    int updateId = sc.nextInt();

                    System.out.print("Enter New Salary: ");
                    double newSalary = sc.nextDouble();

                    employeeDAO.updateSalaryById(updateId, newSalary);
                    break;

                case 4:
                    System.out.print("Enter Employee ID: ");
                    int deleteId = sc.nextInt();

                    employeeDAO.removeEmployeeById(deleteId);
                    break;

                case 5:
                    System.out.println("Exiting Application...");
                    sc.close();
                    factory.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");

            }

        }
    }
}
