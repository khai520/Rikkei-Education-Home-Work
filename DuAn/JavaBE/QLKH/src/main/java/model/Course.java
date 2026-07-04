package model;

import java.time.LocalDate;

public class Course {
    private int id;
    private String name;
    private int duration;
    private String instruction;
    private LocalDate create_at;

    public Course() {

    }

    public Course(int id, String name, int duration, String instruction, LocalDate create_at) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.instruction = instruction;
        this.create_at = create_at;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getInstruction() {
        return instruction;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }
    public LocalDate getCreate_at() {
        return create_at;
    }
    public void setCreate_at(LocalDate create_at) {
        this.create_at = create_at;
    }
    @Override
    public String toString() {
        return "Course [id =" + id + ", name =" + name  + ", duration=" + duration + ", instruction=" + instruction + ", create_at=" + create_at + "]";
    }
}
