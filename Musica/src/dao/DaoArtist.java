package dao;
import database.Database;
import interfaces.Factory;
import interfaces.IDao;
import interfaces.IDatabase;
import models.Artist;
import models.Entity;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DaoArtist implements IDao {
    private IDatabase database;
    private static IDao instance;

    private DaoArtist(){
        database=Database.getInstance();
    }

    public synchronized static IDao getInstance(){
        if (instance==null)
            instance=new DaoArtist();
        return instance;
    }




    @Override
    public void add(Entity e) {
        String queryInsert = "insert into artist (record_label_id,name) values (?,?);";
        if(e instanceof Artist){
            String record=((Artist)e).getRecord_label_id()+"";
            String name=((Artist)e).getName();
            database.executeUpdate(queryInsert,record,name);
        }

    }

    @Override
    public List<Map<String, String>> read(String query, String... params) {
        return database.executeQuery(query);
    }

    @Override
    public List<Map<String, String>> read() {
        String queryRead = "select * from artist";
        List<Map<String,String>> lista = database.executeQuery(queryRead);
        return lista;
    }

    @Override
    public List<Entity> readAll() {
        List<Entity> ris = new ArrayList<>();
        Entity entity;
        for(Map<String,String> artist : read()){
            artist.put("elemento", "artist");
            entity = Factory.make(artist);
            ris.add(entity);
        }
        return ris;
    }

    @Override
    public void update(Entity e) {
        String queryUpdate = "update artist set record_label_id = ?, name = ? where id = ?";

        if(e instanceof Artist) {
            Artist a = (Artist) e;
            database.executeUpdate(queryUpdate, String.valueOf(a.getRecord_label_id()), a.getName(),
                    String.valueOf(e.getId()) );
        }

    }

    @Override
    public void update(int id, String proprieta, String valoreNuovo) {
        String queryUpdateValore = "update [tabella] set " + proprieta + " = ? where id = ?";

        queryUpdateValore = queryUpdateValore.replace("[tabella]","artist");
        database.executeUpdate(queryUpdateValore,valoreNuovo,id+"");

    }

    @Override
    public void delete(int id) {
        String queryDelete = "delete from artist where id = ?";
        database.executeUpdate(queryDelete,String.valueOf(id));

    }

    @Override
    public Entity cercaPerId(int id) {
        String queryReadOne = "select * from Artist where id = ?";
        Entity entity;
        List<Map<String,String>> maps = database.executeQuery(queryReadOne,String.valueOf(id));
        Map<String,String> artist = maps.get(0);
        artist.put("elemento", "artist");
        entity = Factory.make(artist);
        return entity;
    }

    @Override
    public Entity cercaPerNome(String nome) {
        Entity ris = new Artist();
        for(Entity e : readAll()){
            if (((Artist)e).getName().equalsIgnoreCase(nome))
                ris = ((Artist)e);

        }
        return ris;
    }
    //CONSEGNA NUMERO 3 -- List the number of songs per artist in descending order
    public List<Map<String,String>> readNCanzoni(){
        String queryNCanzoni=" select a.name, count(s.album_id) nCanzoni from artist a join album album on album.artist_id=a.id join song s on album.id=s.album_id group by a.name order by nCanzoni desc;";
        return database.executeQuery(queryNCanzoni);
    }
    //CONSEGNA NUMERO 4 -- Which artist or artists have recorded the most number of songs?
    public List<Map<String,String>> readArtistaMaxSong(){
        String queryNCanzoni=" select a.name, count(s.album_id) nCanzoni from artist a join album album on album.artist_id=a.id join song s on album.id=s.album_id group by a.name order by nCanzoni desc limit 1;";
        return database.executeQuery(queryNCanzoni);
    }
    //CONSEGNA NUMERO 1 -- List all artists for each record label sorted by artist name
    public List<Map<String,String>>readArtistaCasaDiscografica(){
        String query="SELECT artist.name as NomeArtista, record_label.name as NomeCasa\n" +
                "FROM artist \n" +
                "JOIN record_label ON record_label.id = artist.record_label_id\n" +
                "ORDER BY  artist.name;";
        return database.executeQuery(query);
    }

    //Metodo che crea la view per dei select che mi serviranno successivamente.
    private void creaView(){
        String query="create view viewArtisti as\n" +
                "SELECT a.name AS artist_name, COUNT(s.id) AS num_songs_recorded\n" +
                "FROM Artist a\n" +
                "LEFT JOIN Album al ON a.id = al.artist_id\n" +
                "LEFT JOIN Song s ON al.id = s.album_id\n" +
                "GROUP BY a.id\n" +
                "HAVING num_songs_recorded = (\n" +
                "    SELECT MIN(song_count)\n" +
                "    FROM (\n" +
                "        SELECT COUNT(s.id) AS song_count\n" +
                "        FROM Artist ar\n" +
                "        LEFT JOIN Album al ON ar.id = al.artist_id\n" +
                "        LEFT JOIN Song s ON al.id = s.album_id\n" +
                "        GROUP BY ar.id\n" +
                "    ) AS song_counts\n" +
                ");";
        database.executeUpdate(query);
    }
    //CONSEGNA 5 USANDO LA VIEW -- Which artist or artists have recorded the least number of songs?
    public List<Map<String,String>> readMinNumberSongArtist(){
        //Prima di eseguire la query, controlla se la view esiste già nel database tramite il check()
        //Se non esiste la crea.
        if (!database.checktable("viewArtisti")) {
            creaView();
            System.out.println("Tabella creata");
        }
        String query = "select * from viewArtisti";
        List<Map<String, String>> lista = database.executeQuery(query);
        return lista;
    }
    //CONSEGNA NUMERO 6 USANDO LA VIEW -- How many artists have recorded the least number of songs? Hint: we can wrap the
    //-- results of query 5. with another select to give us total artist count.
    public List<Map<String,String>>numeroArtistiMinCanzoni(){
        if (!database.checktable("viewArtisti")) {
            creaView();
            System.out.println("Tabella creata");
        }
        String query="select count(artist_name) from viewArtisti;";
        return database.executeQuery(query);
    }
    //CONSEGNA NUMERO 7 -- For each artist and album how many songs were less than 5 minutes long?
    public List<Map<String,String>>numeroCanzoniMeno5Min(){
        String query="select a.name,count(s.id)\n" +
                "from artist a\n" +
                "inner join album al on a.id=al.artist_id\n" +
                "inner join song s on al.id=s.album_id\n" +
                "where s.duration>5\n" +
                "group by a.name\n" +
                "order by a.name;";
        return database.executeQuery(query);

    }
    //CONSEGNA NUMERO 8 -- Display a table of all artists, albums, songs and song duration, all ordered in ascending
    //-- order by artist, album and song
    public List<Map<String,String>>tabellaArtistiAlbumCanzoniEDurataCanzoni(){
        String query="SELECT artist.Name AS\n" +
                "artist, album.name AS\n" +
                "album, song.Name AS\n" +
                "song, song.duration AS\n" +
                "duration\n" +
                "FROM artist\n" +
                "JOIN album ON artist.Id = album.artist_id\n" +
                "JOIN song ON album.id = song.album_id\n" +
                "ORDER BY Artist.Name, album.Name, song.Name;";
        return database.executeQuery(query);
    }

}
