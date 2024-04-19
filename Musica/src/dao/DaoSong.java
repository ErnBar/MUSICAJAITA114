package dao;

import database.Database;
import interfaces.Factory;
import interfaces.IDao;
import interfaces.IDatabase;
import models.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import models.Song;

public class DaoSong implements IDao {
    private IDatabase database;
    private String queryInserimento="insert into song (name,album_id,duration)" +
            " values(?,?,?)";
    private String queryRead = "select * from Song";
    private String queryReadOne = "select * from Song where id = ?";
    private String queryDelete = "delete from song where id =? ";
    private String queryUpdate = "update song set album_id=?, name = ?, duration = ? where id =?";
    private static IDao instance;

    private DaoSong(){
        database=Database.getInstance();
    }

    public synchronized static IDao getInstance(){
        if (instance==null)
            instance=new DaoSong();
        return instance;
    }


    @Override
    public void add(Entity e) {
        if(e instanceof Song) {
            Song s = (Song) e;
            database.executeUpdate(queryInserimento,String.valueOf(s.getName()),String.valueOf(s.getAlbum_id()),String.valueOf(s.getDuration()));
        }

    }

    @Override
    public List<Map<String, String>> read(String query, String... params) {
        return database.executeQuery(query);
    }

    @Override
    public List<Map<String, String>> read() {
        return database.executeQuery(queryRead);
    }

    @Override
    public List<Entity> readAll() {
        List<Entity> ris = new ArrayList<>();
        Entity entity;
        for(Map<String,String> song : read()){
            song.put("elemento", "song");
            entity = Factory.make(song);
            ris.add(entity);
        }
        return ris;
    }

    @Override
    public void update(Entity e) {
        if (e instanceof Song){
            Song s = (Song)e;
            database.executeUpdate(queryUpdate, String.valueOf(s.getAlbum_id()),s.getName(), String.valueOf(s.getDuration()), s.getId()+"");
        }

    }

    @Override
    public void update(int id, String proprieta, String valoreNuovo) {
        String queryUpdateValore = "update song set " + proprieta + " =? where id=?";
        database.executeUpdate(queryUpdateValore,valoreNuovo, String.valueOf(id));

    }

    @Override
    public void delete(int id) {
        database.executeUpdate(queryDelete, String.valueOf(id));

    }

//    @Override
//    public Entity cercaPerId(int id) {
//        Entity entity;
//        List<Map<String,String>> maps = database.executeQuery(queryReadOne,String.valueOf(id));
//        Map<String,String> song = maps.get(0);
//        id = Integer.parseInt(song.get("id"));
//        int Album_id = Integer.parseInt(song.get("album_id"));
//        String name = song.get("name");
//        double duration = Double.parseDouble(song.get("duration"));
//        Entity e = new Song(id,Album_id,name,duration);
//        return e;
//    }
@Override
    public Entity cercaPerId(int id) {
        Entity entity;
        List<Map<String,String>> maps = database.executeQuery(queryReadOne,String.valueOf(id));
        Map<String,String> song = maps.get(0);
        song.put("elemento", "song");
        entity = Factory.make(song);
    return entity;
    }

    @Override
    public Entity cercaPerNome(String nome) {
        Entity ris = new Song();
        for(Entity e : readAll()){
            if (((Song)e).getName().equalsIgnoreCase(nome))
                ris = ((Song)e);
        }
        return ris;
    }

}
