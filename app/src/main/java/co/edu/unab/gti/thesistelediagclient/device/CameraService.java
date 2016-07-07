package co.edu.unab.gti.thesistelediagclient.device;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import co.edu.unab.gti.thesistelediagclient.util.AppContext;
import co.edu.unab.gti.thesistelediagclient.util.PermissionHelper;

/**
 * Created by user on 27/05/2016.
 */
public class CameraService {

    private static PictureTakenListener sListener;
    private static StringBuilder mCurrentPhotoPath = new StringBuilder();
    private static final String JPEG_FILE_SUFFIX = ".jpg";
    private static final int ACTION_TAKE_PHOTO = 1;

    public static void takePicture(final Activity activity, final PictureTakenListener listener){
        PermissionHelper.requestCameraPermission(activity, new PermissionHelper.PermissionGrantedListener() {
            @Override
            public void onPermissionGranted() {
                PermissionHelper.requestStoragePermission(activity, new PermissionHelper.PermissionGrantedListener() {
                    @Override
                    public void onPermissionGranted() {
                        sListener = listener;
                        launchTakePictureIntent(activity);
                    }
                });

            }
        });
    }

    private static void launchTakePictureIntent(final Activity activity){
        mCurrentPhotoPath.delete(0,mCurrentPhotoPath.length());
        try {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File f = createImageFile();
            mCurrentPhotoPath.append(f.getAbsolutePath());
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
            activity.startActivityForResult(takePictureIntent,ACTION_TAKE_PHOTO);

        } catch (IOException e) {
            e.printStackTrace();
            mCurrentPhotoPath.delete(0,mCurrentPhotoPath.length());
        }
    }

    public static  final void handleTakePictureIntent(int requestCode, int result){
        if (requestCode == ACTION_TAKE_PHOTO && result == Activity.RESULT_OK) {
            sListener.onPictureTaken(mCurrentPhotoPath.toString());
        }

    }

    public static final String getLastPictureTakenPath(){
        return mCurrentPhotoPath.toString();
    }

    public static final void discardLastPictureTaken(){
        String path = mCurrentPhotoPath.toString();

        if (path != null) {
            File lastFile = new File(path);
            if (lastFile != null) {
                lastFile.delete();
            }
        }
    }

    private static final File createImageFile() throws IOException {
        // Create an image file name

        String imageFileName = UUID.randomUUID().toString();
        File albumDir = getAlbumDir();
        File imageF = File.createTempFile(imageFileName, JPEG_FILE_SUFFIX, albumDir);
        return imageF;
    }

    private static final File getAlbumDir() {
        File storageDir;

        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            storageDir = new File (
                    Environment.getExternalStorageDirectory()
                            +"/Telediag/fotos"
            );

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraService", "failed to create directory");
                        return null;
                    }
                }
            }

        } else {
            storageDir = new File (
                    AppContext.getContext().getFilesDir()
                            +"/Telediag/fotos"
            );

            if (storageDir != null) {
                if (! storageDir.mkdirs()) {
                    if (! storageDir.exists()){
                        Log.d("CameraService", "failed to create directory");
                        return null;
                    }
                }
            }
        }
        return storageDir;
    }

    public interface PictureTakenListener{
        void onPictureTaken(String pictureTakenPath);
    }
}
