package com.animasi.anakgratis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.InputType;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.view.Gravity;
import android.view.MotionEvent;
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
    private String cerita = "";
    private boolean sedangFilm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tampilkanMenu();
    }

    @Override
    public void onBackPressed() {
        if (sedangFilm) {
            tampilkanMenu();
        } else {
            super.onBackPressed();
        }
    }

    private void tampilkanMenu() {
        sedangFilm = false;

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
        petunjuk.setText("Tulis cerita anak (pisahkan kalimat dengan titik):");
        petunjuk.setTextSize(16);
        petunjuk.setTextColor(Color.BLACK);
        petunjuk.setPadding(0, 30, 0, 10);
        layout.addView(petunjuk);

        final EditText isiCerita = new EditText(this);
        isiCerita.setHint("Contoh: Kelinci pergi ke hutan. Dia mencari makanan. Dia bertemu rusa.");
        isiCerita.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        isiCerita.setMinLines(5);
        isiCerita.setGravity(Gravity.TOP);
        isiCerita.setBackgroundColor(Color.WHITE);
        isiCerita.setTextColor(Color.BLACK);
        isiCerita.setPadding(20, 20, 20, 20);
        isiCerita.setText(cerita);
        layout.addView(isiCerita);

        final TextView labelJumlah = new TextView(this);
        labelJumlah.setText("Jumlah adegan maksimal: " + jumlahAdegan);
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
                labelJumlah.setText("Jumlah adegan maksimal: " + jumlahAdegan);
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
                cerita = isiCerita.getText().toString().trim();
                if (cerita.length() == 0) {
                    hasil.setText("Tulis ceritanya dulu ya.");
                    return;
                }
                tampilkanFilm(bagiAdegan(cerita, jumlahAdegan));
            }
        });

        setContentView(scroll);
    }

    private void tampilkanFilm(List<String> adegan) {
        sedangFilm = true;
        AnimasiView film = new AnimasiView(this, adegan, new Runnable() {
            @Override
            public void run() {
                tampilkanMenu();
            }
        });
        setContentView(film);
    }

    private List<String> bagiAdegan(String teksCerita, int jumlah) {
        String[] potongan = teksCerita.split("[.!?\\n]+");
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

class AnimasiView extends View {

    private static final long DURASI = 3500;

    private final List<String> adegan;
    private final Runnable kembali;
    private final Paint cat = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final TextPaint teks = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private final int[] langit = {
            Color.rgb(135, 206, 250),
            Color.rgb(255, 205, 160),
            Color.rgb(205, 180, 240),
            Color.rgb(160, 230, 205)
    };

    private int indeks = 0;
    private long mulai = -1;
    private boolean selesai = false;

    AnimasiView(Context context, List<String> adegan, Runnable kembali) {
        super(context);
        this.adegan = adegan;
        this.kembali = kembali;
    }

    @Override
    protected void onDraw(Canvas c) {
        int w = getWidth();
        int h = getHeight();
        long sekarang = SystemClock.uptimeMillis();
        if (mulai < 0) {
            mulai = sekarang;
        }
        float t = (sekarang - mulai) / (float) DURASI;
        if (t >= 1f) {
            if (indeks < adegan.size() - 1) {
                indeks++;
                mulai = sekarang;
                t = 0f;
            } else {
                selesai = true;
                t = 1f;
            }
        }

        cat.setColor(langit[indeks % langit.length]);
        c.drawRect(0, 0, w, h, cat);

        cat.setColor(Color.rgb(255, 220, 80));
        c.drawCircle(w * 0.82f, h * 0.14f, w * 0.08f, cat);

        cat.setColor(Color.rgb(90, 180, 90));
        c.drawRect(0, h * 0.65f, w, h, cat);

        for (int i = 0; i < 3; i++) {
            float px = w * (0.2f + 0.3f * i);
            cat.setColor(Color.rgb(120, 80, 40));
            c.drawRect(px - w * 0.02f, h * 0.5f, px + w * 0.02f, h * 0.65f, cat);
            cat.setColor(Color.rgb(40, 130, 60));
            c.drawCircle(px, h * 0.46f, w * 0.09f, cat);
        }

        float x = w * (0.15f + 0.7f * t);
        float lompat = (float) Math.abs(Math.sin(t * Math.PI * 6)) * h * 0.05f;
        float y = h * 0.58f - lompat;
        float r = w * 0.07f;

        cat.setColor(Color.WHITE);
        c.drawOval(new RectF(x - r * 1.3f, y - r * 0.9f, x + r * 1.3f, y + r * 0.9f), cat);
        c.drawCircle(x + r * 1.1f, y - r * 1.1f, r * 0.7f, cat);
        c.drawOval(new RectF(x + r * 0.7f, y - r * 2.6f, x + r * 1.0f, y - r * 1.4f), cat);
        c.drawOval(new RectF(x + r * 1.2f, y - r * 2.6f, x + r * 1.5f, y - r * 1.4f), cat);
        cat.setColor(Color.BLACK);
        c.drawCircle(x + r * 1.35f, y - r * 1.2f, r * 0.1f, cat);
        cat.setColor(Color.rgb(255, 150, 170));
        c.drawCircle(x + r * 1.75f, y - r * 1.0f, r * 0.12f, cat);

        teks.setColor(Color.BLACK);
        teks.setTextSize(w * 0.04f);
        c.drawText("Adegan " + (indeks + 1) + "/" + adegan.size(), w * 0.04f, h * 0.05f, teks);

        teks.setTextSize(w * 0.05f);
        String isi = adegan.get(indeks);
        if (selesai) {
            isi = isi + "\n\n(Selesai. Sentuh layar untuk kembali.)";
        }
        StaticLayout sl = new StaticLayout(isi, teks, (int) (w * 0.9f),
                Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
        c.save();
        c.translate(w * 0.05f, h * 0.75f);
        sl.draw(c);
        c.restore();

        if (!selesai) {
            postInvalidateOnAnimation();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (selesai && e.getAction() == MotionEvent.ACTION_UP) {
            kembali.run();
        }
        return true;
    }
    }
