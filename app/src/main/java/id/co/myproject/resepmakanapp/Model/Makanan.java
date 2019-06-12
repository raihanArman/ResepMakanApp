package id.co.myproject.resepmakanapp.Model;

public class Makanan {
    String id_makanan;
    String nama_makanan;
    String gambar;
    String deskripsi;
    String video;
    String resep;
    String rating;
    String feedback;

    public Makanan() {
    }

    public Makanan(String id_makanan, String nama_makanan, String gambar, String deskripsi, String video, String resep, String rating, String feedback) {
        this.id_makanan = id_makanan;
        this.nama_makanan = nama_makanan;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.video = video;
        this.resep = resep;
        this.rating = rating;
        this.feedback = feedback;
    }

    public String getId_makanan() {
        return id_makanan;
    }

    public void setId_makanan(String id_makanan) {
        this.id_makanan = id_makanan;
    }

    public String getNama_makanan() {
        return nama_makanan;
    }

    public void setNama_makanan(String nama_makanan) {
        this.nama_makanan = nama_makanan;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getResep() {
        return resep;
    }

    public void setResep(String resep) {
        this.resep = resep;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
