package com.example.pogeun;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        getWindow().setStatusBarColor(getResources().getColor(R.color.title_color));

        TextView miss_pwd = findViewById(R.id.miss_pwd);
        TextView sign_up = findViewById(R.id.sign_up);
        SpannableString content = new SpannableString(miss_pwd.getText().toString());
        SpannableString content2 = new SpannableString(sign_up.getText().toString());
        content2.setSpan(new UnderlineSpan(), 0, content2.length(), 0);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        miss_pwd.setText(content);
        sign_up.setText(content2);
        Button log_in_btn = findViewById(R.id.login_btn);
        final EditText user_id = findViewById(R.id.login_edit_id);
        final EditText user_pwd = findViewById(R.id.login_edit_pwd);
        log_in_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (user_id.getText().toString().equals("")) {
                        toastMsg("아이디를 다시 확인해주세요!");
                        keyboardUp(user_id);
                    } else if (user_pwd.getText().toString().equals("")) {
                        toastMsg("비밀번호를 다시 확인해주세요!");
                        keyboardUp(user_pwd);
                    } else {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("user_id", user_id.getText().toString());
                        intent.putExtra("user_pwd", user_pwd.getText().toString());
                        startActivity(intent);
                        overridePendingTransition(0, 0);
                        toastMsg("접속에 성공했습니다. 안녕하세요 :)");
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    toastMsg("접속에 실패했습니다 :(");
                }
            }
        });

        miss_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMsg("어쩌라구여 ㅎㅎ");
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMsg("없어도 되는데?");
            }
        });
    }

    public void keyboardUp(EditText editText) {
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        //focus 후 키보드 올리기
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert imm != null;
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public void toastMsg(String s) {
        final LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_layout));
        final TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(LoginActivity.this);
        text.setTextSize(13);
        text.setTextColor(Color.BLACK);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(layout);
        text.setText(s);
        toast.show();
    }

//    private void tedPermission() {
//        PermissionListener permissionListener = new PermissionListener() {
//            @Override
//            public void onPermissionGranted() {
//                // 권한 요청 성공
//            }
//
//            @Override
//            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//                // 권한 요청 실패
//            }
//        };
//        TedPermission.with(LoginActivity.this)
//                .setPermissionListener(permissionListener)
//                .setRationaleMessage("[설정] > [권한] 에서 권한을 허용할 수 있습니다.")
//                .setDeniedMessage("사진 및 파일을 저장하기 위하여 접근 권한이 필요합니다.")
//                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
//                .check();
//    }
}