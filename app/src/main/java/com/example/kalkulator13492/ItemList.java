package com.example.kalkulator13492;

public class ItemList {
    private final String Baris1;
    private final String BarisOperasi;
    private final String Baris2;
    private final String BarisHasil;

    public ItemList(String baris1, String barisOperasi, String baris2, String barisHasil) {
        Baris1 = baris1;
        BarisOperasi = barisOperasi;
        Baris2 = baris2;
        BarisHasil = barisHasil;
    }

    public String getLine1() {
        return Baris1;
    }

    public String getLineOperasi() {
        return BarisOperasi;
    }

    public String getLine2() {
        return Baris2;
    }

    public String getLineHasil() {
        return BarisHasil;
    }
}
