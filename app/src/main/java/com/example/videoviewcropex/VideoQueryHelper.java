package com.example.videoviewcropex;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import java.util.ArrayList;
import java.util.List;

public class VideoQueryHelper {

    public static List<Uri> getAllVideoUris(ContentResolver contentResolver) {
        List<Uri> videoUris = new ArrayList<>();

        // URI để truy vấn video
        Uri videoUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        // Các cột cần lấy từ MediaStore
        String[] projection = {
                MediaStore.Video.Media._ID, // ID của video
        };

        // Truy vấn dữ liệu video
        try (Cursor cursor = contentResolver.query(
                videoUri,
                projection,
                null, // Điều kiện (WHERE)
                null, // Tham số cho điều kiện
                MediaStore.Video.Media.DATE_ADDED + " DESC" // Sắp xếp theo ngày thêm
        )) {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    // Lấy ID của video
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));

                    // Tạo URI cho video
                    Uri videoContentUri = Uri.withAppendedPath(videoUri, String.valueOf(id));
                    videoUris.add(videoContentUri);
                }
            }
        }

        return videoUris;
    }
}