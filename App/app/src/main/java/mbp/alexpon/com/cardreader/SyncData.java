package mbp.alexpon.com.cardreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


public class SyncData extends Activity {

    private Button syn_cancel;
    private Button syn_submit;
    private Spinner spinner;
    private TextView show;

    private String[] student;
    private int[] year;
    private int[] month;
    private int[] date;
    private int[] hour;
    private int[] minute;
    private int[] second;
    private int index;
    private String choose_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCourseDetail();
        setContentView(R.layout.activity_sync_data);
        inits();
        setListener();
    }

    public void inits(){
        choose_course = "";
        spinner = (Spinner) findViewById(R.id.spinner);
        syn_cancel = (Button) findViewById(R.id.sync_cancel);
        syn_submit = (Button) findViewById(R.id.sync_submit);
        show = (TextView) findViewById(R.id.show_data_num);
        Bundle bundle = this.getIntent().getExtras();
        student = bundle.getStringArray("Student");
        year = bundle.getIntArray("Year");
        month = bundle.getIntArray("Month");
        date = bundle.getIntArray("Date");
        hour = bundle.getIntArray("Hour");
        minute = bundle.getIntArray("Minute");
        second = bundle.getIntArray("Second");
        index = bundle.getInt("Index");
        show.setText("共"+index+"筆資料");
    }

    public void setListener(){
        syn_cancel.setOnClickListener(myListener);
        syn_submit.setOnClickListener(myListener);
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sync_cancel:
                    startActivity(new Intent(SyncData.this, MainActivity.class));
                    finish();
                    break;
                case R.id.sync_submit:
                    roster();
                    break;
            }
        }
    };

    private void roster(){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeRosterDataInBackground(index, student, year, month, date,
                hour, minute, second, choose_course, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                Toast.makeText(getApplicationContext(), "Roster Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getCourseDetail(){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.fetchCourseInBackground(new GetCourseCallBack() {
            @Override
            public void done(final Course returnedCourse) {
                if (returnedCourse != null) {

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                            android.R.layout.simple_spinner_item, returnedCourse.class_name);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinner.setAdapter(adapter);
                    spinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
                        public void onItemSelected(AdapterView adapterView, View view, int position, long id){
                            choose_course = returnedCourse.class_name[position];
                        }
                        public void onNothingSelected(AdapterView arg0) {
                        }
                    });
                }
            }
        });
    }

}
