package com.guiji.process.agent.model;

public class CommandResult {
    public static final int EXIT_VALUE_TIMEOUT=-1;
    int exitValue;
    private String output;
    private String error;
 
 
    public int getExitValue() {
        return exitValue;
    }
 
    public void setExitValue(int exitValue) {
        this.exitValue = exitValue;
    }
 
    public String getOutput() {
        return output;
    }
 
    public void setOutput(String output) {
        this.output = output;
    }
 
    public String getError() {
        return error;
    }
 
    public void setError(String error) {
        this.error = error;
    }
}
