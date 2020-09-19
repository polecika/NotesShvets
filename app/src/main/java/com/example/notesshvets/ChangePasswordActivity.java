package com.example.notesshvets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.InputDevice;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.Toast;

public class ChangePasswordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText newPasEdit;
    private ImageView imageViewPassword;

    private static boolean checkImage = false;
    private SharedPreferences savePas;
    private final String KEY_PIN = "password";

    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();
    }

    private void init() {
        newPasEdit = findViewById(R.id.newPasEdit);
        imageViewPassword = findViewById(R.id.imageViewPassword);
        imageViewPassword.setImageResource(R.drawable.ic_visibile_off);
        saveBtn = findViewById(R.id.saveBtn);


        imageViewPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkImage) {
                    imageViewPassword.setImageResource(R.drawable.ic_visibile_off);
                    newPasEdit.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
                    newPasEdit.setSelection(newPasEdit.getText().length());
                    checkImage = false;
                } else {
                    imageViewPassword.setImageResource(R.drawable.ic_visibile);
                    newPasEdit.setInputType(InputType.TYPE_CLASS_NUMBER );
                    newPasEdit.setSelection(newPasEdit.getText().length());
                    checkImage = true;
                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEdit = newPasEdit.getText().toString();
                if (textEdit.length() == 4) {
                    savePas = getSharedPreferences(KEY_PIN, MODE_PRIVATE);
                    SharedPreferences.Editor ed = savePas.edit();
                    ed.putString(KEY_PIN, textEdit);
                    ed.commit();
                    newPasEdit.setText("");
                    Toast.makeText(ChangePasswordActivity.this, R.string.new_password, Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(ChangePasswordActivity.this, R.string.short_password, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}
