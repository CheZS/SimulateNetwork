package util;

public class Buffer {

	public Object[] buffer;
	public Buffer() {
		this.buffer = new Object[8];
	}
	public Buffer(int len) {
		this.buffer = new Object[len];
	}
}
