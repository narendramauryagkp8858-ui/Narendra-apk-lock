
package com.narendra.applock;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.provider.Settings;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

public class AppLockActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(25, 25, 25, 25);
        layout.setBackgroundColor(Color.rgb(12,18,38));

        TextView title = new TextView(this);
        title.setText("APP LOCK SECURITY");
        title.setTextColor(Color.WHITE);
        title.setTextSize(24);
        title.setGravity(Gravity.CENTER);

        TextView info = new TextView(this);
        info.setText(
            "Enable Usage Access permission " +
            "to detect which app is running."
        );
        info.setTextColor(Color.LTGRAY);
        info.setTextSize(16);
        info.setGravity(Gravity.CENTER);

        Button permission = new Button(this);
        permission.setText("ENABLE USAGE ACCESS");

        permission.setOnClickListener(v -> {
            Intent intent = new Intent(
                Settings.ACTION_USAGE_ACCESS_SETTINGS
            );
            startActivity(intent);
        });

        layout.addView(title);
        layout.addView(info);
        layout.addView(permission);

        setContentView(layout);
    }
}
