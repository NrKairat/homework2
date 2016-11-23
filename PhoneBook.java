package homework.homework2;


import java.io.*;
import java.util.ArrayList;

/**
 * Created by кайрат on 23.11.2016.
 */
public class PhoneBook extends Thread{
    private String name="Коля";
    private String email="Kolyan@gmail.com";
    private String ravno="";
    private String probel="";
    private int limit = 1000;
    private int percent =0;
    private int limit2=40;
    private static int countRead=0;
    public static int countWrite=0;
    public static ArrayList<User> listU; //список пользователей


    public static synchronized void incrementCountRead(){
        countRead++;
    }
    public static synchronized void incrementCountWrite(){
        countWrite++;
    }
    public void recovery(){

        listU = new ArrayList<User>();

        Thread rbThread = new UserReadThread(name,email,limit);
        rbThread.start();

        System.out.println();
        System.out.println("Восстановление");

        ravno="";
        limit2=10;
        int limitLow=0;
        probel="";
        countWrite=0;

        while (rbThread.isAlive()){
            probel="";
            percent=countRead*100/limit;
            for (int j = 0; j < limit2; j++){
                probel+=" ";
            }
            if(percent-limitLow>0){limitLow+=10;ravno+="="; limit2--;}
            if(percent<10){System.out.print("\r  "+percent+"% ["+ravno+">"+probel+"]");}
            if(percent>10&&percent<100){System.out.print("\r "+percent+"% ["+ravno+">"+probel+"]");}
            if(percent==100){System.out.print("\r"+percent+"% ["+ravno+">"+probel+"]");}
            probel="";
        }

        if(percent==100){System.out.print("\r"+percent+"% ["+ravno+">"+probel+"]");}



        System.out.println();
        System.out.println("Восстановление Завершено");


    }
    public void save(){

        ravno="";
        limit2=10;
        int limitLow=0;
        probel="";
        countWrite=0;
        System.out.println();
        System.out.println("Сохранение");

        Thread uwThread = new Thread(new UserWriteThread(name, email,limit));
        uwThread.start();

        while (uwThread.isAlive()){
            probel="";
            percent=countWrite*100/limit+1;
            for (int j = 0; j < limit2; j++){
                probel+=" ";
            }
            if(percent-limitLow>0){limitLow+=10;ravno+="="; limit2--;}
            if(percent<10){System.out.print("\r  "+percent+"% ["+ravno+">"+probel+"]");}
            if(percent>10&&percent<100){System.out.print("\r "+percent+"% ["+ravno+">"+probel+"]");}
            if(percent==100){System.out.print("\r"+percent+"% ["+ravno+">"+probel+"]");}
            probel="";
        }

        if(percent==100){System.out.print("\r"+percent+"% ["+ravno+">"+probel+"]");}
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
