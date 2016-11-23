package homework.homework2;


import java.io.*;
import java.util.ArrayList;

/**
 * Created by кайрат on 23.11.2016.
 */
public class PhoneBook extends Thread{
    public static int threadSleepRead=100;
    public static int  threadSleepWrite=100;

    private String name="Коля";
    private String email="Kolyan@gmail.com";
    private String ravno="";
    private String probel="";
    private int limit = 200;
    private int percent =0;
    private int limit2=40;
    private int threadSleepMain=100;
    private static int countRead=0;
    private static int countWrite=0;
    private ArrayList<UserReadThread> listURT; //список потоков
    private ArrayList<User> listU; //список пользователей


    public static synchronized void incrementCountRead(){
        countRead++;
    }
    public static synchronized void incrementCountWrite(){
        countWrite++;
    }
    public void recovery(){
        listURT = new ArrayList<UserReadThread>();
        listU = new ArrayList<User>();

        for (int i = 0; i < limit; i++) { // Создаем 200 потоков на чтение и заносим их в список потоков
            Thread rbThread = new UserReadThread(name+i,i);
            rbThread.start();
            UserReadThread urt = (UserReadThread)rbThread;
            listURT.add(urt);
        }
        System.out.println("Восстановление");
        for (int i = 0; i < limit; i++) {
            try {
                Thread.sleep(threadSleepMain);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            percent=countRead/2+1; //Считаем процент
            if(percent%5==0){ravno+="="; limit2--;} // Добавлем шкалу загрузки, и уменьшаем кол-во пробелов справа


            for (int j = 0; j < limit2; j++){
                probel+=" ";
            }

            if(percent<10){System.out.print("\r  "+percent+"% ["+ravno+">"+probel+"]");}
            if(percent>10&&percent<100){System.out.print("\r "+percent+"% ["+ravno+">"+probel+"]");}
            if(percent==100){System.out.print("\r"+percent+"% ["+ravno+">"+probel+"]");}
            probel="";

        }

        while (countRead<200){ //Проверяем что все потоки завершились
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (UserReadThread userReadThread:listURT){
            listU.add(userReadThread.getUser());
        }
        System.out.println();
        System.out.println("Восстановление Завершено");

    }
    public void save(){

        ravno="";
        limit=200;
        limit2=40;
        probel="";
        countWrite=0;
        System.out.println();
        System.out.println("Сохранение");

        for (int i = 0; i < limit; i++) {
            Thread uwThread = new Thread(new UserWriteThread(name+i, email+i,i));
            uwThread.start();
        }

        for (int i = 0; i < limit; i++) {
            try {
                Thread.sleep(threadSleepMain);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            percent=i*100/limit+1;
            if(percent%5==0){ravno+="="; limit2--;}


            for (int j = 0; j < limit2; j++){
                probel+=" ";
            }

            if(percent<10){System.out.print("\r  "+percent+"% ["+ravno+">"+probel+"]");}
            if(percent>10&&percent<100){System.out.print("\r "+percent+"% ["+ravno+">"+probel+"]");}
            if(percent==100){System.out.print("\r"+percent+"% ["+ravno+">"+probel+"]");}
            probel="";
        }
        while (countWrite<200){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println();
        System.out.println("Сохранение завершено");
    }
    public void showAll(){
        int numberLine=0;
        System.out.println();
        System.out.println("Телефонная книга");
        System.out.printf("%-25s%-25s%n","Имя","Адрес почты");
        System.out.println("---------------------------------------------");
        for (User user:listU){ // Выводим на консоль каждого 99-го пользователя
            if(numberLine%99==0){System.out.printf("%-25s%-25s%n",user.getName(),user.getEmail());}
            numberLine++;
        }
    }
    public static void writeToByte(Object o,String name) {
        ObjectOutputStream oos = null;
        try {
            FileOutputStream fos = new FileOutputStream(name+".txt");
            oos = new ObjectOutputStream(fos);
            oos.writeObject(o);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static User readByte(String name){
        ObjectInputStream ois = null;
        User u = null;
        try {
            FileInputStream fis = new FileInputStream(name+".txt");
            ois = new ObjectInputStream(fis);
            u = (User) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return u;
    }

}
