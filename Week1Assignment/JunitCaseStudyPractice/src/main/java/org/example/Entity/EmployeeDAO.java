package org.example.Entity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class EmployeeDAO {
    private SessionFactory factory;
    public EmployeeDAO(SessionFactory factory){
        this.factory=factory;
    }

    public void addEmployee(Employee employee){
        Session session = null;
        Transaction trx= null;
        try{
            session=factory.openSession();
            trx=session.beginTransaction();
            session.persist(employee);
            trx.commit();
            System.out.println("Employee Added");

        }catch (Exception e){
            if(trx!=null) {
                trx.rollback();
            }
            e.printStackTrace();
        }finally {
            if(session!=null) {
                session.close();
            }
        }
    }
    public Employee getEmployeeDetailsById(int id){
     Session session = null;
     Employee emp = null;
     try{
         session = factory.openSession();
         emp=session.get(Employee.class,id);
     }catch (Exception e){
         e.printStackTrace();
     }finally {
         if(session!=null){
             session.close();
         }
     }
     return emp;
    }

    public void updateSalaryById(int id, double newSalary) {
        Session session = null;
        Transaction trx = null;
        try {
            session=factory.openSession();
            trx=session.beginTransaction();
            Employee emp = session.get(Employee.class, id);
            if (emp != null) {
                emp.setSalary(newSalary);
                trx.commit();
                System.out.println("Salary Updated");
            } else {
                System.out.println("Id not found");
                trx.rollback();
            }
        }catch (Exception e){
            if(trx!=null){
                trx.rollback();
            }
        }finally {
            if(session!=null){
                session.close();
            }
        }
    }

    public void removeEmployeeById(int id){
        Session session = null;
        Transaction trx=null;
        try{
            session=factory.openSession();
            trx = session.beginTransaction();
            Employee emp = session.get(Employee.class, id);
            if(emp!=null){
                session.remove(emp);
                trx.commit();
                System.out.println("Removed Successfully");
            }else {
                System.out.println("Employee not found");
                trx.rollback();
            }
        }catch (Exception e){
            if(trx!=null){
                trx.rollback();
            }
        }finally {
            if(session!=null){
                session.close();
            }
        }
    }
}

