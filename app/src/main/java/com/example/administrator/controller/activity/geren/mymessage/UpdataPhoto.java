package com.example.administrator.controller.activity.geren.mymessage;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.administrator.controller.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class UpdataPhoto extends AppCompatActivity {
    public static final int TAKE_PHOTO=1;
    public static final int CHOOSE_PHOTO=2;
    private Button takePhoto;
    private Button choose;
    private Button upphto_submit;
    private ImageView picture;
    private Uri imageUri;
    private ImageView photo_im_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_photo);
        initview();
        initListenner();

    }

    private void initListenner() {
        photo_im_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(UpdataPhoto.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(UpdataPhoto.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            1);
                }else {
                    openAlbum();
                }
            }
        });

        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建File对象，用于存储拍照后的照片
                File outputImage=new File(getExternalCacheDir(),"output_image.jpg");
                try{
                    if (outputImage.exists()){
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
                if (Build.VERSION.SDK_INT >=24){
                    imageUri= FileProvider.getUriForFile(UpdataPhoto.this,
                            "com.example.administrator.controller.fileprovider",outputImage);
                }else {
                    imageUri=Uri.fromFile(outputImage);
                }
                //启动相机程序
                Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
                startActivityForResult(intent,TAKE_PHOTO);
            }
        });
        upphto_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initview() {
        photo_im_back=(ImageView)findViewById(R.id.photo_im_back) ;
        takePhoto=(Button)findViewById(R.id.take_photo);
        choose=(Button)findViewById(R.id.choose);
        picture=(ImageView)findViewById(R.id.picture);
        upphto_submit=findViewById(R.id.upphto_sbmit);
    }

    public void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case TAKE_PHOTO:
                if (resultCode==RESULT_OK){
                    try {
                        //将拍摄的照片显示出来
                        Bitmap bitmap= BitmapFactory.decodeStream
                                (getContentResolver().openInputStream(imageUri));
                        picture.setImageBitmap(bitmap);             //显示图片
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }break;
            case CHOOSE_PHOTO:
                if (resultCode==RESULT_OK){
                    //判断手机系统版本号
                    if (Build.VERSION.SDK_INT>=19){
                        //4.4及以上系统使用这个方法处理图片
                        handleImageOnkitkat(data);

                    }else {
                        //4.4以下系统使用这个方法处理图片
                        handleImageBeforekitkat(data);
                    }

                }
                break;

            default:
                break;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this,
                            "You denied the permission",Toast.LENGTH_LONG).show();
                }break;
            default:
        }
    }
    private void handleImageOnkitkat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (DocumentsContract.isDocumentUri(this,uri)){
                //如果是document类型的Uri,则通过document id处理
                String docId=DocumentsContract.getDocumentId(uri);
                if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                    String id=docId.split(":")[1];//解析出数字格式的id
                    String selection=MediaStore.Images.Media._ID+"="+id;
                    imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
                }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                    Uri contentUri= ContentUris.withAppendedId(Uri.parse
                            ("content://downloads/public_downloads"),Long.valueOf(docId));
                    //  ("content://com.android.providers.media.documents/document/image:62"),Long.valueOf(docId));
                    imagePath=getImagePath(contentUri,null);
                }
            }else if ("content".equalsIgnoreCase(uri.getScheme())){
                //如果是content类型的Uri，则使用普通方式
                imagePath=getImagePath(uri,null);
            }else if("file".equalsIgnoreCase(uri.getScheme())){
                //如果是file类型的Uri,直接获取图片路径即可
                imagePath=uri.getPath();
            }
        }
        displayImage(imagePath);//根据图片路径显示图片
    }
    private void handleImageBeforekitkat(Intent data){
        Uri uri= data.getData();
        String imagePath=getImagePath(uri,null);
        displayImage(imagePath);

    }
    private String getImagePath(Uri uri,String selection){
        String path=null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor=getContentResolver().query(uri,
                null,selection,null,null);
        if (cursor!=null){
            if (cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }return path;
    }
    private void displayImage(String imagePath){
        if (imagePath!=null){
            Bitmap bitmap=BitmapFactory.decodeFile(imagePath);
            picture.setImageBitmap(bitmap);
        }else {
            Toast.makeText(this,"failed to get image",Toast.LENGTH_LONG).show();
        }
    }

}
