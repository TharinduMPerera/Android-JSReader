package com.tharinduapps.jsreader.Utils;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by tharindu on 3/6/18.
 */

public class Utils {

    public static File getJSCacheFile(Context context) throws IOException {
        File cacheFile = new File(context.getCacheDir(), "myJS.js");
        try {
            InputStream inputStream = context.getAssets().open("myJS.js");
            try {
                FileOutputStream outputStream = new FileOutputStream(cacheFile);
                try {
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = inputStream.read(buf)) > 0) {
                        outputStream.write(buf, 0, len);
                    }
                } finally {
                    outputStream.close();
                }
            } finally {
                inputStream.close();
            }
        } catch (IOException e) {
            throw new IOException("Could not open the file", e);
        }
        return cacheFile;
    }
}
