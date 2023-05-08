package edu.huflit.app_do_an_vat.Model;

public class Food {
    private String food_url, food_name, foodDescribe, food_rate, food_type;
    private int food_price, food_id;

    public Food(int food_id, String food_url, String food_name, String foodDescribe, String food_rate, String food_type, int food_price) {
        this.food_id = food_id;
        this.food_name = food_name;
        this.foodDescribe = foodDescribe;
        this.food_type = food_type;
        this.food_price = food_price;
        this.food_url = food_url;
        this.food_rate = food_rate;
    }

    public String getFood_url() {
        return food_url;
    }

    public void setFood_url(String food_url) {
        this.food_url = food_url;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public String getFoodDescribe() {
        return foodDescribe;
    }

    public void setFoodDescribe(String foodDescribe) {
        this.foodDescribe = foodDescribe;
    }

    public String getFood_rate() {
        return food_rate;
    }

    public void setFood_rate(String food_rate) {
        this.food_rate = food_rate;
    }

    public String getFood_type() {
        return food_type;
    }

    public void setFood_type(String food_type) {
        this.food_type = food_type;
    }

    public int getFood_price() {
        return food_price;
    }

    public void setFood_price(int food_price) {
        this.food_price = food_price;
    }

    public int getFood_id() {
        return food_id;
    }

    public void setFood_id(int food_id) {
        this.food_id = food_id;
    }
}