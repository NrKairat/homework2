package homework.homework2;

/**

 */
public class Main {
    public static void main(String[] args) {
        PhoneBook pb = new PhoneBook();

         pb.save(); //Запись контактов
         pb.recovery();//Загрузка контактов
         pb.showAll(); // Вывод на экран каждого 99-го пользователя
    }
}
