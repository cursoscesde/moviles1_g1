package com.moviles2.loginui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    TextView tvAlert;
    CheckBox cbSaveSession;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userState = loadPreferences();
        if(userState.equals("ok")){
            Intent changeScreen = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(changeScreen);
            finish();
        }
        else{
            setContentView(R.layout.activity_main);
        }

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        tvAlert = findViewById(R.id.tvAlert);
        cbSaveSession = findViewById(R.id.cbSaveSession);
    }

    public void validateUser(View view){
        //obtengo los datos ingresados por el usuario
        String userEmail = "juanzabaladev@gmail.com";
        String userPassword = "123456789";

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();
        // valido los datos
        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "todos los campos están vacíos", Toast.LENGTH_LONG).show();
            tvAlert.setText("email y password inválidos");
        }
        else if(password.length() < 8){
            Toast.makeText(this, "La contraseña debe tener más de 8 carácteres", Toast.LENGTH_SHORT).show();
            tvAlert.setText("La contraseña debe tener más de 8 carácteres");
        }
        else if(email.equals(userEmail) && password.equals(userPassword)){
            boolean isCheckeSession = cbSaveSession.isChecked();
            if(isCheckeSession){
                // guarda la sesión
                savePreferences();
                Toast.makeText(this, "Sessión guardada", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "Inicio de sesión correcto", Toast.LENGTH_SHORT).show();
            Intent changeScreen = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(changeScreen);
            finish();
        }
        else{
            Toast.makeText(this, "No se encuentra el usuario", Toast.LENGTH_LONG).show();
        }
    }

    public void goToRegister(View view){
        Intent changeScreen = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(changeScreen);
    }

    public void savePreferences(){
        String state = "ok";
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit(); // modo edición
        editor.putString("userState",state); // modifico
        editor.commit(); // guardo los cambios
    }

    public String loadPreferences(){
        SharedPreferences preferences = getSharedPreferences("user", Context.MODE_PRIVATE);
        String userState = preferences.getString("userState","error");
        return userState;
    }
}