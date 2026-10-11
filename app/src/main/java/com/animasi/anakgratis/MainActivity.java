package com.animasi.anakgratis;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class MainActivity extends Activity {

    private int jumlahAdegan = 5;
    private String cerita = "";
    private boolean sedangFilm = false;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        Data.muat(this);
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
        LinearLayout lay = new LinearLayout(this);
        lay.setOrientation(LinearLayout.VERTICAL);
        lay.setPadding(30, 30, 30, 30);
        scroll.addView(lay);

        TextView judul = new TextView(this);
        judul.setText("Animasi Anak Gratis");
        judul.setTextSize(28);
        judul.setTextColor(Color.rgb(40, 80, 180));
        judul.setGravity(Gravity.CENTER);
        lay.addView(judul);

        TextView info = new TextView(this);
        info.setText("Tulis isi cerita, pisahkan kalimat dengan titik. Tokoh, benda, dan tempat "
                + "dikenali otomatis dari kata di file kata.txt dan latar.txt.");
        info.setTextSize(15);
        info.setTextColor(Color.BLACK);
        info.setPadding(0, 30, 0, 10);
        lay.addView(info);

        final EditText isi = new EditText(this);
        isi.setHint("Contoh: Monyet makan pisang di hutan. Hujan turun di sawah. Ikan berenang di laut.");
        isi.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        isi.setMinLines(5);
        isi.setGravity(Gravity.TOP);
        isi.setBackgroundColor(Color.WHITE);
        isi.setTextColor(Color.BLACK);
        isi.setPadding(20, 20, 20, 20);
        isi.setText(cerita);
        lay.addView(isi);

        final TextView lj = new TextView(this);
        lj.setText("Jumlah adegan maksimal: " + jumlahAdegan);
        lj.setTextSize(16);
        lj.setTextColor(Color.BLACK);
        lj.setPadding(0, 30, 0, 10);
        lay.addView(lj);

        SeekBar sb = new SeekBar(this);
        sb.setMax(11);
        sb.setProgress(jumlahAdegan - 1);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar s, int p, boolean u) {
                jumlahAdegan = p + 1;
                lj.setText("Jumlah adegan maksimal: " + jumlahAdegan);
            }

            public void onStartTrackingTouch(SeekBar s) {
            }

            public void onStopTrackingTouch(SeekBar s) {
            }
        });
        lay.addView(sb);

        Button tombol = new Button(this);
        tombol.setText("Buat Film");
        tombol.setTextColor(Color.WHITE);
        tombol.setBackgroundColor(Color.rgb(40, 80, 180));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.topMargin = 40;
        tombol.setLayoutParams(lp);
        lay.addView(tombol);

        final TextView hasil = new TextView(this);
        hasil.setTextColor(Color.BLACK);
        hasil.setPadding(0, 30, 0, 0);
        lay.addView(hasil);

        tombol.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cerita = isi.getText().toString().trim();
                List<String> ad = bagi(cerita, jumlahAdegan);
                if (ad.isEmpty()) {
                    hasil.setText("Tulis ceritanya dulu ya.");
                    return;
                }
                sedangFilm = true;
                setContentView(new AnimasiView(MainActivity.this, ad, new Runnable() {
                    public void run() {
                        tampilkanMenu();
                    }
                }));
            }
        });
        setContentView(scroll);
    }

    private List<String> bagi(String teks, int jumlah) {
        List<String> k = new ArrayList<String>();
        for (String p : teks.split("[.!?\\n]+")) {
            if (p.trim().length() > 0) {
                k.add(p.trim());
            }
        }
        if (k.size() <= jumlah) {
            return k;
        }
        List<String> hasil = new ArrayList<String>();
        int ukuran = (int) Math.ceil(k.size() / (double) jumlah);
        for (int i = 0; i < k.size(); i += ukuran) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < Math.min(i + ukuran, k.size()); j++) {
                if (sb.length() > 0) {
                    sb.append(". ");
                }
                sb.append(k.get(j));
            }
            hasil.add(sb.toString());
        }
        return hasil;
    }
}

class Data {
    static class Latar {
        Set<String> kunci = new HashSet<String>();
        int langit = Color.rgb(135, 206, 250);
        int tanah = Color.rgb(90, 180, 90);
        String[] langitEmoji = new String[0];
        String[] hiasan = new String[0];
    }

    static final Map<String, String> kata = new HashMap<String, String>();
    static final List<Latar> latar = new ArrayList<Latar>();
    static final Set<String> TERBANG = new HashSet<String>(Arrays.asList("burung", "elang", "kupu",
            "lebah", "merpati", "dara", "beo", "kakatua", "nuri", "kelelawar", "nyamuk", "pesawat",
            "helikopter", "roket", "ufo", "balon", "layangan", "malaikat", "peri", "naga"));
    static final Set<String> AIR = new HashSet<String>(Arrays.asList("ikan", "buntal", "hiu", "paus",
            "lumba", "gurita", "cumi", "udang", "kepiting", "lobster", "duyung", "penyu"));
    static boolean siap = false;

