package interfaces;
import models.Entity;
import java.util.List;
import java.util.Map;

public interface IServiceMusic {

    List<Entity> record_label();
    List<Entity> album();
    List<Entity> artist();

    List<Entity> song();

    Entity cercaPerId(int id,String nomeTabella);

    Entity cercaPerNome(String nome,String nomeTabella);

    void addRecord_Label(Entity e);
    void eliminaRecord_label(int id);
    void aggiornaRecord_label(int id, String colonna, String valoreNuovo);
    void addAlbum(Entity e);
    void eliminaAlbum(int id);
    void aggiornaAlbum(int id, String colonna, String valoreNuovo);
    void addArtist(Entity e);
    void eliminaArtist(int id);
    void aggiornaArtist(int id, String colonna, String valoreNuovo);
    void addSong(Entity e);
    void eliminaSong(int id);
    void aggiornaSong(int id, String colonna, String valoreNuovo);

    List<Map<String, String>> readNoArtist();
    List<Map<String, String>> readNCanzoni();

    List<Map<String,String>> readArtistaMaxSong();

    List<Map<String,String>>readArtistaCasaDiscografica();

    List<Map<String,String>>readArtistaMinCanzoni();

    List<Map<String,String>>numeroArtistiMinCanzoni();

    List<Map<String,String>>numeroCanzoniMeno5Min();

    List<Map<String,String>>tabellaArtistiAlbumCanzoniEDurataCanzoni();


}
