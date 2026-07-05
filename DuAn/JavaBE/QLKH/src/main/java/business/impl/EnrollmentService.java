package business.impl;

import business.IEnrollmentService;
import dao.impl.EnrollmentDAO;
import model.Course;
import model.Enrollment;
import model.Student;
import model.dto.EnrollmentDTO;


import java.util.List;

public class EnrollmentService implements IEnrollmentService {
    private final EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
    @Override
    public List<Student> getStudentByCourseId(int id , Enrollment.Status status) {
        return enrollmentDAO.getStudentByCourseId(id , status);
    }

    @Override
    public List<Course> getCourseByStudentId(int id , Enrollment.Status status) {
        return enrollmentDAO.getCourseByStudentId(id, status);
    }

    @Override
    public List<EnrollmentDTO> getAllDTO(Enrollment.Status status) {
        return enrollmentDAO.getAllDTO(status);
    }

    @Override
    public List<Enrollment> getAll(Enrollment.Status status) {
        return enrollmentDAO.getAll(status);
    }

    @Override
    public boolean checkId(int id, Enrollment.Status status) {
        return enrollmentDAO.checkId(id, status);
    }

    @Override
    public boolean checkIC(int studentId, int courseid, Enrollment.Status status) {
        return enrollmentDAO.checkIC(studentId, courseid, status);
    }

    @Override
    public boolean addEnrollment(int studentid , int courseid) {
        return enrollmentDAO.addEnrollment(studentid , courseid);
    }


    @Override
    public boolean updateStatus(int id, Enrollment.Status status) {
        return enrollmentDAO.updateStatus(id, status);
    }

    @Override
    public boolean deleteEnrollment(int studentId, int courseId) {
        return enrollmentDAO.deleteByStudentAndCourse(studentId, courseId);
    }
}
