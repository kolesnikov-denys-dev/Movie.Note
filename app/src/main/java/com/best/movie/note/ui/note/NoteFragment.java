package com.best.movie.note.ui.note;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.best.movie.note.R;
import com.best.movie.note.adapters.CommonContentAdapter;
import com.best.movie.note.adapters.NotesAdapter;
import com.best.movie.note.adapters.SeasonsAdapter;
import com.best.movie.note.databinding.NoteFragmentBinding;
import com.best.movie.note.model.response.movies.movie.MovieResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.best.movie.note.utils.Constants.CONTENT_TYPE_MOVIE;
import static com.best.movie.note.utils.Constants.CONTENT_TYPE_TV_SHOW;
import static com.best.movie.note.utils.Constants.KEY_CONTENT_ID;
import static com.best.movie.note.utils.Constants.KEY_CONTENT_TYPE;
import static com.best.movie.note.utils.Constants.KEY_GENRES;
import static com.best.movie.note.utils.Constants.KEY_NAME;
import static com.best.movie.note.utils.Constants.KEY_ORIGINAL_NAME;
import static com.best.movie.note.utils.Constants.KEY_POSTER_PATH;

public class NoteFragment extends Fragment {

    private NoteViewModel mViewModel;
    private int contentId;
    private int contentType;
    private String name;
    private String genres;
    private String posterPath;
    private NoteFragmentBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference notesDatabaseReferences;
    private FirebaseDatabase database;
    private ChildEventListener notesChildEventListener;
    private Note currentNote;
    private String currentNoteId;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.note_fragment, container, false);
        binding.setButtonHandler(new NoteFragmentButtonsHandler());
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            contentId = getArguments().getInt(KEY_CONTENT_ID);
            contentType = getArguments().getInt(KEY_CONTENT_TYPE);
            name = getArguments().getString(KEY_ORIGINAL_NAME);
            genres = getArguments().getString(KEY_GENRES);
            posterPath = getArguments().getString(KEY_POSTER_PATH);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(name);
        }

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        notesDatabaseReferences = database.getReference().child("notes");
        attachNotesDatabaseReferenceListener();
    }

    private void attachNotesDatabaseReferenceListener() {
        if (notesChildEventListener == null) {
            notesChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    Note note = snapshot.getValue(Note.class);
                    if (note.getIdContent() == contentId && note.getIdUser().equals(mAuth.getUid())) {
                        binding.noteEditText.setText(note.getNote(), TextView.BufferType.EDITABLE);
                        currentNote = note;
                        currentNoteId = snapshot.getKey();
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

    public class NoteFragmentButtonsHandler {
        public void saveNoteH(View view) {
            saveNote();
        }
    }

    private void saveNote() {
        Note note = new Note();
        note.setIdContent(contentId);
        note.setContentType(contentType);
        note.setName(name);
        note.setGenres(genres);
        note.setPosterPath(posterPath);
        note.setIdUser(mAuth.getCurrentUser().getUid());
        note.setNote(binding.noteEditText.getText().toString());

        if (currentNote == null) {
            notesDatabaseReferences.push().setValue(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity(), "Note was saved.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Saved error.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            notesDatabaseReferences.child(currentNoteId).setValue(note).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Toast.makeText(getActivity(), "Note was update.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Note update error.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }







}