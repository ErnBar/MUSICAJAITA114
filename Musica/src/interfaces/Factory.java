package interfaces;

import models.*;

import java.util.Map;

public interface Factory {//PATTERN CREAZIONALE

    static Entity make(String tipoConcreto){
        Entity entity = null;
        switch (tipoConcreto.toLowerCase()){
            case "artist":
                entity = new Artist();
                break;
            case "song":
                entity = new Song();
                break;
            case "album":
                entity = new Album();
                break;
            case "record_label":
                entity = new Record_label();
                break;
        }
        return entity;
    }
    static Entity make(Map<String,String> map){
        Entity e = null;
        if(map.containsKey("elemento")){
            //se esiste la chiave elemento devo creare un oggetto
            e = make(map.get("elemento"));
            //prendo i valori salvati nella mappa e li passo ai set dell'oggetto appena
            //creato
        }
        map.remove("elemento");
        if(e!=null)
            e.fromMap(map);


        return e;
    }
}