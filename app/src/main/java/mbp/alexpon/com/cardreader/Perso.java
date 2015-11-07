package mbp.alexpon.com.cardreader;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class Perso extends Activity {

    private NfcAdapter myNfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilter;

    private TextView card_id;
    private EditText student_id;
    private EditText student_name;
    private EditText department;
    private Button perso_cancel;
    private Button perso_submit;
    private String get_card_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perso);

        initViews();
        setListener();

        pendingSetting();
        myNfcAdapter = NfcAdapter.getDefaultAdapter(this);

    }

    public void initViews(){
        card_id = (TextView) findViewById(R.id.card_id);
        student_id = (EditText) findViewById(R.id.et_student_id);
        student_name = (EditText) findViewById(R.id.et_student_name);
        department = (EditText) findViewById(R.id.et_department);
        perso_cancel = (Button) findViewById(R.id.perso_cancel);
        perso_submit = (Button) findViewById(R.id.perso_submit);
    }

    public void setListener(){
        perso_cancel.setOnClickListener(myListener);
        perso_submit.setOnClickListener(myListener);
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.perso_cancel:
                    finish();
                    break;
                case R.id.perso_submit:
                    push_to_server();
                    break;
            }
        }
    };

    private void push_to_server(){
        final User user = new User(get_card_id, student_id.getText().toString(), student_name.getText().toString(), department.getText().toString());
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeUserDataInBackground(user, new GetUserCallBack() {
            @Override
            public void done(User returnedUser) {
                finish();
            }
        });
    }

    private void pendingSetting(){
        Intent intent = new Intent(this, Perso.class);
        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        intentFilter = new IntentFilter[]{};
    }

    @Override
    protected void onResume() {
        myNfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilter, null);
        super.onResume();
    }

    @Override
    protected void onPause() {
        myNfcAdapter.disableForegroundDispatch(this);
        super.onPause();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        get_card_id = ByteArrayToHexString(intent.getByteArrayExtra(myNfcAdapter.EXTRA_ID));
        card_id.setText(get_card_id);
        super.onNewIntent(intent);
    }

    private String ByteArrayToHexString(byte [] array){
        int i, j, in;
        String [] hex = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};
        String out = "";

        for(i = 0; i<array.length; i++){
            in = (int) array[i] & 0xff;
            j = (in >> 4) & 0x0f;
            out += hex[j];
            j = in & 0x0f;
            out += hex[j];
        }
        return out;
    }

}
