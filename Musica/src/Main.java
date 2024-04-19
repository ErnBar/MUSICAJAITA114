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
        Scanner scanner=new Scanner(System.in);

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
                    System.out.println(("Scegli su quale tabella lavorare:\n" +
                            "1:RECORD_LABEL\n" +
                            "2:ARTIST\n" +
                            "3:ALBUM\n" +
                            "4:SONG"));
                    sceltaTabella=Integer.parseInt(scanner.nextLine());
                    switch (sceltaTabella){
                        case 1:
                            System.out.println("Inserisci nome");
                            String nome=scanner.nextLine();
                            Entity record=new Record_label(-45,nome);
                            music.addRecord_Label(record);
                            break;
                        case 2:
                            System.out.println("Inserisci Record_label_id");
                            int record_label_id=Integer.parseInt(scanner.nextLine());
                            System.out.println("Inserisci Nome");
                            nome=scanner.nextLine();
                            Entity artist=new Artist(-45,record_label_id,nome);
                            music.addArtist(artist);
                            break;
                        case 3:
                            System.out.println("Inserisci Artist_id");
                            int artist_id=Integer.parseInt(scanner.nextLine());
                            System.out.println("Inserisci nome");
                            nome=scanner.nextLine();
                            System.out.println("Inserisci data.FORMATO YYYY-MM-GG");
                            String data_release=scanner.nextLine();
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                Date date = formatter.parse(data_release);
                                java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                                Entity album=new Album(-45,artist_id,nome,sqlDate);
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
                            System.out.println("Inserisci album_id");
                            int album_id=Integer.parseInt(scanner.nextLine());
                            System.out.println("Inserisci nome");
                            nome=scanner.nextLine();
                            System.out.println("Inserisci durata");
                            double durata=Double.parseDouble(scanner.nextLine());
                            Entity song=new Song(-45,album_id,nome,durata);
                            music.addSong(song);
                            break;
                        default:;
                    }
                    break;
                case 4:
                    System.out.println(("Scegli su quale tabella lavorare:\n" +
                            "1:RECORD_LABEL\n" +
                            "2:ARTIST\n" +
                            "3:ALBUM\n" +
                            "4:SONG"));
                    sceltaTabella=Integer.parseInt(scanner.nextLine());
                    System.out.println("Inserisci ID del record da eliminare.");
                    id=Integer.parseInt(scanner.nextLine());
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
                    System.out.println(("Scegli su quale tabella lavorare:\n" +
                            "1:RECORD_LABEL\n" +
                            "2:ARTIST\n" +
                            "3:ALBUM\n" +
                            "4:SONG"));
                    sceltaTabella=Integer.parseInt(scanner.nextLine());
                    System.out.println("Scegli id della riga da modificare");
                    id=Integer.parseInt(scanner.nextLine());
                    switch (sceltaTabella){
                        case 1:
                            System.out.println("Scegli la colonna da modificare tra:\n" +
                                    "| NAME |");
                            String colonna=scanner.nextLine();
                            System.out.println("Inserisci nuovo valore.");
                            String valoreNuovo=scanner.nextLine();
                            music.aggiornaRecord_label(id,colonna,valoreNuovo);
                            break;
                        case 2:
                            System.out.println("Scegli la colonna da modificare tra:\n" +
                                    "| record_label_id | name |");
                            colonna=scanner.nextLine();
                            System.out.println("Inserisci nuovo valore.");
                            valoreNuovo=scanner.nextLine();
                            music.aggiornaArtist(id,colonna,valoreNuovo);
                            break;
                        case 3:
                            System.out.println("Scegli la colonna da modificare tra:\n" +
                                    "| Artist_id | name | date_release");
                            colonna=scanner.nextLine();
                            System.out.println("Inserisci nuovo valore.");
                            valoreNuovo=scanner.nextLine();
                            music.aggiornaAlbum(id,colonna,valoreNuovo);
                            break;
                        case 4:
                            System.out.println("Scegli la colonna da modificare tra:\n" +
                                    "| Album_id | name | Duration");
                            colonna=scanner.nextLine();
                            System.out.println("Inserisci nuovo valore.");
                            valoreNuovo=scanner.nextLine();
                            music.aggiornaSong(id,colonna,valoreNuovo);
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
            System.out.println("\nVuoi effettuare altre operazioni?");
            rispostaOperazioni=scanner.nextLine();
        }while (rispostaOperazioni.equalsIgnoreCase("si"));
        System.out.println("Arrivederci!");
        scanner.close();


    }
}