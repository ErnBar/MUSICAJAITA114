package models;

import interfaces.IMappable;

public abstract class Entity implements IMappable {
    private int id;

    public Entity(){}

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "id=" + id;
    }
}