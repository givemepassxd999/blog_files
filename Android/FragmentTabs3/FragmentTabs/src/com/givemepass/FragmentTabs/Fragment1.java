package com.givemepass.FragmentTabs;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabWidget;

public class Fragment1 extends Fragment {
	
	private View v;
	private TabHost mTabHost;
    private TabManager mTabManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		v = inflater.inflate(R.layout.fragment1_tabs, container, false);
		mTabHost = (TabHost)v.findViewById(R.id.TabHost01);
        mTabHost.setup();
        
        mTabManager = new TabManager(getActivity(), mTabHost, R.id.realtabcontents);
        mTabHost.setCurrentTab(0);
        mTabManager.addTab(mTabHost.newTabSpec("Tab1").setIndicator("Tab1",this.getResources().getDrawable(R.drawable.car1)),
        		Fragment1_Tab1.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("Tab2").setIndicator("Tab2",this.getResources().getDrawable(R.drawable.car2)),
        		Fragment1_Tab2.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("Tab3").setIndicator("Tab3",this.getResources().getDrawable(R.drawable.car3)),
        		Fragment3.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("Tab4").setIndicator("Tab4",this.getResources().getDrawable(R.drawable.car4)),
        		Fragment4.class, null);
        
        DisplayMetrics dm = new DisplayMetrics();   
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm); //先取得螢幕解析度  
        int screenWidth = dm.widthPixels;   //取得螢幕的寬
           
           
        TabWidget tabWidget = mTabHost.getTabWidget();   //取得tab的物件
        int count = tabWidget.getChildCount();   //取得tab的分頁有幾個
        if (count > 3) {   //如果超過三個就來處理滑動
            for (int i = 0; i < count; i++) {   
                tabWidget.getChildTabViewAt(i).setMinimumWidth((screenWidth) / 4);//設定每一個分頁最小的寬度   
            }   
        }
		return v;
	}
}