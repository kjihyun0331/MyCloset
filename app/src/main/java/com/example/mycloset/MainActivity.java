package com.example.mycloset;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.window.SplashScreen;
import android.window.SplashScreenView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity{

    File file;
    TextView cityView,weatherView,tempView;
    Button btnWeather,btnCody;
    static RequestQueue requestQueue;
    double lat,lon;
    ImageView iv1,iv2,iv3;
    ImageButton ibAdd,ibCloset;
    private FusedLocationProviderClient fusedLocationClient;    // 현재 위치를 가져오기 위한 변수
    int REQUEST_CODE = 1;
    int howMany[] = new int[12];
    Random random = new Random();
    double temp=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("오늘 뭐입지?");

        cityView = findViewById(R.id.cityView);
        weatherView = findViewById(R.id.weatherView);
        tempView = findViewById(R.id.tempView);
        btnWeather=findViewById(R.id.btnWeather);
        iv1=findViewById(R.id.iv1);
        iv2=findViewById(R.id.iv2);
        iv3=findViewById(R.id.iv3);
        ibAdd=findViewById(R.id.ibAdd);
        ibCloset=findViewById(R.id.ibCloset);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SharedPreferences sp = getSharedPreferences("sp",Activity.MODE_PRIVATE);
        howMany[0]=sp.getInt("many0",0);
        howMany[1]=sp.getInt("many1",1);
        howMany[2]=sp.getInt("many2",6);
        howMany[3]=sp.getInt("many3",5);
        howMany[4]=sp.getInt("many4",0);
        howMany[5]=sp.getInt("many5",1);
        howMany[6]=sp.getInt("many6",5);
        howMany[7]=sp.getInt("many7",2);
        howMany[8]=sp.getInt("many8",0);
        howMany[9]=sp.getInt("many9",0);
        howMany[10]=sp.getInt("many10",3);
        howMany[11]=sp.getInt("many11",3);

        btnWeather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
                //날씨데이터 활용
                CurrentCall(lat,lon);
            }
        });


        btnCody=findViewById(R.id.btnCody);
        btnCody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int out=-1,top=-1,bot=-1;

                if(temp<-50){
                    Toast.makeText(getApplicationContext(),"날씨 정보 없음",Toast.LENGTH_SHORT).show();
                }else if(temp<5){
                    out=3;
                    top=7;
                    bot=11;
                }else if(temp<9){
                    out=2;
                    out=7;
                    bot=11;
                }else if(temp<16){
                    out=0;
                    top=6;
                    bot=10;
                }else if(temp<22){
                    out=1;
                    top=5;
                    bot=9;
                }else{
                    top=4;
                    bot=8;
                }
                File imgOut = new  File("/sdcard/"+out+random.nextInt(howMany[out])+".jpg");
                File imgTop = new  File("/sdcard/"+top+random.nextInt(howMany[top])+".jpg");
                File imgBot = new  File("/sdcard/"+bot+random.nextInt(howMany[bot])+".jpg");
                if(imgOut.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgOut.getAbsolutePath());
                    iv1.setImageBitmap(myBitmap);
                }
                if(imgTop.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgTop.getAbsolutePath());
                    iv2.setImageBitmap(myBitmap);
                }
                if(imgBot.exists()){
                    Bitmap myBitmap = BitmapFactory.decodeFile(imgBot.getAbsolutePath());
                    iv3.setImageBitmap(myBitmap);
                }

            }
        });

        //volley를 쓸 때 큐가 비어있으면 새로운 큐 생성하기
        if(requestQueue == null){
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),AddActivity.class);
                intent.putExtra("HowMany",howMany);
                startActivityForResult(intent,0);
            }
        });

        ibCloset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ClosetActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences sp = getSharedPreferences("sp",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("many0",howMany[0]);
        editor.putInt("many1",howMany[1]);
        editor.putInt("many2",howMany[2]);
        editor.putInt("many3",howMany[3]);
        editor.putInt("many4",howMany[4]);
        editor.putInt("many5",howMany[5]);
        editor.putInt("many6",howMany[6]);
        editor.putInt("many7",howMany[7]);
        editor.putInt("many8",howMany[8]);
        editor.putInt("many9",howMany[9]);
        editor.putInt("many10",howMany[10]);
        editor.putInt("many11",howMany[11]);

        editor.apply();
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(resultCode==RESULT_OK){
            int num = data.getIntExtra("selected",0);
            if(num!=-1){
                howMany[num]++;
            }
        }
    }

    private void CurrentCall(double lat,double lon){

        String url = "http://api.openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&units=metric&appid=4867f95c4113e5d2f02f6e9c5624958e";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(String response) {

                try {
                    //api로 받은 파일 jsonobject로 새로운 객체 선언
                    JSONObject jsonObject = new JSONObject(response);

                    //도시 키값 받기
                    String city = jsonObject.getString("name");
                    cityView.setText(city);

                    //날씨 키값 받기
                    JSONArray weatherJson = jsonObject.getJSONArray("weather");
                    JSONObject weatherObj = weatherJson.getJSONObject(0);
                    String weather = weatherObj.getString("description");
                    weatherView.setText(weather);



                    //기온 키값 받기
                    JSONObject tempK = new JSONObject(jsonObject.getString("main"));
                    double tempDo = (Math.round((tempK.getDouble("temp"))));
                    tempView.setText(tempDo +  "°C");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }

        };

        request.setShouldCache(false);
        requestQueue.add(request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {   // 권한요청이 허용된 경우

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    Toast.makeText(this, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show();    // 권한요청이 거절된 경우
                    return;
                }

                // 위도와 경도를 가져온다.
                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // Got last known location. In some rare situations this can be null.
                                if (location != null) {
                                    // 파라미터로 받은 location을 통해 위도, 경도 정보 get
                                    lat=location.getLatitude();
                                    lon=location.getLongitude();
                                }
                            }
                        });
            }
            else{
                Toast.makeText(this, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show();    // 권한요청이 거절된 경우
            }
        }
    }
}