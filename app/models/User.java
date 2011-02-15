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
    public String password;
    
    @Required
    public String fullname;

    public String needConfirmation;
    
    
    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = Codec.hexMD5(password);
        this.fullname = fullname;
        this.needConfirmation = Codec.UUID();
    }
    
    public boolean checkPassword(String password) {
        return password.equals(Codec.hexMD5(password));
    }

    public static User connect(String email, String password) {
        return find("byEmailAndPassword", email, password).first();
    }

    public static User findByRegistrationUUID(String uuid) {
        return find("needConfirmation", uuid).first();
    }
    
    public String toString() {
        return email;
    }
 
}