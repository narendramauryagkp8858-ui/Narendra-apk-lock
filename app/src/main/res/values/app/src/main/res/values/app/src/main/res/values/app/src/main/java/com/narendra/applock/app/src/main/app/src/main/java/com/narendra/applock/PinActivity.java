
package com.narendra.applock;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class PinActivity extends Activity {

    private final String DEFAULT_PIN = "8546";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(30, 30, 30, 30);
        layout.setBackgroundColor(Color.rgb(12,18,38));

        TextView title = new TextView(this);
        title.setText("🔐 NARENDRA APPLOCK");
        title.setTextColor(Color.WHITE);
        title.setTextSize(25);
        title.setGravity(Gravity.CENTER);

        EditText pin = new EditText(this);
        pin.setHint("Enter Your PIN");
        pin.setTextColor(Color.WHITE);
        pin.setHintTextColor(Color.GRAY);
        pin.setInputType(2);

        Button unlock = new Button(this);
        unlock.setText("UNLOCK");

        unlock.setOnClickListener(v -> {
            if (pin.getText().toString().equals(DEFAULT_PIN)) {
                Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(this, "Wrong PIN!", Toast.LENGTH_SHORT).show();
                pin.setText("");
            }
        });

        layout.addView(title);
        layout.addView(pin);
        layout.addView(unlock);

        setContentView(layout);
    }
}
