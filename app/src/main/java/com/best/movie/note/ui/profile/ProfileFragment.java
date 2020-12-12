package com.best.movie.note.ui.profile;

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
import com.best.movie.note.databinding.FragmentProfileBinding;
import com.best.movie.note.model.User;
import com.best.movie.note.ui.movies.MoviesFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileFragment extends Fragment {

    private NavController navController;
    private ProfileViewModel notesViewModel;
    private FragmentProfileBinding binding;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference usersDatabaseReferences;

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
//        usersDatabaseReferences = database.getReference().child("users");
//
        if (mAuth.getCurrentUser() != null) {
            binding.logInTextView.setText("Logout");
            binding.topTextView.setText(mAuth.getCurrentUser().getEmail());
        } else {
            binding.logInTextView.setText("Sign in");
            binding.topTextView.setText("Log In to your profile to have access to MOVINOTES on all your devices");
        }
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
////        updateUI(currentUser);
//    }

    public class ProfileFragmentButtonsHandler {
        Bundle bundle = new Bundle();

        public void logInButton(View view) {
            FirebaseAuth.getInstance().signOut();
            binding.logInTextView.setText("Sign in");
//            bundle.putString("what_open", getString(R.string.upcoming));
            navController.navigate(R.id.action_navigation_profile_to_signInFragment);
        }
    }

}