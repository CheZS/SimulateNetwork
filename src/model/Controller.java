package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Controller {

	public int h;
	public int currentTime;
	public Map<Node, Integer> nodes;
	public List<Link> links;
	public boolean[][] topo;
	
	public Controller(int nodeNum) {
		this.nodes = new HashMap<Node, Integer>();
		this.topo = new boolean[nodeNum][nodeNum];
		this.links = new ArrayList<Link>();
	}
	
	public boolean addLink(Node node1, Node node2) {
		Link link = new Link();
		link.addNode(node1);
		link.addNode(node2);
		boolean b = node1.addLink(link);
		b = b && node2.addLink(link);
		if (b == true) {
			updateTopo(node1, node2, true);
			links.add(link);
		} else {
			node1.removeLink(link);
			node2.removeLink(link);
		}
		return b;
	}
	
	public void removeLink(Link link) {
		Node node1 = link.nodes[0];
		Node node2 = link.nodes[1];
		node1.removeLink(link);
		node2.removeLink(link);
		links.remove(link);
	}
	
	public void removeLink(Node node1, Node node2) {
		for (int i = 0; i < node1.nodes.length; i++) {
			if (node1.nodes[i] == node2) {
				Link link = node1.links[i];
				removeLink(link);
			}
		}
	}
	
	public void updateTopo(Node node1, Node node2, boolean b) {
		int i = nodes.get(node1);
		int j = nodes.get(node2);
		topo[i][j] = b;
		topo[j][i] = b;
	}
	
	public void computeSwitch() {
		
	}
	public void computeTimeDelay() {
		
	}
	public void computeBandwidth() {
		
	}
}
