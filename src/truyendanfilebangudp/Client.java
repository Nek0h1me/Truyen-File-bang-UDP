package truyendanfilebangudp;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String[] args) {
        String serverIP = "127.0.0.1"; // địa chỉ server
        int port = 9876;               // cổng server
        byte[] buffer = new byte[1024];

        try (DatagramSocket socket = new DatagramSocket();
             FileInputStream fis = new FileInputStream("send_file.txt")) {

            InetAddress serverAddress = InetAddress.getByName(serverIP);

            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                DatagramPacket packet = new DatagramPacket(buffer, bytesRead, serverAddress, port);
                socket.send(packet);
            }

            // Gửi "EOF" để báo hiệu hết file
            byte[] endMsg = "EOF".getBytes();
            DatagramPacket endPacket = new DatagramPacket(endMsg, endMsg.length, serverAddress, port);
            socket.send(endPacket);

            System.out.println("Đã gửi file xong!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
