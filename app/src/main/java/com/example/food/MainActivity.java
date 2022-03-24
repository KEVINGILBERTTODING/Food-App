package com.example.food;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText edtName, edtPrice, edtDesc;
    EditText path_name;
    Button btnChoose, btnAdd, btnList, btnDashboard;
    ImageView imageView;
    Intent MyfileIntent;

    final int REQUEST_CODE_GALLERY = 999;

    public static SQLiteHelper sqLiteHelper;

    public static final String EXTRA_IMAGE = "com.example.food.IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();


        path_name     = findViewById(R.id.namaGambar);
        sqLiteHelper = new SQLiteHelper(this, "FoodDB.sqlite", null, 1);
<<<<<<< HEAD
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS FOOD(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, image BLOB)");
=======

        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS FOOD(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price VARCHAR, description TEXT, image BLOB)");
>>>>>>> aa05a8e8511fcc68fbbc4d061e9963976e79eb01

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        MainActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );

            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {

            Bundle extras = getIntent().getExtras();
            @Override
            public void onClick(View view) {
                try{
                    sqLiteHelper.insertData(
                            edtName.getText().toString().trim(),
                            edtPrice.getText().toString().trim(),
                            edtDesc.getText().toString().trim(),
                            imageViewToByte(imageView)


                    );
                    Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                    edtName.setText("");
                    edtPrice.setText("");
                    edtDesc.setText("");
                    imageView.setImageResource(R.mipmap.ic_launcher);

                    backMain();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FoodList.class);
                startActivity(intent);
            }
        });
    }

    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
                        String path = data.getData().getPath();
                        path_name.setText(path);

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void init(){
        edtName = (EditText) findViewById(R.id.edtName);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtDesc = (EditText) findViewById(R.id.edtDesc);
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        imageView = (ImageView) findViewById(R.id.imageView);
        btnDashboard    =   findViewById(R.id.btnDashboard);

    }

    private void backMain() {
        Intent intent = new Intent(MainActivity.this, FoodList.class);
        startActivity(intent);
    }



}
