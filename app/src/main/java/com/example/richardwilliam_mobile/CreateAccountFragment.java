package com.example.richardwilliam_mobile;

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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class CreateAccountFragment extends Fragment {

    EditText nameIn, emailIn, passwordIn, repasswordIn;
    Button createButton;
    String emailString, passwordString, nameString, repasswordString;
    String alert = "";
    private FirebaseAuth auth;
    View.OnClickListener signup = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            emailString = emailIn.getText().toString();
            passwordString = passwordIn.getText().toString();
            repasswordString = repasswordIn.getText().toString();
            nameString = nameIn.getText().toString();
            if(validator(nameString, passwordString, repasswordString, emailString)){
                auth.createUserWithEmailAndPassword(emailString, passwordString)
                        .addOnCompleteListener(requireActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    UserProfileChangeRequest insertName = new UserProfileChangeRequest.Builder().setDisplayName(nameString).build();

                                    task.getResult().getUser().updateProfile(insertName);
                                    Toast.makeText(requireActivity(), "Register Success, returning to Login Page", Toast.LENGTH_SHORT).show();
                                    getParentFragmentManager().popBackStack();


                                }else{
                                    Toast.makeText(requireActivity(), "Register unsuccessful Error exception:\n" + task.getException(), Toast.LENGTH_SHORT).show();
                                    //Disini saya mengalami kendala saat ingin melakukan register ke firebase
                                    //dan ketika saya memanggil fungsi task.getException(), saya mendapatkan pemberitahuan eror firebase api key not valid
                                    //saya tidak tahu penyebabnya, tapi masalah ini selesai dengan mengubah versi android services di build gradle
                                    //dari com.google.gms:google-services:4.3.10 menjadi com.google.gms:google-services:4.3.0
                                }
                            }
                        });
            }else{
                Toast.makeText(requireActivity(), alert, Toast.LENGTH_SHORT).show();
                alert = "";
            }



        }
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_account, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        nameIn = view.findViewById(R.id.signup_name_et);
        emailIn = view.findViewById(R.id.signup_email_et);
        passwordIn = view.findViewById(R.id.signup_password_et);
        repasswordIn = view.findViewById(R.id.signup_reenter_password_et);

        auth = FirebaseAuth.getInstance();
        createButton = view.findViewById(R.id.create_account_button);

        createButton.setOnClickListener(signup);


    }

    public boolean validator(String name, String password, String repassword, String email){
        if(!name.isEmpty()){
            if(name.length()>=5){
                if(!email.isEmpty()){
                    if(email.contains("@")){
                        if(email.endsWith(".com")){
                            if((!password.isEmpty())&&(!repassword.isEmpty())){
                                if(password.equals(repassword)){
                                    return true;
                                }else {
                                    alert += "Password and Reenter password must be same\n";
                                }
                            }else {
                                alert+="Password and Re-enter password cannot be empty!\n";
                            }
                        }else{
                            alert += "Email must ends with '.com'\n";
                        }
                    }else{
                        alert += "Email must contain '@' character\n";
                    }
                }else{
                    alert+="Email cannot be empty!\n";
                }
            }else {
                alert += "Name must be at least 5 characters\n";
            }
        }else {
            alert+="Name cannot be empty!\n";
        }

        return  false;
    }
}