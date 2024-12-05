package com.best.movie.note.ui.fragments.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.best.movie.note.R;
import com.best.movie.note.databinding.FragmentProfileBinding;
import com.best.movie.note.ui.fragments.note.Note;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {
    private NavController navController;
    private ProfileViewModel notesViewModel;
    private FragmentProfileBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private GoogleSignInClient mGoogleSignInClient;
    private DatabaseReference usersDatabaseReferences;
    private DatabaseReference notesDatabaseReferences;
    private ChildEventListener notesChildEventListener;
    private int notesCount = 0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        binding.setButtonHandler(new ProfileFragment.ProfileFragmentButtonsHandler());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        notesDatabaseReferences = database.getReference().child("notes");
//        usersDatabaseReferences = database.getReference().child("users");

        if (mAuth.getCurrentUser() != null) {
            binding.logInTextView.setText("Logout");
            binding.topTextView.setText(mAuth.getCurrentUser().getEmail());
            attachNotesDatabaseReferenceListener();
        } else {
            binding.logInTextView.setText("Sign in");
            binding.topTextView.setText("Log In to your profile to have access to MOVINOTES on all your devices");
        }
    }

    public void logOutGoogle() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(), gso);

        // logout
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }

    private void attachNotesDatabaseReferenceListener() {
        if (notesChildEventListener == null) {
            notesChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Note note = snapshot.getValue(Note.class);
                    if (note.getIdUser().equals(mAuth.getUid())) {
                        notesCount++;
                        binding.notesCountTextView.setText(notesCount + " notes");
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            };
            notesDatabaseReferences.addChildEventListener(notesChildEventListener);
        }
    }

    public class ProfileFragmentButtonsHandler {
        Bundle bundle = new Bundle();
        public void logInButton(View view) {
            logOutGoogle();
            FirebaseAuth.getInstance().signOut();
            binding.logInTextView.setText("Sign in");
//            bundle.putString("what_open", getString(R.string.upcoming));
            navController.navigate(R.id.action_navigation_profile_to_signInFragment);
        }
    }
}