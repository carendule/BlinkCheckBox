package com.caren.blinkcheckbox;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class mainActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        BlinkCheckBox blinkCheckBox1 = findViewById(R.id.box1);
        BlinkCheckBox blinkCheckBox2 = findViewById(R.id.box2);
        BlinkCheckBox blinkCheckBox3 = findViewById(R.id.box3);

        blinkCheckBox1.setOnCheckedChangeListener(new BlinkCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(BlinkCheckBox view, boolean isChecked) {
                Toast.makeText(mainActivity.this,"box1 clicked",Toast.LENGTH_SHORT).show();
            }
        });

        blinkCheckBox2.setOnCheckedChangeListener(new BlinkCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(BlinkCheckBox view, boolean isChecked) {
                Toast.makeText(mainActivity.this,"box2 clicked",Toast.LENGTH_SHORT).show();
            }
        });

        blinkCheckBox3.setOnCheckedChangeListener(new BlinkCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(BlinkCheckBox view, boolean isChecked) {
                Toast.makeText(mainActivity.this,"box3 clicked",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
