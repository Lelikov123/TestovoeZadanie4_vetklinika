package DAO;

import Classes.Owners;
import Classes.Animals;
import java.util.List;

public interface OwnerDAO {
    Owners findByPhone(String phone);
    int save(Owners owner);

}