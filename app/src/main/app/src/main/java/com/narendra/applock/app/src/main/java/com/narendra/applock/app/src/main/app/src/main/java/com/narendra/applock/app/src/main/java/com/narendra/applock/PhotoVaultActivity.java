
package com.narendra.applock;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Button;
import android.widget.ImageView;

public class PhotoVaultActivity extends Activity {

    private static final int PICK_IMAGE = 100;
    private ImageView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setPadding(25, 25, 25, 25);
        layout.setBackgroundColor(Color.rgb(12,18,38));

        TextView title = new TextView(this);
        title.setText("PHOTO VAULT");
        title.setTextColor(Color.WHITE);
        title.setTextSize(25);
        title.setGravity(Gravity.CENTER);

        preview = new ImageView(this);
        preview.setAdjustViewBounds(true);
        preview.setMaxHeight(400);

        Button choose = new Button(this);
        choose.setText("SELECT PHOTO");

        choose.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(intent, PICK_IMAGE);
        });

        layout.addView(title);
        layout.addView(choose);
        layout.addView(preview);

        setContentView(layout);
    }

    @Override
    protected void onActivityResult(
        int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE &&
            resultCode == RESULT_OK && data != null) {

            Uri uri = data.getData();

            if (uri != null) {
                preview.setImageURI(uri);
            }
        }
    }
}
