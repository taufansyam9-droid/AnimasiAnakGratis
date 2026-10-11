package com.animasi.anakgratis;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private int jumlahAdegan = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(Color.rgb(245, 248, 255));

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(30, 30, 30, 30);
        scroll.addView(layout);

        TextView judul = new TextView(this);
        judul.setText("Animasi Anak Gratis");
        judul.setTextSize(28);
        judul.setTextColor(Color.rgb(40, 80, 180));
        judul.setGravity(Gravity.CENTER);
        layout.addView(judul);

        TextView petunjuk = new TextView(this);
        petunjuk.setText("Tulis cerita anak di bawah ini:");
        petunjuk.setTextSize(16);
        petunjuk.setTextColor(Color.BLACK);
        petunjuk.setPadding(0, 30, 0, 10);
        layout.addView(petunjuk);

        final EditText isiCerita = new EditText(this);
        isiCerita.setHint("Contoh: Kelinci pergi ke hutan. Dia bertemu rusa. Mereka bermain bersama.");
        isiCerita.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        isiCerita.setMinLines(5);
        isiCerita.setGravity(Gravity.TOP);
        isiCerita.setBackgroundColor(Color.WHITE);
        isiCerita.setTextColor(Color.BLACK);
        isiCerita.setPadding(20, 20, 20, 20);
        layout.addView(isiCerita);

        final TextView labelJumlah = new TextView(this);
        labelJumlah.setText("Jumlah adegan: " + jumlahAdegan);
        labelJumlah.setTextSize(16);
        labelJumlah.setTextColor(Color.BLACK);
        labelJumlah.setPadding(0, 30, 0, 10);
        layout.addView(labelJumlah);

        SeekBar penggeser = new SeekBar(this);
        penggeser.setMax(11);
        penggeser.setProgress(jumlahAdegan - 1);
        penggeser.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                jumlahAdegan = progress + 1;
                labelJumlah.setText("Jumlah adegan: " + jumlahAdegan);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        layout.addView(penggeser);

        Button tombol = new Button(this);
        tombol.setText("Buat Film");
        tombol.setTextColor(Color.WHITE);
        tombol.setBackgroundColor(Color.rgb(40, 80, 180));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = 40;
        tombol.setLayoutParams(lp);
        layout.addView(tombol);

        final TextView hasil = new TextView(this);
        hasil.setTextSize(16);
        hasil.setTextColor(Color.BLACK);
        hasil.setPadding(0, 30, 0, 0);
        layout.addView(hasil);

        tombol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cerita = isiCerita.getText().toString().trim();
                if (cerita.length() == 0) {
                    hasil.setText("Tulis ceritanya dulu ya.");
                    return;
                }
                List<String> adegan = bagiAdegan(cerita, jumlahAdegan);
                StringBuilder sb = new StringBuilder();
                sb.append("Cerita dipecah jadi ").append(adegan.size()).append(" adegan:\n\n");
                for (int i = 0; i < adegan.size(); i++) {
                    sb.append("Adegan ").append(i + 1).append(": ").append(adegan.get(i)).append("\n\n");
                }
                sb.append("(Pembuatan video akan ditambahkan di tahap berikutnya.)");
                hasil.setText(sb.toString());
            }
        });

        setContentView(scroll);
    }

    private List<String> bagiAdegan(String cerita, int jumlah) {
        String[] potongan = cerita.split("[.!?\\n]+");
        List<String> kalimat = new ArrayList<String>();
        for (String p : potongan) {
            String t = p.trim();
            if (t.length() > 0) {
                kalimat.add(t);
            }
        }
        if (kalimat.size() <= jumlah) {
            return kalimat;
        }
        List<String> hasil = new ArrayList<String>();
        int ukuran = (int) Math.ceil(kalimat.size() / (double) jumlah);
        for (int i = 0; i < kalimat.size(); i += ukuran) {
            StringBuilder sb = new StringBuilder();
            int batas = Math.min(i + ukuran, kalimat.size());
            for (int j = i; j < batas; j++) {
                if (sb.length() > 0) {
                    sb.append(". ");
                }
                sb.append(kalimat.get(j));
            }
            hasil.add(sb.toString());
        }
        return hasil;
    }
}
