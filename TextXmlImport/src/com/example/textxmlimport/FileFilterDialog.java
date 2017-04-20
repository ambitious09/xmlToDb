package com.example.textxmlimport;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
/*
 * 文件选择弹出框
 */
public class FileFilterDialog extends Dialog implements View.OnClickListener{
	static Context context;
    private ListView fileListDialogView=null; 
    private FileFilterAdapter mFileListDialogAdapter;
    private ArrayList<HashMap<String, String>> list_data = null;
    private TextView mTextView;
    private Button fileDialogButtonCancel;
    private int  mItem;
    public FileFilterDialog(Context context) {        
    	super(context); 
    	// TODO Auto-generated constructor stub        
    	this.context = context;    
    }  
    public FileFilterDialog(Context context,final ArrayList<HashMap<String, String>> listData,
			int mItem){        
		super(context);        
		this.context = context;   
		this.list_data = listData;
		this.mTextView = mTextView;
		this.mItem = mItem;
	}
     public FileFilterDialog(Context context, int theme,final ArrayList<HashMap<String, String>> listData,
			int mItem){        
		super(context, theme);        
		this.context = context;   
		this.list_data = listData;
		this.mTextView = mTextView;
		this.mItem = mItem;
	}
	protected void onCreate(Bundle savedInstanceState){  
	    super.onCreate(savedInstanceState);         
	    if(mItem != 5 && mItem != 6){
	    	FileFilterDialog.this.setCanceledOnTouchOutside(true);
	    }else{
	    	FileFilterDialog.this.setCanceledOnTouchOutside(false);
	    }
	    setContentView(R.layout.file_filter_list_dialog);  
	    TextView fileDialogTextView = (TextView)findViewById(R.id.file_dialog_title_id);
	    fileListDialogView = (ListView)findViewById(R.id.file_dialog_file_list);
	    fileDialogButtonCancel = (Button)findViewById(R.id.file_dialog_button_cancel_id);
	    fileDialogButtonCancel.setOnClickListener(this);
	    
	    mFileListDialogAdapter = new FileFilterAdapter(this.context,list_data);
	    fileListDialogView.setAdapter(mFileListDialogAdapter);
	    /*导入还原包时没有语言文件*/
	    
	    fileListDialogView.setOnItemClickListener(new OnItemClickListener(){
	    	 
	        @Override
	        public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
	                long arg3) {
	            // TODO Auto-generated method stub
	        		switch (mItem) {
	        		case 3:
	        			((MainActivity) context).GetIntroductionData(arg2);
	        			break;	        			
					default:
						break;
					}
	        		FileFilterDialog.this.dismiss();
	        	}	
	    });
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.file_dialog_button_cancel_id:
				this.dismiss();
				break;
			default:
				break;
		}
	}
	//导入还原包禁用返回键
	public boolean onKeyDown(int keyCode, KeyEvent event)  
    {  
    	if((mItem == 5 || mItem == 6) && keyCode == KeyEvent.KEYCODE_BACK)  
        {             
			return true;  
        }  
        else  
        {   
            return super.onKeyDown(keyCode, event);  
        }
    } 
}
