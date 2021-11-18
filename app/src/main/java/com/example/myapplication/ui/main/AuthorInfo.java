package com.example.myapplication.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Bean.Author;
import com.example.myapplication.R;

/*

    AuthorInfoFragment
    Support for author info,get info from SharedPreferences.
    related on MainActivity and LoginActivity

 */

public class AuthorInfo extends Fragment {
    Author author;
    public static AuthorInfo newInstance() {
        return new AuthorInfo();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.author_info_fragment,container,false);
        SharedPreferences sp = getContext().getSharedPreferences("author", Context.MODE_PRIVATE);
        String name = sp.getString("author_name","null");
        Log.d("Author_infor","getName" + name);
        TextView textView = view.findViewById(R.id.author_name);
        textView.setText(name);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

}