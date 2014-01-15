/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package asynctestapp;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Administrator
 */
public class Listener extends Thread {

    private static final int BUFFERSIZE = 65535;

    public boolean cancel = false;

    private ServerSocket s;
    private Socket clientSocket;

    @Override
    public void run() {
        try {
            s = new ServerSocket(18182);
            while (!cancel) {
                System.out.println("Socket listening to 18182");
                clientSocket = s.accept();
                byte[] buffer = new byte[BUFFERSIZE];
                int read;
                int totalRead = 0;
                InputStream clientInputStream = clientSocket.getInputStream();
                while ((read = clientInputStream.read(buffer)) != -1) {
                        System.out.println("Reading from Socket: " + read);
                        totalRead += read;
                        String testStr = new String(buffer);
                        System.out.print(testStr);
                }
                clientSocket.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void stopSocket() {
        try {
            clientSocket.close();
            s.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
