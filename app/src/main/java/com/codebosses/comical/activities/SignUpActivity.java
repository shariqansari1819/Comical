package com.codebosses.comical.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.codebosses.comical.R;
import com.codebosses.comical.common.Constants;
import com.codebosses.comical.databinding.ActivitySignUpBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.pojo.User;
import com.codebosses.comical.utils.L;
import com.codebosses.comical.utils.PrefUtils;
import com.codebosses.comical.utils.ValidUtils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpActivity extends AppCompatActivity {

    //    Android fields....
    private ActivitySignUpBinding signUpBinding;
    private Context context;
    private SweetAlertDialog sweetAlertDialog;

    //    Firebase fields....
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up);

        context = this;

//        Android fields initialization....
        sweetAlertDialog = new SweetAlertDialog(context);
        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("Creating account...");

//        Firebase fields initialization....
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        signUpBinding.setClickHandler(new SignUpClickHandler());
    }

    private void validateUser() {
        String name = signUpBinding.editTextNameSignUp.getText().toString();
        String email = signUpBinding.editTextEmailSignUp.getText().toString();
        String password = signUpBinding.editTextPasswordSignUp.getText().toString();
        if (ValidUtils.validateEditTexts(signUpBinding.editTextNameSignUp, signUpBinding.editTextEmailSignUp, signUpBinding.editTextPasswordSignUp)) {
            if (ValidUtils.validateEmail(signUpBinding.editTextEmailSignUp)) {
                if (ValidUtils.validatePassword(password)) {
                    createUser(name, email, password);
                } else {
                    L.showToast(context, getResources().getString(R.string.invalid_password));
                }
            } else {
                L.showToast(context, getResources().getString(R.string.invalid_email_address));
            }
        } else {
            L.showToast(context, getResources().getString(R.string.please_fill_all_fields));
        }
    }

    private void createUser(String name, String email, String passsword) {
        sweetAlertDialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email, passsword)
                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User(task.getResult().getUser().getUid(), name, email, "", "", "", Constants.EMAIL);
                            firebaseFirestore.collection(EndpointKeys.USERS)
                                    .document(task.getResult().getUser().getUid())
                                    .set(user)
                                    .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            sweetAlertDialog.dismiss();
                                            if (task.isSuccessful()) {
                                                PrefUtils.saveToPrefs(context, EndpointKeys.USER_NAME, user.getUserName());
                                                PrefUtils.saveToPrefs(context, EndpointKeys.USER_EMAIL, user.getUserEmail());
                                                PrefUtils.saveToPrefs(context, EndpointKeys.USER_PASSWORD, passsword);
                                                Intent intent = new Intent(context, MainActivity.class);
                                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                startActivity(intent);
                                                finish();
                                            } else {
                                                L.showToast(context, task.getException().getMessage());
                                            }
                                        }
                                    });
                        } else {
                            sweetAlertDialog.dismiss();
                            L.showToast(context, task.getException().getMessage());
                        }
                    }
                });
    }

    public class SignUpClickHandler {

        public void onSignUpClick(View view) {
            validateUser();
        }

        public void onBackClick(View view) {
            onBackPressed();
        }

    }
}