package com.givemepass.downloadwebpic;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class DownloadWebPicture {
    private Bitmap bmp;
    public DownloadWebPicture(){
        bmp = null;
    }
    public Bitmap getImg(){
        return bmp;
    }
    public void handleWebPic(final String url, final Handler handler){
        new Thread(new Runnable(){
            @Override
            public void run() {
                bmp = getUrlPic(url);
                Message msg = new Message();
                msg.what = 1;
                handler.sendMessage(msg);
            } 
        }).start();
    }
    private synchronized Bitmap getUrlPic(String url) {
        
        Bitmap webImg = null;
        
        try {
            URL imgUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) imgUrl.openConnection();
            httpURLConnection.connect();
            InputStream inputStream = httpURLConnection.getInputStream();
            
            int length = (int) httpURLConnection.getContentLength();
            
            Log.e("length",Integer.toString(length));
            int tmpLength = 512;
            int readLen = 0,desPos = 0;
//            if (length != -1) {
	            byte[] img = new byte[999999];
	            byte[] tmp = new byte[tmpLength];
            
                while ((readLen = inputStream.read(tmp)) > 0) {
                	Log.e("readLen",Integer.toString(readLen));
                    System.arraycopy(tmp, 0, img, desPos, readLen);
                    desPos += readLen;
                }
                webImg = BitmapFactory.decodeByteArray(img, 0,img.length);
                if(desPos != length){
                    throw new IOException("Only read" + desPos +"bytes");
                }
//            }
            httpURLConnection.disconnect();
        }
        catch (IOException e) {
            Log.e("IOException",e.toString());
        }
  
        return webImg;
    }
}
