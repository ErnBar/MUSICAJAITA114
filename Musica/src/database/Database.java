package database;

import interfaces.IDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database implements IDatabase {

    private String percorso,user,password;
    private Connection connection;

    private final static String DRIVER="com.mysql.cj.jdbc.Driver";
    private static IDatabase instance;

    private Database(){
        this.user="root";
        this.password="root";
        setPercorso("musica");
    }
    public synchronized static IDatabase getInstance(){
        if (instance == null)
            instance=new Database();
        return instance;
    }//SINGLETON

    public String getPercorso() {
        return percorso;
    }

    public void setPercorso(String nomeDB) {
        //INDIRIZZO PER ARRIVARE AL SERVER E AL DATABASE CHE VOGLIO USARE
        //COMUNICO UN PROTOCOLLO CHE INDICA CHE USERO'LA LIBRERIA jdbc
        //localhost:3306/----->INDIRIZZO DOVE SI TROVA IL SERVER DEL DB,
        //NEL NOSTRO CASO SI TROVA NEL NOSTRO PC ----> //localhost -PORTA:3306
        String url = "jdbc:mysql://localhost:3306/";
        //TIMEZON INDICA CHE CI STIAMO CONNETTENDO IN QUESTO MOMENTO CON QUESTA DECODIFICA;
        String timezone = "?useSSL=false&serverTimezone=UTC?useSSL=false&serverTimezone=UTC";
        this.percorso = url+nomeDB+timezone;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Map<String, String>> executeQuery(String query, String... params) {
        apriConnessione();
        List<Map<String, String>>ris=new ArrayList<>();
        PreparedStatement ps=null;
        ResultSet rs=null;
        Map<String,String>rigaTabella;
        try {
            ps=connection.prepareStatement(query);
            for (int i=0;i<params.length;i++){
                ps.setString(i+1,params[i]);
            }
            rs=ps.executeQuery();
            System.out.println("Query: "+query+" Eseguita!");
            while (rs.next()){
                rigaTabella=new HashMap<>();
                for (int i=1;i<=rs.getMetaData().getColumnCount();i++){
                    rigaTabella.put(rs.getMetaData().getColumnLabel(i),rs.getString(i));
                }
                ris.add(rigaTabella);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            chiudiComunicazioni(ps,rs);
            chiudiConnessione();
        }
        return ris;
    }

    @Override
    public void executeUpdate(String query, String... params) {
        apriConnessione();
        PreparedStatement ps = null;
        try{
            ps=connection.prepareStatement(query);
            for (int i=0;i<params.length;i++){
                ps.setString(i+1,params[i]);
            }
            int righeEseguite=ps.executeUpdate();
            if (righeEseguite>0)
                System.out.println("La query "+query+" è stata eseguita.");
//            if (righeEseguite==0)
//                System.out.println("La query "+query+" non è stato eseguita");
        }catch (Exception e){
//            e. printStackTrace();
            System.err.println("Errore nell'esecuzione della query "+query);
        }finally {
            chiudiComunicazioni(ps,null);
            chiudiConnessione();
        }

    }

    public boolean checktable(String nome)  {
        apriConnessione();
        boolean ris=false;
        try {
            DatabaseMetaData dbm = connection.getMetaData();
            /*DatabaseMetaData è una classe fornita da JDBC che fornisce metadati sul database,
            come informazioni sulle tabelle, gli indici e così via. Il metodo getTables di DatabaseMetaData
            viene utilizzato per ottenere un ResultSet contenente informazioni sulle tabelle nel database.*/
            ResultSet rs = dbm.getTables(null, null, nome, null);
            if (rs.next()) {
                ris = true;

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            chiudiConnessione();
        }

        return ris;
    }

    public void apriConnessione(){
        try {
            Class.forName(DRIVER);
            connection= DriverManager.getConnection(percorso,user,password);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            System.err.println("Controlla il connettore");
        }catch (Exception e){
            e.printStackTrace();
            System.err.println("La connessione non è aperta. Controlla il percorso,user,password.");
        }
    }


    public void chiudiConnessione(){
        try {
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Hai provato a chiudere una connessione, ma ci sono degli errori.");
        }
    }


    public void chiudiComunicazioni(PreparedStatement ps, ResultSet rs){
        try {
            if (ps!=null)
                ps.close();
            if (rs!=null)
                rs.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Ho provato a chiudere ps ed rs.");
        }
    }
}
