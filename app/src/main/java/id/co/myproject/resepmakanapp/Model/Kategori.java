package id.co.myproject.resepmakanapp.Model;

public class Kategori {
    String id;
    String nama_kategori;
    String gambar_kategori;
    String jumlah_makanan;


    public Kategori() {
    }

    public Kategori(String id, String nama_kategori, String gambar_kategori) {
        this.id = id;
        this.nama_kategori = nama_kategori;
        this.gambar_kategori = gambar_kategori;
    }

    public String getJumlah_makanan() {
        return jumlah_makanan;
    }

    public void setJumlah_makanan(String jumlah_makanan) {
        this.jumlah_makanan = jumlah_makanan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getGambar_kategori() {
        return gambar_kategori;
    }

    public void setGambar_kategori(String gambar_kategori) {
        this.gambar_kategori = gambar_kategori;
    }
}
