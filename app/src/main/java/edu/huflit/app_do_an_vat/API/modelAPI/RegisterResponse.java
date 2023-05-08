package edu.huflit.app_do_an_vat.API.modelAPI;

public class RegisterResponse {
    String message;
    public RegisterResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
