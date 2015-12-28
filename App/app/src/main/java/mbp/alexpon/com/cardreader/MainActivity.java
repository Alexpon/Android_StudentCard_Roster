package mbp.alexpon.com.cardreader;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    private Button btn_auto;
    private Button btn_manully;
    private NfcAdapter myNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        checkNfc();
        inits();
        setListener();
    }

    public void inits(){
        btn_auto = (Button) findViewById(R.id.auto);
        btn_manully = (Button) findViewById(R.id.manually);
    }

    public void setListener(){
        btn_auto.setOnClickListener(myListener);
        btn_manully.setOnClickListener(myListener);
    }

    private View.OnClickListener myListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.auto:
                    Intent intent1 = new Intent(MainActivity.this, AutoUpdate.class);
                    startActivity(intent1);
                    break;
                case R.id.manually:
                    Intent intent2 = new Intent(MainActivity.this, ManuallyUpdate.class);
                    startActivity(intent2);
                    finish();
                    break;
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
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

}
