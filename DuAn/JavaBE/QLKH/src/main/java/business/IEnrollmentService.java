package business;

import model.Course;
import model.Enrollment;
import model.Student;
import model.dto.EnrollmentDTO;

import java.util.List;

public interface IEnrollmentService {
    public List<Student> getStudentByCourseId(int id , Enrollment.Status status);
    public List<Course> getCourseByStudentId(int id, Enrollment.Status status);
    public List<EnrollmentDTO> getAllDTO( Enrollment.Status status);
    public List<Enrollment> getAll( Enrollment.Status status);
    public boolean checkId (int id , Enrollment.Status status);
    public boolean checkIC(int studentId , int courseid ,  Enrollment.Status status);
    public boolean addEnrollment(int studentid , int courseid);
    public boolean updateStatus(int id, Enrollment.Status status);
    public boolean deleteEnrollment(int studentId, int courseId);
}
