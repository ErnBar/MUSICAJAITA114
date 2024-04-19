package models;

import java.util.Date;

public class Album extends Entity {

    private  int artist_id;

    private String name;

    private Date date_release;
    public Album(){};

    public Album(int id, int artist_id, String name, Date date_release) {
        super(id);
        this.artist_id = artist_id;
        this.name = name;
        this.date_release = date_release;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate_release() {
        return date_release;
    }

    public void setDate_release(Date date_release) {
        this.date_release = date_release;
    }

    @Override
    public String toString() {
        return "Album:" + super.toString()+
                ", Artista Id:" + artist_id +
                ", Titolo: " + name + '\'' +
                ", Data Rilascio: " + date_release +
                "; " ;
    }
}
