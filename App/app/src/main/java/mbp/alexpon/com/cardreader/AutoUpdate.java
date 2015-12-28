package mbp.alexpon.com.cardreader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class AutoUpdate extends Activity {

    private Button btn_cancel;
    private Button btn_perso;
    private Button btn_submit;
    private Spinner spinner;
    private String choose_course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCourseDetail();
        setContentView(R.layout.activity_auto_update);
        initViews();
        setListener();
    }

    public void initViews(){
        btn_cancel = (Button) findViewById(R.id.auto_cancle);
        btn_perso = (Button) findViewById(R.id.auto_perso);
        btn_submit = (Button) findViewById(R.id.auto_submit);
        spinner = (Spinner) findViewById(R.id.auto_spinner);
    }

    public void setListener(){
        btn_cancel.setOnClickListener(myListener);
        btn_perso.setOnClickListener(myListener);
        btn_submit.setOnClickListener(myListener);
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.auto_cancle:
                    Intent intent1 = new Intent(AutoUpdate.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                    break;
                case R.id.auto_perso:
                    Intent intent2 = new Intent(AutoUpdate.this, Perso.class);
                    startActivity(intent2);
                    break;
                case R.id.auto_submit:
                    Bundle bundle = new Bundle();
                    bundle.putString("course", choose_course);
                    Intent intent3 = new Intent(AutoUpdate.this, AutoSync.class);
                    intent3.putExtras(bundle);
                    startActivity(intent3);
                    break;
            }
        }
    };

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
                            //Toast.makeText(SyncData.this, "您選擇"+adapterView.getSelectedItem().toString(), Toast.LENGTH_LONG).show();
                            choose_course = returnedCourse.class_name[position];
                        }
                        public void onNothingSelected(AdapterView arg0) {
                            //Toast.makeText(SyncData.this, "您沒有選擇任何項目", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        });
    }
}
