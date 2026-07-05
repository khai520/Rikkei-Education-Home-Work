package model;

import java.time.LocalDate;

public class Student {
    private int id ;
    private String name;
    private LocalDate dob;
    private String email;
    private boolean sex ;
    private String phone;
    private Role role;
    public enum Role {
        ADMIN,
        STUDENT
    }
    private String password;
    private LocalDate create_at;

    public Student() {

    }

    public Student(int id, String name, LocalDate dob, String email, boolean sex, String phone, String password, LocalDate create_at , Role role) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.email = email;
        this.sex = sex;
        this.phone = phone;
        this.password = password;
        this.create_at = create_at;
        this.role = role;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) { this.id = id; }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public LocalDate getDob() {
        return dob;
    }
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public boolean getSex(){
        return sex;
    }
    public void setSex (boolean sex){
        this.sex = sex;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public LocalDate getCreate_at() {
        return create_at;
    }
    public void setCreate_at(LocalDate create_at) {
        this.create_at = create_at;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name  + ", dob=" + dob + ", email=" + email + ", sex=" + (sex ? "Nam" : "Nữ")  + ", phone=" + phone  + ", password=" + password + ", create_at=" + create_at + "]";
    }
}
