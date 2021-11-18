package com.example.myapplication.ui.main;

/*
    LoginActivity
    for login a new author,this activity starts only when first start ,
    insert a new author to SharedPreferences

    related only on AuthorInfo
 */

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sp=this.getSharedPreferences("author", Context.MODE_PRIVATE);
        EditText editText = (EditText) findViewById(R.id.login_author);
        Button button = (Button) findViewById(R.id.new_author);

        button.setOnClickListener(v -> {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("author_name",editText.getText().toString());
            editor.commit();
            Intent intent = new Intent(this,MainActivity.class);
            startActivity(intent);
        });
        //if exist , go to MainActivity directly
        if (sp.contains("author_name")) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}