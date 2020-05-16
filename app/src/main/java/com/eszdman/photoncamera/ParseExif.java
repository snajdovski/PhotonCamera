package com.eszdman.photoncamera;

import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CaptureResult;
import android.util.Log;
import android.util.Rational;

import androidx.exifinterface.media.ExifInterface;
import java.io.IOException;
import static android.hardware.camera2.CaptureResult.*;
import static androidx.exifinterface.media.ExifInterface.*;

public class ParseExif {
    static String getTime(long exposuretime){
        String out;
        long sec = 1000000000;
        double time = (double)(exposuretime)/sec;
        out = String.valueOf((time));
        //if(time < 1.0) out = "1/"+String.valueOf((int)(1.0/time));
        return out;
    }
    static String tag = "ParseExif";
    public static ExifInterface Parse(CaptureResult result, String path){
        ExifInterface inter = null;
        try {
            inter = new ExifInterface(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //inter.setAttribute(TAG_MODEL,Camera2Api.mCameraCharacteristics.get(CameraCharacteristics.));
        int rotation = Camera2Api.context.getOrientation(MainActivity.act.getWindowManager().getDefaultDisplay().getRotation());
        int orientation = ORIENTATION_FLIP_HORIZONTAL;
        switch (rotation) {
            case 90:
                orientation = ExifInterface.ORIENTATION_ROTATE_90;
                break;
            case 180:
                orientation = ExifInterface.ORIENTATION_ROTATE_180;
                break;
            case 270:
                orientation = ExifInterface.ORIENTATION_ROTATE_270;
                break;
        }
        inter.setAttribute(TAG_ORIENTATION,Integer.toString(orientation));
        inter.setAttribute(TAG_SENSITIVITY_TYPE, String.valueOf(SENSITIVITY_TYPE_ISO_SPEED));
        Log.d(tag, "sensivity:"+result.get(SENSOR_SENSITIVITY).toString());
        inter.setAttribute(TAG_PHOTOGRAPHIC_SENSITIVITY,result.get(SENSOR_SENSITIVITY).toString());
        inter.setAttribute(TAG_F_NUMBER,result.get(LENS_APERTURE).toString());
        inter.setAttribute(TAG_FOCAL_LENGTH,result.get(LENS_FOCAL_LENGTH).toString());
        inter.setAttribute(TAG_FOCAL_LENGTH_IN_35MM_FILM,result.get(LENS_FOCAL_LENGTH).toString());
        inter.setAttribute(TAG_COPYRIGHT,"PhotonCamera");
        inter.setAttribute(TAG_APERTURE_VALUE,result.get(LENS_APERTURE).toString());
        inter.setAttribute(TAG_EXPOSURE_TIME,getTime(result.get(SENSOR_EXPOSURE_TIME)));
        //inter.setAltitude(TAG_);
        return inter;
    }
}