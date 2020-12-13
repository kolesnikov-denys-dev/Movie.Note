package com.best.movie.note.ui.note;


import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import static com.best.movie.note.utils.Utils.setImage;

public class Note {

    private String idUser;
    private long idContent;
    private long contentType;
    private String note;
    private String name;
    private String genres;
    private String posterPath;

    @BindingAdapter({"posterPath"})
    public static void loadImage(ImageView imageView, String imageUrl) {
        setImage(imageView, imageUrl);
    }

    public Note(String idUser, long idContent, long contentType, String note, String name, String genres, String posterPath) {
        this.idUser = idUser;
        this.idContent = idContent;
        this.contentType = contentType;
        this.note = note;
        this.name = name;
        this.genres = genres;
        this.posterPath = posterPath;
    }

    public Note() {
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public long getIdContent() {
        return idContent;
    }

    public void setIdContent(long idContent) {
        this.idContent = idContent;
    }

    public long getContentType() {
        return contentType;
    }

    public void setContentType(long contentType) {
        this.contentType = contentType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
