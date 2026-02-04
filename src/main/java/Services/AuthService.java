package Services;

import DAO.OwnerDAOImpl;
import DAO.VeterinarianDAOImpl;
import Classes.Owners;
import Classes.Veterinarians;

public class AuthService {
    private OwnerDAOImpl ownerDAO;
    private VeterinarianDAOImpl vetDAO;

    public AuthService() {
        ownerDAO = new OwnerDAOImpl();
        vetDAO = new VeterinarianDAOImpl();
    }

    public Owners loginOwner(String phone) {
        return ownerDAO.findByPhone(phone);
    }

    public Veterinarians loginVeterinarian(String phone) {
        return vetDAO.findByPhone(phone);
    }

    public Owners registerOwner(String lastName, String firstName, String patronymic, String phone) {
        Owners owner = new Owners();
        owner.setLast_name(lastName);
        owner.setFirst_name(firstName);
        owner.setPhone(phone);
        owner.setAddress("");
        owner.setPatronymic(patronymic);

        int id = ownerDAO.save(owner);
        if (id > 0) {
            owner.setID_owner(id);
            return owner;
        }
        return null;
    }
}