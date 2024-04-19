package models;


public class Song extends Entity {
    private int album_id;
    private String name;
    private double duration;
    public Song(){};

    public Song(int id, int album_id, String name, double duration) {
        super(id);
        this.album_id = album_id;
        this.name = name;
        this.duration = duration;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "Song: " + super.toString()+
                ", Album_id=" + album_id +
                ", Name='" + name + '\'' +
                ", Duration=" + duration +
                "; " ;
    }
}
