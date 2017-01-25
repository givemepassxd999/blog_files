package com.givemepass.FragmentTabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Fragment1_Tab1_ImageView extends Fragment {
	private int[] image = {
            R.drawable.cat, R.drawable.flower, R.drawable.hippo,
            R.drawable.monkey, R.drawable.mushroom, R.drawable.panda,
            R.drawable.rabbit, R.drawable.raccoon
    };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.fragment1_tab1_imageview, container, false);
		ImageView img = (ImageView)v.findViewById(R.id.image_view);
		img.setImageResource(image[getArguments().getInt("position")]);
		return v;
	}

}
