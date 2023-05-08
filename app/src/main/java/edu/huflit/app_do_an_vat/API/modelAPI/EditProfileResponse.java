package edu.huflit.app_do_an_vat.API.modelAPI;

public class EditProfileResponse {
    String message;
    public EditProfileResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
