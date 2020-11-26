package com.best.movie.note.ui.tmdb;

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

import com.best.movie.note.R;
import com.best.movie.note.databinding.FragmentProfileBinding;
import com.best.movie.note.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    private ProfileViewModel notesViewModel;
    private FragmentProfileBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference usersDatabaseReferences;
    private boolean loginModeActive;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setButtonHandler(new ProfileFragmentButtonsHandler());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersDatabaseReferences = database.getReference().child("users");

        if (mAuth.getCurrentUser() != null) {
            binding.loginSignUpButton.setText("Logout");
        }
    }


    private void loginSignUpUser(String email, String password) {
        if (loginModeActive) {
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
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("check", "signInWithEmail:success");
                                    FirebaseUser user = mAuth.getCurrentUser();
//
//                                    Intent intent = new Intent(SignInActivity.this, UserListActivity.class);
////                                    intent.putExtra("user_name",nameEditText.getText().toString().trim());
//                                    startActivity(intent);
//                                    finish();

//                                updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("check", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
//                                updateUI(null);
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

//                                    Intent intent = new Intent(SignInActivity.this, UserListActivity.class);
////                                    intent.putExtra("user_name",nameEditText.getText().toString().trim());
//                                    startActivity(intent);
//                                    finish();
                                    //   updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("check", "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(getActivity(), "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    // updateUI(null);
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

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }


    public class ProfileFragmentButtonsHandler {

        Bundle bundle = new Bundle();

        public void loginSignUp(View view) {
            loginSignUpUser(binding.emailEditText.getText().toString(), binding.passwordEditText.getText().toString());
        }

        public void toggleLoginMode(View view) {
            if (loginModeActive) {
                loginModeActive = false;
                binding.nameEditText.setVisibility(View.VISIBLE);
                binding.loginSignUpButton.setText("Sign Up");
                binding.toggleLoginSignUpextView.setText("Or, log in");
                binding.repeatPasswordEditText.setVisibility(View.VISIBLE);
            } else {
                loginModeActive = true;
                binding.nameEditText.setVisibility(View.GONE);
                binding.loginSignUpButton.setText("Log In");
                binding.toggleLoginSignUpextView.setText("Or, sign up");
                binding.repeatPasswordEditText.setVisibility(View.GONE);
            }
        }

//        public void upComingSeeAll(View view) {
//            bundle.putString("what_open", getString(R.string.upcoming));
//            navController.navigate(R.id.navigation_movies_list, bundle);
//        }
    }

}