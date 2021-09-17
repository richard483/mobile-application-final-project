package com.example.richardwilliam_mobile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginFragment extends Fragment {

    Button createAccButton;
    Button loginButton;
    EditText emailIn, passwordIn;
    FirebaseAuth auth;
    SharedPreferences sp;
    String emailString, passwordString;
    View.OnClickListener logintoSignup = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getParentFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, new CreateAccountFragment())
                    .addToBackStack(null).commit();
        }
    };

    View.OnClickListener toHome = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            emailString = emailIn.getText().toString();
            passwordString = passwordIn.getText().toString();
            auth.signInWithEmailAndPassword(emailString, passwordString)
                    .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(requireActivity(), "Login Success, directing to Home", Toast.LENGTH_SHORT).show();

                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("name", task.getResult().getUser().getDisplayName());
                                editor.putString("email", task.getResult().getUser().getEmail());
                                editor.apply();

                                Intent toMain = new Intent(requireActivity(), MainActivity.class);
                                startActivity(toMain);
                            } else {
                                Toast.makeText(requireActivity(), "Register unsuccessful Error exception:\n" + task.getException(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        createAccButton = view.findViewById(R.id.create_account_button);
        loginButton = view.findViewById(R.id.login_button);
        emailIn = view.findViewById(R.id.login_email_et);
        passwordIn = view.findViewById(R.id.login_password_et);
        sp = requireActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);

        auth = FirebaseAuth.getInstance();

        createAccButton.setOnClickListener(logintoSignup);
        loginButton.setOnClickListener(toHome);
    }
}