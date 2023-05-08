package edu.huflit.app_do_an_vat.API.modelAPI;

public class EditProfileRequest {
    String fullname,phone, city, district, road;
    public EditProfileRequest(String fullname, String phone, String district, String city, String road){
        this.fullname = fullname;
        this.phone = phone;
        this.district = district;
        this.city = city;
        this.road = road;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }
}
