package com.example.pogeun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Date;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    final DatabaseReference myRef = database.getReference("User Login");
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
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                //users의 모든 자식들의 key값과 value 값들을 iterator로 참조합니다.
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    //찾고자 하는 ID값은 key로 존재하는 값
                                    if (Objects.equals(dataSnapshot.getKey(), user_id.getText().toString())) {
                                        try {
                                            if (Objects.equals(dataSnapshot.child("비밀번호").getValue(), user_pwd.getText().toString())) {
                                                createThreadAndDialog();
                                                return;
                                            }else{
                                                toastMsg("비밀번가 일치하지 않습니다.");
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                toastMsg("존재하지 않는 아이디입니다.");
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
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
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                View view = LayoutInflater.from(LoginActivity.this).inflate(R.layout.sign_up_dialog, null, false);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                final EditText sign_up_id = view.findViewById(R.id.sign_up_id);
                final EditText sign_up_pwd = view.findViewById(R.id.sign_up_pwd);
                final Button sign_up_btn = view.findViewById(R.id.sign_up_btn);
                final Button sign_up_cencel = view.findViewById(R.id.sign_up_cancel);
                sign_up_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {//마찬가지로 중복 유무 확인
                                    if (sign_up_id.getText().toString().equals(dataSnapshot.getKey())) {
                                        toastMsg("이미 존재하는 아이디 입니다.");
                                        myRef.removeEventListener(this);
                                        sign_up_id.setText("");
                                        sign_up_pwd.setText("");
                                        keyboardUp(sign_up_id);
                                        return;
                                    }else if(sign_up_id.getText().toString().equals("")){
                                        toastMsg("아이디를 입력해주세요.");
                                        keyboardUp(sign_up_id);
                                    }else if(sign_up_pwd.getText().toString().equals("")){
                                        toastMsg("비밀번호를 입력해주세요.");
                                        keyboardUp(sign_up_pwd);
                                    }else{
                                        Date date = new Date(System.currentTimeMillis()); //날짜

                                        myRef.child(sign_up_id.getText().toString()).child("가입일").setValue(date.toString());
                                        myRef.child(sign_up_id.getText().toString()).child("비밀번호").setValue(sign_up_pwd.getText().toString());
                                        //users를 가리키는 기본 참조에서 시작 -> child(Id를 key로 가지는 자식) ->child("가입일 이라는 key를 갖는 자식")의 value를 날짜로 저장

                                        toastMsg("가입을 완료했습니다.\n해당 아이디로 로그인이 가능합니다.");
                                        alertDialog.dismiss();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });
                    }
                });
                sign_up_cencel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
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

    private ProgressDialog loading_Dialog; // Loading Dialog
    void createThreadAndDialog() {
        /* ProgressDialog */
        loading_Dialog = ProgressDialog.show(LoginActivity.this, null,
                "정보를 불러오는 중입니다..", true, false);

        Thread thread = new Thread(new Runnable() {
            public void run() {
                // 시간걸리는 처리
                handler.sendEmptyMessage(0);
            }
        });
        thread.start();
    }

    private Handler handler = new  Handler() {
        public void  handleMessage(Message msg) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    loading_Dialog.dismiss();
                    // View갱신
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    toastMsg("접속에 성공했습니다. 안녕하세요 :)");
                }
            },2000);
        }
    };

}