package com.example.richardwilliam_mobile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class HomeFragment extends Fragment {

    Button profileButton;
    ImageView img1, img2, img3;
    View.OnClickListener panggang = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getParentFragmentManager().beginTransaction().replace(R.id.main_frame_container, new ImageViewFragment() )
                    .addToBackStack(null).commit();

        }
    };

    View.OnClickListener toProfile = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent moreProfile = new Intent(requireActivity(), ProfileActivity.class);
            startActivity(moreProfile);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        img1 = view.findViewById(R.id.iv1);

        profileButton = view.findViewById(R.id.profile_button);



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refGal1 = database.getReference("gallery1");

        refGal1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picture picture = snapshot.getValue(Picture.class);
                Glide.with(requireActivity()).load(picture.getImages()).into(img1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });


        profileButton.setOnClickListener(toProfile);

        img1.setOnClickListener(panggang);

    }
}