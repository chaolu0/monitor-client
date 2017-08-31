package client;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import portocol.MyPortocol;

public class SendImgThread extends Thread {
	public boolean shoudShow = false;
	private int time = 1;
	private Robot mRobot;
	private Rectangle rect;// 桌面
	private Socket mSocket;
	private MyPortocol portocol;
	
	public MyPortocol getPortocol() {
		return portocol;
	}
	public synchronized void setStart(boolean run){
		shoudShow = run;
	}
	public SendImgThread(Socket socket) {

		try {
			mSocket = socket;
			portocol = new MyPortocol(mSocket);
			mRobot = new Robot();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dm = tk.getScreenSize();
		rect = new Rectangle(0, 0, dm.width, dm.height);

	}

	@Override
	public void run() {
		if (mRobot != null) {

				while (true) {
					try {
					if (shoudShow) {
						//System.out.println("*");
						BufferedImage image = mRobot.createScreenCapture(rect);
						byte[] buff = getCompressedImage(image);
						portocol.sendFileArray(buff);
										
					}
					Thread.sleep(time);
					} catch (Exception e) {
						try {
							Thread.sleep(10*1000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
						e.printStackTrace();
						continue;
					}
				}

		}

	}

	public static byte[] getCompressedImage(BufferedImage image) {
		byte[] imageData = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "jpg", baos);
			//ImageIO.write(image, "jpg", new File("e:\\ttp.jpg"));
			imageData = baos.toByteArray();
			// System.out.println(new String(imageData));
		} catch (IOException ex) {
			imageData = null;
		}

		return imageData;
	}

}
