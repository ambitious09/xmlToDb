package com.example.textxmlimport;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity  implements OnClickListener{
            Button bt;
    public static int window_width;
    public static Context mContext=null;
    public static File[] mFileList = null;
    public static File mFile;
//    String str="/storage/emulated/0";
    private MyApplication myApplication;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mContext=this;
		WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
		  window_width = wm.getDefaultDisplay().getWidth();
		setContentView(R.layout.activity_main);
		myApplication=MyApplication.getInstance();
		initView();
		Log.i("tag", "onCreate");
	}
	private void initView()
	{
		bt=(Button) findViewById(R.id.bt);
		bt.setOnClickListener(this);
	}
	@Override
	protected void onResume()
	{
	// TODO Auto-generated method stub
	super.onResume();
	if (!TextUtils.isEmpty(myApplication.getStr())&&myApplication.getStr().length()>0)
	{
		bt.setText(myApplication.getStr());
	}
	Log.i("tag", "onresume");
	}
	@Override
	protected void onStart()
	{
	// TODO Auto-generated method stub
	super.onStart();
	Log.i("tag", "onStart");
	}
	@Override
	protected void onStop()
	{
	// TODO Auto-generated method stub
	super.onStop();
	Log.i("tag", "onStop");
	}
	@Override
	public void onClick(View v) {
		mHandler.sendEmptyMessage(0);
		
	}
	public Handler mHandler = new Handler() {
		 public void handleMessage(android.os.Message msg) {
			 CreateIntroductionWindow();
		 };
	};
	 /*
		 * 弹出导入界面窗口
		 */
		public void  CreateIntroductionWindow() {
	    	LayoutInflater inflater = LayoutInflater.from(mContext);
	    	ArrayList<HashMap<String, String>> mFileListData = new ArrayList<HashMap<String, String>>();
			ArrayList<HashMap<String, String>> mNewFileListData = new ArrayList<HashMap<String, String>>();
			FileFilterManger fileSelect = new FileFilterManger();
			mFileListData = fileSelect.getFileList(getApplicationContext());
			for (int i = 0; i < mFileListData.size(); i++) {
				HashMap<String, String> map = mFileListData.get(i);
				mNewFileListData.add(map);
			}
			FileFilterDialog dialog = new FileFilterDialog(this,R.style.add_recipe_dialog_style,mNewFileListData,3);
			mFileList = fileSelect.getFileDataList(getApplicationContext());
			dialog.getWindow().setGravity(Gravity.CENTER);
			dialog.show();	
			WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
			lp.width = (int)(window_width*5/6); //设置宽度
			dialog.getWindow().setAttributes(lp);
	    }
		/*
		 * 导入事件处理
		 */
		public static void  GetIntroductionData(int mItem) {
			mFile = mFileList[mItem];
			Intent intent = new Intent(mContext, IntroductionRecipeActivity.class);
			intent.putExtra("mContext_item", "2");
			mContext.startActivity(intent);
		}
}
