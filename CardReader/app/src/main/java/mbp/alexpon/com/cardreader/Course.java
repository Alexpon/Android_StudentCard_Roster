package mbp.alexpon.com.cardreader;

/**
 * Created by apple on 2015/10/10.
 */
public class Course {

    public int index;
    public int[] class_id;
    public String[] class_name;

    public Course(int index, int[] class_id, String[] class_name){
        this.index = index;
        this.class_id = class_id;
        this.class_name = class_name;
    }

}
