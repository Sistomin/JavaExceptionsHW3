import org.w3c.dom.CDATASection;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.io.Closeable;

//Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке,
// разделенные пробелом:
//Фамилия Имя Отчество датарождения номертелефона пол
//
//Форматы данных:
//фамилия, имя, отчество - строки
//датарождения - строка формата dd.mm.yyyy
//номертелефона - целое беззнаковое число без форматирования
//пол - символ латиницей f или m.
//
//Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым,
// вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
//
//Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.
// Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы.
// Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано,
// пользователю выведено сообщение с информацией, что именно неверно.
//
//Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку
// должны записаться полученные данные, вида
//
//<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
//
//Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
//
//Не забудьте закрыть соединение с файлом.
//
//При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано,
// пользователь должен увидеть стектрейс ошибки.

public class UserInfoApp {
    public static void main(String[] args) throws FileSystemException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите фамилию, имя, отчество, дату рождения (в формате dd.mm.yyyy), номер телефона " +
                "(число без разделителей) и пол(символ латиницей f или m), разделенные пробелом");
        String input = scanner.nextLine();

        try {
            String[]data = input.split(" ");
            if (data.length != 6){
                throw new IllegalArgumentException("Колличество данных не совподает с запрошенным.");
            }

        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        Date dateOfBirth = parseDate(data[3]);
        int phoneNumber = Integer.parseInt(data[4]);
        char gender = data[5].charAt(0);

            // вывод обработанных данных
            System.out.println("Фамилия: " + lastName);
            System.out.println("Имя: " + firstName);
            System.out.println("Отчество: " + middleName);
            System.out.println("Дата рождения: " + formatDate(dateOfBirth));
            System.out.println("Номер телефона: " + phoneNumber);
            System.out.println("Пол: " + gender);

            String fileName = "C:\\Users\\Admin\\Desktop\\Java Исключения\\lesson3\\HW3\\HW\\Tasks\\EndWork\\src\\main\\java\\" + data[0].toLowerCase() + ".txt";
            File file = new File(fileName);
            try (FileWriter fileWriter = new FileWriter(file, true)){
                if (file.length() > 0){
                    fileWriter.write('\n');
                }
                fileWriter.write(String.format("<%s> <%s> <%s> <%s> <%s> <%s>",
                        data[0], data[1], data[2], data[3], data[4], data[5]));
                System.out.println("Введенные данные записаны в файл " + data[0]);
                System.out.println("Путь к файлу: " + file);
            }catch (IOException e){
                throw new FileSystemException("Возникла ошибка при работе с файлом");
            }

        } catch (NumberFormatException e){
            System.out.println("Ошибка: Неверный формат номера телефона.");
        } catch (IllegalArgumentException e){
            System.out.println("Ошибка: " + e.getMessage());
        } catch (ParseException e){
            System.out.println("Ошибка: неверный формат даты");
        } catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Ошибка: Недостаточно данных.");
        }

    }

    private static Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.parse(dateStr);
    }

    private static String formatDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        return format.format(date);
    }

}
