package com.givemepass.FragmentTabs;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.widget.TabHost;
import android.widget.TabWidget;

/**
 * This demonstrates how you can implement switching between the tabs of a
 * TabHost through fragments.  It uses a trick (see the code below) to allow
 * the tabs to switch between fragments instead of simple views.
 */
public class FragmentTabs extends FragmentActivity {
    private TabHost mTabHost;
    private TabManager mTabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.fragment_tabs);
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        
        mTabManager = new TabManager(this, mTabHost, R.id.realtabcontent);
        
        mTabHost.setCurrentTab(0);
        mTabManager.addTab(mTabHost.newTabSpec("Fragment1").setIndicator("Fragment1",this.getResources().getDrawable(android.R.drawable.ic_dialog_alert)),
        		Fragment1.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("Fragment2").setIndicator("Fragment2",this.getResources().getDrawable(android.R.drawable.ic_lock_lock)),
        		Fragment2.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("Fragment3").setIndicator("Fragment3",this.getResources().getDrawable(android.R.drawable.ic_input_add)),
        		Fragment3.class, null);
        mTabManager.addTab(mTabHost.newTabSpec("Fragment4").setIndicator("Fragment4",this.getResources().getDrawable(android.R.drawable.ic_delete)),
        		Fragment4.class, null);

        
        
        DisplayMetrics dm = new DisplayMetrics();   
        getWindowManager().getDefaultDisplay().getMetrics(dm); //先取得螢幕解析度  
        int screenWidth = dm.widthPixels;   //取得螢幕的寬
           
           
        TabWidget tabWidget = mTabHost.getTabWidget();   //取得tab的物件
        int count = tabWidget.getChildCount();   //取得tab的分頁有幾個
        if (count > 3) {   //如果超過三個就來處理滑動
            for (int i = 0; i < count; i++) {   
                tabWidget.getChildTabViewAt(i).setMinimumWidth((screenWidth) / 3);//設定每一個分頁最小的寬度   
            }   
        }

    }
}