package model;

public class Frame {

	public int len;
	public String src;
	public String dst;
	public int createTime;
	
	public Frame(int len, String src, String dst, int currendTime) {
		this.len = len;
		this.src = new String(src);
		this.dst = new String(dst);
		this.createTime = currendTime;
	}
}
