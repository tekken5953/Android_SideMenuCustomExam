package com.example.sidemenucustomexam;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.Calendar;

public class Alarm_Fragment extends Fragment {
    TimePicker timePicker;
    Calendar calendar = Calendar.getInstance();
    int hour = calendar.get(Calendar.HOUR_OF_DAY);
    int min = calendar.get(Calendar.MINUTE);

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        timePicker = getActivity().findViewById(R.id.time_picker);

        getActivity().findViewById(R.id.add_alarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toastMsg("해당시간으로 알람을 설정하였습니다.");
                //TODO
            }
        });
        getActivity().findViewById(R.id.current_time).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                toastMsg("타이머를 현재시간으로 설정하였습니다.");
                timePicker.setHour(hour);
                timePicker.setMinute(min);
            }
        });
    }

    ViewGroup viewGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.alarm_fragment,container,false);
        return viewGroup;
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
