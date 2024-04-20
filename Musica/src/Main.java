import interfaces.IServiceMusic;
import utils.IScanner;
import utils.IStampa;
import models.*;
import services.ServiceMusic;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        IServiceMusic music=new ServiceMusic();

        System.out.println("Benvenuto.");
        String rispostaOperazioni="si";
        do {
            switch (IScanner.genericScanner(Integer.class,"Scegli operazione da eseguire:\n" +
                    "1:Visualizza Tabelle\n" +
                    "2:Ricerca un elemento per ID di una tabella a scelta\n" +
                    "3:Aggiungi elemento in una tabella a scelta\n" +
                    "4:Elimina un record da una tabella a scelta\n" +
                    "5:Aggiorna un record di una tabella a scelta\n" +
                    "6:Elenco artisti per ogni etichetta discografica ordinati per nome dell'artista\n" +
                    "7:Case discografiche senza artisti\n" +
                    "8:Canzoni per artista in ordine descendente\n" +
                    "9:Artista con più canzoni\n" +
                    "10:Artisti con meno canzoni\n" +
                    "11:Numero artisti con meno canzoni.\n" +
                    "12:Numero canzoni per ogni artista con meno di 5 minuti\n" +
                    "13:Tabella di artisti,album,canzoni e durata canzoni\n")){
                case 1:
                    switch (IScanner.genericScanner(Integer.class,"Scegli quale tabella visualizzare:\n" +
                            "1:RECORD_LABEL\n" +
                            "2:ARTIST\n" +
                            "3:ALBUM\n" +
                            "4:SONG")){
                        case 1:
                            System.out.println(IStampa.stampaConEntity(music.record_label()));
                            break;
                        case 2:
                            System.out.println(IStampa.stampaConEntity(music.artist()));
                            break;
                        case 3:
                            System.out.println(IStampa.stampaConEntity(music.album()));
                            break;
                        case 4:
                            System.out.println(IStampa.stampaConEntity(music.song()));
                            break;
                        default:;
                    }
                    break;
                case 2:
                    int sceltaTabella=IScanner.genericScanner(Integer.class,"Scegli quale tabella visualizzare:\n" +
                            "1:RECORD_LABEL\n" +
                            "2:ARTIST\n" +
                            "3:ALBUM\n" +
                            "4:SONG");
                    int id=IScanner.genericScanner(Integer.class,"Inserisci ID.");
                    switch (sceltaTabella){
                        case 1:
                            System.out.println(music.cercaPerId(id,"record_label"));
                            break;
                        case 2:
                            System.out.println(music.cercaPerId(id,"artist"));
                            break;
                        case 3:
                            System.out.println(music.cercaPerId(id,"album"));
                            break;
                        case 4:
                            System.out.println(music.cercaPerId(id,"song"));
                            break;
                        default:;
                    }
                    break;
                case 3:
                    switch (IScanner.genericScanner(Integer.class,"Scegli su quale tabella lavorare:\n" +
                            "1:RECORD_LABEL\n" +
                            "2:ARTIST\n" +
                            "3:ALBUM\n" +
                            "4:SONG")){
                        case 1:
                            Entity record=new Record_label(-45,IScanner.genericScanner(String.class,"Inserisci nome"));
                            music.addRecord_Label(record);
                            break;
                        case 2:
                            Entity artist=new Artist(-45,IScanner.genericScanner(Integer.class,"Inserisci Record_label_id"),
                                    IScanner.genericScanner(String.class,"Inserisci Nome"));
                            music.addArtist(artist);
                            break;
                        case 3:
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date date = formatter.parse(IScanner.genericScanner(String.class,"Inserisci data.FORMATO YYYY-MM-GG"));
                                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                                Entity album=new Album(-45,IScanner.genericScanner(Integer.class,"Inserisci Artist_id")
                                        ,IScanner.genericScanner(String.class,"Inserisci nome")
                                        ,sqlDate);
                                music.addAlbum(album);
                            } catch (ParseException e) {
                                throw new RuntimeException(e);
                            }
                            //OPPURE DIRETTAMENTE SENZA TRY/CATCH SENZA GESTIRE L'ECCEZIONE
                            /*java.sql.Date sqlDate = java.sql.Date.valueOf(data_release);
                            Entity album = new Album(-45, artist_id, nome, sqlDate);
                            music.addAlbum(album);*/
                            break;
                        case 4:
                            Entity song=new Song(-45,IScanner.genericScanner(Integer.class,"Inserisci album_id")
                                    ,IScanner.genericScanner(String.class,"Inserisci nome"),
                                    IScanner.genericScanner(Double.class,"Inserisci durata"));
                            music.addSong(song);
                            break;
                        default:;
                    }
                    break;
                case 4:
                    sceltaTabella=IScanner.genericScanner(Integer.class,"Scegli su quale tabella lavorare:\n" +
                            "1:RECORD_LABEL\n" +
                            "2:ARTIST\n" +
                            "3:ALBUM\n" +
                            "4:SONG");
                    id=IScanner.genericScanner(Integer.class,"Inserisci ID del record da eliminare.");
                    switch (sceltaTabella){
                        case 1:
                            music.eliminaRecord_label(id);
                            break;
                        case 2:
                            music.eliminaArtist(id);
                            break;
                        case 3:
                            music.eliminaAlbum(id);
                            break;
                        case 4:
                            music.eliminaSong(id);
                            break;
                        default:;
                    }
                    break;
                case 5:
                    sceltaTabella=IScanner.genericScanner(Integer.class,"Scegli su quale tabella lavorare:\n" +
                            "1:RECORD_LABEL\n" +
                            "2:ARTIST\n" +
                            "3:ALBUM\n" +
                            "4:SONG");
                    id=IScanner.genericScanner(Integer.class,"Scegli id della riga da modificare");
                    switch (sceltaTabella){
                        case 1:
                            music.aggiornaRecord_label(id,IScanner.genericScanner(String.class,"Scegli la colonna da modificare tra:\n" +
                                    "| NAME |"),IScanner.genericScanner(String.class,"Inserisci nuovo valore."));
                            break;
                        case 2:
                            music.aggiornaArtist(id,IScanner.genericScanner(String.class,"Scegli la colonna da modificare tra:\n" +
                                            "| record_label_id | name |"),
                                    IScanner.genericScanner(String.class,"Inserisci nuovo valore."));
                            break;
                        case 3:
                            music.aggiornaAlbum(id,IScanner.genericScanner(String.class,"Scegli la colonna da modificare tra:\n" +
                                            "| Artist_id | name | date_release"),
                                    IScanner.genericScanner(String.class,"Inserisci nuovo valore."));
                            break;
                        case 4:
                            music.aggiornaSong(id,IScanner.genericScanner(String.class,"Scegli la colonna da modificare tra:\n" +
                                            "| Album_id | name | Duration"),
                                    IScanner.genericScanner(String.class,"Inserisci nuovo valore."));
                            break;
                        default:;
                    }
                    break;
                case 6:
                    System.out.println(IStampa.stampaMappa(music.readArtistaCasaDiscografica()));
                    break;
                case 7:
                    System.out.println(IStampa.stampaMappa(music.readNoArtist()));
                    break;
                case 8:
                    System.out.println(IStampa.stampaMappa(music.readNCanzoni()));
                    break;
                case 9:
                    System.out.println(IStampa.stampaMappa(music.readArtistaMaxSong()));
                    break;
                case 10:
                    System.out.println(IStampa.stampaMappa(music.readArtistaMinCanzoni()));
                    break;
                case 11:
                    System.out.println(IStampa.stampaMappa(music.numeroArtistiMinCanzoni()));
                    break;
                case 12:
                    System.out.println(IStampa.stampaMappa(music.numeroCanzoniMeno5Min()));
                    break;
                case 13:
                    System.out.println(IStampa.stampaMappa(music.tabellaArtistiAlbumCanzoniEDurataCanzoni()));
                    break;
                case null:
                    break;
                default:;
            }
            rispostaOperazioni=IScanner.genericScanner(String.class,"\nVuoi effettuare altre operazioni?");
        }while (rispostaOperazioni.equalsIgnoreCase("si"));
        System.out.println("Arrivederci!");


    }
}