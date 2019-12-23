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
    private String fileInfo = "File_Login_Password";

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
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(openFileInput(fileInfo)));
            String[] stringInfo = bufferedReader.readLine().split(";");
            loginFromFile = stringInfo[0].toString();
            passwordFromFile = stringInfo[1].toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (loginFromFile.equals(editLogin.getText().toString()) && passwordFromFile.equals(editPassword.getText().toString())) {
                Toast.makeText(this, R.string.ok_info, Toast.LENGTH_SHORT).show();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (bufferedReader == null) {
                Toast.makeText(this, R.string.empty_info, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.error_info, Toast.LENGTH_SHORT).show();
            }
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
                FileOutputStream fileOutputStreamLogin = openFileOutput(fileInfo, MODE_PRIVATE);
                OutputStreamWriter outputStreamWriterLogin = new OutputStreamWriter(fileOutputStreamLogin);
                bufferedWriter = new BufferedWriter(outputStreamWriterLogin);
                bufferedWriter.write(stringLogin + ";" + stringPassword + ";");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bufferedWriter != null) {
                    try {
                        bufferedWriter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(this, R.string.saved_info, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void initView() {
        editLogin = findViewById(R.id.editTextLogin);
        editPassword = findViewById(R.id.editTextPassword);
        buttonOK = findViewById(R.id.buttonOK);
        buttonRegistration = findViewById(R.id.buttonRegistration);
    }
}
