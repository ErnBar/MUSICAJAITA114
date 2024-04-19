package models;



public class Record_label extends Entity {
    private String name;

    public Record_label(){};

    public Record_label(int id, String name) {
        super(id);
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Record_label: " +super.toString()+
                ", Name='" + name + '\'' +
                "; " ;
    }
}
