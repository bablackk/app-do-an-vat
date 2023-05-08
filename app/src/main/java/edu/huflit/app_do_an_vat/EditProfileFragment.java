package edu.huflit.app_do_an_vat;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import edu.huflit.app_do_an_vat.API.APIService;
import edu.huflit.app_do_an_vat.API.modelAPI.EditProfileRequest;
import edu.huflit.app_do_an_vat.API.modelAPI.EditProfileResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditProfileFragment extends Fragment {
Button mbtnCancel, mbtnSave;
EditText metfullName, metPhone, metAddress, metCity, metQuan;
SharedPreferences sharedPreferences;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        sharedPreferences = getActivity().getSharedPreferences("data", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String fullname = sharedPreferences.getString("fullname", "");
        String phone = sharedPreferences.getString("phone", "");
        String address = sharedPreferences.getString("address", "");
        String city = sharedPreferences.getString("city", "");
        String quan = sharedPreferences.getString("quan", "");
        metfullName = view.findViewById(R.id.etName);
        metPhone = view.findViewById(R.id.etPhone);
        metAddress = view.findViewById(R.id.etAddress);
        metCity = view.findViewById(R.id.etCity);
        metQuan = view.findViewById(R.id.etQuan);
        metfullName.setText(fullname);
        metPhone.setText(phone);
        metAddress.setText(address);
        metCity.setText(city);
        metQuan.setText(quan);
        mbtnCancel= view.findViewById(R.id.btnCancel);
        mbtnSave = view.findViewById(R.id.btnSave);

        mbtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment infoProfile = new ProfileFragment();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.layout_profile, infoProfile).addToBackStack(null).commit();
            }
        });
        mbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = metfullName.getText().toString();
                String phone = metPhone.getText().toString();
                String address = metAddress.getText().toString();
                String city = metCity.getText().toString();
                String quan = metQuan.getText().toString();
                CallAPIEditProfile(token, fullname, phone, quan, city, address);

                final int timeoutMillis = 300; // thời gian timeout
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Fragment infoProfile = new ProfileFragment();
                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.layout_profile, infoProfile).addToBackStack(null).commit();
                    }
                }, timeoutMillis);
            }
        });
        return view;
    }
    public void CallAPIEditProfile(String token, String fullname, String phone, String quan, String city, String address) {
        if(Objects.equals(token, "")) {
            Toast.makeText(getActivity(), "Người dùng không tồn tại", Toast.LENGTH_SHORT).show();
        }
        EditProfileRequest editProfileRequest = new EditProfileRequest(fullname, phone, quan, city, address);
        APIService.apiService.editProfile("Bearer "+ token, editProfileRequest).enqueue(new Callback<EditProfileResponse>() {
            @Override
            public void onResponse(Call<EditProfileResponse> call, Response<EditProfileResponse> response) {
                if(response.code() !=200) {
                    Toast.makeText(getActivity(), "Không thể cập nhật", Toast.LENGTH_SHORT).show();
                }
                EditProfileResponse editProfileResponse = response.body();
                Toast.makeText(getActivity(), editProfileResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<EditProfileResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Có lỗi xảy ra", Toast.LENGTH_SHORT).show();
            }
        });

    }
}