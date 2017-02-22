package windowmanager.project.angel.com.windowmanagerexample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button startServiceBtn;
    private Button stopServiceBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startServiceBtn = (Button) findViewById(R.id.startService);
        stopServiceBtn = (Button) findViewById(R.id.stopService);

    }

    public void startServiceClick(View v){
        Log.i("MainActivity---","startService");
        Intent startIntent = new Intent(this, WindowService.class);
        startService(startIntent);
    }

    public void stopServiceClick(View v){
        Log.i("MainActivity---","stopService");
        Intent stopIntent = new Intent(this, WindowService.class);
        stopService(stopIntent);
    }


    public void toSearchClick(View v){
        Intent intent = new Intent(MainActivity.this, SearchListActivity.class);
        startActivity(intent);
    }
}
