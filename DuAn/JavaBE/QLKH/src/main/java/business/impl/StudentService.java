package business.impl;

import business.IStudentService;
import dao.impl.StudentDAO;
import model.Student;

import java.util.List;

public class StudentService implements IStudentService {
    private final StudentDAO studentDAO = new StudentDAO();
    @Override
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    public int login(String email, String password)
    {
        return studentDAO.login(email , password);
    }

    @Override
    public Student getStudentById(int id ,String  role) {
        return studentDAO.getStudentById(id , role);
    }

    @Override
    public boolean checkTk(String email) {
        return studentDAO.checkTk(email);
    }

    @Override
    public Student getStudentByEmail(String email , String role) {
        return studentDAO.getStudentByEmail(email , role);
    }

    @Override
    public List<Student> getStudentByName(String name, String role) {
        return studentDAO.getStudentByName(name , role);
    }

    @Override
    public boolean register(Student student) {
        return studentDAO.addStudent(student);
    }

    @Override
    public boolean updatePassword(int id, String password) {
        return studentDAO.updatePassword(id, password);
    }

    @Override
    public boolean updateStudent(Student student) {
        return studentDAO.updateStudent(student);
    }

    @Override
    public boolean deleteStudent(int id) {
        return studentDAO.deleteStudent(id);
    }

}
