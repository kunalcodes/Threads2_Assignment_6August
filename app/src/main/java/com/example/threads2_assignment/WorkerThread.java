package com.example.threads2_assignment;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

public class WorkerThread extends HandlerThread {

    private android.os.Handler mainThreadHandler;
    private String name;
    private String data;
    private Context context;

    public WorkerThread(Context context, String name, Handler mainThreadHandler, String data) {
        super(name);
        this.name = name;
        this.context = context;
        this.data = data;
        this.mainThreadHandler = mainThreadHandler;
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        downloadFile();
    }

    private void downloadFile() {
        try {
            /*
            Create a directory
            */
            File directory = new File(context.getFilesDir() + File.separator + name);
            if (!directory.exists()) {
                directory.mkdir();
            }
            /*
            Create a file inside the directory
            */

            File file = new File(directory, "file.txt");

            if (!file.exists()) {
                file.createNewFile();
            }

            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            OutputStreamWriter writer = new OutputStreamWriter(fileOutputStream);
            writer.append(data + "\n");
            writer.close();
            /*
            File is written successfully
             */

            //Read from file
            FileInputStream fileInputStream = new FileInputStream(file);
            int fileData = fileInputStream.read();
            StringBuffer stringBuffer = new StringBuffer();
            while (fileData != -1) {
                char ch = (char) fileData;
                stringBuffer = stringBuffer.append(ch);
                fileData = fileInputStream.read();
            }


            Message message = Message.obtain();
            message.obj = stringBuffer.toString();
            mainThreadHandler.sendMessage(message);

        } catch (Exception e) {

        }
    }
}
