package com.example.gradientwallpaper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.gradientwallpaper.fragment.SavedFragment;
import com.example.gradientwallpaper.fragment.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    public static ViewPager viewPager;
    private static final int REQUEST_PERMISSION_CODE=1;
    String folderName="Gradient Wallpaper";
    DrawerLayout drawerLayout;
    FragmentManager fragmentManager;
    SavedFragment savedFragment= new SavedFragment();
    static ViewPagerAdapter viewPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addEvents() {
//        fragmentManager=getSupportFragmentManager();
//        fragmentManager.beginTransaction()
//                .add(R.id.viewPager,savedFragment)
//                .show(savedFragment)
//                .commit();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.item1).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.item3).setChecked(true);
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.item1:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.item3:
                        if (ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                ==PackageManager.PERMISSION_GRANTED){
                            createDirectoty(folderName);
                            viewPager.setCurrentItem(1);
                            savedFragment.onResume();
                        }else {
                            checkStoragePermission();
                        }
                        break;
                    case R.id.item2:
                        xuLyMoManHinhCreate();
                        break;
                }
                return true;
            }
        });
    }

    private void xuLyMoManHinhCreate() {
        Intent intent=new Intent(MainActivity.this,CreateActivity.class);
        startActivity(intent);

    }

    private void addControls() {
        bottomNavigationView=findViewById(R.id.bottom_nav);
        viewPager=findViewById(R.id.viewPager);
        viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        drawerLayout=findViewById(R.id.drawerLayout);
    }
    private void checkStoragePermission() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED
                &&checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this,R.string.permission_granted,Toast.LENGTH_LONG).show();
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
                    .setTitle(R.string.permission_needed)
                    .setMessage(R.string.message_permission_needed)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[] {Manifest.permission.READ_EXTERNAL_STORAGE,
                                            Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_PERMISSION_CODE );
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
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
        Toast.makeText(MainActivity.this,"FeedBack",Toast.LENGTH_SHORT).show();
    }
    public void clickRate(View view){
        Toast.makeText(MainActivity.this,"Rate",Toast.LENGTH_SHORT).show();
    }
    public void clickShare(View view){
        Toast.makeText(MainActivity.this,"Share",Toast.LENGTH_SHORT).show();
    }
    public void clickPrivacyPolicy(View view){
        Toast.makeText(MainActivity.this,"Privacy Policy",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
    public void transactFragment(Fragment fragment, boolean reload) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        if (reload) {
            getSupportFragmentManager().popBackStack();
        }
        transaction.replace(R.id.viewPager, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MainActivity.refreshFragments();
    }
    public static void refreshFragments(){
       viewPager.setAdapter(viewPagerAdapter);
    }
}