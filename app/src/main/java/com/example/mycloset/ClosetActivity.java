package com.example.mycloset;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class ClosetActivity extends AppCompatActivity {
    ImageView clBot1,clBot2,clBot3,clBot4,clOut1,clOut2,clOut3,clOut4,clTop1,clTop2,clTop3,clTop4;
    ImageButton imHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.closet);

        setTitle("내 옷장 보기");

        imHome =findViewById(R.id.ibHome);

        clBot1=findViewById(R.id.clBot1);
        clBot2=findViewById(R.id.clBot2);
        clBot3=findViewById(R.id.clBot3);
        clBot4=findViewById(R.id.clBot4);

        clTop1=findViewById(R.id.clTop1);
        clTop2=findViewById(R.id.clTop2);
        clTop3=findViewById(R.id.clTop3);
        clTop4=findViewById(R.id.clTop4);

        clOut1=findViewById(R.id.clOut1);
        clOut2=findViewById(R.id.clOut2);
        clOut3=findViewById(R.id.clOut3);
        clOut4=findViewById(R.id.clOut4);

        File out1 = new  File("/sdcard/10.jpg");
        File out2 = new  File("/sdcard/20.jpg");
        File out3 = new  File("/sdcard/30.jpg");
        File out4 = new  File("/sdcard/24.jpg");


        File top1 = new  File("/sdcard/50.jpg");
        File top2 = new  File("/sdcard/60.jpg");
        File top3 = new  File("/sdcard/70.jpg");
        File top4 = new  File("/sdcard/62.jpg");

        File bot1 = new  File("/sdcard/100.jpg");
        File bot2 = new  File("/sdcard/102.jpg");
        File bot3 = new  File("/sdcard/111.jpg");
        File bot4 = new  File("/sdcard/112.jpg");

        Bitmap o1 = BitmapFactory.decodeFile(out1.getAbsolutePath());
        clOut1.setImageBitmap(o1);
        Bitmap o2 = BitmapFactory.decodeFile(out2.getAbsolutePath());
        clOut2.setImageBitmap(o2);
        Bitmap o3 = BitmapFactory.decodeFile(out3.getAbsolutePath());
        clOut3.setImageBitmap(o3);
        Bitmap o4 = BitmapFactory.decodeFile(out4.getAbsolutePath());
        clOut4.setImageBitmap(o4);

        Bitmap t1 = BitmapFactory.decodeFile(top1.getAbsolutePath());
        clTop1.setImageBitmap(t1);
        Bitmap t2 = BitmapFactory.decodeFile(top2.getAbsolutePath());
        clTop2.setImageBitmap(t2);
        Bitmap t3 = BitmapFactory.decodeFile(top3.getAbsolutePath());
        clTop3.setImageBitmap(t3);
        Bitmap t4 = BitmapFactory.decodeFile(top3.getAbsolutePath());
        clTop4.setImageBitmap(t4);

        Bitmap b1 = BitmapFactory.decodeFile(bot1.getAbsolutePath());
        clBot1.setImageBitmap(b1);
        Bitmap b2 = BitmapFactory.decodeFile(bot2.getAbsolutePath());
        clBot2.setImageBitmap(b2);
        Bitmap b3 = BitmapFactory.decodeFile(bot3.getAbsolutePath());
        clBot3.setImageBitmap(b3);
        Bitmap b4 = BitmapFactory.decodeFile(bot3.getAbsolutePath());
        clBot4.setImageBitmap(b4);

        imHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

}
