package dao;

import model.Course;
import model.Student;

import java.util.List;

public interface ICourseDAO {
    public List<Course> getAllCourses();
    public Course getCourseById(int id);
    public List<Course> getCourseByName( String name);
    public boolean addCourse(Course c);

    public boolean updateCourse(Course c);
    public boolean deleteCourse(int id);
}
