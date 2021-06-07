package com.example.gradientwallpaper;

import androidx.annotation.RequiresApi;
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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static android.content.ContentValues.TAG;

public class ViewCollectionActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSION_CODE = 1;
    RelativeLayout layoutView;
    ImageButton btnBack,btnSave,btnApply;
    String folderName="Gradient Wallpaper";
    OutputStream outputStream;
    ImageView imageView;
    private static Gradient gradient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_collection);
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(ViewCollectionActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        ==PackageManager.PERMISSION_GRANTED){
                    createDirectoty(folderName);
                    saveImage();
                }else {
                    checkStoragePermission();
                }
            }
        });
        btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(ViewCollectionActivity.this)
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

    private void createDirectoty(String folderName) {
        File file=new File(Environment.getExternalStorageDirectory()+"/Pictures",folderName);
        if (!file.exists()){
            file.mkdirs();
        }else {
            return;
        }
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
                            ActivityCompat.requestPermissions(ViewCollectionActivity.this,
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

    private void addControls() {
        layoutView=findViewById(R.id.layoutView);
        btnApply=findViewById(R.id.btnApply);
        btnBack=findViewById(R.id.btnBack);
        btnSave=findViewById(R.id.btnSave);
        imageView=findViewById(R.id.imageView);
        Intent intent=getIntent();
        gradient= (Gradient) intent.getSerializableExtra("GRADIENT");
        int gradientColor=gradient.getResGradient();
        layoutView.setBackground(getResources().getDrawable(gradient.getResGradient()));
        imageView.setImageDrawable(getResources().getDrawable(gradient.getResGradient()));
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
}