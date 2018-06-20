package com.andrzejpudzisz.multimediaboardcontrollereng;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.DialogInterface;
import com.skydoves.colorpickerpreference.ColorEnvelope;
import com.skydoves.colorpickerpreference.ColorListener;
import com.skydoves.colorpickerpreference.ColorPickerDialog;

public class WriteMsgActivity extends AppCompatActivity {
    public static final String MSGFL = "com.andrzejpudzisz.multimediaboardcontrollereng.MSGFL";
    public static final String MSGSL = "com.andrzejpudzisz.multimediaboardcontrollereng.MSGSL";
    public static final String COLOR = "com.andrzejpudzisz.multimediaboardcontrollereng.COLOR";

    private TextView txtMsg;
    private EditText txtMsgFL, txtMsgSL;
    private Button btnPickColor, btnChange;
    private String colorValue = "555555";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_msg);

        txtMsg = (TextView) findViewById(R.id.txtOpenHours);
        txtMsgFL = (EditText) findViewById(R.id.txtMsgFL);
        txtMsgSL = (EditText) findViewById(R.id.txtMsgSL);
        btnPickColor = (Button) findViewById(R.id.btnPickColor);
        btnChange = (Button) findViewById(R.id.btnSaveChanges);

        btnPickColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startColorPickerDialog();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivityExtras();
            }
        });
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

    private void startMainActivityExtras() {
        String fl = txtMsgFL.getText().toString();
        String sl = txtMsgSL.getText().toString();

        Intent intent = new Intent();
        intent.putExtra(MSGFL, fl);
        intent.putExtra(MSGSL, sl);
        intent.putExtra(COLOR, colorValue);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void startColorPickerDialog() {
        ColorPickerDialog.Builder builder = new ColorPickerDialog.Builder(this, AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        builder.setTitle("Choosing color window");
        builder.setPreferenceName("Choose color");
        builder.setPositiveButton(getString(R.string.confirm), new ColorListener() {
            @Override
            public void onColorSelected(ColorEnvelope colorEnvelope) {
                colorValue = colorEnvelope.getColorHtml();
                txtMsgFL.setBackgroundColor(colorEnvelope.getColor());
                txtMsgSL.setBackgroundColor(colorEnvelope.getColor());
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}
