package app.client.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MyDateTimeFormatter {
    private static MyDateTimeFormatter instace = new MyDateTimeFormatter();

    private MyDateTimeFormatter(){}

    public static MyDateTimeFormatter getInstance(){
        return instace;
    }

    public String formatDateClassic(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return localDateTime.format(formatter);
    }

    public String formatOnlyDate(LocalDateTime localDateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return localDateTime.format(formatter);
    }

}
