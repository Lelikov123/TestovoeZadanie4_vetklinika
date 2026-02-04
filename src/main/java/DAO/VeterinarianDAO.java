package DAO;

import Classes.Veterinarians;
import java.util.List;

public interface VeterinarianDAO {
    Veterinarians findById(int id);
    Veterinarians findByPhone(String phone);
    List<Veterinarians> findAll();

}