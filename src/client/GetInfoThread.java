package client;

import java.net.Socket;
import net.sf.json.JSONObject;
import portocol.MyPortocol;

public class GetInfoThread extends Thread {
	public interface IGetCListener {
		void info(String string);
	}

	private IGetCListener listener;

	public void setListener(IGetCListener l) {
		this.listener = l;
	}

	private Socket mSocket;
	private MyPortocol portocol;

	public GetInfoThread(Socket socket) {
		mSocket = socket;
		portocol = new MyPortocol(mSocket);
	}

	@Override
	public void run() {
		while (true) {
			try {
				// System.out.println("before");
				Object object = portocol.getInfomation();
				// System.out.println("behind");
				System.out.println(object.toString());
				// System.out.println("#");
				JSONObject jsonObject = JSONObject.fromObject(object);
				String msg = (String) jsonObject.get("cmd");
				System.out.println(msg);
				listener.info(msg);

			} catch (Exception e) {
				break;
				// e.printStackTrace();
			}
		}
	}

}
