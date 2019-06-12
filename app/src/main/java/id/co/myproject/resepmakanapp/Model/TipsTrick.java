package id.co.myproject.resepmakanapp.Model;

public class TipsTrick {
    String id;
    String judul;
    String isi;
    String gambar;

    public TipsTrick() {
    }

    public TipsTrick(String id, String judul, String isi, String gambar) {
        this.id = id;
        this.judul = judul;
        this.isi = isi;
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
