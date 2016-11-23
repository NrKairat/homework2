package homework.homework2;

/**
 * Created by кайрат on 23.11.2016.
 */
public class UserWriteThread implements Runnable {
    String name;
    String email;
    int delay;
    UserWriteThread(String name,String email,int delay){
        this.name = name;
        this.email = email;
        this.delay=delay;
    }
    public void run() {
        try {
            Thread.sleep(delay*PhoneBook.threadSleepWrite);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        User u =new User(name, email);
        PhoneBook.writeToByte(u,name);
        PhoneBook.incrementCountWrite();

    }
}
