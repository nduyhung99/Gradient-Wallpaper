package com.example.gradientwallpaper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    ImageView imageView;
    private int color1,color2,color3,color4;
    private List<Integer> listColors;
    private int[] array;
    private int currentWay, currentColor1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_view);
        listColors=new ArrayList<Integer>();
        imageView=findViewById(R.id.imageView);
//        Bitmap b = BitmapFactory.decodeByteArray(
//                getIntent().getByteArrayExtra("byteArray"),0,getIntent()
//                        .getByteArrayExtra("byteArray").length);
//        imageView.setImageBitmap(b);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("ALL_COLORS");
        listColors=bundle.getIntegerArrayList("MY_COLORS");
        currentWay=bundle.getInt("CURRENT_WAY");
        currentColor1=bundle.getInt("CURRENT_COLOR1");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            array = listColors.stream().mapToInt(i->i).toArray();
        }
        if (currentWay==0) {
            setLayoutColorGradientTB();}
        else{
            switch (currentWay) {
                case 1:
                    setColorCardGradientBT();
                    break;
                case 2:
                    setColorCardGradientLR();
                    break;
                case 3:
                    setColorCardGradientRL();
                    break;
                case 4:
                    setLayoutColorGradientTB();
                    break;
                case 5:
                    setColorCardGradientBL_TR();
                    break;
                case 6:
                    setColorCardGradientBR_TL();
                    break;
                case 7:
                    setColorCardGradientTL_BR();
                    break;
                case 8:
                    setColorCardGradientTR_BL();
                    break;

            }
        }
    }
    private void setLayoutColorGradientTB(){
        if (listColors.size()==0){
            return;
        }else if (listColors.size()==1){
            imageView.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM, array);
            imageView.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientLR(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageView.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.LEFT_RIGHT, array);
            imageView.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientBT(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageView.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.BOTTOM_TOP, array);
            imageView.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientRL(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageView.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.RIGHT_LEFT, array);
            imageView.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientTL_BR(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageView.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.TL_BR, array);
            imageView.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientBL_TR(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageView.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.BL_TR, array);
            imageView.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientBR_TL(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageView.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.BR_TL, array);
            imageView.setBackground(gradientDrawable);
        }
    }
    public void setColorCardGradientTR_BL(){
        if (listColors.size()==0){
            return;
        }else if(listColors.size()==1){
            imageView.setBackgroundColor(currentColor1);
        }else{
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.TR_BL, array);
            imageView.setBackground(gradientDrawable);
        }
    }
}