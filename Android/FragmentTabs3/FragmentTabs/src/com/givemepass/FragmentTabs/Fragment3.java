package com.givemepass.FragmentTabs;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class Fragment3 extends Fragment {
	private GridView gridView;
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
		View v = inflater.inflate(R.layout.fragment3_gridview, container, false);
		List<Map<String, Object>> items = new ArrayList<Map<String,Object>>();
		for (int i = 0; i < image.length; i++) {
		     Map<String, Object> item = new HashMap<String, Object>();
		     item.put("image", image[i]);
		     item.put("text", imgText[i]);
		     items.add(item);
		}
		SimpleAdapter adapter = new SimpleAdapter(getActivity(), 
		    items, R.layout.grid_item, new String[]{"image", "text"},
		    new int[]{R.id.image, R.id.text});
		
		gridView = (GridView)v.findViewById(R.id.main_page_gridview);
		gridView.setNumColumns(3);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener(){
		    @Override
		    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		       Toast.makeText(getActivity(), "§A«ö¤U "+imgText[position],    
		            Toast.LENGTH_SHORT).show();   
		    }
		            
		});
		return v;
	}
}
