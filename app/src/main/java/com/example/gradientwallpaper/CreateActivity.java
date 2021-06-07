package com.example.gradientwallpaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import yuku.ambilwarna.AmbilWarnaDialog;

import static android.content.ContentValues.TAG;

public class CreateActivity extends AppCompatActivity {
    ImageView btnBack, color1, color2, color3, color4, straighten,twine,view,next;
    ImageView imageGradient;
    int currentColor;
    private int currentColor1,currentColor2,currentColor3,currentColor4,tra,twi,currenWay;
    private int[] colors;
    private List<Integer> listColors;
    private String straightenColor="";
    private String twineColor="";
    private int[] array;
// Straighten: BOTTOM_TOP,TOP_BOTTOM,LEFT_RIGHT,RIGHT_LEFT
// Twine: TL_BR,BL_TR,BR_TL,TR_BL
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        addControls();
        addEvents();
        colors=new int[4];
        listColors=new ArrayList<Integer>();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        color1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog1(true);
            }
        });
        color2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentColor1==0){
                    Toast.makeText(CreateActivity.this,"Vui lòng chọn màu phía trước!",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    openDialog2(false);
                }
            }
        });
        color3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentColor2==0){
                    Toast.makeText(CreateActivity.this,"Vui lòng chọn màu phía trước!",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    openDialog3(false);
                }
            }
        });
        color4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentColor3==0){
                    Toast.makeText(CreateActivity.this,"Vui lòng chọn màu phía trước!",Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    openDialog4(false);
                }
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bitmap bitmap=getViewBitmap(imageGradient);
                Intent intent=new Intent();
                intent.setClass(CreateActivity.this,ViewActivity.class);
//                ByteArrayOutputStream bs = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG,100,bs);
//                intent.putExtra("byteArray",bs.toByteArray());
                Bundle bundle=new Bundle();
                bundle.putIntegerArrayList("MY_COLORS", (ArrayList<Integer>) listColors);
                bundle.putInt("CURRENT_WAY",currenWay);
                bundle.putInt("CURRENT_COLOR1",currentColor1);
                intent.putExtra("ALL_COLORS",bundle);
                startActivity(intent);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(CreateActivity.this,NextActivity.class);
                Bundle bundle=new Bundle();
                bundle.putIntegerArrayList("MY_COLORS1", (ArrayList<Integer>) listColors);
                bundle.putInt("CURRENT_WAY1",currenWay);
                bundle.putInt("CURRENT_COLOR11",currentColor1);
                intent.putExtra("ALL_COLORS1",bundle);
                startActivity(intent);
            }
        });
        straighten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentColor1==0){
                    return;
                }else{


                twine.setBackgroundColor(0);
                straighten.setBackgroundColor(ContextCompat.getColor(CreateActivity.this, R.color.teal_200));
                if (tra==0){
                    setColorCardGradientBT();
                    tra+=1;
                    twi=0;
                    currenWay=1;
                }else if (tra==1){
                    setColorCardGradientLR();
                    tra+=1;
                    currenWay=2;
                }else if (tra==2){
                    setColorCardGradientRL();
                    tra+=1;
                    currenWay=3;
                }else if (tra==3){
                    setColorCardGradientTB();
                    tra=0;
                    currenWay=4;
                }
            }
            }

        });
        twine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentColor1==0){
                    return;
                }else{
                straighten.setBackgroundColor(0);
                twine.setBackgroundColor(ContextCompat.getColor(CreateActivity.this, R.color.teal_200));
                if (twi==0){
                    setColorCardGradientBL_TR();
                    twi+=1;
                    currenWay=5;
                }else if (twi==1){
                    setColorCardGradientBR_TL();
                    twi+=1;
                    currenWay=6;
                }else if (twi==2){
                    setColorCardGradientTL_BR();
                    twi+=1;
                    currenWay=7;
                }else if (twi==3){
                    setColorCardGradientTR_BL();
                    twi=0;
                    currenWay=8;
                }
            }
            }
        });
    }

    private void addControls() {
        btnBack=findViewById(R.id.btnBack);
        color1=findViewById(R.id.color1);
        color2=findViewById(R.id.color2);
        color3=findViewById(R.id.color3);
        color4=findViewById(R.id.color4);
        view=findViewById(R.id.view);
        next=findViewById(R.id.next);
        straighten=findViewById(R.id.straighten);
        twine=findViewById(R.id.twine);
        imageGradient=findViewById(R.id.imageGradient);
    }
    public void openDialog1(boolean supportAlpha){
        AmbilWarnaDialog ambilWarnaDialog=new AmbilWarnaDialog(this, currentColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Window win=getWindow();
                win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    win.setStatusBarColor(color);
                }
                currentColor1=color;
                colors[0]=currentColor1;
                if (listColors.isEmpty()){
                    listColors.add(0,currentColor1);
                }else{
                    listColors.set(0,currentColor1);
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    array = listColors.stream().mapToInt(i->i).toArray();
                }
                if (currentColor1!=0){
                    color1.setBackgroundColor(currentColor1);
                    setColorCardGradientTB();
                    color1.setImageResource(0);
                    next.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                }else{
                    return;
                }

            }
        });
        ambilWarnaDialog.show();
    }
    public void openDialog2(boolean supportAlpha){
        AmbilWarnaDialog ambilWarnaDialog=new AmbilWarnaDialog(this, currentColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Window win=getWindow();
                win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    win.setStatusBarColor(color);
                }
                currentColor2=color;
                if (listColors.size()<2){
                    listColors.add(currentColor2);
                }else {
                    listColors.set(1,currentColor2);
                }
                colors[1]=currentColor2;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    array = listColors.stream().mapToInt(i->i).toArray();
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    array = listColors.stream().mapToInt(i->i).toArray();
                }
                if (currentColor2!=0){
                    color2.setBackgroundColor(currentColor2);
                    setColorCardGradientTB();
                    color2.setImageResource(0);
                    next.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                }else{
                    return;
                }

            }
        });
        ambilWarnaDialog.show();
    }
    public void openDialog3(boolean supportAlpha){
        AmbilWarnaDialog ambilWarnaDialog=new AmbilWarnaDialog(this, currentColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Window win=getWindow();
                win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    win.setStatusBarColor(color);
                }
                currentColor3=color;
                if (listColors.size()<3){
                    listColors.add(currentColor3);
                }else {
                    listColors.set(2,currentColor3);
                }
                colors[2]=currentColor3;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    array = listColors.stream().mapToInt(i->i).toArray();
                }
                if (currentColor3!=0){
                    color3.setBackgroundColor(currentColor3);
                    setColorCardGradientTB();
                    color3.setImageResource(0);
                    next.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                }else{
                    return;
                }

            }
        });
        ambilWarnaDialog.show();
    }
    public void openDialog4(boolean supportAlpha){
        AmbilWarnaDialog ambilWarnaDialog=new AmbilWarnaDialog(this, currentColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {

            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                Window win=getWindow();
                win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                currentColor4=color;
                if (listColors.size()<4){
                    listColors.add(currentColor4);
                }else {
                    listColors.set(3,currentColor4);
                }
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    array = listColors.stream().mapToInt(i->i).toArray();
                }
                colors[3]=currentColor4;
                if (currentColor4!=0){
                    color4.setImageResource(0);
                    color4.setBackgroundColor(currentColor4);
                    setColorCardGradientTB();
                    next.setVisibility(View.VISIBLE);
                    view.setVisibility(View.VISIBLE);
                }else{
                    return;
                }

            }
        });
        ambilWarnaDialog.show();
    }
    public void setColorCardGradientTB(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageGradient.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, array);
            imageGradient.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientLR(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageGradient.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, array);
            imageGradient.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientBT(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageGradient.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.BOTTOM_TOP, array);
            imageGradient.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientRL(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageGradient.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.RIGHT_LEFT, array);
            imageGradient.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientTL_BR(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageGradient.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR, array);
            imageGradient.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientBL_TR(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageGradient.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.BL_TR, array);
            imageGradient.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientBR_TL(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageGradient.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.BR_TL, array);
            imageGradient.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientTR_BL(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageGradient.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.TR_BL, array);
            imageGradient.setBackground(gradientDrawable);
        }
    }
    public static Bitmap getViewBitmap(View v) {
        v.clearFocus();
        v.setPressed(false);

        boolean willNotCache = v.willNotCacheDrawing();
        v.setWillNotCacheDrawing(false);

        // Reset the drawing cache background color to fully transparent
        // for the duration of this operation
        int color = v.getDrawingCacheBackgroundColor();
        v.setDrawingCacheBackgroundColor(0);

        if (color != 0) {
            v.destroyDrawingCache();
        }
        v.buildDrawingCache();
        Bitmap cacheBitmap = v.getDrawingCache();
        if (cacheBitmap == null) {
            Log.e(TAG, "failed getViewBitmap(" + v + ")", new RuntimeException());
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(cacheBitmap);

        // Restore the view
        v.destroyDrawingCache();
        v.setWillNotCacheDrawing(willNotCache);
        v.setDrawingCacheBackgroundColor(color);

        return bitmap;
    }
// Straighten: BOTTOM_TOP,TOP_BOTTOM,LEFT_RIGHT,RIGHT_LEFT
// Twine: TL_BR,BL_TR,BR_TL,TR_BL
}