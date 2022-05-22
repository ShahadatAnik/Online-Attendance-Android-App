package edu.ewubd.onlineattendance2019160068;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridView;
    CourseAdapter courseAdapter;
    ArrayList<CourseList> arrayList;
    boolean doubleBackToExitPressedOnce = false;

    private String URL = "https://muthosoft.com/univ/attendance/report.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = findViewById(R.id.gridview);
        arrayList = new ArrayList<>();

        findViewById(R.id.btn_exit).setOnClickListener(v->finish());

        String[] keys = {"my_courses", "sid"};
        String[] values = {"true", "2019160068"};
        httpRequest(keys, values);

    }
    @SuppressLint("StaticFieldLeak")
    private void httpRequest(final String keys[], final String values[]){
        new AsyncTask<Void, Void, ArrayList>(){
            @Override
            protected ArrayList<CourseList> doInBackground(Void... param){
                try{
                    List<NameValuePair> params = new ArrayList<NameValuePair>();
                    for(int i=0; i<keys.length; i++){
                        params.add(new BasicNameValuePair(keys[i], values[i]));
                    }

                    String data = JSONParser.getInstance().makeHttpRequest(URL, "POST", params);
                    ArrayList<CourseList> arrayList = new ArrayList<>();
                    CourseList courseList = new CourseList(data);
                    arrayList.add(courseList);

                    return arrayList;
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList arrayList){
                if(arrayList != null){
                    try{
                        super.onPostExecute(arrayList);
                        loadData(arrayList);
                    }
                    catch(Exception ex){
                        ex.printStackTrace();
                    }
                }
            }
        }.execute();

    }
    void loadData(ArrayList arrayList){
        courseAdapter = new CourseAdapter(this, arrayList);
        gridView.setAdapter(courseAdapter);
        courseAdapter.notifyDataSetChanged();

    }
    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("@mainActivity onStart");
    }
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}