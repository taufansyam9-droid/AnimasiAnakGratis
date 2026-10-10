package com.animasi.anakgratis;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setGravity(Gravity.CENTER);
        layout.setBackgroundColor(Color.rgb(245, 248, 255));
        layout.setPadding(30, 30, 30, 30);

        TextView judul = new TextView(this);
        judul.setText("Animasi Anak Gratis");
        judul.setTextSize(28);
        judul.setTextColor(Color.rgb(40, 80, 180));
        judul.setGravity(Gravity.CENTER);

        TextView keterangan = new TextView(this);
        keterangan.setText(
            "Selamat datang!\n\n" +
            "Aplikasi Animasi Anak Gratis\n" +
            "Versi 1.0"
        );
        keterangan.setTextSize(18);
        keterangan.setTextColor(Color.BLACK);
        keterangan.setGravity(Gravity.CENTER);
        keterangan.setPadding(0, 30, 0, 30);

        layout.addView(judul);
        layout.addView(keterangan);

        setContentView(layout);
    }
}
