package models;
 
import java.util.*;
import javax.persistence.*;
 
import play.db.jpa.*;
import play.data.validation.*;
import play.libs.Codec;

@Entity
public class User extends Model {
 
    @Email
    @Required
    public String email;
    
    @Required
    public String passwordHash;
    
    @Required
    public String fullname;

    public String needConfirmation;
    
    
    public User(String email, String password, String fullname) {
        this.email = email;
        this.passwordHash = Codec.hexMD5(password);
        this.fullname = fullname;
        this.needConfirmation = Codec.UUID();
    }

    public static User findByEmail(String email) {
        return find("email", email).first();
    }

    public boolean checkPassword(String password) {
        return passwordHash.equals(Codec.hexMD5(password));
    }

    public static User findByRegistrationUUID(String uuid) {
        return find("needConfirmation", uuid).first();
    }
 
}