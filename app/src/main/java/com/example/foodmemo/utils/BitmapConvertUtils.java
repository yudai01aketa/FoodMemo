package com.example.foodmemo.utils;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

public class BitmapConvertUtils {
  private BitmapConvertUtils() {}
  public static String getBase64(Bitmap bitmap){
    final ByteArrayOutputStream byteArrayOutPutStream = new ByteArrayOutputStream();
    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutPutStream);
    final byte[] imageBytes = byteArrayOutPutStream.toByteArray();
    return Base64.encodeToString(imageBytes, Base64.DEFAULT);

  }
}
