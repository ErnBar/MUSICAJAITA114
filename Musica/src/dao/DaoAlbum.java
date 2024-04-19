package dao;
import database.Database;
import interfaces.Factory;
import interfaces.IDao;
import interfaces.IDatabase;
import models.Album;
import models.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DaoAlbum implements IDao {
    private IDatabase database;

    private String queryInsert = "insert into album (artist_id,name,date_release) values (?,?,?)";
    private String queryRead = "select * from album";
    private String queryUpdate = "update album set artist_id=?, name = ?, date_release = ? where id =?";

    private String queryDelete = "delete from album where id =? ";

    private String queryReadOne = "select * from album where id = ?";

    private static IDao instance;

    private DaoAlbum(){
        database=Database.getInstance();
    }
    public synchronized static IDao getInstance(){
        if (instance==null)
            instance=new DaoAlbum();
        return instance;
    }

    @Override
    public void add(Entity e) {
        if (e instanceof Album)
            database.executeUpdate(queryInsert, String.valueOf(((Album) e).getArtist_id()),((Album)e).getName(), String.valueOf(((Album) e).getDate_release()));

    }

    @Override
    public List<Map<String, String>> read(String query, String... params) {
        return null;
    }

    @Override
    public List<Map<String, String>> read() {
        List<Map<String,String>> lista = database.executeQuery(queryRead);

        return lista;
    }

//METODO CHE UTILIZZAVAMO PRIMA  DELLA FACTORY PER LA READALL();
//    public List<Entity> readAll() {
//        List<Entity> listaEntity = new ArrayList<>();
//        for (Map<String, String> map : read()) {
//            String dateString = map.get("date_release");
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            Date date = null;
//            try {
//                date = formatter.parse(dateString);
//            } catch (ParseException e) {
//                System.out.println("Errore durante il parsing della data: " + e.getMessage());
//            }
//            Entity entity = new Album(Integer.parseInt(map.get("id")), Integer.parseInt(map.get("artist_id")),
//                    map.get("name"), date);
//            //oppure java.sql.Date.valueOf(map.get("date_release"))
//            listaEntity.add(entity);
//        }
//        return listaEntity;
//    }
    @Override
    public List<Entity> readAll() {
        List<Entity> listaEntity = new ArrayList<>();
        Entity entity;
        for (Map<String, String> album : read()) {
            album.put("elemento","album");
            entity = Factory.make(album);
            //oppure java.sql.Date.valueOf(map.get("date_release"))
            listaEntity.add(entity);
        }
        return listaEntity;
    }

    @Override
    public void update(Entity e) {
        if (e instanceof Album){
            Album a = (Album)e;
            database.executeUpdate(queryUpdate, String.valueOf(a.getArtist_id()),a.getName(), String.valueOf(((Album) e).getDate_release()));
        }
    }

    @Override
    public void update(int id, String proprieta, String valoreNuovo) {
        String queryUpdateValore = "update album set " + proprieta + " =? where id=?";
        database.executeUpdate(queryUpdateValore,valoreNuovo, String.valueOf(id));
    }

    @Override
    public void delete(int id) {
        database.executeUpdate(queryDelete, String.valueOf(id));
    }


//    public Entity cercaPerId(int id) {
//        List<Map<String,String>> maps = database.executeQuery(queryReadOne,String.valueOf(id));
//        Map<String,String> album = maps.get(0);
//        id = Integer.parseInt(album.get("id"));
//        int idArtist = Integer.parseInt(album.get("artist_id"));
//        String name = album.get("name");
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//        String date = album.get("date_release");
//        Date date_release = null;
//        try {
//            date_release = formatter.parse(date);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        Entity e = new Album(id,idArtist,name,date_release);
//OPPURE java.sql.Date.valueOf(map.get("date_release")) al posto del try e del catch
//        return e;
//    }
    @Override
    public Entity cercaPerId(int id) {
        Entity entity;
        List<Map<String,String>> maps = database.executeQuery(queryReadOne,String.valueOf(id));
        Map<String,String> album = maps.get(0);
        album.put("elemento","album");
        entity = Factory.make(album);
        return entity;
    }

    @Override
    public Entity cercaPerNome(String nome) {
        Entity ris = new Album();
        for(Entity e : readAll()){
            if (((Album)e).getName().equalsIgnoreCase(nome))
                ris = ((Album)e);
        }
        return ris;
    }

}
