package edu.huflit.app_do_an_vat.Model;

public class Topping {
    private  String topping_url,topping_name;
    private int topping_price,topping_id;

    public Topping(int topping_id,String topping_url, String topping_name, int topping_price) {
        this.topping_url = topping_url;
        this.topping_name = topping_name;
        this.topping_price = topping_price;
        this.topping_id = topping_id;
    }

    public int getTopping_id() {
        return topping_id;
    }

    public void setTopping_id(int topping_id) {
        this.topping_id = topping_id;
    }

    public String getTopping_url() {
        return topping_url;
    }

    public void setTopping_url(String topping_url) {
        this.topping_url = topping_url;
    }

    public String getTopping_name() {
        return topping_name;
    }

    public void setTopping_name(String topping_name) {
        this.topping_name = topping_name;
    }

    public int getTopping_price() {
        return topping_price;
    }

    public void setTopping_price(int topping_price) {
        this.topping_price = topping_price;
    }
}
