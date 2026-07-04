package dao.impl;

import dao.IStudentDAO;
import model.Student;
import org.mindrot.jbcrypt.BCrypt;
import utils.DBUtil;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements IStudentDAO {

    private void setobj(ResultSet rs, Student s) throws SQLException {
        s.setId(rs.getInt("id"));
        s.setName(rs.getString("name"));
        s.setDob(rs.getDate("dob").toLocalDate());
        s.setEmail(rs.getString("email"));
        s.setSex(rs.getBoolean("sex"));
        s.setPhone(rs.getString("phone"));
        s.setRole(Student.Role.valueOf(rs.getString("role")));
        s.setCreate_at(rs.getDate("create_at").toLocalDate());
    }

    private Student setsql(String str, PreparedStatement pstmt) throws SQLException {
        pstmt.setString(1, str);
        ResultSet rs = pstmt.executeQuery();
        Student s = new Student();
        if (rs.next()) {
            setobj(rs, s);
            return s;
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {
        Connection conn = null;
        List<Student> students = new ArrayList<>();
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from student WHERE role != 'ADMIN'";
            if (conn == null) {
                throw new RuntimeException("Không kết nối được database");
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Student s = new Student();
                setobj(rs, s);
                students.add(s);
            }
            return students;
        } catch (SQLException e) {
            System.out.println("Không tìm thấy bảng student");
            return students;
        }
        finally {
            DBUtil.closeConnection(conn);
        }
    }


    @Override
    public Student getStudentById( int id , String role) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM student WHERE id = ? AND role != ? ;" );
            pstmt.setInt(1, id);
            pstmt.setString(2, role == null ? "null" : role);
            ResultSet rs = pstmt.executeQuery();
            Student s = new Student();
            if (rs.next()) {
                setobj(rs, s);
                return s;
            }

            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DBUtil.closeConnection(conn);
        }
    }



    @Override
    public Student getStudentByEmail(String email , String role) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM student WHERE email = ? AND role != ?");
            pstmt.setString(1, email);
            pstmt.setString(2, role == null ? "null" : role);
            ResultSet rs = pstmt.executeQuery();
            Student s = new Student();
            if (rs.next()) {
                setobj(rs, s);
                return s;
            }
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DBUtil.closeConnection(conn);
        }
    }



    @Override
    public List<Student> getStudentByName(String name , String role) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement( "SELECT * FROM student WHERE name ILIKE ? AND role != ?");
            pstmt.setString(1, "%" + name + "%");
            pstmt.setString(2, role == null ? "null" : role);
            ResultSet rs = pstmt.executeQuery();
            List<Student> students = new ArrayList<>();
            while (rs.next()) {
                Student s = new Student();
                setobj(rs, s);
                students.add(s);
            }
            return students;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public boolean checkTk(String email) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement("SELECT password FROM student WHERE email = ?");
            pstmt.setString(1 , email);
            ResultSet rs = pstmt.executeQuery();
            Student  s = new Student();
            if (rs.next()) {
                s.setPassword(rs.getString("password"));
            }
            return s.getPassword() == null || s.getPassword().isEmpty();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtil.closeConnection(conn);
        }

    }

    @Override
    public int login(String email, String password) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            PreparedStatement pstmt = conn.prepareStatement( "SELECT id, password FROM student WHERE email = ?");
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String hash = rs.getString("password");

                if (BCrypt.checkpw(password, hash)) {
                    return rs.getInt("id");
                }
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public boolean addStudent(Student student) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            PreparedStatement  pstmt = conn.prepareStatement(
            "INSERT INTO student(name, dob, email, sex, phone, role, password )" +
                "VALUES (?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, student.getName());
            pstmt.setDate(2, java.sql.Date.valueOf(student.getDob()));
            pstmt.setString(3, student.getEmail());
            pstmt.setBoolean(4, student.getSex());
            pstmt.setString(5, student.getPhone());
            pstmt.setString(6, student.getRole().name());
            pstmt.setString(7, BCrypt.hashpw(student.getPassword(), BCrypt.gensalt()));
            int rs = pstmt.executeUpdate();
            return rs > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public boolean updateStudent(Student student) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            PreparedStatement  pstmt = conn.prepareStatement(
            "UPDATE student SET name = ? , dob = ? , sex = ? ,  phone = ?  WHERE id = ?");
            pstmt.setString(1, student.getName());
            pstmt.setDate(2, java.sql.Date.valueOf(student.getDob()));
            pstmt.setBoolean(3, student.getSex());
            pstmt.setString(4, student.getPhone());
            pstmt.setInt(5, student.getId());
            int rs = pstmt.executeUpdate();
            return rs > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public boolean deleteStudent(int id) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            PreparedStatement  pstmt = conn.prepareStatement(
                    "DELETE FROM student WHERE id = ?");
            pstmt.setInt(1, id);
            int rs = pstmt.executeUpdate();
            return rs > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtil.closeConnection(conn);
        }
    }

    @Override
    public boolean  updatePassword(int id, String password) {
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            assert conn != null;
            PreparedStatement  pstmt = conn.prepareStatement(
                    "UPDATE student SET password = ? WHERE id = ?");
            pstmt.setString(1, BCrypt.hashpw(password, BCrypt.gensalt()));
            pstmt.setInt(2, id);
            int rs = pstmt.executeUpdate();
            return rs > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        finally {
            DBUtil.closeConnection(conn);
        }
    }
}
