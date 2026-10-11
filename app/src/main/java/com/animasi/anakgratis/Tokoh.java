package com.animasi.anakgratis;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import java.util.HashMap;
import java.util.Map;

class Tokoh {

    // {perempuan, baju, rambut, aksesori, skala%, warnaAksesori}
    static final Map<String, int[]> MANUSIA = new HashMap<String, int[]>();
    // {badan, sayap, paruh, jambul}
    static final Map<String, int[]> BURUNG = new HashMap<String, int[]>();

    private static final Paint cat = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final Path jalur = new Path();

    static {
        cat.setStrokeCap(Paint.Cap.ROUND);
        // aksesori: 0 tidak ada, 1 mahkota, 2 topi jerami, 3 topi koki, 4 topi/helm,
        // 5 helm astronot, 6 dokter, 8 topi santa, 9 hidung badut, 10 jubah
        orang("bayi", 0, 0x7EC8E3, 0x3C1E14, 0, 55, 0);
        orang("anak", 0, 0x4682DC, 0x1E1E1E, 0, 75, 0);
        orang("bocah", 0, 0x4682DC, 0x1E1E1E, 0, 75, 0);
        orang("adik", 1, 0xF5A0C8, 0x3C1E14, 0, 75, 0);
        orang("budi", 0, 0xDC3C3C, 0x1E1E1E, 0, 80, 0);
        orang("ani", 1, 0xF05A96, 0x3C1E14, 0, 80, 0);
        orang("gadis", 1, 0xF05A96, 0x3C1E14, 0, 85, 0);
        orang("kakak", 0, 0x50B464, 0x1E1E1E, 0, 95, 0);
        orang("laki", 0, 0x3C78DC, 0x1E1E1E, 0, 100, 0);
        orang("lelaki", 0, 0x3C78DC, 0x1E1E1E, 0, 100, 0);
        orang("pria", 0, 0x3C78DC, 0x1E1E1E, 0, 100, 0);
        orang("ayah", 0, 0x646496, 0x1E1E1E, 0, 100, 0);
        orang("bapak", 0, 0x646496, 0x1E1E1E, 0, 100, 0);
        orang("wanita", 1, 0xA064C8, 0x3C1E14, 0, 100, 0);
        orang("perempuan", 1, 0xA064C8, 0x3C1E14, 0, 100, 0);
        orang("ibu", 1, 0xDC6482, 0x3C1E14, 0, 100, 0);
        orang("nenek", 1, 0x8C78B4, 0xE6E6E6, 0, 95, 0);
        orang("kakek", 0, 0x78785A, 0xE6E6E6, 0, 95, 0);
        orang("pengantin", 1, 0xFFFFFF, 0x3C1E14, 0, 100, 0);
        orang("guru", 0, 0x8C5A3C, 0x1E1E1E, 0, 100, 0);
        orang("dokter", 0, 0xFFFFFF, 0x1E1E1E, 6, 100, 0);
        orang("perawat", 1, 0xFFFFFF, 0x3C1E14, 6, 100, 0);
        orang("polisi", 0, 0x28508C, 0x1E1E1E, 4, 100, 0x1E3C78);
        orang("tentara", 0, 0x5A6E3C, 0x1E1E1E, 4, 100, 0x4B5A32);
        orang("pilot", 0, 0x1E3C78, 0x1E1E1E, 4, 100, 0x1E3C78);
        orang("koki", 0, 0xFFFFFF, 0x1E1E1E, 3, 100, 0);
        orang("petani", 0, 0x78A050, 0x1E1E1E, 2, 100, 0);
        orang("nelayan", 0, 0xE6A03C, 0x1E1E1E, 2, 100, 0);
        orang("astronot", 0, 0xF0F0F0, 0x1E1E1E, 5, 100, 0);
        orang("pemadam", 0, 0xD23C28, 0x1E1E1E, 4, 100, 0xFFC81E);
        orang("montir", 0, 0x3C6EB4, 0x1E1E1E, 4, 100, 0x3C6EB4);
        orang("ilmuwan", 0, 0xFFFFFF, 0x1E1E1E, 0, 100, 0);
        orang("seniman", 0, 0xDC8C3C, 0x1E1E1E, 4, 100, 0xC83C3C);
        orang("penyanyi", 1, 0xE646B4, 0x3C1E14, 0, 100, 0);
        orang("detektif", 0, 0x8C6E50, 0x1E1E1E, 4, 100, 0x78643C);
        orang("pelari", 0, 0xF0643C, 0x1E1E1E, 0, 100, 0);
        orang("penari", 1, 0xE6469C, 0x3C1E14, 0, 100, 0);
        orang("perenang", 0, 0x28A0DC, 0x1E1E1E, 0, 100, 0);
        orang("raja", 0, 0xA01E3C, 0x1E1E1E, 1, 100, 0);
        orang("ratu", 1, 0x8C28B4, 0x3C1E14, 1, 100, 0);
        orang("putri", 1, 0xF05AA0, 0x78461E, 1, 100, 0);
        orang("pangeran", 0, 0x3C50C8, 0x78461E, 1, 100, 0);
        orang("pahlawan", 0, 0x3C64DC, 0x1E1E1E, 10, 100, 0xDC2828);
        orang("badut", 0, 0xF0C81E, 0xFF8C1E, 9, 100, 0);
        orang("santa", 0, 0xDC1E1E, 0xFFFFFF, 8, 100, 0);

        burung("burung", 0x4682E6, 0x2850AA, 0xFFC828, 0);
        burung("elang", 0x78501E, 0x50320F, 0xFFC828, 0);
        burung("merpati", 0xC8CDD7, 0x9BA5B9, 0xFFA064, 0);
        burung("dara", 0xC8CDD7, 0x9BA5B9, 0xFFA064, 0);
        burung("beo", 0x3CB45A, 0x1E7D3C, 0xFF9619, 0);
        burung("kakatua", 0xFAFAFA, 0xE6E6E6, 0x555555, 1);
        burung("nuri", 0xE63232, 0x28A03C, 0xFFC828, 0);
    }