    static void muat(Context ctx) {
        if (siap) {
            return;
        }
        siap = true;
        for (String s : baris(ctx, "kata.txt")) {
            int p = s.indexOf('=');
            if (p > 0) {
                kata.put(s.substring(0, p).trim().toLowerCase(Locale.ROOT), s.substring(p + 1).trim());
            }
        }
        for (String s : baris(ctx, "latar.txt")) {
            String[] p = s.split("\\|");
            if (p.length < 3) {
                continue;
            }
            try {
                Latar l = new Latar();
                for (String k : p[0].split(",")) {
                    l.kunci.add(k.trim().toLowerCase(Locale.ROOT));
                }
                l.langit = Color.parseColor("#" + p[1].trim());
                l.tanah = Color.parseColor("#" + p[2].trim());
                l.langitEmoji = pecah(p.length > 3 ? p[3] : "");
                l.hiasan = pecah(p.length > 4 ? p[4] : "");
                latar.add(l);
            } catch (Exception e) {
                // baris rusak dilewati
            }
        }
        if (kata.isEmpty()) {
            kata.put("kelinci", "\uD83D\uDC07");
        }
        if (latar.isEmpty()) {
            latar.add(new Latar());
        }
    }

    static String[] pecah(String s) {
        s = s.trim();
        return s.length() == 0 ? new String[0] : s.split("\\s+");
    }

    static List<String> baris(Context ctx, String nama) {
        List<String> hasil = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(ctx.getAssets().open(nama), "UTF-8"));
            String s;
            while ((s = br.readLine()) != null) {
                if (s.startsWith("\uFEFF")) {
                    s = s.substring(1);
                }
                s = s.trim();
                if (s.length() > 0 && !s.startsWith("#")) {
                    hasil.add(s);
                }
            }
            br.close();
        } catch (Exception e) {
            // file tidak ada: pakai bawaan
        }
        return hasil;
    }

    static String[] token(String s) {
        String[] a = s.toLowerCase(Locale.ROOT).split("[^a-z]+");
        for (int i = 0; i < a.length; i++) {
            if (a[i].endsWith("nya") && a[i].length() > 5) {
                a[i] = a[i].substring(0, a[i].length() - 3);
            }
        }
        return a;
    }
}

class AnimasiView extends View {
    private static final long DURASI = 4000;
    // Ubah ke false jika tokoh terlihat berjalan mundur
    private static final boolean BALIK = true;

    private final List<String> adegan;
    private final Runnable kembali;
    private final String[] kataTokoh, tokoh, benda;
    private final Data.Latar[] latarAd;
    private final boolean[] bawahAir;
    private final Paint cat = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint emo = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final TextPaint teks = new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private int indeks = 0;
    private long mulai = -1;
    private boolean selesai = false;

    AnimasiView(Context ctx, List<String> adegan, Runnable kembali) {
        super(ctx);
        this.adegan = adegan;
        this.kembali = kembali;
        emo.setTextAlign(Paint.Align.CENTER);
        int n = adegan.size();
        kataTokoh = new String[n];
        tokoh = new String[n];
        benda = new String[n];
        latarAd = new Data.Latar[n];
        bawahAir = new boolean[n];
        String sebelum = "kelinci";
        for (int i = 0; i < n; i++) {
            String[] tk = Data.token(adegan.get(i));
            String t1 = null, t2 = null;
            for (String k : tk) {
                if (Data.kata.containsKey(k)) {
                    if (t1 == null) {
                        t1 = k;
                    } else if (t2 == null && !k.equals(t1)) {
                        t2 = k;
                    }
                }
            }
            if (t1 == null) {
                t1 = sebelum;
            }
            sebelum = t1;
            kataTokoh[i] = t1;
            String em = Data.kata.get(t1);
            tokoh[i] = em == null ? "\uD83D\uDC07" : em;
            benda[i] = t2 == null ? "" : Data.kata.get(t2);
            Data.Latar L = null;
            for (String k : tk) {
                for (Data.Latar x : Data.latar) {
                    if (L == null && x.kunci.contains(k)) {
                        L = x;
                    }
                }
            }
            bawahAir[i] = (L == null && Data.AIR.contains(t1));
            if (L == null) {
                L = i > 0 ? latarAd[i - 1] : Data.latar.get(0);
            }
            latarAd[i] = L;
        }
    }

    private void rect(Canvas c, float l, float t, float r, float b, int warna) {
        cat.setColor(warna);
        c.drawRect(l, t, r, b, cat);
    }

    private void emoji(Canvas c, String e, float x, float y, float ukuran, float sudut, boolean balik) {
        emo.setTextSize(ukuran);
        c.save();
        c.rotate(sudut, x, y);
        if (balik) {
            c.scale(-1f, 1f, x, y);
        }
        c.drawText(e, x, y, emo);
        c.restore();
    }

