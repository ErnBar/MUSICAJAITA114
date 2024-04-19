package services;
import dao.DaoAlbum;
import dao.DaoArtist;
import dao.DaoRecord_label;
import dao.DaoSong;
import interfaces.IDao;
import interfaces.IServiceMusic;
import models.Entity;
import java.util.List;
import java.util.Map;

public class ServiceMusic implements IServiceMusic {

    private IDao daoRecord_Label;
    private IDao daoArtist;
    private IDao daoAlbum;
    private IDao daoSong;

    public ServiceMusic() {
        daoRecord_Label=DaoRecord_label.getInstance();
        daoArtist=DaoArtist.getInstance();
        daoAlbum=DaoAlbum.getInstance();
        daoSong=DaoSong.getInstance();
    }
    @Override
    public List<Entity> record_label() {
        return daoRecord_Label.readAll();
    }

    @Override
    public List<Entity> album() {
        return daoAlbum.readAll();
    }

    @Override
    public List<Entity> artist() {
        return daoArtist.readAll();
    }

    @Override
    public List<Entity> song() {
        return daoSong.readAll();
    }

    @Override
    public Entity cercaPerId(int id,String nomeTabella) {
        Entity entity = null;
        switch (nomeTabella.toLowerCase()){
            case "record_label":
                 entity=daoRecord_Label.cercaPerId(id);
                 break;
            case "artist":
                 entity=daoArtist.cercaPerId(id);
                 break;
            case "album":
                entity=daoAlbum.cercaPerId(id);
                break;
            case "song":
                entity=daoSong.cercaPerId(id);
            default:;
        }
        return entity;
    }

    @Override
    public Entity cercaPerNome(String nome,String nomeTabella) {
        Entity entity = null;
        switch (nomeTabella.toLowerCase()){
            case "record_label":
                entity=daoRecord_Label.cercaPerNome(nome);
                break;
            case "artist":
                entity=daoArtist.cercaPerNome(nome);
                break;
            case "album":
                entity=daoAlbum.cercaPerNome(nome);
                break;
            case "song":
                entity=daoSong.cercaPerNome(nome);
                break;
            default:;
        }
        return entity;
    }

    @Override
    public void addRecord_Label(Entity e) {
        daoRecord_Label.add(e);
    }

    @Override
    public void eliminaRecord_label(int id) {
        daoRecord_Label.delete(id);
    }

    @Override
    public void aggiornaRecord_label(int id, String colonna, String valoreNuovo) {
        daoRecord_Label.update(id,colonna,valoreNuovo);
    }


    @Override
    public void addAlbum(Entity e) {
        daoAlbum.add(e);
    }

    @Override
    public void eliminaAlbum(int id) {
        daoAlbum.delete(id);
    }

    @Override
    public void aggiornaAlbum(int id, String colonna, String valoreNuovo) {
        daoAlbum.update(id,colonna,valoreNuovo);
    }

    @Override
    public void addArtist(Entity e) {
        daoArtist.add(e);
    }

    @Override
    public void eliminaArtist(int id) {
        daoArtist.delete(id);
    }

    @Override
    public void aggiornaArtist(int id, String colonna, String valoreNuovo) {
        daoArtist.update(id,colonna,valoreNuovo);
    }

    @Override
    public void addSong(Entity e) {
        daoSong.add(e);
    }

    @Override
    public void eliminaSong(int id) {
        daoSong.delete(id);
    }

    @Override
    public void aggiornaSong(int id, String colonna, String valoreNuovo) {
        daoSong.update(id,colonna,valoreNuovo);
    }

    @Override
    public List<Map<String, String>> readNoArtist() {
        return ((DaoRecord_label)daoRecord_Label).readNoArtist();
    }

    @Override
    public List<Map<String, String>> readNCanzoni() {
        return  ((DaoArtist)daoArtist).readNCanzoni();
    }

    @Override
    public List<Map<String, String>> readArtistaMaxSong() {
        return ((DaoArtist)daoArtist).readArtistaMaxSong();
    }

    public List<Map<String,String>>readArtistaCasaDiscografica(){
         return ((DaoArtist)daoArtist).readArtistaCasaDiscografica();
    }

    @Override
    public List<Map<String, String>> readArtistaMinCanzoni() {
        return ((DaoArtist)daoArtist).readMinNumberSongArtist();
    }

    @Override
    public List<Map<String, String>> numeroArtistiMinCanzoni() {
        return ((DaoArtist)daoArtist).numeroArtistiMinCanzoni();
    }

    @Override
    public List<Map<String, String>> numeroCanzoniMeno5Min() {
        return ((DaoArtist)daoArtist).numeroCanzoniMeno5Min();
    }

    @Override
    public List<Map<String, String>> tabellaArtistiAlbumCanzoniEDurataCanzoni() {
        return ((DaoArtist)daoArtist).tabellaArtistiAlbumCanzoniEDurataCanzoni();
    }


}
