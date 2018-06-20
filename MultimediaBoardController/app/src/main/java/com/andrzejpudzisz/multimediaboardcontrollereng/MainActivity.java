package com.andrzejpudzisz.multimediaboardcontrollereng;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String address;
    private Client client;

    //private int timeDelay = 1500;

    private Boolean editing = false;
    private EditText txtIP;
    private TextView txtWriteIP, txtConnected, txtStateFL, txtStateSL, txtChange;
    private Button btnChangeHours, btnS1, btnS2, btnWriteMsg;

    private String monData;
    private String tueData;
    private String wedData;
    private String thuData;
    private String friData;
    public static final String MONH = "com.andrzejpudzisz.multimediaboardcontrollereng.MONH";
    public static final String TUEH = "com.andrzejpudzisz.multimediaboardcontrollereng.TUEH";
    public static final String WEDH = "com.andrzejpudzisz.multimediaboardcontrollereng.WEDH";
    public static final String THUH = "com.andrzejpudzisz.multimediaboardcontrollereng.THUH";
    public static final String FRIH = "com.andrzejpudzisz.multimediaboardcontrollereng.FRIH";

    public enum State {
        OPEN,
        OCCUPIED,
        CLOSE,
        OWN
    }

    private State actualState;

    /** MESSAGES

     BYTE :                         MESSAGE:
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtIP = (EditText) findViewById(R.id.txtIP);
        txtWriteIP = (TextView) findViewById(R.id.txtWriteIP);
        txtConnected = (TextView) findViewById(R.id.txtConnected);
        txtStateFL = (TextView) findViewById(R.id.txtStateFL);
        txtStateSL = (TextView) findViewById(R.id.txtStateSL);
        txtChange = (TextView) findViewById(R.id.txtChange);
        btnChangeHours = (Button) findViewById(R.id.btnChangeHours);
        btnS1 = (Button) findViewById(R.id.btnS1);
        btnS2 = (Button) findViewById(R.id.btnS2);
        btnWriteMsg = (Button) findViewById(R.id.btnWriteMsg);

        setAddress("192.168.22.10");
        txtIP.setText(address);

        txtIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editing) {
                    txtIP.setFocusable(false);
                    txtIP.setFocusableInTouchMode(false);
                    editing = false;
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(txtIP.getWindowToken(), 0);
                    setAddress(txtIP.getText().toString());
                    clientExecute((byte) 0, " ");
                    setStateVisuals();
                } else {
                    txtIP.setFocusable(true);
                    txtIP.setFocusableInTouchMode(true);
                    editing = true;
                }
            }
        });

        btnChangeHours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startOpenHoursActivity();
            }
        });

        btnS1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actualState == State.OCCUPIED || actualState == State.CLOSE || actualState == State.OWN) {
                    actualState = State.OPEN;
                    setOpenVisuals();
                    clientExecute((byte) 1, " ");
                } else if (actualState == State.OPEN) {
                    actualState = State.OCCUPIED;
                    setOccupiedVisuals();
                    clientExecute((byte) 2, " ");
                }
            }
        });

        btnS2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualState = State.CLOSE;
                setCloseVisuals();
                clientExecute((byte) 3, " ");
            }
        });

        btnWriteMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startWriteMsgActivity();
            }
        });

        actualState = State.CLOSE;

        /*new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                clientExecute((byte) 0, " ");
            }
        }, timeDelay);*/

        clientExecute((byte) 0, " ");

        setStateVisuals();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Bundle extras = intent.getExtras();
                actualState = State.OWN;
                btnS1.setText(getResources().getText(R.string.openFL));
                txtStateFL.setText(extras.getString(WriteMsgActivity.MSGFL));
                txtStateSL.setText(extras.getString(WriteMsgActivity.MSGSL));
                txtStateFL.setBackgroundColor(Color.parseColor("#" + extras.getString(WriteMsgActivity.COLOR)));
                txtStateSL.setBackgroundColor(Color.parseColor("#" + extras.getString(WriteMsgActivity.COLOR)));
                clientExecute((byte) 4, extras.getString(WriteMsgActivity.MSGFL)
                        + "&" + extras.getString(WriteMsgActivity.MSGSL)
                        + "&" + Color.parseColor("#" + extras.getString(WriteMsgActivity.COLOR)));
            }
        } else if (requestCode == 2) {
            if(resultCode == RESULT_OK) {
                Bundle extras = intent.getExtras();
                clientExecute((byte) 5, extras.getString(OpenHoursActivity.NEWMONSH)
                        + "&" + extras.getString(OpenHoursActivity.NEWMONFH)
                        + "&" + extras.getString(OpenHoursActivity.ISMON));
                clientExecute((byte) 6, extras.getString(OpenHoursActivity.NEWTUESH)
                        + "&" + extras.getString(OpenHoursActivity.NEWTUEFH)
                        + "&" + extras.getString(OpenHoursActivity.ISTUE));
                clientExecute((byte) 7, extras.getString(OpenHoursActivity.NEWWEDSH)
                        + "&" + extras.getString(OpenHoursActivity.NEWWEDFH)
                        + "&" + extras.getString(OpenHoursActivity.ISWED));
                clientExecute((byte) 8, extras.getString(OpenHoursActivity.NEWTHUSH)
                        + "&" + extras.getString(OpenHoursActivity.NEWTHUFH)
                        + "&" + extras.getString(OpenHoursActivity.ISTHU));
                clientExecute((byte) 9, extras.getString(OpenHoursActivity.NEWFRISH)
                        + "&" + extras.getString(OpenHoursActivity.NEWFRIFH)
                        + "&" + extras.getString(OpenHoursActivity.ISFRI));
            }
        }
    }

    private void setOpenVisuals() {
        btnS1.setText(getResources().getText(R.string.occupiedFL));
        txtStateFL.setText(getResources().getText(R.string.openFL));
        txtStateSL.setText(getResources().getText(R.string.openSL));
        txtStateFL.setBackgroundColor(getResources().getColor(R.color.green));
        txtStateSL.setBackgroundColor(getResources().getColor(R.color.green));
    }

    private void setOccupiedVisuals() {
        btnS1.setText(getResources().getText(R.string.openFL));
        txtStateFL.setText(getResources().getText(R.string.occupiedFL));
        txtStateSL.setText(getResources().getText(R.string.occupiedSL));
        txtStateFL.setBackgroundColor(getResources().getColor(R.color.yellow));
        txtStateSL.setBackgroundColor(getResources().getColor(R.color.yellow));
    }

    private void setCloseVisuals() {
        btnS1.setText(getResources().getText(R.string.openFL));
        txtStateFL.setText(getResources().getText(R.string.closeFL));
        txtStateSL.setText(getResources().getText(R.string.closeSL));
        txtStateFL.setBackgroundColor(getResources().getColor(R.color.red));
        txtStateSL.setBackgroundColor(getResources().getColor(R.color.red));
    }

    private void startOpenHoursActivity() {
        Intent intent = new Intent(MainActivity.this, OpenHoursActivity.class);
        intent.putExtra(MONH, monData);
        intent.putExtra(TUEH, tueData);
        intent.putExtra(WEDH, wedData);
        intent.putExtra(THUH, thuData);
        intent.putExtra(FRIH, friData);
        startActivityForResult(intent, 2);
    }

    private void startWriteMsgActivity() {
        Intent intent = new Intent(MainActivity.this, WriteMsgActivity.class);
        startActivityForResult(intent, 1);
    }

    private void setStateVisuals() {
        switch(actualState) {
            case OPEN:
                setOpenVisuals();
                break;
            case OCCUPIED:
                setOccupiedVisuals();
                break;
            case CLOSE:
                setCloseVisuals();
                break;
            case OWN:
                btnS1.setText(getResources().getText(R.string.openFL));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void setAddress(String str) {
        address = str;
    }

    public TextView getTxtStateFL() {
        return txtStateFL;
    }

    public TextView getTxtStateSL() {
        return txtStateSL;
    }

    public void setTxtConnected(String txtConnected) {
        this.txtConnected.setText(txtConnected);
    }

    public void setTxtStateFL(String txtStateFL) {
        this.txtStateFL.setText(txtStateFL);
    }

    public void setTxtStateSL(String txtStateSL) {
        this.txtStateSL.setText(txtStateSL);
    }

    public void setStateColor(int color) {
        txtStateFL.setBackgroundColor(color);
        txtStateSL.setBackgroundColor(color);
    }

    public void setBtnS1(String btnS1) {
        this.btnS1.setText(btnS1);
    }

    public void setMonData(String monData) {
        this.monData = monData;
    }

    public void setTueData(String tueData) {
        this.tueData = tueData;
    }

    public void setWedData(String wedData) {
        this.wedData = wedData;
    }

    public void setThuData(String thuData) {
        this.thuData = thuData;
    }

    public void setFriData(String friData) {
        this.friData = friData;
    }

    public void setActualState(State actualState) {
        this.actualState = actualState;
    }

    private void clientExecute(byte b, String msg) {
        try {
            client = new Client(address, 8080, this, b, msg);
            client.execute();
        } catch (Exception e) {

        }
    }

}
