package com.example.sidemenucustomexam;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements View.OnClickListener {
    private Context mContext = MainActivity.this;
    private ViewGroup mainLayout;   //사이드 나왔을때 클릭방지할 영역
    private ViewGroup viewLayout;   //전체 감싸는 영역
    private ViewGroup sideLayout;   //사이드바만 감싸는 영역
    private Boolean isMenuShow = false;
    private Boolean isExitFlag = false;
    RelativeLayout relativeLayout1, relativeLayout2, relativeLayout3, add_layout1, add_layout2, add_layout3;
    ImageView add_btn1, add_btn2, add_btn3,share,option;

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
        if (isMenuShow) {
            closeMenu();
        } else {
            if (isExitFlag) {
                finish();
            } else {
                isExitFlag = true;
                Toast.makeText(this, "뒤로가기를 한번더 누르시면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isExitFlag = false;
                    }
                }, 2000);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        addSideView();  //사이드바 add
        relativeLayout1 = (RelativeLayout) findViewById(R.id.child_layout);
        relativeLayout2 = (RelativeLayout) findViewById(R.id.child_layout2);
        relativeLayout3 = (RelativeLayout) findViewById(R.id.child_layout3);
        add_btn1 = (ImageView) findViewById(R.id.add_img1);
        add_btn2 = (ImageView) findViewById(R.id.add_img2);
        add_btn3 = (ImageView) findViewById(R.id.add_img3);
        add_layout1 = (RelativeLayout) findViewById(R.id.btn_side_level_1);
        add_layout2 = (RelativeLayout) findViewById(R.id.btn_side_level_2);
        add_layout3 = (RelativeLayout) findViewById(R.id.btn_side_level_3);
        share = (ImageView) findViewById(R.id.share);
        option = (ImageView) findViewById(R.id.option_img);
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

                if (relativeLayout1.getVisibility() == View.GONE) {
                    add_btn1.setImageResource(R.drawable.minus);
                    relativeLayout1.setVisibility(View.VISIBLE);
                } else {
                    add_btn1.setImageResource(R.drawable.plus);
                    relativeLayout1.setVisibility(View.GONE);
                }
            }

            @Override
            public void btnChild2() {
                if (relativeLayout2.getVisibility() == View.GONE) {
                    add_btn2.setImageResource(R.drawable.minus);
                    relativeLayout2.setVisibility(View.VISIBLE);
                } else {
                    add_btn2.setImageResource(R.drawable.plus);
                    relativeLayout2.setVisibility(View.GONE);
                }
            }

            @Override
            public void btnChild3() {
                if (relativeLayout3.getVisibility() == View.GONE) {
                    add_btn3.setImageResource(R.drawable.minus);
                    relativeLayout3.setVisibility(View.VISIBLE);
                } else {
                    add_btn3.setImageResource(R.drawable.plus);
                    relativeLayout3.setVisibility(View.GONE);
                }
            }

            @Override
            public void share() {
                try{
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String text = "이것을 공유합니다";
                    intent.putExtra(Intent.EXTRA_TEXT, text);
                    Intent chooser = Intent.createChooser(intent,"친구에게 공유하기");
                    startActivity(chooser);
                    closeMenu();
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(mContext, "공유할수 없는 환경입니다", Toast.LENGTH_SHORT).show();
                    closeMenu();
                }
            }

            @Override
            public void option() {
                closeMenu();
                Intent intent = new Intent(MainActivity.this, OptionActivity.class);
                startActivity(intent);
                overridePendingTransition(0,0);
                finish();
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_menu) {
            showMenu();
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
        }, 450);
    }

    public void showMenu() {
        isMenuShow = true;
        Animation slide = AnimationUtils.loadAnimation(this, R.anim.sidebar_show);
        sideLayout.startAnimation(slide);
        viewLayout.setVisibility(View.VISIBLE);
        viewLayout.setEnabled(true);
        mainLayout.setEnabled(false);
    }
}