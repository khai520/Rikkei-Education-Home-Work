package dao.impl;

import dao.IEnrollmentDAO;
import model.Course;
import model.Enrollment;
import model.Student;
import model.dto.EnrollmentDTO;
import utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class EnrollmentDAO implements IEnrollmentDAO {
    private void setobje(ResultSet rs, Enrollment e) throws SQLException {
        e.setId(rs.getInt("id"));
        e.setCourse_id(rs.getInt("course_id"));
        e.setStudent_id(rs.getInt("student_id"));
        e.setStatus(Enrollment.Status.valueOf(rs.getString("status")));
        e.setRegistered_at(rs.getTimestamp("registered_at").toLocalDateTime());
    }
    private void setobjc(ResultSet rs, Course c) throws SQLException
    {
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setDuration(rs.getInt("duration"));
        c.setInstructor(rs.getString("instructor"));
        c.setCreate_at(rs.getDate("create_at").toLocalDate());
    }
    private void setobjs(ResultSet rs, Student s) throws SQLException {
        s.setId(rs.getInt("id"));
        s.setName(rs.getString("name"));
        s.setDob(rs.getDate("dob").toLocalDate());
        s.setEmail(rs.getString("email"));
        s.setSex(rs.getBoolean("sex"));
        s.setPhone(rs.getString("phone"));
        s.setRole(Student.Role.valueOf(rs.getString("role")));
        s.setCreate_at(rs.getDate("create_at").toLocalDate());
    }
    @Override
    public List<Student> getStudentByCourseId(int id , Enrollment.Status status) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        List<Student> students = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            pstmt = conn.prepareStatement(
                    """
                    SELECT s.*
                    FROM enrollment e
                    JOIN student s
                        ON e.student_id = s.id
                    WHERE e.course_id = ? AND e.status = ?
                    """);
            pstmt.setInt(1, id);
            pstmt.setString(2, status.name());
            rs = pstmt.executeQuery();

            while ( rs.next() ) {
                Student s = new Student();
                setobjs(rs, s);
                students.add(s);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            return students;
        }
        finally {
            DBUtil.closeConnection(conn , rs, pstmt);
        }
    }

    @Override
    public List<Course> getCourseByStudentId(int id , Enrollment.Status status) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        List<Course> courses = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            pstmt = conn.prepareStatement(
                    """
                   SELECT c.*
                   FROM enrollment e
                   JOIN course c
                       ON e.course_id = c.id
                   WHERE e.student_id = ? AND e.status = ?
                   """);
            pstmt.setInt(1, id);
            pstmt.setString(2, status.name());
            rs = pstmt.executeQuery();

            while ( rs.next() ) {
                Course c = new Course();
                setobjc(rs, c);
                courses.add(c);
            }
            return courses;
        } catch (SQLException e) {
            e.printStackTrace();
            return courses;
        }
        finally {
            DBUtil.closeConnection(conn , rs, pstmt);
        }
    }

    @Override
    public List<EnrollmentDTO> getAllDTO(Enrollment.Status status) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        List<EnrollmentDTO> enrollmentDTOS = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            pstmt = conn.prepareStatement(
                    """
                    SELECT
                       e.id,
                       s.name AS student_name,
                       c.name AS course_name,
                       e.status,
                       e.registered_at
                   FROM enrollment e
                   JOIN student s
                       ON e.student_id = s.id
                   JOIN course c
                       ON e.course_id = c.id
                   WHERE e.status = ?
                   """);
            pstmt.setString(1, status.name());
            rs = pstmt.executeQuery();

            while ( rs.next() ) {
                EnrollmentDTO e = new EnrollmentDTO();
                e.id =  rs.getInt("id");
                e.studentName = rs.getString("student_name");
                e.courseName = rs.getString("course_name");
                e.status = Enrollment.Status.valueOf(rs.getString("status"));
                e.registered_at = rs.getTimestamp("registered_at").toLocalDateTime();
                enrollmentDTOS.add(e);
            }
            return enrollmentDTOS;
        } catch (SQLException e) {
            e.printStackTrace();
            return enrollmentDTOS;
        }
        finally {
            DBUtil.closeConnection(conn , rs, pstmt);
        }
    }

    @Override
    public List<Enrollment> getAll(Enrollment.Status status) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        List<Enrollment> enrollments = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            pstmt = conn.prepareStatement(
                    """
                    SELECT
                       id,
                       student_id,
                       course_id,
                       status,
                       registered_at
                   FROM enrollment
                   WHERE status = ?
                   """);
            pstmt.setString(1, status.name());
            rs = pstmt.executeQuery();

            while ( rs.next() ) {
                Enrollment e = new Enrollment();
                setobje(rs, e);
                enrollments.add(e);
            }
            return enrollments;
        } catch (SQLException e) {
            e.printStackTrace();
            return enrollments;
        }
        finally {
            DBUtil.closeConnection(conn , rs, pstmt);
        }
    }

    @Override
    public boolean existCourse(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            pstmt = conn.prepareStatement(
                    """
                    SELECT s.*
                    FROM enrollment e
                    JOIN student s
                        ON e.student_id = s.id
                    WHERE e.course_id = ?
                    """);
            pstmt.setInt(1, id);
            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtil.closeConnection(conn , null, pstmt);
        }
    }

    @Override
    public boolean checkIC(int studentId, int courseid, Enrollment.Status status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            pstmt = conn.prepareStatement(
                    """
                    SELECT
                       *
                   FROM enrollment e
                   JOIN student s
                       ON e.student_id = s.id
                   JOIN course c
                       ON e.course_id = c.id
                   WHERE e.status = ? AND e.student_id = ? AND e.course_id = ?
                   """);
            pstmt.setString(1, status.name());
            pstmt.setInt(2, studentId);
            pstmt.setInt(3, courseid);

            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtil.closeConnection(conn , null, pstmt);
        }
    }

    @Override
    public boolean checkId(int id , Enrollment.Status status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            pstmt = conn.prepareStatement(
                    """
                    SELECT
                       *
                   FROM enrollment
                   WHERE status = ? AND id = ?
                   """);
            pstmt.setString(1, status.name());
            pstmt.setInt(2, id);

            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtil.closeConnection(conn , null, pstmt);
        }
    }

    @Override
    public boolean updateStatus(int id, Enrollment.Status status) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            pstmt = conn.prepareStatement(
                """
                   UPDATE enrollment
                   SET status = ?
                   WHERE id = ?;
                   """);
            pstmt.setString(1, status.name());
            pstmt.setInt(2, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtil.closeConnection(conn , null, pstmt);
        }
    }

    @Override
    public boolean addEnrollment(int studentid , int courseid) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            assert conn != null;

            pstmt = conn.prepareStatement(
            """
                INSERT INTO enrollment(student_id, course_id)
                VALUES (?, ?)
            """);

            pstmt.setInt(1, studentid);
            pstmt.setInt(2, courseid);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeConnection(conn , null, pstmt);
        }
    }

    @Override
    public boolean deleteByStudentAndCourse(int studentId, int courseId) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DBUtil.getConnection();
            assert conn != null;

            pstmt = conn.prepareStatement("""
                DELETE FROM enrollment
                WHERE student_id = ? AND course_id = ?
                """);

            pstmt.setInt(1, studentId);
            pstmt.setInt(2, courseId);

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DBUtil.closeConnection(conn , null, pstmt);
        }
    }


}
