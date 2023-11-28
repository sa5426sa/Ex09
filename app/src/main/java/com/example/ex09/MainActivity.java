package com.example.ex09;

import static java.lang.System.exit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 1;
    private final String FILENAME = "text.txt";
    TextView tV;
    EditText eT;
    String text;
    private boolean extExist, permExist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tV = findViewById(R.id.tV);
        eT = findViewById(R.id.eT);

        extExist = isExternalStorageAvailable();
        permExist = checkPermission();

        if (!extExist) {
            Toast.makeText(this, "External memory not installed", Toast.LENGTH_SHORT).show();
        }

        if (permExist) {
            Toast.makeText(this, "External memory access permission granted", Toast.LENGTH_SHORT).show();
        } else {
            requestPermission();
        }

        read(null);
    }

    public void onSave(View view) {
        text = eT.getText().toString();
        if (extExist && permExist) {
            try {
                text = eT.getText().toString();
                File externalDir = Environment.getExternalStorageDirectory();
                File file = new File(externalDir, FILENAME);
                file.getParentFile().mkdirs();
                FileWriter writer = new FileWriter(file);
                writer.write(text);
                writer.close();
                Toast.makeText(this, "Text file saved successfully", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to save text file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onReset(View view) {
        if (extExist && permExist) {
            try {
                text = eT.getText().toString();
                File externalDir = Environment.getExternalStorageDirectory();
                File file = new File(externalDir, FILENAME);
                file.getParentFile().mkdirs();
                FileWriter writer = new FileWriter(file);
                writer.write(text);
                writer.close();
                Toast.makeText(this, "Text file cleared successfully", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to clear text file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onExit(View view) {
        text = eT.getText().toString();
        if (extExist && permExist) {
            try {
                text = eT.getText().toString();
                File externalDir = Environment.getExternalStorageDirectory();
                File file = new File(externalDir, FILENAME);
                file.getParentFile().mkdirs();
                FileWriter writer = new FileWriter(file);
                writer.write(text);
                writer.close();
                exit(0);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to save text file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void read(View view) {
        if (extExist && permExist) {
            try {
                File externalDir = Environment.getExternalStorageDirectory();
                File file = new File(externalDir, FILENAME);
                file.getParentFile().mkdirs();
                FileReader reader = new FileReader(file);
                BufferedReader bR = new BufferedReader(reader);
                StringBuilder sB = new StringBuilder();
                String line = bR.readLine();
                while (line != null) {
                    sB.append(line + '\n');
                    line = bR.readLine();
                }
                bR.close();
                reader.close();
                tV.setText(sB.toString());
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to read text file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission to access external storage granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission to access external storage NOT granted", Toast.LENGTH_LONG).show();
            }
        }
    }

}