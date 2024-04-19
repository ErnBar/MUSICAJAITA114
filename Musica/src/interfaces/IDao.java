package interfaces;


import models.Entity;
import java.util.List;
import java.util.Map;

public interface IDao {

    void add(Entity e);

    List<Map<String, String>> read(String query, String... params);

    List<Map<String, String>> read();

    List<Entity> readAll();

    void update(Entity e);

    void update(int id, String proprieta, String valoreNuovo);

    void delete(int id);

    Entity cercaPerId(int id);

    public Entity cercaPerNome(String nome);


}