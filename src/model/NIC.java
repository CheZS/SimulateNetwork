package model;

public class NIC {

	public int speed;
	public Frame[] sendBuffer;
	public Frame[] receiveBuffer;
	public int remainSendLength;
	public double averPacketLen;
	public double averPacketNumPerH;
	public int currentPacketNum;
	public double ratio;
	
	public NIC(int speed, double ratio) {
		this.speed = speed;
		this.sendBuffer = new Frame[8];
		this.receiveBuffer = new Frame[8];
		this.remainSendLength = 0;
		this.averPacketLen = 0;
		this.averPacketNumPerH = 0;
		this.currentPacketNum = 0;
		this.ratio = ratio;
	}
	public void tick() {
		averPacketNumPerH = ratio * currentPacketNum + (1 - ratio) * averPacketNumPerH;
		currentPacketNum = 0;
	}
	public Frame send() {
		remainSendLength -= speed;
		if (remainSendLength <= 0) {
			int i = 1;
			Frame res = sendBuffer[0];
			for (; i < sendBuffer.length && sendBuffer[i] != null; i++) {
				sendBuffer[i - 1] = sendBuffer[i];
			}
			if (i < sendBuffer.length) {
				sendBuffer[i] = null;
			}
			remainSendLength = sendBuffer[0].len;
			return res;
		}
		return null;
	}
	public boolean pushToSend(Frame frame) {
		for (int i = 0; i < sendBuffer.length; i++) {
			if (sendBuffer[i] == null) {
				sendBuffer[i] = frame;
				averPacketLen = ratio * frame.len + (1 - ratio) * averPacketLen;
				currentPacketNum++;
				return true;
			}
		}
		return false;
	}
	public boolean receive(Frame frame) {
		for (int i = 0; i < receiveBuffer.length; i++) {
			if (receiveBuffer[i] == null) {
				receiveBuffer[i] = frame;
				return true;
			}
		}
		return false;
	}
	public Frame[] pollToCompute(int frameNum) {
		int i = 0;
		for (; i < receiveBuffer.length && i < frameNum 
				&& receiveBuffer[i] != null; i++) {}
		Frame[] res = new Frame[i];
		for (int j = 0; j < i; j++) {
			res[j] = receiveBuffer[j];
		}
		int k = 0;
		for (int j = i + 1; j < receiveBuffer.length && receiveBuffer[j] != null; j++, k++) {
			receiveBuffer[k] = receiveBuffer[j];
		}
		for (; k < receiveBuffer.length; k++) {
			receiveBuffer[k] = null;
		}
		return res;
	}
	public double traficIntensity() {
		// TODO L(b/pkt) * a(pkt/h(ms)) / R(b/s) * 1(s) / h(ms)
		// L 包平均长度
		// a 平均每秒包个数
		// R 带宽： 双绞线1Gb/s，NIC 1000Mb/s，取小者
		// h 步长，h毫秒
		return averPacketLen * averPacketNumPerH / (speed / 1000);
	}
}
