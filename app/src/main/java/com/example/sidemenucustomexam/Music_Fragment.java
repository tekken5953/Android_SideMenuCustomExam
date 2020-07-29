package com.example.sidemenucustomexam;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class Music_Fragment extends Fragment {

    ViewGroup viewGroup;
    RecyclerView mRecyclerView = null;
    ArrayList<RecyclerItem> mList = new ArrayList<RecyclerItem>();
    final Field[] fields = R.raw.class.getFields();
    int imgID = 0;
    String music_name, music_singer;
    Bitmap bitmap;
    Drawable drawable;
    float height = 100;
    float width = 100;
    RecyclerImageTextAdapter mAdapter = new RecyclerImageTextAdapter(mList);
    String back_main = "music";

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView text = getActivity().findViewById(R.id.change_btn);
        text.setText(back_main);

        mRecyclerView = getActivity().findViewById(R.id.recyclerview);
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new RecyclerImageTextAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);

        // 아이템 추가.
        for (Field field : fields) {
            try {
                imgID = getResources().getIdentifier(field.getName(), "drawable", null);
                bitmap = ((BitmapDrawable) mRecyclerView.getResources().getDrawable(imgID)).getBitmap();
                Bitmap resizedBmp = Bitmap.createScaledBitmap(bitmap, (int) width, (int) height, true);
                drawable = new BitmapDrawable(resizedBmp);
            } catch (Exception e) {
                e.printStackTrace();
                bitmap = ((BitmapDrawable) mRecyclerView.getResources().getDrawable(R.drawable.no_img)).getBitmap();
                drawable = new BitmapDrawable(bitmap);
            }

            String c = field.getName();
            music_name = c.substring(0, c.indexOf("_")).replace("9", " ");
            music_name = music_name.substring(0, 1).toUpperCase() + music_name.substring(1);
            music_singer = c.substring(c.indexOf("_") + 1).replace("9", " ");
            music_singer = music_singer.substring(0, 1).toUpperCase() + music_singer.substring(1);
            addItem(drawable, music_name, music_singer);
            drawable = null;
        }
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mAdapter.setOnItemClickListener(new RecyclerImageTextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(), PlayerActivity.class);
                intent.putExtra("index", position);
                back_main = "player";
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mList.clear();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.music_fragment, container, false);
        return viewGroup;
    }

    public void addItem(Drawable icon, String title, String desc) {
        RecyclerItem item = new RecyclerItem();
        item.setIconDrawable(icon);
        item.setTitleStr(title);
        item.setSingerStr(desc);
        mList.add(item);
    }
}
