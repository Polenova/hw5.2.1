package ru.android.polenova;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editLogin;
    private EditText editPassword;
    private Button buttonOK;
    private Button buttonRegistration;
    private String fileLogin = "File_Login";
    private String filePassword = "File_Password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfoFromFile();
            }
        });
        buttonRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfoToFile();
            }
        });
    }

    private void getInfoFromFile() {
        String loginFromFile = null;
        String passwordFromFile = null;
        BufferedReader bufferedReaderLogin = null;
        BufferedReader bufferedReaderPassword = null;
        try {
            bufferedReaderLogin = new BufferedReader(new InputStreamReader(openFileInput(fileLogin)));
            loginFromFile = bufferedReaderLogin.readLine().toString();
            bufferedReaderLogin.close();
            bufferedReaderPassword = new BufferedReader(new InputStreamReader(openFileInput(filePassword)));
            passwordFromFile = bufferedReaderPassword.readLine().toString();
            bufferedReaderPassword.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bufferedReaderLogin == null && bufferedReaderPassword == null) {
            Toast.makeText(this, R.string.empty_info, Toast.LENGTH_SHORT).show();
        } else if (loginFromFile.equals(editLogin.getText().toString()) && passwordFromFile.equals(editPassword.getText().toString())) {
            Toast.makeText(this, R.string.ok_info, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.error_info, Toast.LENGTH_SHORT).show();
        }
    }

    private void saveInfoToFile() {
        String stringLogin = editLogin.getText().toString();
        String stringPassword = editPassword.getText().toString();
        if (stringLogin.equals("")) {
            Toast.makeText(this, R.string.empty_login, Toast.LENGTH_SHORT).show();
        } else if (stringPassword.equals("")) {
            Toast.makeText(this, R.string.empty_password, Toast.LENGTH_SHORT).show();
        } else {
            BufferedWriter bufferedWriter = null;
            try {
                FileOutputStream fileOutputStreamLogin = openFileOutput(fileLogin, MODE_PRIVATE);
                OutputStreamWriter outputStreamWriterLogin = new OutputStreamWriter(fileOutputStreamLogin);
                bufferedWriter = new BufferedWriter(outputStreamWriterLogin);
                bufferedWriter.write(stringLogin);
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                FileOutputStream fileOutputStreamLogin = openFileOutput(filePassword, MODE_PRIVATE);
                OutputStreamWriter outputStreamWriterLogin = new OutputStreamWriter(fileOutputStreamLogin);
                bufferedWriter = new BufferedWriter(outputStreamWriterLogin);
                bufferedWriter.write(stringPassword);
                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(this, R.string.saved_info, Toast.LENGTH_SHORT).show();
        }
    }

    private void initView() {
        editLogin = findViewById(R.id.editTextLogin);
        editPassword = findViewById(R.id.editTextPassword);
        buttonOK = findViewById(R.id.buttonOK);
        buttonRegistration = findViewById(R.id.buttonRegistration);
    }
}
