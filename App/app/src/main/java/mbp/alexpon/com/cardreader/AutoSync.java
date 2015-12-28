package mbp.alexpon.com.cardreader;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class AutoSync extends Activity {

    private TextView title;
    private Button back;
    private String course;


    private String[] roster_student;
    private int[] send_year;
    private int[] send_month;
    private int[] send_date;
    private int[] send_hour;
    private int[] send_minute;
    private int[] send_second;
    private static int MAX_USER_MEMORY = 200;

    private NfcAdapter myNfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilter;

    private SoundPool soundPool;
    private int sneezeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_sync);
        initViews();
        setListener();
        pendingSetting();
        myNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes aa = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            soundPool = new SoundPool.Builder()
                    .setMaxStreams(10)
                    .setAudioAttributes(aa)
                    .build();
            sneezeId = soundPool.load(this, R.raw.bee, 1);
        }
        else{
            soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
            sneezeId = soundPool.load(this, R.raw.bee, 1);
        }
    }

    public void initViews(){
        Bundle bundle = this.getIntent().getExtras();
        course = bundle.getString("course");
        title = (TextView) findViewById(R.id.auto_sync_title);
        title.setText(course);
        back = (Button) findViewById(R.id.auto_sync_back);

        roster_student = new String[1];
        send_year = new int[1];
        send_month = new int[1];
        send_date = new int[1];
        send_hour = new int[1];
        send_minute = new int[1];
        send_second = new int[1];
    }

    public void setListener(){
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AutoSync.this, AutoUpdate.class);
                startActivity(intent);
            }
        });
    }

    private void pendingSetting(){
        Intent intent = new Intent(this, AutoSync.class);
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

        roster_student[0] = ByteArrayToHexString(intent.getByteArrayExtra(myNfcAdapter.EXTRA_ID));
        DateTime dateTime = getTime();
        send_year[0] = dateTime.year;
        send_month[0] = dateTime.month;
        send_date[0] = dateTime.date;
        send_hour[0] = dateTime.hour;
        send_minute[0] = dateTime.minute;
        send_second[0] = dateTime.second;

        soundPool.play(sneezeId, 1, 1, 0, 0, 1);
        roster();
        Toast.makeText(this, ByteArrayToHexString(intent.getByteArrayExtra(myNfcAdapter.EXTRA_ID)) + " " + dateTime.year + "/" + dateTime.month + "/" +
                dateTime.date + " " + dateTime.hour + ":" + dateTime.minute + ":" + dateTime.second, Toast.LENGTH_LONG).show();
        super.onNewIntent(intent);
    }

    private void roster(){
        ServerRequests serverRequests = new ServerRequests(this);
        serverRequests.storeRosterDataInBackground(1, roster_student, send_year, send_month, send_date,
                send_hour, send_minute, send_second, course, new GetUserCallBack() {
                    @Override
                    public void done(User returnedUser) {
                        Toast.makeText(getApplicationContext(), "Roster Success!", Toast.LENGTH_SHORT).show();
                    }
                });
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


    private DateTime getTime(){
        Time t = new Time(Time.getCurrentTimezone());
        t.setToNow();
        int year = t.year;
        int month = t.month+1;
        int date = t.monthDay;
        int hour = t.hour;
        int minute = t.minute;
        int second = t.second;
        return new DateTime(year, month, date, hour, minute, second);
    }

}
