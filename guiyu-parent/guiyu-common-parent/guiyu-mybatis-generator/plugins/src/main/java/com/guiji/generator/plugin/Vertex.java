package com.guiji.generator.plugin;

public class Vertex {


	//存放点信息
	public int data;
	//与该点邻接的第一个边节点
	public Edge firstEdge;
	public int getData() {
		return data;
	}
	public void setData(int data) {
		this.data = data;
	}
	public Edge getFirstEdge() {
		return firstEdge;
	}
	public void setFirstEdge(Edge firstEdge) {
		this.firstEdge = firstEdge;
	}

	
}
