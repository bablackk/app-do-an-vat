package edu.huflit.app_do_an_vat.API.modelAPI;

public class LoginResponse {
    String message, email, phone, token;
    public LoginResponse(String message, String email, String phone, String token){
        this.message = message;
        this.email = email;
        this.phone = phone;
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
