package mbp.alexpon.com.cardreader;

/**
 * Created by apple on 15/9/7.
 */
public class DateTime {
    int year, month, date;
    int hour, minute, second;

    public DateTime(int year, int month, int date, int hour, int minute, int second){
        this.year = year;
        this.month = month;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.second = second;
    }
}
