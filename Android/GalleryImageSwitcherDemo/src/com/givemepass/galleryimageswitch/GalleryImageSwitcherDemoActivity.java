package com.givemepass.galleryimageswitch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.ViewSwitcher.ViewFactory;

public class GalleryImageSwitcherDemoActivity extends Activity {
    /** Called when the activity is first created. */
    private Gallery gallery,gallery2;
    private ImageSwitcher imageSwitcher;
    private SimpleAdapter simpleAdapter;
    private int[] image = {
            R.drawable.cat, R.drawable.flower, R.drawable.hippo,
            R.drawable.monkey, R.drawable.mushroom, R.drawable.panda,
            R.drawable.rabbit, R.drawable.raccoon
    };
    private String[] imgText = {
            "cat", "flower", "hippo", "monkey", "mushroom", "panda", "rabbit", "raccoon"
    };
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        imageSwitcher = (ImageSwitcher)findViewById(R.id.image_switcher);
        gallery = (Gallery)findViewById(R.id.gallery);
        gallery2 = (Gallery)findViewById(R.id.gallery2);
        List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < image.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("image", image[i]);
            item.put("text", imgText[i]);
            items.add(item);
        }
        simpleAdapter = new SimpleAdapter(this, 
                items, R.layout.simple_adapter, new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text});
        
        gallery.setAdapter(simpleAdapter);
        gallery2.setAdapter(simpleAdapter);
        gallery.setOnItemSelectedListener(new OnItemSelectedListener(){

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				gallery2.setSelection(arg2);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        gallery.setOnDragListener(new OnDragListener(){

			@Override
			public boolean onDrag(View v, DragEvent event) {
				
				
				return true;
			}
        	
        });
        imageSwitcher.setFactory(new ViewFactory(){

            @Override
            public View makeView() {
                ImageView imageView = new ImageView(GalleryImageSwitcherDemoActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT, 180));
                return imageView;
            }
            
        });
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right));
        gallery.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position,
                    long id) {
                imageSwitcher.setImageResource(image[position]);
            }
        });
    }
}