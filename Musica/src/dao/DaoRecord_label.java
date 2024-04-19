package dao;
import database.Database;
import interfaces.Factory;
import interfaces.IDao;
import interfaces.IDatabase;
import models.Entity;
import models.Record_label;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DaoRecord_label implements IDao {

    private IDatabase database;

    private static IDao instance;

    private DaoRecord_label(){
        database=Database.getInstance();
    }

    public synchronized static IDao getInstance(){
        if (instance==null)
            instance=new DaoRecord_label();
        return instance;
    }

    private String queryInsert="insert into Record_label(name) values(?);";
    private String queryUpdate="update Record_label set name= ? where id= ?;";

    private String queryDelete="delete from Record_label where id= ?;";

    private String queryRead="select* from Record_label;";

    private String queryReadOne="select* from Record_label where id= ?;";

    private String queryCercaPerNome="select* from Record_label where name= ?;";



    @Override
    public void add(Entity e) {
        if (e instanceof Record_label){
            database.executeUpdate(queryInsert,((Record_label)e).getName());
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
        for(Map<String,String> record_label : read()){
            record_label.put("elemento", "record_label");
            entity = Factory.make(record_label);
            ris.add(entity);
        }
        return ris;
    }

    @Override
    public void update(Entity e) {
        if (e instanceof Record_label){
            Record_label r = (Record_label) e;
            database.executeUpdate(queryUpdate, r.getName(), r.getId()+"");
        }
    }

    @Override
    public void update(int id, String colonna, String valoreNuovo) {
        String queryUpdateValore = "update record_label set " + colonna + " = ? where id=?;";
        database.executeUpdate(queryUpdateValore,valoreNuovo, String.valueOf(id));
    }

    @Override
    public void delete(int id) {
        database.executeUpdate(queryDelete,String.valueOf(id));
    }

    @Override
    public Entity cercaPerId(int id) {
        Entity entity;
        List<Map<String,String>>maps=database.executeQuery(queryReadOne,String.valueOf(id));
        Map<String, String>record_label=maps.get(0);
        record_label.put("elemento", "record_label");
        entity = Factory.make(record_label);
        return entity;
    }

    @Override
    public Entity cercaPerNome(String nome) {
        List<Map<String,String>>maps=database.executeQuery(queryCercaPerNome,(nome));
        Map<String, String>record_label=maps.get(0);
        int idRecord_label= Integer.parseInt(record_label.get("id"));
        Entity e=new Record_label(idRecord_label,record_label.get("name"));
        return e;
    }




    //CONSEGNA NUMERO 2 -- Which record labels have no artists?
    public List<Map<String,String>> readNoArtist() {
        String queryReadArtistNull= "select rl.name from musica.record_label rl left join artist a on a.record_label_id=rl.id where a.id is null";
        return database.executeQuery(queryReadArtistNull);
    }

}
