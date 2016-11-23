package homework.homework2;

/**
 * Created by кайрат on 23.11.2016.
 */
public class UserReadThread extends Thread{
    String name;
    int delay;
    User user;

    public User getUser() {
        return user;
    }

    UserReadThread(String name, int delay){
        this.name = name;
        this.delay=delay;

    }
    public void run() {
        try {
            Thread.sleep(delay*PhoneBook.threadSleepRead);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        user = PhoneBook.readByte(name);
        PhoneBook.incrementCountRead();

    }
}
