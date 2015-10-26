package mbp.alexpon.com.cardreader;

/**
 * Created by apple on 15/8/19.
 */
public class User {
    String card_id, student_id, student_name, department;

    public User(String card_id, String student_id, String student_name, String department){
        this.card_id = card_id;
        this.student_id = student_id;
        this.student_name = student_name;
        this.department = department;
    }

    public User(String card_id){
        this.card_id = card_id;
        this.student_id = "";
        this.student_name = "";
        this.department ="";
    }


}
