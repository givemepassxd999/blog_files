package com.givemepass.downloadwebpic;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class DownloadWebPictureDemoActivity extends Activity {
    /** Called when the activity is first created. */
    private DownloadWebPicture loadPic;
    private Handler mHandler;
    private ProgressBar progressBar;
    private ImageView imageView;
    //private final static String url = "http://uploadingit.com/file/lltpirkd9pk3jbuw/raccoon.png";
//    private final static String url = "http://image.anobii.com/anobi/image_book.php?type=3&item_id=012af2ba5ceae803e5&time=1335707751";
    private final static String url = "http://140.118.206.184/IMG01.jpg";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        imageView = (ImageView)findViewById(R.id.imageView);
        loadPic = new DownloadWebPicture();
        
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch(msg.what){
                    case 1:
                    	progressBar.setVisibility(View.GONE);
                        imageView.setImageBitmap(loadPic.getImg());
                        break;
                }
                super.handleMessage(msg);
            }
        };
        loadPic.handleWebPic(url, mHandler);
    }
}