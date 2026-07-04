package business.impl;

import business.ICourseService;
import dao.impl.CourseDAO;
import model.Course;

import java.util.List;

public class CourseService implements ICourseService {
    private final CourseDAO courseDAO = new CourseDAO();
    @Override
    public List<Course> getAllCourse() {
        return courseDAO.getAllCourses();
    }

    @Override
    public List<Course> getCourseByName(String name) {
        return courseDAO.getCourseByName(name);
    }

    @Override
    public Course getCourseById(int id) {
        return courseDAO.getCourseById(id);
    }

    @Override
    public boolean addCourse(Course course) {
        return courseDAO.addCourse(course);
    }

    @Override
    public boolean updateCourse(Course course) {
        return courseDAO.updateCourse(course);
    }

    @Override
    public boolean deleteCourse(int id) {
        return courseDAO.deleteCourse(id);
    }
}
