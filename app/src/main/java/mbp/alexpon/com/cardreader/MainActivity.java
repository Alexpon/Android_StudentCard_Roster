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
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Button first;
    private Button sync;
    private TextView data_area;

    private String card_id;
    private String[] roster_student;
    private int[] send_year;
    private int[] send_month;
    private int[] send_date;
    private int[] send_hour;
    private int[] send_minute;
    private int[] send_second;
    private int index;
    private static int MAX_USER_MEMORY = 200;

    private NfcAdapter myNfcAdapter;
    private PendingIntent pendingIntent;
    private IntentFilter[] intentFilter;

    private SoundPool soundPool;
    private int sneezeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inits();
        setListener();
        pendingSetting();
        myNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        checkNfc();

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

    public void inits(){
        index = 0;
        roster_student = new String[MAX_USER_MEMORY];
        send_year = new int[MAX_USER_MEMORY];
        send_month = new int[MAX_USER_MEMORY];
        send_date = new int[MAX_USER_MEMORY];
        send_hour = new int[MAX_USER_MEMORY];
        send_minute = new int[MAX_USER_MEMORY];
        send_second = new int[MAX_USER_MEMORY];
        first = (Button) findViewById(R.id.first);
        sync = (Button) findViewById(R.id.sync);
        data_area = (TextView) findViewById(R.id.data_area);
        data_area.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void setListener(){
        first.setOnClickListener(myListener);
        sync.setOnClickListener(myListener);
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.first:
                    Intent intent1 = new Intent(MainActivity.this, Perso.class);
                    startActivity(intent1);
                    break;
                case R.id.sync:
                    Bundle bundle = new Bundle();
                    bundle.putStringArray("Student", roster_student);
                    bundle.putIntArray("Year", send_year);
                    bundle.putIntArray("Month", send_month);
                    bundle.putIntArray("Date", send_date);
                    bundle.putIntArray("Hour", send_hour);
                    bundle.putIntArray("Minute", send_minute);
                    bundle.putIntArray("Second", send_second);
                    bundle.putInt("Index", index);
                    Intent intent2 = new Intent(MainActivity.this, SyncData.class);
                    intent2.putExtras(bundle);

                    startActivity(intent2);
                    finish();
                    break;
            }

        }
    };

    private void pendingSetting(){
        Intent intent = new Intent(this, MainActivity.class);
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
        //User user = new User(ByteArrayToHexString(intent.getByteArrayExtra(myNfcAdapter.EXTRA_ID)));
        //checkUser(user);
        card_id = ByteArrayToHexString(intent.getByteArrayExtra(myNfcAdapter.EXTRA_ID));
        roster_student[index] = card_id;

        DateTime dateTime = getTime();
        send_year[index] = dateTime.year;
        send_month[index] = dateTime.month;
        send_date[index] = dateTime.date;
        send_hour[index] = dateTime.hour;
        send_minute[index] = dateTime.minute;
        send_second[index] = dateTime.second;
        index++;

        data_area.append(card_id + " " + dateTime.year + "/" + dateTime.month + "/" +
                dateTime.date + " " + dateTime.hour + ":" + dateTime.minute + ":" + dateTime.second + "\n");

        soundPool.play(sneezeId, 1, 1, 0, 0, 1);

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

    private void checkNfc(){
        if(myNfcAdapter == null){
            Toast.makeText(this, R.string.NFC_not_support, Toast.LENGTH_LONG).show();
            finish();
        }
        if(!myNfcAdapter.isEnabled()){
            Toast.makeText(this, R.string.NFC_not_open,Toast.LENGTH_LONG).show();
        }
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
