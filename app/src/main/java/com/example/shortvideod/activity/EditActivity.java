package com.example.shortvideod.activity;

import static android.provider.MediaStore.MediaColumns.DATA;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ActivityEditBinding;

public class EditActivity extends BaseActivity {

    static final int GALLERY_COVER_CODE = 1002;
    static final int PERMISSION_REQUEST_CODE = 101;
    static final int GALLERY_CODE = 1001;
    ActivityEditBinding binding;
    Uri selectedImage;
    String picturePath;
    Uri selectedCoverImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit);
        iniView();
    }

    private void iniView() {
        openEditSheet();

        binding.coverImag.setVisibility(View.VISIBLE);
        binding.coverLay.setOnClickListener(v -> chooseCoverPhoto());

        binding.close.setOnClickListener(v -> finish());
        binding.done.setOnClickListener(v -> finish());

    }

    private void openEditSheet() {
        binding.lytimg.setOnClickListener(v -> choosePhoto());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            selectedImage = data.getData();

            Log.d("TAG", "onActivityResult: " + selectedImage);

            Glide.with(this).load(selectedImage).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(false).into(binding.userImg);

            String[] filePathColumn = {DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();

            Log.d("TAG", "picpath:2 " + picturePath);
            Log.d("TAG", "onActivityResultpicpath: " + picturePath);
        }

        if (requestCode == GALLERY_COVER_CODE && resultCode == RESULT_OK && null != data) {
            binding.coverLay.setVisibility(View.GONE);
            binding.coverLay.setVisibility(View.GONE);

            selectedCoverImage = data.getData();
            Log.d("TAG", "onActivityResult: " + selectedCoverImage);
            Glide.with(this).load(selectedCoverImage).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(false).into(binding.coverImag);

            String[] filePathColumn = {DATA};

            Cursor cursor = getContentResolver().query(selectedCoverImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String pictureCoverPath = cursor.getString(columnIndex);
            cursor.close();

            Log.d("TAG", "picpath:2 " + pictureCoverPath);
            Log.d("TAG", "onActivityResultpicpath: " + pictureCoverPath);
        }

    }


    private void chooseCoverPhoto() {
        if (checkPermission()) {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, GALLERY_COVER_CODE);
        } else {
            requestPermission();
        }
    }

    private void choosePhoto() {

        if (checkPermission()) {
            Intent i = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, GALLERY_CODE);
        } else {
            requestPermission();
        }

    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }


}