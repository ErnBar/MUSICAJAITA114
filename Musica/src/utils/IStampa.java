package utils;

import models.Entity;

import java.util.List;
import java.util.Map;

public interface IStampa {
    static String stampaConEntity(List<Entity> lista) {
        String ris = "";
        for (Entity e : lista) {
            ris += "\n" + e.toString();
        }
        return ris;
    }
    //IL METODO CON STRINGBUILDER E'PIU' EFFICIENTE IN TERMINI DI PRESTAZIONI.
    static String stampaMappa(List<Map<String,String>> list) {
        StringBuilder ris = new StringBuilder();
        for (Map<String,String> map : list) {
            /*Per ogni mappa, viene eseguito un altro ciclo su ogni coppia chiave-valore nella mappa.
            Questo viene fatto utilizzando un oggetto EntrySet, che rappresenta l'insieme delle associazioni chiave-valore
            all'interno della mappa.
            Per ogni coppia chiave-valore, viene aggiunto un'istanza di String al StringBuilder ris.*/
            for (Map.Entry<String,String> map2 : map.entrySet()) {
                ris.append("| "+map2.getKey()).append(" : ").append(map2.getValue()+" |");
            }
            ris.append("\n");
        }
        return ris.toString();
    }

//    static String stampaMappa(List<Map<String,String>> list) {
//        String result = "";
//        for (Map<String,String> map : list) {
//            for (Map.Entry<String,String> entry : map.entrySet()) {
//                // Concateniamo direttamente le stringhe
//                result += "| " + entry.getKey() + " : " + entry.getValue() + " |";
//            }
//            // Aggiungiamo un carattere di nuova linea
//            result += "\n";
//        }
//        return result;
//    }
}
