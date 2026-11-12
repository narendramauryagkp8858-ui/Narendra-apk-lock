
package com.narendra.applock;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;

public class MainActivity extends Activity {

    LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(25, 35, 25, 25);
        layout.setGravity(Gravity.CENTER_HORIZONTAL);
        layout.setBackgroundColor(Color.rgb(12,18,38));

        TextView title = new TextView(this);
        title.setText("🔐 NARENDRA APPLOCK");
        title.setTextColor(Color.WHITE);
        title.setTextSize(25);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);

        layout.addView(title);

        addButton("APP LOCK", AppLockActivity.class);
        addButton("PHOTO VAULT", PhotoVaultActivity.class);
        addButton("VIDEO VAULT", VideoVaultActivity.class);
        addButton("CHANGE PIN", null);
        addButton("SETTINGS", null);

        setContentView(layout);
    }

    private void addButton(String name, Class<?> target) {
        Button button = new Button(this);
        button.setText(name);
        button.setTextSize(16);

        button.setOnClickListener(v -> {
            if (target != null) {
                startActivity(new Intent(this, target));
            }
        });

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 65
            );

        params.setMargins(0, 20, 0, 0);
        layout.addView(button, params);
    }
}
