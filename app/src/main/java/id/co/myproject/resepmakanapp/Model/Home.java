package id.co.myproject.resepmakanapp.Model;

import java.util.List;

public class Home {
    public static final int BANNER_LIST = 0;
    public static final int KATEGORI_LIST = 1;
    public static final int MAKANAN_LIST = 2;
    public static final int TIPS_LIST = 3;

    private int type;

    ///////Slider
    private List<Makanan> bannerList;

    public Home(int type, String makanan, List<Makanan> bannerList) {
        this.type = type;
        this.bannerList = bannerList;
    }

    ///////Slider

    ///////Kategori
    private List<Kategori> kategoriList;

    public Home(int type, List<Kategori> kategoriList) {
        this.type = type;
        this.kategoriList = kategoriList;
    }

    ///////Kategori


    ///////Makanan
    private List<Makanan> makananList;

    public Home(int type, int a,  List<Makanan> makananList) {
        this.type = type;
        this.makananList = makananList;
    }
    ///////Makanan

    //////Tips
    private List<TipsTrick> tipsTrickList;

    public Home(int type, int a,String b,List<TipsTrick> tipsTrickList) {
        this.type = type;
        this.tipsTrickList = tipsTrickList;
    }
    //////Tips


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Kategori> getKategoriList() {
        return kategoriList;
    }

    public void setKategoriList(List<Kategori> kategoriList) {
        this.kategoriList = kategoriList;
    }

    public List<Makanan> getMakananList() {
        return makananList;
    }

    public void setMakananList(List<Makanan> makananList) {
        this.makananList = makananList;
    }

    public List<Makanan> getBannerList() {
        return bannerList;
    }

    public void setBannerList(List<Makanan> bannerList) {
        this.bannerList = bannerList;
    }

    public List<TipsTrick> getTipsTrickList() {
        return tipsTrickList;
    }

    public void setTipsTrickList(List<TipsTrick> tipsTrickList) {
        this.tipsTrickList = tipsTrickList;
    }
}
