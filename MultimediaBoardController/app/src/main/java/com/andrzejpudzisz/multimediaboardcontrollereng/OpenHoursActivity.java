package com.andrzejpudzisz.multimediaboardcontrollereng;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenHoursActivity extends AppCompatActivity {

    private Button btnSaveChanges;
    // txtclkMonS - Monday START hour
    private TextView txtclkMonS, txtclkMonF, txtclkTueS, txtclkTueF, txtclkWedS, txtclkWedF,
            txtclkThuS, txtclkThuF, txtclkFriS, txtclkFriF;
    private ToggleButton cbMon, cbTue, cbWed, cbThu, cbFri;

    public static final String NEWMONSH = "com.andrzejpudzisz.multimediaboardcontrollereng.NEWMONSH";
    public static final String NEWMONFH = "com.andrzejpudzisz.multimediaboardcontrollereng.NEWMONFH";
    public static final String NEWTUESH = "com.andrzejpudzisz.multimediaboardcontrollereng.NEWTUESH";
    public static final String NEWTUEFH = "com.andrzejpudzisz.multimediaboardcontrollereng.NEWTUEFH";
    public static final String NEWWEDSH = "com.andrzejpudzisz.multimediaboardcontrollereng.NEWWEDSH";
    public static final String NEWWEDFH = "com.andrzejpudzisz.multimediaboardcontrollereng.NEWWEDFH";
    public static final String NEWTHUSH = "com.andrzejpudzisz.multimediaboardcontrollereng.NEWTHUSH";
    public static final String NEWTHUFH = "com.andrzejpudzisz.multimediaboardcontrollereng.NEWTHUFH";
    public static final String NEWFRISH = "com.andrzejpudzisz.multimediaboardcontrollereng.NEWFRISH";
    public static final String NEWFRIFH = "com.andrzejpudzisz.multimediaboardcontrollereng.NEWFRIFH";
    public static final String ISMON = "com.andrzejpudzisz.multimediaboardcontrollereng.ISMON";
    public static final String ISTUE = "com.andrzejpudzisz.multimediaboardcontrollereng.ISTUE";
    public static final String ISWED = "com.andrzejpudzisz.multimediaboardcontrollereng.ISWED";
    public static final String ISTHU = "com.andrzejpudzisz.multimediaboardcontrollereng.ISTHU";
    public static final String ISFRI = "com.andrzejpudzisz.multimediaboardcontrollereng.ISFRI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_hours);

        btnSaveChanges = (Button) findViewById(R.id.btnSaveChanges);
        txtclkMonS = (TextView) findViewById(R.id.txtclkMonS);
        txtclkMonF = (TextView) findViewById(R.id.txtclkMonF);
        txtclkTueS = (TextView) findViewById(R.id.txtclkTueS);
        txtclkTueF = (TextView) findViewById(R.id.txtclkTueF);
        txtclkWedS = (TextView) findViewById(R.id.txtclkWedS);
        txtclkWedF = (TextView) findViewById(R.id.txtclkWedF);
        txtclkThuS = (TextView) findViewById(R.id.txtclkThuS);
        txtclkThuF = (TextView) findViewById(R.id.txtclkThuF);
        txtclkFriS = (TextView) findViewById(R.id.txtclkFriS);
        txtclkFriF = (TextView) findViewById(R.id.txtclkFriF);
        cbMon = (ToggleButton) findViewById(R.id.cbMon);
        cbTue = (ToggleButton) findViewById(R.id.cbTue);
        cbWed = (ToggleButton) findViewById(R.id.cbWed);
        cbThu = (ToggleButton) findViewById(R.id.cbThu);
        cbFri = (ToggleButton) findViewById(R.id.cbFri);

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateTableAndStartMainActivity();
            }
        });

        txtclkMonS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickHour(txtclkMonS);
            }
        });

        txtclkMonF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickHour(txtclkMonF);
            }
        });

        txtclkTueS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickHour(txtclkTueS);
            }
        });

        txtclkTueF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickHour(txtclkTueF);
            }
        });

        txtclkWedS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickHour(txtclkWedS);
            }
        });

        txtclkWedF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickHour(txtclkWedF);
            }
        });

        txtclkThuS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickHour(txtclkThuS);
            }
        });

        txtclkThuF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickHour(txtclkThuF);
            }
        });

        txtclkFriS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickHour(txtclkFriS);
            }
        });

        txtclkFriF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickHour(txtclkFriF);
            }
        });

        cbMon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtclkMonS.setText("10:00");
                txtclkMonF.setText("12:00");
            }
        });

        cbTue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtclkTueS.setText("10:00");
                txtclkTueF.setText("12:00");
            }
        });

        cbWed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtclkWedS.setText("10:00");
                txtclkWedF.setText("12:00");
            }
        });

        cbThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtclkThuS.setText("10:00");
                txtclkThuF.setText("12:00");
            }
        });

        cbFri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtclkFriS.setText("10:00");
                txtclkFriF.setText("12:00");
            }
        });

        cbMon.setChecked(false);
        cbTue.setChecked(false);
        cbWed.setChecked(false);
        cbThu.setChecked(false);
        cbFri.setChecked(false);

        setHours();
    }

    private void pickHour(final TextView tc) {

        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;

        mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String h = String.valueOf(selectedHour), m = String.valueOf(selectedMinute), txt;
                if(selectedMinute < 10) {
                    m = "0" + m;
                }
                txt = h + ":" + m;
                tc.setText(txt);
            }
        }, hour, minute, true);

        mTimePicker.setTitle("Choose hour");
        mTimePicker.show();
    }

    private void hoursParser(String data, TextView tvS, TextView tvF, ToggleButton tb) {
        Pattern strinngWith2Numbers = Pattern.compile("(\\d+)\\D+(\\d+)\\D*(\\d+)\\D+(\\d+)");
        Matcher matcher = strinngWith2Numbers.matcher(data);
        String s, f;
        if(!matcher.matches()) {
            tvS.setText("0");
            tvF.setText("0");
            tb.setChecked(true);
        } else {
            s = matcher.group(1) + ":" + matcher.group(2);
            f = matcher.group(3) + ":" + matcher.group(4);
            tvS.setText(s);
            tvF.setText(f);
            tb.setChecked(false);
        }
    }

    private void setHours() {
        Bundle b = getIntent().getExtras();
        if(b != null) {
            hoursParser(b.getString(MainActivity.MONH), txtclkMonS, txtclkMonF, cbMon);
            hoursParser(b.getString(MainActivity.TUEH), txtclkTueS, txtclkTueF, cbTue);
            hoursParser(b.getString(MainActivity.WEDH), txtclkWedS, txtclkWedF, cbWed);
            hoursParser(b.getString(MainActivity.THUH), txtclkThuS, txtclkThuF, cbThu);
            hoursParser(b.getString(MainActivity.FRIH), txtclkFriS, txtclkFriF, cbFri);
        }
    }

    private void updateTableAndStartMainActivity() {
        Intent intent = new Intent();
        intent.putExtra(NEWMONSH, txtclkMonS.getText());
        intent.putExtra(NEWMONFH, txtclkMonF.getText());
        intent.putExtra(NEWTUESH, txtclkTueS.getText());
        intent.putExtra(NEWTUEFH, txtclkTueF.getText());
        intent.putExtra(NEWWEDSH, txtclkWedS.getText());
        intent.putExtra(NEWWEDFH, txtclkWedF.getText());
        intent.putExtra(NEWTHUSH, txtclkThuS.getText());
        intent.putExtra(NEWTHUFH, txtclkThuF.getText());
        intent.putExtra(NEWFRISH, txtclkFriS.getText());
        intent.putExtra(NEWFRIFH, txtclkFriF.getText());
        intent.putExtra(ISMON, String.valueOf(!cbMon.isChecked()));
        intent.putExtra(ISTUE, String.valueOf(!cbTue.isChecked()));
        intent.putExtra(ISWED, String.valueOf(!cbWed.isChecked()));
        intent.putExtra(ISTHU, String.valueOf(!cbThu.isChecked()));
        intent.putExtra(ISFRI, String.valueOf(!cbFri.isChecked()));
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        startMainActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}
