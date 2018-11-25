package com.guiji.process.agent.model;


import java.util.List;

public class CfgAgentNodeVO {

    private List<CfgProcessOperVO> nodeOpers;

    private List<CfgProcessVO> processes;

    public List<CfgProcessOperVO> getNodeOpers() {
        return nodeOpers;
    }

    public void setNodeOpers(List<CfgProcessOperVO> nodeOpers) {
        this.nodeOpers = nodeOpers;
    }

    public List<CfgProcessVO> getProcesses() {
        return processes;
    }

    public void setProcesses(List<CfgProcessVO> processes) {
        this.processes = processes;
    }

    @Override
    public String toString() {
        return "CfgAgentNodeVO{" +
                "nodeOpers=" + nodeOpers +
                ", processes=" + processes +
                '}';
    }
}
