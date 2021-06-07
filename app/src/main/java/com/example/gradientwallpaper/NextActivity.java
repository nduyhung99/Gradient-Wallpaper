package com.example.gradientwallpaper;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class NextActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 1;
    ImageView save,home,apply,imageView;
    String folderName="Gradient Wallpaper";
    OutputStream outputStream;
    private List<Integer> listColors;
    private int[] array;
    private int color1,color2,color3,color4;
    private int currentWay, currentColor1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);
        addControls();
        addEvents();
    }

    private void addEvents() {
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(NextActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        ==PackageManager.PERMISSION_GRANTED){
                    createDirectoty(folderName);
                    saveImage();
                }else {
                    checkStoragePermission();
                }
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(NextActivity.this)
                        .setTitle("Cài đặt hình nền!")
                        .setMessage("Cài đặt hình thành hình nền điện thoại.")
                        .setPositiveButton("xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setPhoneWallpaper();
//                                createDirectoty(folderName);
//                                saveImage();
                            }
                        })
                        .setNegativeButton("không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }
        });
    }

    private void addControls() {
        save=findViewById(R.id.save);
        home=findViewById(R.id.home);
        apply=findViewById(R.id.apply);
        imageView=findViewById(R.id.imageView);
        listColors=new ArrayList<Integer>();
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("ALL_COLORS1");
        listColors=bundle.getIntegerArrayList("MY_COLORS1");
        currentWay=bundle.getInt("CURRENT_WAY1");
        currentColor1=bundle.getInt("CURRENT_COLOR11");
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
        }else {
            GradientDrawable gradientDrawable = new GradientDrawable(
                    GradientDrawable.Orientation.TOP_BOTTOM,
                    array);
            imageView.setBackground(gradientDrawable);
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
    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,"Permission granted",Toast.LENGTH_LONG).show();
            createDirectoty(folderName);
        }else {
            requestPermission();
        }
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(NextActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_CODE );
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
        }
    }
    private void createDirectoty(String folderName) {
        File file=new File(Environment.getExternalStorageDirectory()+"/Pictures",folderName);
        if (!file.exists()){
            file.mkdirs();
        }else {
            return;
        }
    }
    private void saveImage() {
        Bitmap bitmap1=getViewBitmap(imageView);
        String fileName=String.format("%d.png",System.currentTimeMillis());
        File outFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                +"/Pictures/"+folderName,fileName);
        try {
            outputStream=new FileOutputStream(outFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        bitmap1.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        try {
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void setPhoneWallpaper() {
        Bitmap bitmap=getViewBitmap(imageView);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            wallpaperManager.setBitmap(bitmap);
            Toast.makeText(getApplicationContext(),"Cài hình nền thành công!",Toast.LENGTH_SHORT).show();
        }
        catch(IOException e){
            Toast.makeText(getApplicationContext(),"Cài hình nền thất bại!",Toast.LENGTH_SHORT).show();

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