package sample;

import client.GetInfoThread;
import client.SendImgThread;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.Socket;

public class Main extends Application {
    private SendImgThread sendThread;
    private GetInfoThread getThread;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        initSocket();
    }

    private void initSocket() {
        Socket socket = null;
        try {
            socket = new Socket("183.175.12.183", 9987);
        } catch (IOException e) {
            System.exit(0);
            e.printStackTrace();
        }
        sendThread = new SendImgThread(socket);
        sendThread.start();
        sendThread.shoudShow = false;

        getThread = new GetInfoThread(socket);
        getThread.setListener(new GetInfoThread.IGetCListener() {

            @Override
            public void info(String string) {
                if (string.equals("start")) {
                    sendThread.setStart(true);
                } else if (string.equals("stop")) {
                    sendThread.setStart(false);
                } else if (string.equals("heart")) {
                    JSONObject json = new JSONObject();
                    json.accumulate("cmd", "alive");
                    try {
                        System.out.println("get");
                        sendThread.getPortocol().sendJson(json);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if(string.equals("shxy")){
                    //System.out.println("");
                }

            }
        });
        getThread.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
