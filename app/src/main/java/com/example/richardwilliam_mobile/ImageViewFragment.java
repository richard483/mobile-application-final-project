package com.example.richardwilliam_mobile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ImageViewFragment extends Fragment {

    ImageView viewImage;
    TextView desContent;
    Button backButt;

    View.OnClickListener goBack = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getParentFragmentManager().popBackStack();
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference refGal1 = database.getReference("gallery1");

        viewImage = view.findViewById(R.id.view_image);
        backButt = view.findViewById(R.id.view_back);
        desContent = view.findViewById(R.id.desc_content_tv);

        refGal1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Picture picture = snapshot.getValue(Picture.class);
                Glide.with(requireActivity()).load(picture.getImages()).into(viewImage);
                desContent.setText(picture.getDesc());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });

        backButt.setOnClickListener(goBack);
    }
}