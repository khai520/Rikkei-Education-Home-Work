package business;

import model.Student;

import java.util.List;

public interface IStudentService {
    public List<Student> getAllStudents();
    public int login(String username, String password);
    public Student getStudentById(int id , String  role);
    public boolean checkTk(String email);
    public Student getStudentByEmail(String email , String role);
    public List<Student> getStudentByName(String name , String role);
    public boolean register(Student student);
    public boolean updatePassword(int id, String password);
    public boolean updateStudent(Student student);
    public boolean deleteStudent(int id);
    public boolean ktmkc(int id , String password);
}
