package homework.homework2;

/**
 * Created by кайрат on 23.11.2016.
 */
public class UserWriteThread implements Runnable {
    private String name;
    private String email;
    private int limit;

    UserWriteThread(String name,String email, int limit){
        this.name = name;
        this.email = email;
        this.limit = limit;
    }
    public void run() {

        for (int i = 0; i < limit; i++) {
            User u =new User(name+i, email+i);
            PhoneBook.writeToByte(u,name+i);
            PhoneBook.incrementCountWrite();
        }


    }
}
