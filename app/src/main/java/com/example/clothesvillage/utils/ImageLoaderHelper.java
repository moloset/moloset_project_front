

package com.example.clothesvillage.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.clothesvillage.L;
import com.example.clothesvillage.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.List;


public class ImageLoaderHelper {


    public static int OPEN_IMAGE_REQUEST_CODE = 49018;
    private static String CACHE_DIR_NAME = "pictures";

    private static String TAG = ImageLoaderHelper.class.getSimpleName();
    private Context mContext;
    private Activity mActivity;
    private String[] mItems = null;
    private File mCacheDir;
    private Uri mCameraUri;


    public Uri getmCameraUri() {
        return mCameraUri;
    }

    public void setmCameraUri(Uri mCameraUri) {
        this.mCameraUri = mCameraUri;
    }


    public ImageLoaderHelper(Context context) {
        mContext = context;
        mItems = new String[]{
                "사진 촬영",
                "사진 선택"
        };
    }


    public AlertDialog.Builder imageLoaderDialogBuilder(ActivityLauncher launcher, Activity activity) {
        setmCameraUri(null);
        return new AlertDialog.Builder(mContext)
                .setItems(mItems, (dialogInterface, i) -> {
                    Intent intent;
                    if (i == 0) {
                        getCameraIntent(launcher, activity);
                        return;
                    } else {
                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    }
                    launcher.startActivityForResult(intent, OPEN_IMAGE_REQUEST_CODE);
                })
                .setNegativeButton("취소", ((dialogInterface, i) -> {
                }));
    }


    public AlertDialog.Builder imageLoaderDialogBuilderEdit(ActivityLauncher launcher, Activity activity) {
        mItems = new String[]{
                "사진 촬영",
                "사진 선택,"
        };

        setmCameraUri(null);
        return new AlertDialog.Builder(mContext).setItems(mItems, (dialogInterface, i) -> {
            if (i == 0) {
                getCameraIntent(launcher, activity);
            } else if (i == 1) {
                getGallaryIntent(launcher, activity);

            }
        }).setNegativeButton("취소", ((dialogInterface, i) -> {
        }));
    }


    public static void setProfileImage(Context context, Uri imageUri, ImageView imageView, String imageUpdate) {
        if (null == imageUri || "".equals(imageUri.toString())) {
            int placeHolderResId = R.mipmap.ic_launcher;
            Glide.with(context)
                    .load(imageUri)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(placeHolderResId)
                    .centerCrop()
                    .fitCenter()
                    .dontAnimate()
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(imageUri)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .dontAnimate()
                    .into(imageView);
        }
    }

    public static void setProfileImage(Context context, byte[] imageUri, ImageView imageView, String imageUpdate) {
        if (null == imageUri || imageUri.length == 0) {
            int placeHolderResId = R.mipmap.ic_launcher;
            Glide.with(context)
                    .load(imageUri)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .placeholder(placeHolderResId)
                    .centerCrop()
                    .fitCenter()
                    .dontAnimate()
                    .into(imageView);
        } else {
            Glide.with(context)
                    .load(imageUri)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .centerCrop()
                    .fitCenter()
                    .dontAnimate()
                    .into(imageView);
        }
    }

    private void getCameraIntent(ActivityLauncher launcher, Activity activity) {

        Dexter.withActivity(activity).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                L.i("::report allPermission " + report.areAllPermissionsGranted());
                if (report.areAllPermissionsGranted()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                    String folderName = "Clothes";// 폴더명
                    String path = null;
                    path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + folderName;
                    File fileFolderPath = new File(path);
                    if (!fileFolderPath.exists()) {
                        if (false == fileFolderPath.mkdir()) {
                            L.d("[getCameraIntent] mkdir fail.");
                            return;
                        }
                    }

                    String url = "cloth_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                    Uri outputFileUri = FileProvider.getUriForFile(mContext, mContext.getApplicationContext().getPackageName() + ".provider", new File(path, url));

                    List<ResolveInfo> resolvedIntentActivities = mContext.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolvedIntentInfo : resolvedIntentActivities) {
                        String packageName = resolvedIntentInfo.activityInfo.packageName;

                        mContext.grantUriPermission(packageName, outputFileUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }

                    setmCameraUri(outputFileUri);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    launcher.startActivityForResult(intent, OPEN_IMAGE_REQUEST_CODE);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).check();
    }

    private void getGallaryIntent(ActivityLauncher launcher, Activity activity) {
        Dexter.withActivity(activity).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if (report.areAllPermissionsGranted()) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    launcher.startActivityForResult(intent, OPEN_IMAGE_REQUEST_CODE);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }
        }).check();
    }
}


