package com.guiji.generator.plugin;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class graph {
	public Vertex[] vertexList; // 存放点的集合

	public graph(int vertexNum) {
		this.vertexNum = vertexNum;
		vertexList = new Vertex[vertexNum];
	}

	// 点个数
	public int vertexNum;
	// 边个数
	public int edgeLength;
	
	public boolean isVisited[];

	public void initVertext(int datas[]) {
		for (int i = 0; i < vertexNum; i++) {
			Vertex vertext = new Vertex();
			vertext.data = datas[i];
			vertext.firstEdge = null;
			vertexList[i] = vertext;
			// System.out.println("i"+vertexList[i]);
		}
		isVisited = new boolean[vertexNum];
		for (int i = 0; i < isVisited.length; i++) {
			isVisited[i] = false;
		}
	}

	// 针对x节点添加边节点y
	public void addEdge(int x, int y, int weight) {
		Edge edge = new Edge();
		edge.setVertexId(y);
		edge.setWeight(weight);
		// 第一个边节点
		System.out.println(vertexList.length);
		if (null == vertexList[x].firstEdge) {
			vertexList[x].firstEdge = edge;
			edge.setNext(null);
		}
		// 不是第一个边节点,则采用头插法
		else {
			edge.next = vertexList[x].firstEdge;
			vertexList[x].firstEdge = edge;
		}
	}

	// 得到x的邻接点为y的后一个邻接点位置,为-1说明没有找到
	public int getNextNode(int x, int y) {
		int next_node = -1;
		Edge edge = vertexList[x].firstEdge;
		if (null != edge && y == -1) {
			int n = edge.vertexId;
			// 元素还不在stack中
			if (!states.get(n))
				return n;
			return -1;
		}

		while (null != edge) {
			// 节点未访问
			if (edge.vertexId == y) {
				if (null != edge.next) {
					next_node = edge.next.vertexId;
					if (!states.get(next_node))
						return next_node;
				} else
					return -1;
			}
			edge = edge.next;
		}
		return -1;
	}

	// 代表某节点是否在stack中,避免产生回路
	public Map<Integer, Boolean> states = new HashMap();

	// 存放放入stack中的节点
	public Stack<Integer> stack = new Stack();

	// 输出2个节点之间的输出路径
public void visit(int x,int y){
       //初始化所有节点在stack中的情况
       for(int i=0;i<vertexNum;i++){
		states.put(i,false);
	}
        //stack top元素
        int top_node;
	//存放当前top元素已经访问过的邻接点,若不存在则置-1,此时代表访问该top元素的第一个邻接点
        int adjvex_node=-1;
	int next_node;
	stack.add(x);
	states.put(x,true);
	while(!stack.isEmpty()){
		top_node=stack.peek();
		//找到需要访问的节点
               if(top_node==y){
					//打印该路径
					printPath();
					adjvex_node=stack.pop();
					states.put(adjvex_node,false);
               }else{
					//访问top_node的第advex_node个邻接点
		            next_node=getNextNode(top_node,adjvex_node);
					if(next_node!=-1){
						stack.push(next_node);
						//置当前节点访问状态为已在stack中
		                states.put(next_node,true);
						//临接点重置
		                adjvex_node=-1;
					}
		                  //不存在临接点，将stack top元素退出 
		              else{
						//当前已经访问过了top_node的第adjvex_node邻接点
		                                adjvex_node=stack.pop();
						//不在stack中
						states.put(adjvex_node,false);
					}
				}
	}
}

	// 打印stack中信息,即路径信息

	public void printPath() {
		StringBuilder sb = new StringBuilder();
		for (Integer i : stack) {
			sb.append(i + "->");
		}
		sb.delete(sb.length() - 2, sb.length());
		System.out.println(sb.toString());
	}

	public static void main(String[] args) {
		graph g = new graph(9);
		g.initVertext(new int[] {0,1,2,3,4,5,6,7,8});
		g.addEdge(0,1, 1);
		g.addEdge(0,2,1);
		g.addEdge(0,8,1);
		g.addEdge(1,5,1);
		g.addEdge(2,3,1);
		g.addEdge(2,6,1);
		g.addEdge(3,4,1);
		g.addEdge(3,7,1);
		g.addEdge(5,6,1);
		g.addEdge(6,7,1);
		g.addEdge(8,2,1);
		g.visit(0, 7);
	}
}
