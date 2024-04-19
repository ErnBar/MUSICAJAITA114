package models;

public class Artist extends Entity {

    private int record_label_id;

    private String name;

    public Artist(){};


    public Artist(int id, int record_label_id, String name) {
        super(id);
        this.record_label_id = record_label_id;
        this.name = name;
    }



    public int getRecord_label_id() {
        return record_label_id;
    }

    public void setRecord_label_id(int record_label_id) {
        this.record_label_id = record_label_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Artist: " + super.toString()+
                ", Record_label_id=" + record_label_id +
                ", Name='" + name + '\'' +
                "; " ;
    }
}
