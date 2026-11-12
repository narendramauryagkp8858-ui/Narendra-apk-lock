
package com.narendra.applock;

import android.app.Activity;
import android.os.Bundle;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class ChangePinActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(25, 25, 25, 25);
        layout.setBackgroundColor(Color.rgb(12,18,38));

        TextView title = new TextView(this);
        title.setText("CHANGE YOUR PIN");
        title.setTextColor(Color.WHITE);
        title.setTextSize(25);

        EditText oldPin = new EditText(this);
        oldPin.setHint("Current PIN");
        oldPin.setInputType(2);

        EditText newPin = new EditText(this);
        newPin.setHint("New PIN (4 digits)");
        newPin.setInputType(2);

        Button save = new Button(this);
        save.setText("SAVE NEW PIN");

        save.setOnClickListener(v -> {

            SharedPreferences prefs =
                getSharedPreferences("NarendraLock", MODE_PRIVATE);

            String current = prefs.getString("pin", "8546");

            if (!oldPin.getText().toString().equals(current)) {
                Toast.makeText(this, "Wrong Current PIN",
                    Toast.LENGTH_SHORT).show();
                return;
            }

            String updated = newPin.getText().toString();

            if (!updated.matches("[0-9]{4}")) {
                Toast.makeText(this, "Enter exactly 4 digits",
                    Toast.LENGTH_SHORT).show();
                return;
            }

            prefs.edit().putString("pin", updated).apply();

            Toast.makeText(this, "PIN Changed Successfully",
                Toast.LENGTH_LONG).show();

            finish();
        });

        layout.addView(title);
        layout.addView(oldPin);
        layout.addView(newPin);
        layout.addView(save);

        setContentView(layout);
    }
}
