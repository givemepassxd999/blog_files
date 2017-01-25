package com.givemepass.FragmentTabs;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class Fragment1_Tab2 extends ListFragment {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		String[] arr = new String[]{
				"º±¦×¶º","Âû»L¶º","ªoÂû¶º","±Æ°©¶º","®]®©¶º","Âû±Æ¶º","Äq¦×¶º"
		};
		ArrayAdapter<String> adapter = 
			new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arr);
		setListAdapter(adapter);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.fragment2_list, container, false);
	}
}
