package com.example.pogeun;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context mContext = MainActivity.this;
    private ViewGroup mainLayout;   //사이드 나왔을때 클릭방지할 영역
    private ViewGroup viewLayout;   //전체 감싸는 영역
    private ViewGroup sideLayout;   //사이드바만 감싸는 영역
    private Boolean isMenuShow = false;
    private Boolean isExitFlag = false;
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Home_Fragment fragment1 = new Home_Fragment();
    private Music_Fragment fragment2 = new Music_Fragment();
    private Alarm_Fragment fragment3 = new Alarm_Fragment();
    private Option_Fragment fragment4 = new Option_Fragment();
    private UserInfo_Fragment fragment5 = new UserInfo_Fragment();
    private BottomNavigationView bottomNavigationView;

    RelativeLayout add_layout1, add_layout2, add_layout3;
    ImageView add_btn1, add_btn2, add_btn3, share, option, user_icon;
    String end_str = "뒤로가기를 한번 더 누르시면\n앱이 종료됩니다.";
    String share_str = "공유할수 없는 환경입니다.";
    TextView back_main;
    ImageView log_out;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment.isVisible() && fragment.equals(fragment1)) {
                if (isMenuShow) {
                    closeMenu();
                } else {
                    if (isExitFlag) {
                        finish();
                    } else {
                        isExitFlag = true;

                        toastMsg(end_str);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                isExitFlag = false;
                            }
                        }, 2000);
                    }
                }
            } else if (fragment.isVisible() && fragment.equals(fragment5)) {
                bottomNavigationView.setSelectedItemId(R.id.bottom_option);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment4).commitAllowingStateLoss();
            } else {
                bottomNavigationView.setSelectedItemId(R.id.bottom_home);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment1).commitAllowingStateLoss();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.title_color));
        setContentView(R.layout.activity_main);
        init();
        addSideView();  //사이드바 add
        add_btn1 = findViewById(R.id.edit_name_btn);
        add_btn2 = findViewById(R.id.edit_id_btn);
        add_btn3 = findViewById(R.id.edit_email_btn);
        add_layout1 = findViewById(R.id.btn_side_level_1);
        add_layout2 = findViewById(R.id.btn_side_level_2);
        add_layout3 = findViewById(R.id.btn_side_level_3);
        share = findViewById(R.id.share);
        option = findViewById(R.id.option_img);
        user_icon = findViewById(R.id.user_icon);
        back_main = findViewById(R.id.change_btn);
        log_out = findViewById(R.id.log_out);

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(mContext, log_out);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.popmenu, popup.getMenu());
                v.setPadding(0,0,0,20);

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.pop_my_info:
                                bottomNavigationView.setSelectedItemId(R.id.bottom_option);
                                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment5).commitAllowingStateLoss();
                                break;
                            case R.id.pop_log_out:
                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                                toastMsg("로그아웃 하셨습니다.\n새로 로그인 해주세요.");
                                break;
                            case R.id.pop_terminate:
                                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("포근 종료");
                                builder.setMessage("앱을 종료하시겠습니까?");
                                builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        System.exit(0);
                                    }
                                });
                                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                                break;
                        }

                        return true;
                    }
                });

                popup.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        v.setPadding(0,0,0,0);
                    }
                });

                popup.show();//showing popup menu
            }
        });

        bottomNavigationView = findViewById(R.id.navigationView);
        // 첫화면에 띄워야 할 것들 지정해주기
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment1).commitAllowingStateLoss();
        //바텀 네비게이션뷰 안의 아이템들 설정
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.bottom_home: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment1).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bottom_music: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment2).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bottom_alarm: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment3).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.bottom_option: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment4).commitAllowingStateLoss();
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void init() {
        findViewById(R.id.btn_menu).setOnClickListener(this);
        mainLayout = findViewById(R.id.id_main);
        viewLayout = findViewById(R.id.fl_silde);
        sideLayout = findViewById(R.id.view_sildebar);
    }

    private void addSideView() {
        SideBarView sidebar = new SideBarView(mContext);
        sideLayout.addView(sidebar);

        viewLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        sidebar.setEventListener(new SideBarView.EventListener() {

            @Override
            public void btnCancel() {
                closeMenu();
            }

            @Override
            public void btnChild1() {
                closeMenu();
                bottomNavigationView.setSelectedItemId(R.id.bottom_home);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment1).commitAllowingStateLoss();
            }

            @Override
            public void btnChild2() {
                closeMenu();
                bottomNavigationView.setSelectedItemId(R.id.bottom_music);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment2).commitAllowingStateLoss();
            }

            @Override
            public void btnChild3() {
                closeMenu();
                bottomNavigationView.setSelectedItemId(R.id.bottom_alarm);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment3).commitAllowingStateLoss();
            }

            @Override
            public void share() {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String text = "이것을 공유합니다";
                    intent.putExtra(Intent.EXTRA_TEXT, text);
                    Intent chooser = Intent.createChooser(intent, "친구에게 공유하기");
                    startActivity(chooser);
                    closeMenu();
                } catch (Exception e) {
                    e.printStackTrace();
                    toastMsg(share_str);
                    closeMenu();
                }
            }

            @Override
            public void option() {
                closeMenu();
                bottomNavigationView.setSelectedItemId(R.id.bottom_option);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment4).commitAllowingStateLoss();
            }

            @Override
            public void my_page() {
                closeMenu();
                bottomNavigationView.setSelectedItemId(R.id.bottom_option);
                getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment5).commitAllowingStateLoss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_menu) {
            showMenu();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (back_main.getText().toString().equals("music")) {
            bottomNavigationView.setSelectedItemId(R.id.bottom_music);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment2).commitAllowingStateLoss();
        } else {
            bottomNavigationView.setSelectedItemId(R.id.bottom_home);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment1).commitAllowingStateLoss();
        }
    }

    public void closeMenu() {
        isMenuShow = false;
        Animation slide = AnimationUtils.loadAnimation(mContext, R.anim.sidebar_hidden);
        sideLayout.startAnimation(slide);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                viewLayout.setVisibility(View.GONE);
                viewLayout.setEnabled(false);
                mainLayout.setEnabled(true);
            }
        }, 400);
    }

    public void showMenu() {
        isMenuShow = true;
        Animation slide = AnimationUtils.loadAnimation(this, R.anim.sidebar_show);
        sideLayout.startAnimation(slide);
        viewLayout.setVisibility(View.VISIBLE);
        viewLayout.setEnabled(true);
        mainLayout.setEnabled(false);
    }

    public void toastMsg(String s) {
        final LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        final TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(MainActivity.this);
        text.setTextSize(13);
        text.setTextColor(Color.BLACK);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 600);
        toast.setView(layout);
        text.setText(s);
        toast.show();
    }
}