package com.andrzejpudzisz.multimediaboardcontrollereng;

import android.graphics.Color;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client extends AsyncTask<Void, Void, Void> {

    String dstAddress;
    int dstPort;
    String response = "";
    MainActivity mActivity;

    byte b;
    String msg;

    private  TextView txtConnected, txtStateFl, txtStateSl;
    private Button btnS1;

    Client(String addr, int port, MainActivity mActivity, byte b, String msg) {
        dstAddress = addr;
        dstPort = port;
        this.mActivity = mActivity;
        this.b = b;
        this.msg = msg;
    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected Void doInBackground(Void... arg0) {

        Socket socket = null;
        DataOutputStream os = null;
        byte[] buffer = new byte[1024];
        int bytesRead;
        ByteArrayOutputStream byteArrayOutputStream = null;
        InputStream inputStream = null;

        try {
            socket = new Socket(dstAddress, dstPort);
            os = new DataOutputStream(socket.getOutputStream());
            byteArrayOutputStream = new ByteArrayOutputStream(1024);
            inputStream = socket.getInputStream();

            os.writeByte(b);
            os.writeUTF(msg);
            os.flush();

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String[] parts = response.split("&");
                String correct = "false";

                switch(b) {
                    case 0:
                        mActivity.setTxtConnected("Connected");
                        mActivity.setStateColor(Color.parseColor(parts[1]));
                        mActivity.setTxtStateFL(parts[2]);
                        mActivity.setTxtStateSL(parts[3]);
                        mActivity.setActualState(checkStateVisuals());
                        mActivity.setMonData(parts[4]);
                        mActivity.setTueData(parts[5]);
                        mActivity.setWedData(parts[6]);
                        mActivity.setThuData(parts[7]);
                        mActivity.setFriData(parts[8]);
                        correct = parts[9];
                        break;
                    case 1:
                        correct = parts[0];
                        break;
                    case 2:
                        correct = parts[0];
                        break;
                    case 3:
                        correct = parts[0];
                        break;
                    case 4:
                        correct = parts[0];
                        break;
                    case 5:
                        correct = parts[0];
                        break;
                    case 6:
                        correct = parts[0];
                        break;
                    case 7:
                        correct = parts[0];
                        break;
                    case 8:
                        correct = parts[0];
                        break;
                    case 9:
                        correct = parts[0];
                        break;
                }
                if(correct.equals("ok")) {
                    Toast.makeText(mActivity, "Data sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mActivity, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });

        super.onPostExecute(result);
    }

    private MainActivity.State checkStateVisuals() {
        if(mActivity.getTxtStateFL().getText().equals(R.string.openFL) &&
                mActivity.getTxtStateSL().getText().equals(R.string.openSL)) {
            return MainActivity.State.OPEN;
        } else if(mActivity.getTxtStateFL().getText().equals(R.string.occupiedFL) &&
                mActivity.getTxtStateSL().getText().equals(R.string.occupiedSL)) {
            return MainActivity.State.OCCUPIED;
        } else if(mActivity.getTxtStateFL().getText().equals(R.string.closeFL) &&
                mActivity.getTxtStateSL().getText().equals(R.string.closeSL)) {
            return MainActivity.State.CLOSE;
        } else {
            return MainActivity.State.OWN;
        }
    }

}