    private static void orang(String k, int cewek, int baju, int rambut, int aks, int skala, int warnaAks) {
        MANUSIA.put(k, new int[]{cewek, baju, rambut, aks, skala, warnaAks});
    }

    private static void burung(String k, int badan, int sayap, int paruh, int jambul) {
        BURUNG.put(k, new int[]{badan, sayap, paruh, jambul});
    }

    private static int w(int rgb) {
        return 0xFF000000 | rgb;
    }

    // ---------- alat gambar ----------

    private static void isi(Canvas c, float l, float t, float r, float b, int warna) {
        cat.setStyle(Paint.Style.FILL);
        cat.setColor(warna);
        c.drawRect(l, t, r, b, cat);
    }

    private static void bulat(Canvas c, float x, float y, float r, int warna) {
        cat.setStyle(Paint.Style.FILL);
        cat.setColor(warna);
        c.drawCircle(x, y, r, cat);
    }

    private static void oval(Canvas c, float l, float t, float r, float b, int warna) {
        cat.setStyle(Paint.Style.FILL);
        cat.setColor(warna);
        c.drawOval(new RectF(l, t, r, b), cat);
    }

    private static void segitiga(Canvas c, float x1, float y1, float x2, float y2,
                                 float x3, float y3, int warna) {
        cat.setStyle(Paint.Style.FILL);
        cat.setColor(warna);
        jalur.reset();
        jalur.moveTo(x1, y1);
        jalur.lineTo(x2, y2);
        jalur.lineTo(x3, y3);
        jalur.close();
        c.drawPath(jalur, cat);
    }

    private static void garis(Canvas c, float x1, float y1, float x2, float y2,
                              float tebal, int warna) {
        cat.setStyle(Paint.Style.STROKE);
        cat.setStrokeWidth(tebal);
        cat.setColor(warna);
        c.drawLine(x1, y1, x2, y2, cat);
        cat.setStyle(Paint.Style.FILL);
    }

    private static void cincin(Canvas c, float x, float y, float r, float tebal, int warna) {
        cat.setStyle(Paint.Style.STROKE);
        cat.setStrokeWidth(tebal);
        cat.setColor(warna);
        c.drawCircle(x, y, r, cat);
        cat.setStyle(Paint.Style.FILL);
    }

    // ---------- manusia ----------

