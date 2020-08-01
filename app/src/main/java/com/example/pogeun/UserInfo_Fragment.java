package com.example.pogeun;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class UserInfo_Fragment extends Fragment {

    ViewGroup viewGroup;
    private static final int REQUEST_CODE = 0;
    private static final int REQUEST_CODE2 = 1;
    Bitmap resizedBmp,resizedBmp2;
    ImageView user_info_ring, user_info_back;
    String back_main = "user_info";
    TextView user_name_info, user_id_info, user_email_info;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        user_info_ring = viewGroup.findViewById(R.id.user_icon_ring);
        user_info_back = viewGroup.findViewById(R.id.user_back_ring);
        user_name_info = getActivity().findViewById(R.id.user_name_info);
        user_id_info = getActivity().findViewById(R.id.user_id_info);
        user_email_info = getActivity().findViewById(R.id.user_email_info);
        final TextView text = getActivity().findViewById(R.id.back_main);
        ImageButton edit_btn = getActivity().findViewById(R.id.edit_user_info);
        final Animation fade_in = AnimationUtils.loadAnimation(getContext(), R.anim.fadein);
        final Animation fade_out = AnimationUtils.loadAnimation(getContext(), R.anim.fadeout);
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View view = LayoutInflater.from(getContext()).inflate(R.layout.edit_user_info, null, false);
                builder.setView(view);
                final AlertDialog alertDialog = builder.create();
                final Button edit_cancel_btn = view.findViewById(R.id.edit_cancel_btn);
                final ImageView edit_name = view.findViewById(R.id.edit_name_btn);
                final ImageView edit_id = view.findViewById(R.id.edit_id_btn);
                final ImageView edit_email = view.findViewById(R.id.edit_email_btn);
                final ImageView edit_profile = view.findViewById(R.id.edit_profile_btn);
                final ImageView edit_back = view.findViewById(R.id.edit_back_btn);
                final EditText edit_name_edit = view.findViewById(R.id.edit_name);
                final EditText edit_id_edit = view.findViewById(R.id.edit_id);
                final EditText edit_email_edit = view.findViewById(R.id.edit_email);
                final ImageView name_ok = view.findViewById(R.id.name_ok);
                final ImageView name_no = view.findViewById(R.id.name_no);
                final ImageView id_ok = view.findViewById(R.id.id_ok);
                final ImageView id_no = view.findViewById(R.id.id_no);
                final ImageView email_ok = view.findViewById(R.id.email_ok);
                final ImageView email_no = view.findViewById(R.id.email_no);
                final TextView edited_name = view.findViewById(R.id.edited_name);
                final TextView edited_id = view.findViewById(R.id.edited_id);
                final TextView edited_email = view.findViewById(R.id.edited_email);
                final ImageView edited_profile = view.findViewById(R.id.edited_profile);
                final ImageView edited_back = view.findViewById(R.id.edited_back);

                edited_name.setText(user_name_info.getText().toString());
                edited_id.setText(user_id_info.getText().toString());
                edited_email.setText(user_email_info.getText().toString());

                if (resizedBmp != null){
                    edited_profile.setImageBitmap(resizedBmp);
                }
                if (resizedBmp2 != null){
                    edited_back.setImageBitmap(resizedBmp2);
                }

                edit_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                fadeAnimaion("닉네임",edit_name, name_ok, name_no, edit_name_edit,edited_name, fade_in, fade_out,user_name_info);
                fadeAnimaion("아이디",edit_id, id_ok, id_no, edit_id_edit, edited_id, fade_in, fade_out,user_id_info);
                fadeAnimaion("이메일",edit_email, email_ok, email_no, edit_email_edit, edited_email, fade_in, fade_out,user_email_info);

                edit_profile.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType
                                (MediaStore.Images.Media.CONTENT_TYPE);
                        edited_profile.setColorFilter(null);
                        startActivityForResult(intent, REQUEST_CODE);
                        text.setText(back_main);
                        alertDialog.dismiss();
                    }
                });
                edit_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType
                                (MediaStore.Images.Media.CONTENT_TYPE);
                        edited_back.setColorFilter(null);
                        startActivityForResult(intent, REQUEST_CODE2);
                        text.setText(back_main);
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.userinfo_fragment, container, false);
        return viewGroup;
    }

    public void fadeAnimaion(final String s, final ImageView edit, final ImageView ok, final ImageView no, final EditText editText, final TextView text,
                             final Animation in, final Animation out, final TextView textView) {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.getVisibility() == View.VISIBLE) {
                    edit.startAnimation(out);
                    text.startAnimation(out);
                    edit.setVisibility(View.GONE);
                    text.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            editText.startAnimation(in);
                            editText.setVisibility(View.VISIBLE);
                            ok.startAnimation(in);
                            ok.setVisibility(View.VISIBLE);
                            no.startAnimation(in);
                            no.setVisibility(View.VISIBLE);
                        }
                    }, 700);
                }

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        toastMsg(s +" 수정 완료 :)");
                        edit.setVisibility(View.VISIBLE);
                        text.setVisibility(View.VISIBLE);
                        text.setText(editText.getText().toString());
                        editText.setVisibility(View.GONE);
                        no.setVisibility(View.GONE);
                        ok.setVisibility(View.GONE);
                        text.setText(editText.getText().toString());
                        textView.setText(editText.getText().toString());
                        //키보드 내리기
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edit.setVisibility(View.VISIBLE);
                        text.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.GONE);
                        no.setVisibility(View.GONE);
                        ok.setVisibility(View.GONE);
                        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(INPUT_METHOD_SERVICE);
                        assert imm != null;
                        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                    }
                });
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK){
                try {
                    assert data != null;
                    Uri img_uri = data.getData();
                    assert img_uri != null;
                    String img_string = img_uri.toString();
                    ImageView img1 = getActivity().findViewById(R.id.user_icon);
                    InputStream in = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    //selected Image`s size is bigger than imginfo`s one
                    resizedBmp = Bitmap.createScaledBitmap(img, (int) 200, (int) 200, true);
                    assert in != null;
                    in.close();
                    img1.setImageBitmap(resizedBmp);
                    user_info_ring.setImageBitmap(resizedBmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK){
                try {
                    assert data != null;
                    Uri img_uri = data.getData();
                    assert img_uri != null;
                    String img_string = img_uri.toString();
                    ImageView img3 = getActivity().findViewById(R.id.user_back);
                    InputStream in = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(data.getData());
                    Bitmap img = BitmapFactory.decodeStream(in);
                    //selected Image`s size is bigger than imginfo`s one
                    resizedBmp2 = Bitmap.createScaledBitmap(img, (int) 500, (int) 300, true);
                    assert in != null;
                    in.close();
                    img3.setImageBitmap(resizedBmp2);
                    user_info_back.setImageBitmap(resizedBmp2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

        public void toastMsg(String s) {
        final LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) getActivity().findViewById(R.id.toast_layout));
        final TextView text = layout.findViewById(R.id.text);
        Toast toast = new Toast(getContext());
        text.setTextSize(13);
        text.setTextColor(Color.BLACK);
        toast.setGravity(Gravity.CENTER_HORIZONTAL, 0, 600);
        toast.setView(layout);
        text.setText(s);
        toast.show();
    }
}