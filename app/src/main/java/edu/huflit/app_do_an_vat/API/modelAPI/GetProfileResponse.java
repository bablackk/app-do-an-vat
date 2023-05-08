package edu.huflit.app_do_an_vat.API.modelAPI;

public class GetProfileResponse {
    String message, fullname, email, phone, city, district, road;
    public GetProfileResponse(String message, String fullname, String email, String phone, String city, String district, String road) {
        this.message = message;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.road = road;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
