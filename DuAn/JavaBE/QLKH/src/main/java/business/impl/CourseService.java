package business.impl;

import business.ICourseService;
import dao.impl.CourseDAO;
import dao.impl.EnrollmentDAO;
import model.Course;
import model.Enrollment;

import java.util.List;

public class CourseService implements ICourseService {
    private final CourseDAO courseDAO = new CourseDAO();
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
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
        if(enrollmentDAO.existCourse(id )){
            System.out.println("Khóa học đang có học viên.");
            return false;
        }
        return courseDAO.deleteCourse(id);
    }
}