    static void gambarManusia(Canvas c, float x, float gy, float r0, int[] p, float t) {
        float r = r0 * p[4] / 100f;
        boolean cewek = p[0] == 1;
        int baju = w(p[1]);
        int rambut = w(p[2]);
        int aks = p[3];
        int warnaAks = w(p[5]);
        int kulit = Color.rgb(250, 205, 160);
        int merah = Color.rgb(220, 40, 40);
        int emas = Color.rgb(255, 205, 40);
        float ayun = (float) Math.sin(t * Math.PI * 10);
        float napas = (float) Math.sin(t * Math.PI * 6) * r * 0.03f;
        float pinggul = gy - r * 1.3f;
        float bahu = gy - r * 2.7f + napas;
        float ky = gy - r * 3.4f + napas;
        int celana = cewek ? kulit : Color.rgb(60, 60, 120);

        if (aks == 10) {
            segitiga(c, x - r * 0.3f, bahu, x - r * 1.3f + ayun * r * 0.15f, pinggul + r * 0.7f,
                    x + r * 0.1f, pinggul + r * 0.5f, warnaAks);
        }

        garis(c, x - r * 0.2f, pinggul, x - r * 0.2f + ayun * r * 0.4f, gy, r * 0.28f, celana);
        garis(c, x + r * 0.2f, pinggul, x + r * 0.2f - ayun * r * 0.4f, gy, r * 0.28f, celana);

        if (cewek) {
            segitiga(c, x, bahu, x - r * 0.9f, pinggul + r * 0.1f, x + r * 0.9f, pinggul + r * 0.1f, baju);
        } else {
            isi(c, x - r * 0.45f, bahu, x + r * 0.45f, pinggul, baju);
        }
        if (aks == 6) {
            isi(c, x - r * 0.05f, bahu + r * 0.25f, x + r * 0.05f, bahu + r * 0.6f, merah);
            isi(c, x - r * 0.15f, bahu + r * 0.38f, x + r * 0.15f, bahu + r * 0.48f, merah);
        }

        garis(c, x - r * 0.4f, bahu + r * 0.2f, x - r * 0.6f - ayun * r * 0.3f, bahu + r * 1.0f, r * 0.2f, kulit);
        garis(c, x + r * 0.4f, bahu + r * 0.2f, x + r * 0.6f + ayun * r * 0.3f, bahu + r * 1.0f, r * 0.2f, kulit);

        bulat(c, x, ky - r * 0.1f, r * 0.68f, rambut);
        if (cewek) {
            oval(c, x - r * 0.75f, ky - r * 0.2f, x - r * 0.4f, ky + r * 0.9f, rambut);
        }
        bulat(c, x + r * 0.05f, ky + r * 0.05f, r * 0.58f, kulit);

        if (aks == 8) {
            oval(c, x - r * 0.4f, ky + r * 0.15f, x + r * 0.5f, ky + r * 0.9f, Color.WHITE);
        }
        boolean buka = ((t * 5f) % 1f) < 0.9f;
        if (buka) {
            bulat(c, x + r * 0.28f, ky, r * 0.07f, Color.BLACK);
            bulat(c, x - r * 0.1f, ky, r * 0.07f, Color.BLACK);
        } else {
            garis(c, x + r * 0.2f, ky, x + r * 0.36f, ky, r * 0.04f, Color.BLACK);
            garis(c, x - r * 0.18f, ky, x - r * 0.02f, ky, r * 0.04f, Color.BLACK);
        }
        if (aks != 8) {
            garis(c, x - r * 0.05f, ky + r * 0.3f, x + r * 0.3f, ky + r * 0.3f, r * 0.06f, Color.rgb(180, 60, 60));
        }
        if (aks == 9) {
            bulat(c, x + r * 0.1f, ky + r * 0.12f, r * 0.13f, merah);
        }

        if (aks == 1) {
            isi(c, x - r * 0.45f, ky - r * 0.85f, x + r * 0.45f, ky - r * 0.62f, emas);
            segitiga(c, x - r * 0.45f, ky - r * 0.85f, x - r * 0.3f, ky - r * 1.15f, x - r * 0.15f, ky - r * 0.85f, emas);
            segitiga(c, x - r * 0.15f, ky - r * 0.85f, x, ky - r * 1.3f, x + r * 0.15f, ky - r * 0.85f, emas);
            segitiga(c, x + r * 0.15f, ky - r * 0.85f, x + r * 0.3f, ky - r * 1.15f, x + r * 0.45f, ky - r * 0.85f, emas);
            bulat(c, x, ky - r * 0.73f, r * 0.07f, merah);
        } else if (aks == 2) {
            int jerami = Color.rgb(230, 200, 110);
            oval(c, x - r * 0.45f, ky - r * 1.2f, x + r * 0.55f, ky - r * 0.6f, jerami);
            oval(c, x - r * 0.9f, ky - r * 0.85f, x + r * 0.9f, ky - r * 0.55f, jerami);
        } else if (aks == 3) {
            isi(c, x - r * 0.4f, ky - r * 0.95f, x + r * 0.4f, ky - r * 0.62f, Color.WHITE);
            bulat(c, x, ky - r * 1.1f, r * 0.42f, Color.WHITE);
            bulat(c, x - r * 0.3f, ky - r * 1.0f, r * 0.3f, Color.WHITE);
            bulat(c, x + r * 0.3f, ky - r * 1.0f, r * 0.3f, Color.WHITE);
        } else if (aks == 4) {
            isi(c, x - r * 0.5f, ky - r * 0.9f, x + r * 0.5f, ky - r * 0.62f, warnaAks);
            isi(c, x, ky - r * 0.68f, x + r * 0.8f, ky - r * 0.6f, warnaAks);
            bulat(c, x, ky - r * 0.78f, r * 0.07f, emas);
        } else if (aks == 5) {
            bulat(c, x, ky, r * 0.8f, Color.argb(90, 200, 230, 255));
            cincin(c, x, ky, r * 0.8f, r * 0.1f, Color.rgb(230, 230, 240));
        } else if (aks == 8) {
            segitiga(c, x - r * 0.55f, ky - r * 0.6f, x - r * 0.1f, ky - r * 1.4f, x + r * 0.55f, ky - r * 0.6f, merah);
            bulat(c, x - r * 0.1f, ky - r * 1.45f, r * 0.14f, Color.WHITE);
            isi(c, x - r * 0.6f, ky - r * 0.7f, x + r * 0.6f, ky - r * 0.58f, Color.WHITE);
        }
    }

