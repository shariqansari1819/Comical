package com.codebosses.comical.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codebosses.comical.R;
import com.codebosses.comical.common.Constants;
import com.codebosses.comical.databinding.ActivityLoginBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.endpoints.EndpointUrls;
import com.codebosses.comical.pojo.User;
import com.codebosses.comical.utils.L;
import com.codebosses.comical.utils.PrefUtils;
import com.codebosses.comical.utils.ValidUtils;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 1004;

    //    Android fields....
    private ActivityLoginBinding loginBinding;
    private Context context;
    private SweetAlertDialog sweetAlertDialog;

    //    Firebase fields....
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    //    TODO: Google sign in fields....
    private GoogleSignInClient mGoogleSignInClient;

    //    TODO: Facebook sign in fields....
    private CallbackManager facebookManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        context = this;

//        Android fields initialization....
        sweetAlertDialog = new SweetAlertDialog(context);
        sweetAlertDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("Please wait...");

//        Firebase fields initialization....
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        facebookManager = CallbackManager.Factory.create();

        loginBinding.setClickHandler(new LoginClickHandler());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                L.showToast(context, e.getMessage());
            }
        } else {
            facebookManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    //    TODO: Method to sign in with google using firebase....
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        sweetAlertDialog.show();
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            if (currentUser != null) {
                                final String email, name, userId;
                                String originalPieceOfUrl = "s96-c/photo.jpg";
                                String newPieceOfUrlToAdd = "s400-c/photo.jpg";
                                String profileImage, profileImageThumbnail;
                                userId = currentUser.getUid();
                                email = currentUser.getEmail();
                                name = currentUser.getDisplayName();
                                String phoneNumber = "";
                                profileImage = "";
                                profileImageThumbnail = "";
                                if (currentUser.getPhoneNumber() != null) {
                                    phoneNumber = currentUser.getPhoneNumber();
                                }
                                if (currentUser.getPhotoUrl() != null) {
                                    profileImage = currentUser.getPhotoUrl().toString();
                                    profileImage = profileImage.replace(originalPieceOfUrl, newPieceOfUrlToAdd);
                                    profileImageThumbnail = currentUser.getPhotoUrl().toString();
                                }
                                checkUserAlreadyExist(userId, email, name, phoneNumber, profileImage, profileImageThumbnail, Constants.GMAIL);
                            }
                        } else {
                            sweetAlertDialog.dismiss();
                            if (task.getException() != null) {
                                L.showToast(context, task.getException().getMessage());
                            }
                        }
                    }
                });
    }

    private void checkUserAlreadyExist(String userId, String email, String name, String phoneNumber, String profileImage, String profileImageThumbnail, String loginType) {
        firebaseFirestore.collection(EndpointKeys.USERS)
                .document(userId)
                .get()
                .addOnSuccessListener(this, new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot != null && documentSnapshot.exists()) {
                            User user = documentSnapshot.toObject(User.class);
                            if (user != null) {
                                saveDataToPref(user);
                            }
                        } else {
                            User user = new User(userId, name, email, profileImage, profileImageThumbnail, phoneNumber, loginType);
                            createUserInFirestore(user);
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                sweetAlertDialog.dismiss();
                L.showToast(context, e.getMessage());
            }
        });
    }

    private void createUserInFirestore(User userModel) {
        firebaseFirestore.collection("Users")
                .document(userModel.getUserId())
                .set(userModel)
                .addOnSuccessListener(this, new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        saveDataToPref(userModel);
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                sweetAlertDialog.dismiss();
                L.showToast(context, e.getMessage());
            }
        });
    }

    //    TODO: Method to trigger facebook sign in....
    private void registerCallback() {
        LoginManager loginManager = LoginManager.getInstance();
        List<String> permissionList = new ArrayList<>();
        permissionList.add(EndpointKeys.EMAIL);
        permissionList.add(EndpointKeys.PUBLIC_PROFILE);
        loginManager.logInWithReadPermissions(this, permissionList);
        loginManager.registerCallback(facebookManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {
                if (error != null) {
                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Facebook Error", error.getMessage());
                }
            }
        });
    }

    //    TODO: Method to handle facebook access token using firebase....
    private void handleFacebookAccessToken(AccessToken accessToken) {
        sweetAlertDialog.show();
        final AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                            String facebookUserId = "";
                            if (currentUser != null) {
                                String email;
                                final String name;
                                final String userId;
                                String profileImage = "";
                                String profileImageThumbnail = "";
                                String phoneNumber = "";

                                userId = currentUser.getUid();
                                email = currentUser.getEmail();
                                name = currentUser.getDisplayName();
                                if (email == null) {
                                    email = "";
                                }
                                if (currentUser.getPhoneNumber() != null) {
                                    phoneNumber = currentUser.getPhoneNumber();
                                }

                                if (currentUser.getPhotoUrl() != null) {
                                    profileImageThumbnail = currentUser.getPhotoUrl().toString();
                                    for (UserInfo profile : currentUser.getProviderData()) {
                                        if (FacebookAuthProvider.PROVIDER_ID.equals(profile.getProviderId())) {
                                            facebookUserId = profile.getUid();
                                            profileImage = EndpointUrls.FACEBOOK_GRAPH_BASE_URL + facebookUserId + EndpointUrls.FACEBOOK_GRAPH_QUALITY_URL;
                                        }
                                    }
                                }
                                checkUserAlreadyExist(userId, email, name, phoneNumber, profileImage, profileImageThumbnail, Constants.FACEBOOK);
                            }
                        } else {
                            if (task.getException() != null) {
                                Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                Log.e("Facebook Error", task.getException().getMessage());
                            }
                            sweetAlertDialog.dismiss();
                        }
                    }
                });
    }

    private void validateUser() {
        String email = loginBinding.editTextEmailLogin.getText().toString();
        String password = loginBinding.editTextPasswordLogin.getText().toString();
        if (ValidUtils.validateEditTexts(loginBinding.editTextEmailLogin, loginBinding.editTextPasswordLogin)) {
            if (ValidUtils.validateEmail(loginBinding.editTextEmailLogin)) {
                if (ValidUtils.validatePassword(password)) {
                    loginUser(email, password);
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

    private void loginUser(String email, String password) {
        sweetAlertDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            firebaseFirestore.collection(EndpointKeys.USERS)
                                    .document(task.getResult().getUser().getUid())
                                    .get()
                                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            sweetAlertDialog.dismiss();
                                            if (task.isSuccessful()) {
                                                if (task.getResult().exists()) {
                                                    User user = task.getResult().toObject(User.class);
                                                    if (user != null) {
                                                        saveDataToPref(user);
                                                    }
                                                }
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

    private void saveDataToPref(User user) {
        PrefUtils.saveToPrefs(context, EndpointKeys.USER_NAME, user.getUserName());
        PrefUtils.saveToPrefs(context, EndpointKeys.USER_EMAIL, user.getUserEmail());
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public class LoginClickHandler {

        public void onLoginClick(View view) {
            validateUser();
        }

        public void onFacebookClick(View view) {
            registerCallback();
        }

        public void onGoogleClick(View view) {
            Intent intent = mGoogleSignInClient.getSignInIntent();
            startActivityForResult(intent, RC_SIGN_IN);
        }

        public void onDontHaveAccountClick(View view) {
            startActivity(new Intent(context, SignUpActivity.class));
        }

    }
}