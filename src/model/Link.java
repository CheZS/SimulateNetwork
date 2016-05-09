package model;

public class Link {

	public int timeDelay;
	public int bandwidth;
	public double symbolErrorRate;
	public Node[] nodes;
	public Frame[] node0Tonode1;	// 循环队列
	public Frame[] node1Tonode0;	// 循环队列
	public int timePoint;
	
	public Link(int timeDelay, int bandwidth, int symbolErrorRate, Node[] nodes) {
		this.timeDelay = timeDelay;
		this.bandwidth = bandwidth;
		this.symbolErrorRate = symbolErrorRate;
		this.nodes = new Node[2];
		for (int i = 0; i < 2; i++) {
			this.nodes[i] = nodes[i];
		}
		this.node0Tonode1 = new Frame[timeDelay];
		this.node1Tonode0 = new Frame[timeDelay];
		this.timePoint = 0;
	}
	
	public Link() {
		
	}
	
	public void tick() {	// 作为观察者
		this.timePoint = (timePoint + 1) % node0Tonode1.length;
	}
	
	public boolean receive(Node node, Frame frame) {
		if (node == this.nodes[0]) {
			this.node0Tonode1[timePoint] = frame;
			return true;
		}
		if (node == this.nodes[1]) {
			this.node1Tonode0[timePoint] = frame;
			return true;
		}
		return false;
	}
	
	public Frame sendTo0() {
		Frame res = node1Tonode0[timePoint];
		node1Tonode0[timePoint] = null;
		return res;
	}
	public Frame sendTo1() {
		Frame res = node0Tonode1[timePoint];
		node0Tonode1[timePoint] = null;
		return res;
	}
	public boolean addNode(Node node) {
		if (nodes[0] == null) {
			nodes[0] = node;
			this.node0Tonode1 = new Frame[timeDelay];
			this.node1Tonode0 = new Frame[timeDelay];
			return true;
		}
		if (nodes[1] == null) {
			nodes[1] = node;
			this.node0Tonode1 = new Frame[timeDelay];
			this.node1Tonode0 = new Frame[timeDelay];
			return true;
		}
		return false;
	}
	
	public boolean removeNode(Node node) {
		if (nodes[0] != null && nodes[0] == node) {
			nodes[0] = null;
			this.node0Tonode1 = null;
			this.node1Tonode0 = null;
			return true;
		}
		if (nodes[1] != null && nodes[1] == node) {
			nodes[1] = null;
			this.node0Tonode1 = null;
			this.node1Tonode0 = null;
			return true;
		}
		return false;
	}
}
