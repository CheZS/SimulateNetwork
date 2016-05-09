package model;

public class Node {

	public NIC[] nics;
	public Link[] links;
	public double computeSpeed;
	
	public Node(double computeSpeed, int nicNum, int nicSpeed, double ratio) {
		this.computeSpeed = computeSpeed;
		this.nics = new NIC[nicNum];
		for (int i = 0; i < nicNum; i++) {
			this.nics[i] = new NIC(nicSpeed, ratio);
		}
		this.links = new Link[nicNum];
	}
	
	public void addLink(Link link, int port) {
		this.links[port] = link;
		link.addNode(this);
	}
	
	public void doSwitch(Frame frame, int port) {
		this.nics[port].pushToSend(frame);
	}
	
	public double TraficIntensity(int port) {
		
		
		return 0;
	}
	
	public void tick() {
		
	}
}
