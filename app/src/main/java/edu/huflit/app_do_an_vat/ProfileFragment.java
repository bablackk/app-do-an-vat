package edu.huflit.app_do_an_vat;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ProfileFragment extends Fragment {
Button mbtnSignout, mbtnEdit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mbtnSignout = view.findViewById(R.id.btnSignOut);
        mbtnEdit = view.findViewById(R.id.btnEditProfile);
        mbtnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mở ra trang edit profile
                Fragment editProfileFragment = new EditProfileFragment();
                FragmentTransaction fragmentEditProfile = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentEditProfile.replace(R.id.layout_profile, editProfileFragment).addToBackStack(null).commit();

            }
        });
        return view;

    }
}