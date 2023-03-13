package com.example.shortvideod.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.shortvideod.R;
import com.example.shortvideod.databinding.ActivityUploadBinding;
import com.example.shortvideod.databinding.BottomSheetPrivacyBinding;
import com.example.shortvideod.design.LocationList;
import com.example.shortvideod.util.Const;
import com.example.shortvideod.util.SessionManager;
import com.example.shortvideod.util.SocialSpanUtil;
import com.example.shortvideod.util.VideoUtil;
import com.example.shortvideod.workers.Draft;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.jakewharton.rxbinding4.widget.RxTextView;
import com.jakewharton.rxbinding4.widget.TextViewAfterTextChangeEvent;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.disposables.Disposable;

public class UploadActivity extends BaseActivity {
    public static final String EXTRA_DRAFT = "draft";
    public static final String EXTRA_SONG = "song";
    public static final String EXTRA_VIDEO = "video";
    private static final int GALLERY_CODE = 1001;
    private static final int GALLERY_COVER_CODE = 1002;
    private static final int PERMISSION_REQUEST_CODE = 101;
    private static final String TAG = "UploadActivity";
    ActivityUploadBinding binding;
    boolean isshop = false;
    private boolean mDeleteOnExit = true;
    private final List<Disposable> mDisposables = new ArrayList();
    private Draft mDraft;
    private UploadActivityViewModel mModel;
    private String mSong;
    private String mVideo;
    private String picturePath;
    private Privacy privacy;
    private Uri selectedImage;
    private LocationList selectedLocation;
    private SessionManager sessionManager;

    public enum Privacy {
        PUBLIC,
        FOLLOWRS,
        PRIVATE
    }

    public static class UploadActivityViewModel extends ViewModel {
        public String description = null;
        public boolean hasComments = true;
        public String[] heshtags;
        public String location = "";
        public String[] mentions;
        public String preview;
        public int privacy;
        public String screenshot;
    }

