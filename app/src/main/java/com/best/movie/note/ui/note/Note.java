package com.best.movie.note.ui.note;


public class Note {

    private String idUser;
    private long idContent;
    private long contentType;
    private String note;

    public Note(String idUser, long idContent, long contentType, String note) {
        this.idUser = idUser;
        this.idContent = idContent;
        this.contentType = contentType;
        this.note = note;
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

    @Override
    public String toString() {
        return "Note{" +
                "idUser='" + idUser + '\'' +
                ", idContent=" + idContent +
                ", contentType=" + contentType +
                ", note='" + note + '\'' +
                '}';
    }
}
