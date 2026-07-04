package dao;

import model.Student;

import java.util.List;

public interface IStudentDAO {
    public List<Student> getAllStudents();
    public Student getStudentById( int id , String role);
    public Student getStudentByEmail( String email , String role);
    public List<Student> getStudentByName( String name , String role);
    public boolean checkTk(String email);
    public int login (String username, String password);
    public boolean addStudent(Student student);
    public boolean updateStudent(Student student);
    public boolean deleteStudent(int id);
    public boolean updatePassword(int id, String password);

}