    @Override
    protected void onDraw(Canvas c) {
        int w = getWidth();
        int h = getHeight();
        long now = SystemClock.uptimeMillis();
        if (mulai < 0) {
            mulai = now;
        }
        float t = (now - mulai) / (float) DURASI;
        if (t >= 1f) {
            if (indeks < adegan.size() - 1) {
                indeks++;
                mulai = now;
                t = 0f;
            } else {
                selesai = true;
                t = 1f;
            }
        }
        Data.Latar L = latarAd[indeks];
        String tk = kataTokoh[indeks];
        boolean air = bawahAir[indeks];
        float pi = (float) Math.PI;

        if (air) {
            rect(c, 0, 0, w, h, Color.rgb(20, 120, 200));
            rect(c, 0, h * 0.8f, w, h, Color.rgb(230, 210, 150));
            cat.setColor(Color.argb(150, 255, 255, 255));
            for (int i = 0; i < 6; i++) {
                float by = h * (0.75f - ((t * 0.5f + 0.17f * i) % 1f) * 0.6f);
                c.drawCircle(w * (0.1f + 0.15f * i), by, w * 0.015f, cat);
            }
        } else {
            rect(c, 0, 0, w, h, L.langit);
            rect(c, 0, h * 0.65f, w, h, L.tanah);
            for (int i = 0; i < L.langitEmoji.length; i++) {
                if (i == 0) {
                    emoji(c, L.langitEmoji[0], w * 0.82f, h * 0.16f, w * 0.14f, 0, false);
                } else {
                    float cx = w * (((0.1f + 0.3f * i) + 0.08f * t) % 1.0f);
                    emoji(c, L.langitEmoji[i], cx, h * (0.1f + 0.05f * i), w * 0.1f, 0, false);
                }
            }
            for (int i = 0; i < L.hiasan.length; i++) {
                float hx = w * (i + 0.5f) / L.hiasan.length;
                float uk = w * (i % 2 == 0 ? 0.16f : 0.13f);
                float goyang = (float) Math.sin(t * pi * 4 + i) * 3f;
                emoji(c, L.hiasan[i], hx, h * 0.67f, uk, goyang, false);
            }
        }

        if (benda[indeks].length() > 0) {
            emoji(c, benda[indeks], w * 0.86f, h * 0.74f, w * 0.12f,
                    (float) Math.sin(t * pi * 4) * 5f, false);
        }

        float sz = w * 0.22f * (1f + 0.03f * (float) Math.sin(t * pi * 6));
        float x = w * (0.15f + 0.5f * t);
        float y;
        float sudut;
        if (air) {
            y = h * 0.55f + (float) Math.sin(t * pi * 6) * sz * 0.15f;
            sudut = (float) Math.sin(t * pi * 6) * 5f;
        } else if (Data.TERBANG.contains(tk)) {
            y = h * 0.38f + (float) Math.sin(t * pi * 4) * sz * 0.25f;
            sudut = (float) Math.sin(t * pi * 8) * 6f;
        } else {
            y = h * 0.74f - (float) Math.abs(Math.sin(t * pi * 8)) * sz * 0.15f;
            sudut = (float) Math.sin(t * pi * 8) * 4f;
        }
        int[] orang = Tokoh.MANUSIA.get(tk);
        int[] burung = Tokoh.BURUNG.get(tk);
        if (orang != null && !air) {
            Tokoh.gambarManusia(c, x, h * 0.72f, w * 0.07f, orang, t);
        } else if (burung != null && !air) {
            Tokoh.gambarBurung(c, x, y, w * 0.08f, burung, t);
        } else if (tk.equals("kupu") && !air) {
            Tokoh.gambarKupu(c, x, y, w * 0.08f, t);
        } else {
            emoji(c, tokoh[indeks], x, y, sz, sudut, BALIK);
        }

        rect(c, 0, h * 0.755f, w, h, Color.argb(190, 255, 255, 255));
        rect(c, 0, 0, w * 0.45f, h * 0.06f, Color.argb(190, 255, 255, 255));
        teks.setColor(Color.BLACK);
        teks.setTextSize(w * 0.04f);
        c.drawText("Adegan " + (indeks + 1) + "/" + adegan.size(), w * 0.03f, h * 0.042f, teks);
        teks.setTextSize(w * 0.05f);
        String isi = adegan.get(indeks);
        if (selesai) {
            isi = isi + "\n\n(Selesai. Sentuh layar untuk kembali.)";
        }
        StaticLayout sl = new StaticLayout(isi, teks, (int) (w * 0.9f),
                Layout.Alignment.ALIGN_CENTER, 1f, 0f, false);
        c.save();
        c.translate(w * 0.05f, h * 0.765f);
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
