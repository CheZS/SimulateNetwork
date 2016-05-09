package model;

public class Node {

	public NIC[] nics;
	public Link[] links;
	public Node[] nodes;
	public double computeSpeed;
	
	public Node(double computeSpeed, int nicNum, int nicSpeed, double ratio) {
		this.computeSpeed = computeSpeed;
		this.nics = new NIC[nicNum];
		for (int i = 0; i < nicNum; i++) {
			this.nics[i] = new NIC(nicSpeed, ratio);
		}
		this.links = new Link[nicNum];
		this.nodes = new Node[nicNum];
	}
	
	public boolean addLink(Link link, int port) {
		if (links[port] != null) return false;
		this.links[port] = link;
		this.nodes[port] = link.getAnotherNode(this);
		return true;
	}
	
	public boolean addLink(Link link) {
		int i = 0;
		while (i < links.length && links[i] != null) i++;
		if (i < links.length) {
			addLink(link, i);
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removeLink(Link link, int port) {
		if (links[port] == link) {
			links[port] = null;
			nodes[port] = null;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean removeLink(Link link) {
		int i = 0;
		for (; i < links.length && links[i] != link; i++) {}
		return i < links.length ? removeLink(link, i) : false;
	}
	
	public void doSwitch(Frame frame, int port) {
		this.nics[port].pushToSend(frame);
	}
	
	public double TraficIntensity(int port) {
		return nics[port].traficIntensity();
	}
	
	public void tick() {
		
	}
}
