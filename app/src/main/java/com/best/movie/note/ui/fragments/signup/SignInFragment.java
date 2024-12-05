package com.best.movie.note.ui.fragments.signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.best.movie.note.R;
import com.best.movie.note.databinding.FragmentSignInBinding;
import com.best.movie.note.model.User;
import com.best.movie.note.ui.fragments.profile.ProfileViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInFragment extends Fragment {
    private NavController navController;
    private ProfileViewModel notesViewModel;
    private FragmentSignInBinding binding;
    private FirebaseAuth mAuth;
    private boolean loginModeActive;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 12345;
    private FirebaseDatabase database;
    private DatabaseReference usersDatabaseReferences;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in, container, false);
        binding.setButtonHandler(new SignUpFragmentButtonsHandler());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersDatabaseReferences = database.getReference().child("users");
    }

    // GOOGLE Region

    public void createGoogleRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);
    }

    private void signInGoogle() {
        createGoogleRequest();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);


                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            createUser(user);
                            // Sign in success, update UI with the signed-in user's information
                            navController.navigate(R.id.action_signInFragment_to_navigation_profile);

                        } else {
                            Toast.makeText(getActivity(), "Sign in error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // GOOGLE Region

    private void loginSignUpUser(String email, String password) {
        if (!loginModeActive) {
            if (binding.passwordEditText.getText().toString().trim().length() < 7) {
                Toast.makeText(getContext(), "Passwords must be at least 7 characters", Toast.LENGTH_SHORT).show();
            } else if (binding.emailEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getContext(), "Please input your email", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Log.d("check", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    navController.navigate(R.id.action_signInFragment_to_navigation_profile);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("check", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } else {
            if (!binding.passwordEditText.getText().toString().trim().equals(binding.repeatPasswordEditText.getText().toString().trim())) {
                Toast.makeText(getContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
            } else if (binding.passwordEditText.getText().toString().trim().length() < 7) {
                Toast.makeText(getContext(), "Passwords must be at least 7 characters", Toast.LENGTH_SHORT).show();
            } else if (binding.emailEditText.getText().toString().trim().equals("")) {
                Toast.makeText(getContext(), "Please input your email", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("check", "createUserWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    createUser(user);
                                    navController.navigate(R.id.action_signInFragment_to_navigation_profile);
                                } else {
                                    Log.w("check", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        }
    }

    private void createUser(FirebaseUser firebaseUser) {
        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(binding.nameEditText.getText().toString().trim());
        usersDatabaseReferences.push().setValue(user);
    }

    public class SignUpFragmentButtonsHandler {
        Bundle bundle = new Bundle();

        public void loginSignUp(View view) {
            loginSignUpUser(binding.emailEditText.getText().toString(), binding.passwordEditText.getText().toString());
        }

        public void signInGoogleH(View view) {
            signInGoogle();
        }

        public void toggleLoginMode(View view) {
            if (loginModeActive) {
                loginModeActive = false;
                binding.nameEditText.setVisibility(View.GONE);
                binding.loginSignUpButton.setText("Sign IN");
                binding.toggleLoginSignUpextView.setText("SIGN UP");
                binding.repeatPasswordEditText.setVisibility(View.GONE);
            } else {
                loginModeActive = true;
                binding.nameEditText.setVisibility(View.VISIBLE);
                binding.loginSignUpButton.setText("SIGN UP");
                binding.toggleLoginSignUpextView.setText("SIGN IN");
                binding.repeatPasswordEditText.setVisibility(View.VISIBLE);
            }
        }
    }
}