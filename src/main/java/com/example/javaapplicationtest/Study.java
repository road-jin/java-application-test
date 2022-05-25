package com.example.javaapplicationtest;

public class Study {

    private StudyStatus status;

    private int limit;

    private String name;

    public Study() {
        this.status = StudyStatus.DRAFT;
        this.limit = 1;
    }

    public Study(int limit) {
        if(limit < 0) {
            throw new IllegalArgumentException("limit는 0보다 커야 합니다.");
        }
        this.status = StudyStatus.DRAFT;
        this.limit = limit;
    }

    public Study(int limit, String name) {
        this.status = StudyStatus.DRAFT;
        this.limit = limit;
        this.name = name;
    }

    public StudyStatus getStatus() {
        return this.status;
    }

    public int getLimit() {
        return limit;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Study{" +
                "status=" + status +
                ", limit=" + limit +
                ", name='" + name + '\'' +
                '}';
    }
}
