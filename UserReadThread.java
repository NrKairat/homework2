package homework.homework2;

/**
 * Created by кайрат on 23.11.2016.
 */
public class UserReadThread extends Thread{
    private String name;
    private int limit;
    private String email;

    UserReadThread(String name,String email,int limit){
        this.name = name;
        this.limit = limit;
        this.email = email;

    }
    public void run() {
        for (int i = 0; i < limit; i++) {
            User user = PhoneBook.readByte(name+i);
            PhoneBook.listU.add(user);
            PhoneBook.incrementCountRead();
        }



    }
}
