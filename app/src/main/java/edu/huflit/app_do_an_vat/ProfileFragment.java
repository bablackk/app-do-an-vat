package edu.huflit.app_do_an_vat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Objects;

import edu.huflit.app_do_an_vat.API.APIService;
import edu.huflit.app_do_an_vat.API.modelAPI.GetProfileResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
Button mbtnSignout, mbtnEdit;
TextView mtvFullname, mtvEmail, mtvPhone, mtvQuan, mtvCity, mtvAddress;
SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = getActivity().getSharedPreferences("data", getActivity().MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        mbtnSignout = view.findViewById(R.id.btnSignOut);
        mbtnEdit = view.findViewById(R.id.btnEditProfile);
        mtvFullname = view.findViewById(R.id.tvName);
        mtvEmail = view.findViewById(R.id.tvEmail);
        mtvPhone = view.findViewById(R.id.tvPhone);
        mtvQuan = view.findViewById(R.id.tvQuan);
        mtvCity = view.findViewById(R.id.tvCity);
        mtvAddress = view.findViewById(R.id.tvAddress);
        CallAPIGetProfile(token);
        mbtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mở ra trang edit profile
                Fragment editProfileFragment = new EditProfileFragment();
                FragmentTransaction fragmentEditProfile = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentEditProfile.replace(R.id.layout_profile, editProfileFragment).addToBackStack(null).commit();

            }
        });
        mbtnSignout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("token", "");
                editor.apply();
                Intent i = new Intent(getActivity(), IntroActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;

    }
    public void CallAPIGetProfile(String token) {
        if(Objects.equals(token, "")) {
            Toast.makeText(getActivity(), "Người dùng không tồn tại", Toast.LENGTH_SHORT).show();
        }
        APIService.apiService.getProfile("Bearer " + token).enqueue(new Callback<GetProfileResponse>() {
            @Override
            public void onResponse(Call<GetProfileResponse> call, Response<GetProfileResponse> response) {
                if(response.code() !=200) {
                    Toast.makeText(getActivity(), "Lỗi kết nối", Toast.LENGTH_SHORT).show();
                    return;
                }
                GetProfileResponse getProfileResponse = response.body();
                initSetText(getProfileResponse.getFullname(), getProfileResponse.getEmail(), getProfileResponse.getPhone(), getProfileResponse.getDistrict(), getProfileResponse.getCity(), getProfileResponse.getRoad());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("fullname", getProfileResponse.getFullname());
                editor.putString("phone", getProfileResponse.getPhone());
                editor.putString("quan", getProfileResponse.getDistrict());
                editor.putString("city", getProfileResponse.getCity());
                editor.putString("address", getProfileResponse.getRoad());
                editor.apply();
            }

            @Override
            public void onFailure(Call<GetProfileResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void initSetText(String fullname, String email, String phone, String quan, String city, String address) {
        mtvFullname.setText(fullname);
        mtvEmail.setText(email);
        mtvPhone.setText(phone);
        mtvQuan.setText(quan);
        mtvCity.setText(city);
        mtvAddress.setText(address);
    }
}