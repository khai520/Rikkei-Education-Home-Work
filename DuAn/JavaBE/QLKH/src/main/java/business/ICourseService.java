package business;


import model.Course;

import java.util.List;

public interface ICourseService {
    public List<Course> getAllCourse();
    public List<Course> getCourseByName(String name);
    public Course getCourseById(int id);
    public boolean addCourse(Course course);
    public boolean updateCourse(Course course);
    public boolean deleteCourse(int id);
}
