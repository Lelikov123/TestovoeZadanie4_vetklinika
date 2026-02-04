package DAO;

import Classes.Animals;
import java.util.List;

public interface AnimalDAO {
    Animals findById(int id);
    List<Animals> findByOwnerId(int ownerId);
    int save(Animals animal);
    boolean delete(int id);

}