    /* access modifiers changed from: protected */
    @SuppressLint("Range")
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 123 && resultCode == -1 && data != null) {
            LocationList location = (LocationList) new Gson().fromJson(data.getStringExtra(Const.DATA), LocationList.class);
            if (location != null) {
                this.selectedLocation = location;
                if (location != null) {
                    this.binding.tvLocation.setText(location.getContry());
                    this.mModel.location = this.binding.tvLocation.getText().toString();
                }
            }
        }
        if (requestCode == 1001 && resultCode == -1 && data != null) {
            this.selectedImage = data.getData();
            Log.d("TAG", "onActivityResult: " + this.selectedImage);
            String[] filePathColumn = {"_data"};
            Cursor cursor = getContentResolver().query(this.selectedImage, filePathColumn, (String) null, (String[]) null, (String) null);
            cursor.moveToFirst();
            this.picturePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();
            Log.d("TAG", "picpath:2 " + this.picturePath);
            Log.d("TAG", "onActivityResultpicpath: " + this.picturePath);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /* access modifiers changed from: protected */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = (ActivityUploadBinding) DataBindingUtil.setContentView(this, R.layout.activity_upload);
        this.mDraft = (Draft) getIntent().getParcelableExtra(EXTRA_DRAFT);
        this.sessionManager = new SessionManager(this);
        this.mModel = (UploadActivityViewModel) new ViewModelProvider(this).get(UploadActivityViewModel.class);
        Draft draft = this.mDraft;
        if (draft != null) {
            this.mSong = !draft.songId.isEmpty() ? this.mDraft.songId : "";
            this.mVideo = this.mDraft.video;
            this.mModel.preview = this.mDraft.preview;
            this.mModel.screenshot = this.mDraft.screenshot;
            this.mModel.description = this.mDraft.description;
            this.mModel.privacy = this.mDraft.privacy;
            this.mModel.hasComments = this.mDraft.hasComments;
            this.mModel.location = this.mDraft.location;
            //this.binding.decriptionView.setText(this.mDraft.description);
            this.binding.tvLocation.setText(this.mDraft.location);
            this.binding.switchComments.setChecked(this.mDraft.hasComments);
        } else {
            this.mSong = getIntent().getStringExtra("song");
            this.mVideo = getIntent().getStringExtra("video");
            Log.d(TAG, "onCreate:songid " + this.mSong);
        }
        Bitmap image = VideoUtil.getFrameAtTime(this.mVideo, TimeUnit.SECONDS.toMicros(3));
        ImageView thumbnail = (ImageView) findViewById(R.id.imageview);
        thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);
        thumbnail.setImageBitmap(image);
        /*thumbnail.setOnClickListener(new UploadActivity$$ExternalSyntheticLambda2(this));
        this.binding.back.setOnClickListener(new UploadActivity$$ExternalSyntheticLambda3(this));
        this.binding.lytLocation.setOnClickListener(new UploadActivity$$ExternalSyntheticLambda4(this));
        this.binding.switchComments.setOnCheckedChangeListener(new UploadActivity$$ExternalSyntheticLambda8(this));
        this.binding.lytPrivacy.setOnClickListener(new UploadActivity$$ExternalSyntheticLambda5(this));
        ((SocialEditText) findViewById(R.id.decriptionView)).setText(this.mModel.description);
        this.mDisposables.add(RxTextView.afterTextChangeEvents(this.binding.decriptionView).skipInitialValue().subscribe(new UploadActivity$$ExternalSyntheticLambda9(this)));
        SocialSpanUtil.apply(this.binding.decriptionView, this.mModel.description, (SocialSpanUtil.OnSocialLinkClickListener) null);
        AutocompleteUtil.setupForHashtags(this, this.binding.decriptionView);
        AutocompleteUtil.setupForUsers(this, this.binding.decriptionView);*/
    }

    /* renamed from: lambda$onCreate$0$com-example-shortvideod-activity-UploadActivity */
    public /* synthetic */ void mo4688x254cc477(View v) {
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtra("video", this.mVideo);
        startActivity(intent);
    }

    /* renamed from: lambda$onCreate$1$com-example-shortvideod-activity-UploadActivity */
    public /* synthetic */ void mo4689x24d65e78(View v) {
        finish();
    }

    /* renamed from: lambda$onCreate$2$com-example-shortvideod-activity-UploadActivity */
    public /* synthetic */ void mo4690x245ff879(View v) {
        startActivityForResult(new Intent(this, LocationChooseActivity.class).putExtra(Const.DATA, this.binding.tvLocation.getText().toString()), 123);
    }

    /* renamed from: lambda$onCreate$3$com-example-shortvideod-activity-UploadActivity */
    public /* synthetic */ void mo4691x23e9927a(CompoundButton buttonView, boolean isChecked) {
        this.mModel.hasComments = isChecked;
    }

    /* renamed from: lambda$onCreate$6$com-example-shortvideod-activity-UploadActivity */
    public /* synthetic */ void mo4694x2286607d(View v) {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.customStyle);
        BottomSheetPrivacyBinding sheetPrivacyBinding = (BottomSheetPrivacyBinding) DataBindingUtil.inflate(LayoutInflater.from(this), R.layout.bottom_sheet_privacy, (ViewGroup) null, false);
        bottomSheetDialog.setContentView(sheetPrivacyBinding.getRoot());
        bottomSheetDialog.show();
        sheetPrivacyBinding.tvPublic.setOnClickListener(v1 -> mo4692x23732c7b(bottomSheetDialog, v1));
        sheetPrivacyBinding.tvOnlyFollowr.setOnClickListener(v2 -> mo4693x22fcc67c(bottomSheetDialog, v2));
    }

    /* renamed from: lambda$onCreate$4$com-example-shortvideod-activity-UploadActivity */
    public /* synthetic */ void mo4692x23732c7b(BottomSheetDialog bottomSheetDialog, View v1) {
        setPrivacy(Privacy.PUBLIC);
        bottomSheetDialog.dismiss();
    }

    /* renamed from: lambda$onCreate$5$com-example-shortvideod-activity-UploadActivity */
    public /* synthetic */ void mo4693x22fcc67c(BottomSheetDialog bottomSheetDialog, View v1) {
        setPrivacy(Privacy.FOLLOWRS);
        bottomSheetDialog.dismiss();
    }

    /* renamed from: lambda$onCreate$7$com-example-shortvideod-activity-UploadActivity */
    public /* synthetic */ void mo4695x220ffa7e(TextViewAfterTextChangeEvent e) throws Throwable {
        Editable editable = e.getEditable();
        this.mModel.description = editable != null ? editable.toString() : null;
    }

    private void choosePhoto() {
        if (checkPermission()) {
            startActivityForResult(new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI), 1001);
        } else {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        return ContextCompat.checkSelfPermission(this, "android.permission.READ_EXTERNAL_STORAGE") == 0;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.READ_EXTERNAL_STORAGE")) {
            Toast.makeText(this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 101);
        }
    }

    private void setPrivacy(Privacy privacy2) {
        this.privacy = privacy2;
        if (privacy2 == Privacy.FOLLOWRS) {
            this.binding.tvPrivacy.setText("My Followers");
        } else {
            this.binding.tvPrivacy.setText("Public");
        }
    }

    private void uploadToServer() {
        Toast.makeText(this, "Uploading Successfully....", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void onDestroy() {
        super.onDestroy();
        for (Disposable disposable : this.mDisposables) {
            disposable.dispose();
        }
        this.mDisposables.clear();
        if (this.mDeleteOnExit && this.mDraft == null) {
            File video = new File(this.mVideo);
            if (!video.delete()) {
                Log.w(TAG, "Could not delete input video: " + video);
            }
        }
    }

    private void deleteDraft() {
        //new MaterialAlertDialogBuilder(this).setMessage((int) R.string.confirmation_delete_draft).setNegativeButton((int) R.string.cancel_button, (DialogInterface.OnClickListener) UploadActivity$$ExternalSyntheticLambda1.INSTANCE).setPositiveButton((CharSequence) "Yes", (DialogInterface.OnClickListener) new UploadActivity$$ExternalSyntheticLambda0(this)).show();
    }

    /* renamed from: lambda$deleteDraft$9$com-example-shortvideod-activity-UploadActivity */
    public /* synthetic */ void mo4687x680f1da1(DialogInterface dialog, int i) {
        dialog.dismiss();
        FileUtils.deleteQuietly(new File(this.mDraft.preview));
        FileUtils.deleteQuietly(new File(this.mDraft.screenshot));
        FileUtils.deleteQuietly(new File(this.mDraft.video));
        setResult(-1);
        finish();
    }

    public void onClickPost(View view) {
        /*String s = this.binding.decriptionView.getText().toString();
        List<String> mentions = this.binding.decriptionView.getMentions();
        List<String> hashtags = this.binding.decriptionView.getHashtags();
        Log.d(TAG, "onClickPost: des " + s);
        Log.d(TAG, "onClickPost: hesh " + hashtags);
        Log.d(TAG, "onClickPost: men " + mentions);*/
        uploadToServer();
    }

    public class LocalVideo {
        String decritption;
        boolean hasComments;
        String heshtags;
        boolean isOriginalS;
        String location;
        String mentions;
        String preview;
        int privacy;
        String screenshot;
        String songId;
        String userId;
        String video;

        public LocalVideo(String songId2, String video2, String screenshot2, String preview2, String decritption2, String location2, String userId2, String heshtags2, String mentions2, boolean hasComments2, int privacy2) {
            this.songId = songId2;
            this.video = video2;
            this.screenshot = screenshot2;
            this.preview = preview2;
            this.decritption = decritption2;
            this.location = location2;
            this.userId = userId2;
            this.heshtags = heshtags2;
            this.mentions = mentions2;
            this.hasComments = hasComments2;
            this.privacy = privacy2;
        }

        public String getSongId() {
            return this.songId;
        }

        public void setSongId(String songId2) {
            this.songId = songId2;
        }

        public String getVideo() {
            return this.video;
        }

        public void setVideo(String video2) {
            this.video = video2;
        }

        public String getScreenshot() {
            return this.screenshot;
        }

        public void setScreenshot(String screenshot2) {
            this.screenshot = screenshot2;
        }

        public String getPreview() {
            return this.preview;
        }

        public void setPreview(String preview2) {
            this.preview = preview2;
        }

        public String getDecritption() {
            return this.decritption;
        }

        public void setDecritption(String decritption2) {
            this.decritption = decritption2;
        }

        public String getLocation() {
            return this.location;
        }

        public void setLocation(String location2) {
            this.location = location2;
        }

        public String getUserId() {
            return this.userId;
        }

        public void setUserId(String userId2) {
            this.userId = userId2;
        }

        public String getHeshtags() {
            return this.heshtags;
        }

        public void setHeshtags(String heshtags2) {
            this.heshtags = heshtags2;
        }

        public String getMentions() {
            return this.mentions;
        }

        public void setMentions(String mentions2) {
            this.mentions = mentions2;
        }

        public boolean isHasComments() {
            return this.hasComments;
        }

        public void setHasComments(boolean hasComments2) {
            this.hasComments = hasComments2;
        }

        public int getPrivacy() {
            return this.privacy;
        }

        public void setPrivacy(int privacy2) {
            this.privacy = privacy2;
        }
    }
}
