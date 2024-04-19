package interfaces;

import java.util.List;
import java.util.Map;

public interface IDatabase {
    //METODO CHE ESEGUE LA QUERY(DDL)CIOE'LE SELECT;
    List<Map<String,String>> executeQuery(String query, String...params);


    void executeUpdate(String query,String...params);

    boolean checktable(String nome);
}
