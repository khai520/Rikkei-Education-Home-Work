package dao.impl;

import dao.ICourseDAO;
import model.Course;
import utils.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseDAO implements ICourseDAO {

    private void setobj(ResultSet rs, Course c) throws SQLException
    {
        c.setId(rs.getInt("id"));
        c.setName(rs.getString("name"));
        c.setDuration(rs.getInt("duration"));
        c.setInstructor(rs.getString("instructor"));
        c.setCreate_at(rs.getDate("create_at").toLocalDate());
    }

    @Override
    public List<Course> getAllCourses() {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        List<Course> courses = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from course";
            if (conn == null) {
                throw new RuntimeException("Không kết nối được database");
            }
            pstmt  = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                setobj(rs, c);
                courses.add(c);
            }
            return courses;
        } catch (SQLException e) {
            System.out.println("Không tìm thấy bảng student");
            return courses;
        }
        finally {
            DBUtil.closeConnection(conn , rs, pstmt);
        }
    }

    @Override
    public Course getCourseById(int id) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            pstmt = conn.prepareStatement( "SELECT * FROM course WHERE id = ?");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            Course c = new Course();
            if (rs.next()) {
                setobj(rs, c);
                return c;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DBUtil.closeConnection(conn , rs, pstmt);
        }
    }

    @Override
    public List<Course> getCourseByName(String name) {
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        List<Course> courses = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            pstmt = conn.prepareStatement( "SELECT * FROM course WHERE name ILIKE ? ");
            pstmt.setString(1, "%" + name + "%");
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Course c = new Course();
                setobj(rs, c);
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
    public boolean addCourse(Course c) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            pstmt = conn.prepareStatement(
                    "INSERT INTO course(name, duration, instructor) VALUES (?, ?, ?)");
            pstmt.setString(1, c.getName());
            pstmt.setInt(2, c.getDuration());
            pstmt.setString(3, c.getInstructor());
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
    public boolean updateCourse(Course c) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            pstmt = conn.prepareStatement(
                    "UPDATE course SET name = ?, duration = ?, instructor = ? WHERE id = ?");
            pstmt.setString(1, c.getName());
            pstmt.setInt(2, c.getDuration());
            pstmt.setString(3, c.getInstructor());
            pstmt.setInt(4, c.getId());
            int rs = pstmt.executeUpdate();
            return rs > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtil.closeConnection(conn , null, pstmt);
        }
    }

    @Override
    public boolean deleteCourse(int id) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
             pstmt = conn.prepareStatement(
                    "DELETE FROM course WHERE id = ?");
            pstmt.setInt(1, id);
            int rs = pstmt.executeUpdate();

            return rs > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtil.closeConnection(conn , null, pstmt);
        }
    }
}
