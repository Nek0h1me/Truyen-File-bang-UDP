package truyendanfilebangudp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) {
        int port = 9876; // cổng nhận
        byte[] buffer = new byte[1024];

        try (DatagramSocket socket = new DatagramSocket(port);
             FileOutputStream fos = new FileOutputStream("received_file.txt")) {

            System.out.println("Server đang chờ dữ liệu UDP trên port " + port);

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String message = new String(packet.getData(), 0, packet.getLength());
                if (message.equals("EOF")) { // báo hiệu hết file
                    System.out.println("Đã nhận file xong!");
                    break;
                }

                fos.write(packet.getData(), 0, packet.getLength());
                fos.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
