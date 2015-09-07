package com.chaqianma.jd.utils;

import android.media.MediaRecorder;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by zhangxd on 2015/7/30.
 */
public class AudioRecorder {
    private static int SAMPLE_RATE_IN_HZ = 8000;

    MediaRecorder recorder = new MediaRecorder();
    final String path;

    public AudioRecorder(String path) {
        //this.path = sanitizePath(path);
        this.path = path;
    }

    private String sanitizePath(String path) {
        if (!path.startsWith("/")) {
            path = "/" + path;
        }
        if (!path.contains(".")) {
            path += ".amr";
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/my" + path;
    }

    public void start() throws IOException {
        String state = android.os.Environment.getExternalStorageState();
        if (!state.equals(android.os.Environment.MEDIA_MOUNTED)) {
            throw new IOException(
                    "SD Card is not mounted,It is  " + state + ".");
        }
        File directory = new File(path).getParentFile();
        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException(
                    "Path to file could not be created");
        }
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setAudioSamplingRate(SAMPLE_RATE_IN_HZ);
        recorder.setOutputFile(path);
        recorder.prepare();
        recorder.start();
    }

    public void stop() throws IOException {
        //设置后不会崩
        recorder.setOnErrorListener(null);
        recorder.setOnInfoListener(null);
        recorder.setPreviewDisplay(null);
        recorder.stop();
        recorder.release();
    }

    public void cancel() throws IOException {
        //设置后不会崩
        recorder.setOnErrorListener(null);
        recorder.setOnInfoListener(null);
        recorder.setPreviewDisplay(null);
        recorder.stop();
        recorder.release();
        recorder = null;
        File file = new File(this.path);
        if (file.exists())
            file.delete();
    }

    public double getAmplitude() {
        if (recorder != null) {
            return (recorder.getMaxAmplitude());
        } else
            return 0;
    }
}
