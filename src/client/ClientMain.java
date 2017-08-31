//package client;
//
//import java.io.IOException;
//import java.net.Socket;
//import java.net.UnknownHostException;
//
//import client.GetInfoThread.IGetCListener;
//import net.sf.json.JSONObject;
//
//public class ClientMain{
//	private static SendImgThread sendThread;
//	private static GetInfoThread getThread;
//
//	public static void main(String[] args) throws UnknownHostException, IOException {
//		Socket socket = new Socket("183.175.12.183", 9987);
//		sendThread = new SendImgThread(socket);
//		sendThread.start();
//		sendThread.shoudShow = false;
//
//		getThread = new GetInfoThread(socket);
//		getThread.setListener(new IGetCListener() {
//
//			@Override
//			public void info(String string) {
//				if (string.equals("start")) {
//					sendThread.setStart(true);
//				} else if (string.equals("stop")) {
//					sendThread.setStart(false);
//				} else if (string.equals("heart")) {
//					JSONObject json = new JSONObject();
//					json.accumulate("cmd", "alive");
//					try {
//						System.out.println("get");
//						sendThread.getPortocol().sendJson(json);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				} else if(string.equals("shxy")){
//					//System.out.println("");
//				}
//
//			}
//		});
//		getThread.start();
//	}
//
//}
