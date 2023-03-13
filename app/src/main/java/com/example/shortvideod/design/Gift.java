package com.example.shortvideod.design;

public class Gift {

    boolean isgeting;
    int price;
    int img;
    int totalsender;
    int coins;
    String name;

    public Gift() {
    }

    public Gift(boolean isgeting, int price, int img, int totalsender, String name, int coins) {
        this.isgeting = isgeting;
        this.price = price;
        this.img = img;
        this.totalsender = totalsender;
        this.name = name;
        this.coins = coins;
    }

    @Override
    public String toString() {
        return "Gift{" +
                "isgeting=" + isgeting +
                ", price=" + price +
                ", img=" + img +
                ", totalsender=" + totalsender +
                ", coins=" + coins +
                ", name='" + name + '\'' +
                '}';
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsgeting() {
        return isgeting;
    }

    public void setIsgeting(boolean isgeting) {
        this.isgeting = isgeting;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getTotalsender() {
        return totalsender;
    }

    public void setTotalsender(int totalsender) {
        this.totalsender = totalsender;
    }


}
