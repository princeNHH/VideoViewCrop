package com.example.videoviewcropex;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.util.List;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;

public class MainActivity extends AppCompatActivity {
    private CenterCropVideoView videoView;
    private MediaPlayer mediaPlayer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.player_view);

        // Play the video
        List<Uri> videoUris = VideoQueryHelper.getAllVideoUris(getContentResolver());
        playVideo(videoUris.get(1).toString());

    }

    private void playVideo(String videoPath) {
        mediaPlayer = new MediaPlayer();

        videoView.setSurfaceTextureListener(new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                try {
                    mediaPlayer.setDataSource(MainActivity.this, android.net.Uri.parse(videoPath));
                    mediaPlayer.setSurface(new Surface(surface));

                    mediaPlayer.setOnVideoSizeChangedListener((mp, videoWidth, videoHeight) -> {
                        videoView.updateVideoSize(videoWidth, videoHeight);
                    });

                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                return true;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

}