    // ---------- burung ----------

    static void gambarBurung(Canvas c, float x, float y, float r, int[] p, float t) {
        int badan = w(p[0]);
        int sayap = w(p[1]);
        int paruh = w(p[2]);
        int gelap = Color.rgb(Color.red(sayap) * 3 / 4, Color.green(sayap) * 3 / 4, Color.blue(sayap) * 3 / 4);
        float kepak = (float) Math.sin(t * Math.PI * 16);

        segitiga(c, x - r * 0.8f, y, x - r * 1.7f, y - r * 0.3f, x - r * 1.7f, y + r * 0.3f, sayap);
        segitiga(c, x - r * 0.4f, y - r * 0.1f, x + r * 0.2f, y - r * 0.1f,
                x - r * 0.6f, y - kepak * r * 1.3f - r * 0.1f, gelap);
        oval(c, x - r * 0.8f, y - r * 0.5f, x + r * 0.8f, y + r * 0.5f, badan);
        bulat(c, x + r * 0.8f, y - r * 0.3f, r * 0.4f, badan);
        segitiga(c, x + r * 1.15f, y - r * 0.4f, x + r * 1.15f, y - r * 0.15f, x + r * 1.7f, y - r * 0.28f, paruh);
        bulat(c, x + r * 0.95f, y - r * 0.4f, r * 0.08f, Color.BLACK);
        if (p[3] == 1) {
            segitiga(c, x + r * 0.65f, y - r * 0.6f, x + r * 0.8f, y - r * 1.3f, x + r * 1.0f, y - r * 0.6f,
                    Color.rgb(255, 220, 60));
        }
        segitiga(c, x - r * 0.5f, y - r * 0.05f, x + r * 0.3f, y - r * 0.05f,
                x - r * 0.2f, y - kepak * r * 1.6f, sayap);
    }

    // ---------- kupu-kupu ----------

    static void gambarKupu(Canvas c, float x, float y, float r, float t) {
        float kepak = (float) Math.abs(Math.sin(t * Math.PI * 14));
        float lb = r * (0.15f + 0.95f * kepak);
        int atas = Color.rgb(255, 140, 40);
        int bawah = Color.rgb(255, 200, 60);
        int tubuh = Color.rgb(60, 40, 30);
        oval(c, x - lb, y - r * 0.8f, x, y, atas);
        oval(c, x, y - r * 0.8f, x + lb, y, atas);
        oval(c, x - lb * 0.8f, y - r * 0.05f, x, y + r * 0.7f, bawah);
        oval(c, x, y - r * 0.05f, x + lb * 0.8f, y + r * 0.7f, bawah);
        oval(c, x - r * 0.12f, y - r * 0.5f, x + r * 0.12f, y + r * 0.6f, tubuh);
        garis(c, x, y - r * 0.5f, x - r * 0.3f, y - r * 0.9f, r * 0.05f, tubuh);
        garis(c, x, y - r * 0.5f, x + r * 0.3f, y - r * 0.9f, r * 0.05f, tubuh);
    }
          }
