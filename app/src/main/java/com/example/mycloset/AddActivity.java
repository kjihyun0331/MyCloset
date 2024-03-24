package com.example.mycloset;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.pedro.library.AutoPermissions;
import com.pedro.library.AutoPermissionsListener;

import java.io.File;

public class AddActivity extends AppCompatActivity implements AutoPermissionsListener {

    Button btnPic,btnSave;
    ImageView ivPic;
    RadioGroup rgType,rgOut,rgTop,rgBot;
    File file;

    int selected = -1;
    int howManyValue=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);
        setTitle("옷 추가하기");

        Intent intent = getIntent();
        int[] newHow=intent.getIntArrayExtra("HowMany");

        ivPic=findViewById(R.id.ivPic);

        AutoPermissions.Companion.loadAllPermissions(this,101);

        btnSave=findViewById(R.id.ibSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent outIntent = new Intent(getApplicationContext(),MainActivity.class);
                outIntent.putExtra("selected",selected);
                setResult(RESULT_OK,outIntent);
                finish();
            }
        });

        rgType = findViewById(R.id.rgType);
        rgBot=findViewById(R.id.rgBot);
        rgOut=findViewById(R.id.rgOut);
        rgTop=findViewById(R.id.rgTop);

        rgTop.setVisibility(View.INVISIBLE);
        rgOut.setVisibility(View.INVISIBLE);
        rgBot.setVisibility(View.INVISIBLE);

        rgType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.typeOut){
                    rgOut.setVisibility(View.VISIBLE);
                    rgBot.setVisibility(View.INVISIBLE);
                    rgTop.setVisibility(View.INVISIBLE);
                }else if(i==R.id.typeBot){
                    rgOut.setVisibility(View.INVISIBLE);
                    rgBot.setVisibility(View.VISIBLE);
                    rgTop.setVisibility(View.INVISIBLE);
                }else if(i==R.id.typeTop){
                    rgOut.setVisibility(View.INVISIBLE);
                    rgBot.setVisibility(View.INVISIBLE);
                    rgTop.setVisibility(View.VISIBLE);
                }else{
                    rgOut.setVisibility(View.INVISIBLE);
                    rgBot.setVisibility(View.INVISIBLE);
                    rgTop.setVisibility(View.INVISIBLE);
                }
            }
        });

        rgOut.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.out1:
                        selected=0;
                        break;
                    case R.id.out2:
                        selected=1;
                        break;
                    case R.id.out3:
                        selected=2;
                        break;
                    case R.id.out4:
                        selected=3;
                        break;
                    default:
                        break;
                }
            }
        });

        rgTop.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.top1:
                        selected=4;
                        break;
                    case R.id.top2:
                        selected=5;
                        break;
                    case R.id.top3:
                        selected=6;
                        break;
                    case R.id.top4:
                        selected=7;
                        break;
                    default:
                        break;
                }
            }
        });

        rgBot.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.bot1:
                        selected=8;
                        break;
                    case R.id.bot2:
                        selected=9;
                        break;
                    case R.id.bot3:
                        selected=10;
                        break;
                    case R.id.bot4:
                        selected=11;
                        break;
                    default:
                        break;
                }
            }
        });

        btnPic=findViewById(R.id.btnPic);
        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected==-1){
                    Toast.makeText(getApplicationContext(),"먼저 옷의 유형을 선택하세요.",Toast.LENGTH_SHORT).show();
                }else{
                    howManyValue=newHow[selected];
                    takePicture();
                }
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[],int[] grantResults){
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);

        AutoPermissions.Companion.parsePermissions(this,requestCode,permissions,this);
    }

    @Override
    public void onDenied(int requestCode, String[] permissions) {

    }

    @Override
    public void onGranted(int requestCode, String[] permissions) {

    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==101&&resultCode==RESULT_OK){
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize=8;
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);

            ivPic.setImageBitmap(bitmap);
        }
    }

    public void takePicture(){
        if(file==null){
            file=createFile();
        }

        Uri fileUri = FileProvider.getUriForFile(this,"org.techtown.capture.intent.fileprovider",file);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivityForResult(intent,101);
        }
    }

    private File createFile(){
        int num=howManyValue+selected*10;
        String filename=String.valueOf(num)+".jpg";
        File storageDir = Environment.getExternalStorageDirectory();
        File outFile=new File(storageDir,filename);

        return outFile;
    }
}