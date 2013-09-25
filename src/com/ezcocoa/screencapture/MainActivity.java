package com.ezcocoa.screencapture;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Iterator;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * 
 * @author Baek, hojun
 * @create Sep 24, 2013
 */
public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    /**
     * capture screen
     * @param rootLayout root layout
     * @param fileName file name
     */
    private void captureScreen(View rootLayout, String fileName) {
        // File root = Environment.getExternalStorageDirectory();
        File root = getExternalFilesDir(null);
        File file = new File(root, fileName);
        Bitmap b = Bitmap.createBitmap(rootLayout.getWidth(), rootLayout.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        rootLayout.draw(c);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            if (fos != null) {
                b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.close();
                Toast.makeText(this, "Successful Capturing", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * This is function you can capture what you're seeing.
     * @param v
     */
    public void onAction(View v) {
        switch(v.getId()) {
        case R.id.activity_main_captureBtn:
            View rootLayout = findViewById(R.id.activity_main_contentLayout);
            captureScreen(rootLayout, "screenshot.jpg");
            break;
            
        case R.id.activity_main_deleteBtn:
            File dir = getExternalFilesDir(null);
            File[] files = dir.listFiles();
            if (files.length > 0) {
                Iterator<File> i = Arrays.asList(files).iterator();
                while(i.hasNext()) {
                    File f = i.next();
                    String fn = f.getAbsolutePath();
                    if (f.delete()) 
                        Log.d("TAG", "Deleted "+fn);
                    else 
                        Log.d("TAG", "Failed "+fn);
                 }
            }
            break;
        }
    }
}
