package com.example.gradientwallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.gradientwallpaper.fragment.CollectionFragment;
import com.example.gradientwallpaper.fragment.SavedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private static final int REQUEST_PERMISSION_CODE=1;
    String folderName="Gradient Wallpaper";
    DrawerLayout drawerLayout;
    private Fragment selectedFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }
    private BottomNavigationView.OnNavigationItemSelectedListener navListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    selectedFragment=null;
                    switch (item.getItemId()){
                        case R.id.item1:
                            selectedFragment=new CollectionFragment();
                            break;
                        case R.id.item2:
                            xuLyMoManHinhCreate();
                            break;
                        case R.id.item3:
                            selectedFragment= new SavedFragment();
                            if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                    ==PackageManager.PERMISSION_GRANTED){
                                createDirectoty(folderName);
                                selectedFragment=new SavedFragment();
                            }else {
                                checkPermission();
                            }

                            break;
                    }
                    if (item.getItemId()!=R.id.item2){
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();
                    }
                    return true;
                }
            };

    private void addEvents() {
    }

    private void xuLyMoManHinhCreate() {
        Intent intent=new Intent(MainActivity.this,CreateActivity.class);
        startActivity(intent);
    }

    private void addControls() {
        bottomNavigationView=findViewById(R.id.bottom_nav);
        drawerLayout=findViewById(R.id.drawerLayout);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new CollectionFragment()).commit();
    }
//    private void checkStoragePermission() {
//        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
//            return;
//        }
//
//        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
//                &&checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(this,R.string.permission_granted,Toast.LENGTH_LONG).show();
//            createDirectoty(folderName);
//        }else {
//            requestPermission();
//        }
//    }
//
//    private void requestPermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            new AlertDialog.Builder(this)
//                    .setTitle(R.string.permission_needed)
//                    .setMessage(R.string.message_permission_needed)
//                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            ActivityCompat.requestPermissions(MainActivity.this,
//                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
//                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_CODE );
//                            if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                                    ==PackageManager.PERMISSION_GRANTED){
//                                createDirectoty(folderName);
//                                selectedFragment=new SavedFragment();
//                            }
//                        }
//                    })
//                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.dismiss();
//                        }
//                    })
//                    .create().show();
//        } else {
//            ActivityCompat.requestPermissions(this,
//                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
//                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION_CODE);
//        }
//    }
    public void createDirectoty(String folderName) {
        File file=new File(Environment.getExternalStorageDirectory()+"/Pictures",folderName);
        if (!file.exists()){
            file.mkdirs();
        }else {
            return;
        }
    }
    public void clickMenu(View view){
        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void clickLogo(View view){
        closeDrawer(drawerLayout);
    }

    private void closeDrawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void clickFeedback(View view){
        sendFeedback(MainActivity.this,"App quá đẹp!");
    }
    public void clickRate(View view){
        Toast.makeText(MainActivity.this,"Rate",Toast.LENGTH_SHORT).show();
    }
    public void clickShare(View view){
        shareApp(MainActivity.this);
    }
    public void clickPrivacyPolicy(View view){
        policy(MainActivity.this);
    }
    public void clickMoreApps(View view){
        moreApps(MainActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
    public void checkPermission(){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return;
        }
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){

        }else{
            String[] permission={Manifest.permission.WRITE_EXTERNAL_STORAGE};
            requestPermissions(permission,REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SavedFragment()).commit();
            }else{
                Toast.makeText(MainActivity.this,"permission denied",Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (bottomNavigationView.getSelectedItemId()==R.id.item3){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new SavedFragment()).commit();
        }else{
            bottomNavigationView.setSelectedItemId(R.id.item1);
        }

    }
    public void moreApps(Context context) {
        final String devName = "No ONLY but PERFECT"; // getPackageName() from Context or Activity object
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pub:" + devName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/search?q=pub:" + devName)));
        }
    }

    public void shareApp(Context context) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "ten app cho vao day for You.");
            String shareMessage = "loi moi viet vao day";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=cho packagename vao day";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            context.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch (Exception e) {
            //e.toString();
        }

    }



    public void sendFeedback(Context context, String content) {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + "ktvmobisolution@gmail.com"));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback about Gradient Wallpaper application");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Dear...,");

        try {
            context.startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }


    public void policy(Context context) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("link policy cho vao day. khi nao chi gui sau"));
            context.startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(context, "No application can handle this request."
                    + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}