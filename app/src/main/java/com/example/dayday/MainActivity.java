package com.example.dayday;

import android.Manifest;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

//네비게이션뷰에서 아이템 클릭시 다음 동작하기 위해 activity에 navigationView의 아이템선택 리스터를 implements
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {
    //위치관리자 객체 참조
    final int PERMISSION =1001;
    private MemoAdapter memoAdapter = new MemoAdapter();

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        checkPermission();

        setContentView(R.layout.activity_main);

        initLayout();

        //ecycleView 초기화
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(memoAdapter);
        //어댑터의 아이템 로드


        getLocation();
    }

    //권한을 체크
    public void checkPermission(){
        //각 권한들이 설정되어있는지 확인
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ){
            //권한이 하나라도 설정이 안되어 있으면 permission을 요청하는 팝업
            ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION
            );
        }

    }

    //reqeustPermissions로 권한요청 한것에 대한 결과가 반환
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION:
                //grantResult는 requestPermission에 요청된 String[]순서로 들어온다
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //권한 허가
                }
                else{
                    //권한거부
                }

                Context context =  MainActivity.this;
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:"+ context.getPackageName()));
                startActivityForResult(intent, 0);

                return ;
        }
    }

    //현재 위치정보를 받아오기 https://bottlecok.tistory.com/54
    //https://webnautes.tistory.com/1315 권한까지
    public void getLocation(){
        LocationManager lm=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //가장최근 위치정보
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }

        //위치 리스너는 위치정보를 전달할때 호출됨 onLocationChnaged()메서드 안에 위치정보 처리를 작업
        LocationListener gpsLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String provider=location.getProvider();
                double longitude=location.getLongitude();
                double latitude=location.getLatitude();
                double altitude=location.getAltitude();

//                txtResult.setText("위치정보 : " + provider + "\n" +
//                        "위도 : " + longitude + "\n" +
//                        "경도 : " + latitude + "\n" +
//                        "고도  : " + altitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
    }

    /*
    drawawer 세팅하기
     */
    //drawertoolbar.xml에서 구현된 메뉴를 파라미터로 ㅂ ㅏㄷ음
    //해당 메뉴 아이템이 선택되면 네비게이션 뷰를 닫아주고 종료
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "item1 clicked..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                Toast.makeText(this, "item2 clicked..", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item3:
                Toast.makeText(this, "item3 clicked..", Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    private void initLayout() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);

        drawerLayout = (DrawerLayout) findViewById(R.id.dl_main_drawer_root);
        //네비게이션 뷰객체에서 이벤트를 받으려면 리스터를 세팅해줘야함
        navigationView = (NavigationView) findViewById(R.id.nv_main_navigation_root);
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.drawer_open,
                R.string.drawer_close
        );
        drawerLayout.addDrawerListener(drawerToggle);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*
    여기까지가 drawer
     */
}
