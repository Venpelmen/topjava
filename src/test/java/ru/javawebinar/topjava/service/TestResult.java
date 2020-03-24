package ru.javawebinar.topjava.service;

public class TestResult {

    private String methodName;
    private long time;
    public TestResult(String methodName, long time) {
        this.methodName = methodName;
        this.time = time;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "methodName='" + methodName + '\'' +
                ", time=" + time +
                '}';
    }
}
