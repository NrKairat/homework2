package homework.homework2;


import java.io.*;
import java.util.ArrayList;

/**
 * Created by кайрат on 23.11.2016.
 */
public class PhoneBook extends Thread{
    private String name="Коля";
    private String email="Kolyan@gmail.com";
    private String progressBar="";
    private String space="";
    private int limitFiles = 100;
    private int percent =0;
    private int limitSpace=40;
    int limitLow=0;
    private Thread uwThread;
    private Thread rbThread;
    private static int countRead=0;
    private static int countWrite=0;
    // Для хранения ссылки на количество потоков, используются 2 класса CountW и CountR, возвращающие соответственно
    // countWrite и countRead, унаследованные от класса Count с методом getCount()
    private CountW cw = new CountW();
    private CountR cr = new CountR();
    public static ArrayList<User> listU; //список пользователей


    public static synchronized void incrementCountRead(){
        countRead++;
    }
    public static synchronized void incrementCountWrite(){countWrite++;    }
    public void recovery(){
        readWithThread();
        showProgressBar(rbThread,"Восстановление",cr);

    }
    public void save(){
        saveWithThread();
        showProgressBar(uwThread,"Сохранение",cw);
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
    public void saveWithThread(){
        uwThread = new Thread(new UserWriteThread(name, email,limitFiles));
        uwThread.start();

    }
    public void readWithThread(){
        listU = new ArrayList<User>();
        rbThread = new UserReadThread(name,email,limitFiles);
        rbThread.start();
    }
    public void showProgressBar(Thread t, String title, Count count){
        progressBar="";
        limitSpace=10;
        limitLow=0;

        System.out.println();
        System.out.println(title);

        while (t.isAlive()){
            space="";
            percent=count.getCount()*100/limitFiles;
            for (int j = 0; j < limitSpace; j++){
                space+=" ";
            }
            if(percent-limitLow>0){limitLow+=10;progressBar+="="; limitSpace--;}
            if(percent<10){System.out.print("\r  "+percent+"% ["+progressBar+">"+space+"]");}
            if(percent>10&&percent<100){System.out.print("\r "+percent+"% ["+progressBar+">"+space+"]");}
            if(percent==100){System.out.print("\r"+percent+"% ["+progressBar+">"+space+"]");}
            space="";
        }
        percent=count.getCount()*100/limitFiles;
        if(percent==100){System.out.print("\r"+percent+"% ["+progressBar+">"+space+"]");}
        System.out.println();
        System.out.println(title+" завершено");
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
    private class Count{
        public int getCount(){return -1;}
    }
    private class CountW extends Count{
        @Override
        public int getCount(){return countWrite;}
    }
    private class CountR extends Count{
        @Override
        public int getCount(){return countRead;}
    }
}
