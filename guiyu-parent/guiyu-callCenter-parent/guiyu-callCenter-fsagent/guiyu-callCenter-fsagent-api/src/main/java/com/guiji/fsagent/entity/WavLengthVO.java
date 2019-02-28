package com.guiji.fsagent.entity;

public class WavLengthVO {
    private String fileName;
    private double length;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "WavLengthVO{" +
                "fileName='" + fileName + '\'' +
                ", length=" + length +
                '}';
    }
}
