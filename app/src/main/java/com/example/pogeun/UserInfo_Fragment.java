package com.example.pogeun;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class UserInfo_Fragment extends Fragment {

    ViewGroup viewGroup;
    String back_main = "my_page";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView text = getActivity().findViewById(R.id.change_btn);
        text.setText(back_main);
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
                edit_cancel_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                fadeAnimaion(edit_name, name_ok, name_no, edit_name_edit, fade_in, fade_out);
                fadeAnimaion(edit_id, id_ok, id_no, edit_id_edit, fade_in, fade_out);
                fadeAnimaion(edit_email, email_ok, email_no, edit_email_edit, fade_in, fade_out);

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

    public void fadeAnimaion(final ImageView edit, final ImageView ok, final ImageView no, final EditText editText,
                             final Animation in, final Animation out) {
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit.getVisibility() == View.VISIBLE) {
                    edit.startAnimation(out);
                    edit.setVisibility(View.GONE);
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
                        toastMsg("닉네임 수정 완료 :)");
                        edit.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.GONE);
                        no.setVisibility(View.GONE);
                        ok.setVisibility(View.GONE);
                    }
                });
                no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        edit.setVisibility(View.VISIBLE);
                        editText.setVisibility(View.GONE);
                        no.setVisibility(View.GONE);
                        ok.setVisibility(View.GONE);
                    }
                });
            }
        });
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