package rhodonite.com.jsonsample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    StartJson mStartJson;
    String Url = "https://raw.githubusercontent.com/winwiniosapp/interview/main/interview.json";

    private ArrayList<String> Files = new ArrayList<String>();
    TextView textView;
    ListView lv;
    public MyAdapter mMyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
        lv = findViewById(R.id.listview);
        mStartJson = StartJson.getInstance();
        mStartJson.func(Url, new StartJson.StartJsonListener() {
            @Override
            public void onSuccess(ArrayList<ListModel> success) {
               setAdapter(success);
            }

            @Override
            public void onTotalCount(String totalCount) {
                textView.setText("totalCount:" + totalCount);
            }

            @Override
            public void onError(String error) {
                Log.e(TAG, error);
            }
        });
    }

    private void setAdapter(ArrayList<ListModel> success){
        mMyAdapter = new MyAdapter(getApplicationContext(),success);
        lv.setAdapter(mMyAdapter);
    }

}