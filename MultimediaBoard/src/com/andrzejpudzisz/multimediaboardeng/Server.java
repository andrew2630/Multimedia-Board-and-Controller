package com.andrzejpudzisz.multimediaboardeng;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    Data data;
    ServerSocket serverSocket;
    String msgReply, color, fl, sl, hours;

    static final int socketServerPORT = 8080;

    public Server(Data data) {
        this.data = data;

        msgReply = "Connected";
        color = "#cccccc";
        fl = " ";
        sl = " ";
        hours = "CLOSED" + "&"
                + "CLOSED" + "&"
                + "CLOSED" + "&"
                + "CLOSED" + "&"
                + "CLOSED";

        Thread socketServerThread = new Thread(new SocketServerThread());
        socketServerThread.start();
    }

    public void onDestroy() {
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class SocketServerThread extends Thread {

        public void run() {
            SwingUtilities.invokeLater(() -> {
                setColor(data.getBgColor());
                fl = data.getOpenFL();
                sl = data.getOpenSL();
                hours = data.getH1() + "&"
                        + data.getH2() + "&"
                        + data.getH3() + "&"
                        + data.getH4() + "&"
                        + data.getH5();
            });

            try {
                serverSocket = new ServerSocket(socketServerPORT);

                while (true) {
                    Socket socket = serverSocket.accept();

                    SocketServerReplyThread socketServerReplyThread = new SocketServerReplyThread(socket);
                    socketServerReplyThread.run();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private class SocketServerReplyThread extends Thread {

        private Socket hostThreadSocket;

        SocketServerReplyThread(Socket socket) {
            hostThreadSocket = socket;
        }

        public void run() {
            OutputStream outputStream;
            InputStream inputStream;

            try {
                outputStream = hostThreadSocket.getOutputStream();
                inputStream = hostThreadSocket.getInputStream();
                PrintStream printStream = new PrintStream(outputStream);
                DataInputStream dIn = new DataInputStream(inputStream);

                byte messageType = dIn.readByte();
                String msg = dIn.readUTF();
                String[] parts = msg.split("&");

                /** MESSAGES

                 BYTE:                          MESSAGE:
                 0 - get data                   " "
                 1 - OPEN state                 " "
                 2 - OCCUPIED state             " "
                 3 - CLOSE state                " "
                 4 - OWN state                  "firstline&secondline&color"
                 5 - monday                     "openhour&closehour&isopen"
                 6 - tuesday                    "openhour&closehour&isopen"
                 7 - wednesday                  "openhour&closehour&isopen"
                 8 - thursday                   "openhour&closehour&isopen"
                 9 - friday                     "openhour&closehour&isopen"

                 */
                switch(messageType) {
                    case 0:
                        printStream.flush();
                        printStream.println(msgReply + "&" + color + "&" + fl + "&" + sl + "&" + hours + "&ok&");
                        break;
                    case 1:
                        setColor(new Color(0, 200, 0));
                        data.setBgColor(new Color(0, 200, 0));
                        fl = "YOU CAN ENTER";
                        data.setOpenFL("YOU CAN ENTER");
                        sl = "NO CLIENTS";
                        data.setOpenSL("NO CLIENTS");
                        printStream.println("ok&");
                        break;
                    case 2:
                        setColor(new Color(200, 200, 0));
                        data.setBgColor(new Color(200, 200, 0));
                        fl = "OCCUPIED";
                        data.setOpenFL("OCCUPIED");
                        sl = "CLIENT IN THE OFFICE";
                        data.setOpenSL("CLIENT IN THE OFFICE");
                        printStream.println("ok&");
                        break;
                    case 3:
                        setColor(new Color(200, 0, 0));
                        data.setBgColor(new Color(200, 0, 0));
                        fl = "CLOSED";
                        data.setOpenFL("CLOSED");
                        sl = " ";
                        data.setOpenSL(" ");
                        printStream.println("ok&");
                        break;
                    case 4:
                        setColor(Color.decode(parts[2]));
                        data.setBgColor(Color.decode(parts[2]));
                        fl = parts[0];
                        data.setOpenFL(parts[0]);
                        sl = parts[1];
                        data.setOpenSL(parts[1]);
                        printStream.println("ok&");
                        break;
                    case 5:
                        if(Boolean.valueOf(parts[2])) {
                            data.setH1(parts[0] + " - " + parts[1]);
                        } else {
                            data.setH1("CLOSED");
                        }
                        printStream.println("ok&");
                        break;
                    case 6:
                        if(Boolean.valueOf(parts[2])) {
                            data.setH2(parts[0] + " - " + parts[1]);
                        } else {
                            data.setH2("CLOSED");
                        }
                        printStream.println("ok&");
                        break;
                    case 7:
                        if(Boolean.valueOf(parts[2])) {
                            data.setH3(parts[0] + " - " + parts[1]);
                        } else {
                            data.setH3("CLOSED");
                        }
                        printStream.println("ok&");
                        break;
                    case 8:
                        if(Boolean.valueOf(parts[2])) {
                            data.setH4(parts[0] + " - " + parts[1]);
                        } else {
                            data.setH4("CLOSED");
                        }
                        printStream.println("ok&");
                        break;
                    case 9:
                        if(Boolean.valueOf(parts[2])) {
                            data.setH5(parts[0] + " - " + parts[1]);
                        } else {
                            data.setH5("CLOSED");
                        }
                        printStream.println("ok&");
                        break;
                }

                printStream.close();
                dIn.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setColor(Color c) {
        color = Integer.toHexString(c.getRGB() & 0xffffff);
        while (color.length() < 6) {
            color = "0" + color;
        }
        color = "#" + color;
    }
}

