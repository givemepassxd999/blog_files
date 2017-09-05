package com.givemepass.FragmentTabs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Fragment1_Tab1 extends Fragment {
	private View v;
	private ListView listView;
    private SimpleAdapter simpleAdapter;
    private int[] image = {
            R.drawable.cat, R.drawable.flower, R.drawable.hippo,
            R.drawable.monkey, R.drawable.mushroom, R.drawable.panda,
            R.drawable.rabbit, R.drawable.raccoon
    };
    private String[] imgText = {
            "cat", "flower", "hippo", "monkey", "mushroom", "panda", "rabbit", "raccoon"
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
		v = inflater.inflate(R.layout.fragment1_tab1, container, false);
		listView = (ListView)v.findViewById(R.id.fragment1_tab1_list);
		List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < image.length; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("image", image[i]);
            item.put("text", imgText[i]);
            items.add(item);
        }
        simpleAdapter = new SimpleAdapter(getActivity(), 
                items, R.layout.fragment1_tab1_simpleadapter, new String[]{"image", "text"},
                new int[]{R.id.image, R.id.text});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Bundle args = new Bundle();
    	           args.putInt("position", position);
    	        
    	        Fragment newFragment = new Fragment1_Tab1_ImageView();
     	       
	        	FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
	        	newFragment.setArguments(args);
	        	   
	        	ft.replace(R.id.realtabcontents, newFragment);
	            ft.setTransition(FragmentTransaction.TRANSIT_NONE);
	            ft.addToBackStack(null);
	            ft.commit();
			}
        	
        });
		return v;
	}
	
}
