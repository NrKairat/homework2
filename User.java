package homework.homework2;

import java.io.Serializable;

/**
 * Created by кайрат on 23.11.2016.
 */
public class User implements Serializable {
    String name;
    String email;
    User(String name,String email ) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
