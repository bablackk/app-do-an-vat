package edu.huflit.app_do_an_vat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import edu.huflit.app_do_an_vat.Database.DBHelper;

public class AddFood extends AppCompatActivity {
    private static boolean checkInit = false;
    private static int IMAGE_REQ=1;
    private static final String TAG = "Upload image  ###";
    private Uri imagePath;
    String Loai[] = {"Trà sữa" , "Bánh ngọt" , "Trà trái cây" , "Cà phê"};
    EditText edtProductName, edtProductPrice, edtProductDescripe;
    Button btnAddProduct;
    String url_img , TheLoai;
    Spinner spnCategory;
    ImageView imgvAddImage,imgBackList;
    DBHelper db;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        sharedPreferences = getSharedPreferences("data", MODE_PRIVATE);
        edtProductName = (EditText) findViewById(R.id.edt_product_name);
        edtProductPrice = (EditText) findViewById(R.id.edt_product_price);
        edtProductDescripe = (EditText) findViewById(R.id.edt_product_descripe);
        imgvAddImage = (ImageView) findViewById(R.id.imgv_add_product);
        spnCategory = (Spinner) findViewById(R.id.spn_product_category);
        btnAddProduct = (Button) findViewById(R.id.btn_add_product);
        imgBackList = (ImageView) findViewById(R.id.back_to_listfood);
        db = new DBHelper(this);
        if (!checkInit) {
            initConfig();
            checkInit = true;
        }
        imgvAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestPermission();
            }
        });
        imgBackList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddFood.this,Home.class);
                startActivity(i);
            }
        });
        ArrayAdapter arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,Loai);
        arrayAdapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        spnCategory.setAdapter(arrayAdapter);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {Toast.makeText(getApplicationContext(), "Bạn đã chọn: " + Loai[position] ,Toast.LENGTH_SHORT).show();
                TheLoai = Loai[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(AddFood.this, "onNothingSelected", Toast.LENGTH_SHORT).show();
            }
        });
        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MediaManager.get().upload(imagePath).callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        Log.d(TAG, "onStart: " + "started");
                        Toast.makeText(AddFood.this,"Adding" , Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                        Log.d(TAG, "onStart: " + "uploading");
                    }

                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        Log.d(TAG, "onStart: " + "usuccess" );
                        url_img = (String) resultData.get("secure_url");
                        Log.e(TAG, "onSuccess: " +url_img );
                        String productName = edtProductName.getText().toString();
                        String productPrice = edtProductPrice.getText().toString();
                        String productDesc = edtProductDescripe.getText().toString();

                        // procate  + imageUrl;
                        Integer stt = db.getFoodData().getCount() ;

                        Boolean checkInsertProductData = db.insertFoodData(stt+1,url_img,productName,Integer.valueOf(productPrice),"none",TheLoai,productDesc);
                        if(checkInsertProductData== true) {
                            Toast.makeText(AddFood.this, "Thêm thành công", Toast.LENGTH_SHORT ).show();
                        }else {
                            Toast.makeText(AddFood.this, "Thêm thất bại", Toast.LENGTH_SHORT ).show();
                        }
                        url_img = "";
                        edtProductName.setText("");
                        edtProductDescripe.setText("");
                        edtProductPrice.setText("");
                    }

                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        Log.d(TAG, "onStart: " + "error");
                    }

                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        Log.d(TAG, "onStart: " + "error");
                    }
                }).dispatch();
            }
        });

    }
    private void RequestPermission(){
        if(ContextCompat.checkSelfPermission(AddFood.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            selectImage();

        }else {
            ActivityCompat.requestPermissions(AddFood.this,new String[]{
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
            },IMAGE_REQ);
        }

    }

    private void selectImage() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(i,IMAGE_REQ);
    }
    @Override
    protected  void onActivityResult(int requestcode , int resultcode, Intent data) {
        super.onActivityResult(requestcode,resultcode,data);
        if(requestcode ==IMAGE_REQ && resultcode == Activity.RESULT_OK && data != null && data.getData()!= null) {
            imagePath = data.getData();

            Picasso.get().load(imagePath).into(imgvAddImage);
        }
    }
    private void initConfig() {
        Map config = new HashMap();
        config.put("cloud_name", "dykvuub0y");
        config.put("api_key" , "264464482455748");
        config.put("api_secret" , "5_5M-_0dqnoo38vWynYtAtPhyNQ");
        MediaManager.init(this, config);

    }
}