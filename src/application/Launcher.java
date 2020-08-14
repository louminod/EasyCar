package application;

import model.controllers.ConnectFrameController;
import view.frames.ConnectFrame;

public class Launcher {
    public static void main(String[] args) {

        // Init the connection frame
        ConnectFrameController controller = new ConnectFrameController();
        ConnectFrame connectFrame = new ConnectFrame(controller);
        connectFrame.buildFrame("Connection", 350, 150);

        // Show the connection frame
        connectFrame.showFrame();
    }
}
