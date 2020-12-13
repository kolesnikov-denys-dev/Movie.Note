package com.best.movie.note.ui.notes;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.adapters.NotesAdapter;
import com.best.movie.note.databinding.FragmentNotesBinding;
import com.best.movie.note.ui.note.Note;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.best.movie.note.utils.Constants.KEY_CONTENT_ID;
import static com.best.movie.note.utils.Constants.KEY_CONTENT_TYPE;
import static com.best.movie.note.utils.Constants.KEY_ORIGINAL_NAME;

public class NotesFragment extends Fragment implements NotesAdapter.OnClickListener {

    private FragmentNotesBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference notesDatabaseReferences;
    private FirebaseDatabase database;
    private ChildEventListener notesChildEventListener;
    private ArrayList<Note> notesList;
    private RecyclerView notesRecyclerView;
    private NotesAdapter notesAdapter;

    private NavController navController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_notes, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        notesDatabaseReferences = database.getReference().child("notes");
        attachNotesDatabaseReferenceListener();

        notesList = new ArrayList<>();
        fillTvShowsSeasonsRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
        attachNotesDatabaseReferenceListener();

    }

    private void attachNotesDatabaseReferenceListener() {
        if (notesChildEventListener == null) {
            notesChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Note note = snapshot.getValue(Note.class);
                    if (note.getIdUser().equals(mAuth.getUid())) {
//                        currentNote = note;
//                        currentNoteId = snapshot.getKey();
                        notesList.add(note);
                        notesAdapter.setList(notesList);
                        notesAdapter.notifyDataSetChanged();
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

    private void fillTvShowsSeasonsRecyclerView() {
        notesRecyclerView = binding.notesRecyclerView;
        notesAdapter = new NotesAdapter();
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        notesRecyclerView.setAdapter(notesAdapter);
        notesAdapter.setOnClickListener(this);
        notesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCastClick(int castId, String originalName, int contentType) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_CONTENT_ID, castId);
        bundle.putInt(KEY_CONTENT_TYPE, contentType);
        bundle.putString(KEY_ORIGINAL_NAME, originalName);
        navController.navigate(R.id.action_navigation_notes_to_mainMovieFragment, bundle);
    }

}