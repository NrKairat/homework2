package homework.homework2;

/**
 * Телефонная книга с обычной сериализацией на 200 контактов. Медленная загрузка, сохранение контактов симулируется с
 * помощью Thread.sleep(). Сохранение и загрузка выполняются в фоновом потоке, для подсчета завершенных птоков
 * используются статические переменные countRead, countWrite.
 */
public class Main {
    public static void main(String[] args) {
        PhoneBook pb = new PhoneBook();
        pb.recovery(); //Загрузка контактов
        pb.save(); //Запись контактов
        pb.showAll(); // Вывод на экран каждого 99-го пользователя
    }
}
