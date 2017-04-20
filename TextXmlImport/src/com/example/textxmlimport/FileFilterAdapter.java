package com.example.textxmlimport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/*
 * 文件列表适配器
 */
public class FileFilterAdapter extends BaseAdapter {
	int left_list_num;
	private List<String> list;
	private Context context;
	private FileData file_data;
	private int selectedPosition = -1;// 当前选中得选项
	private LayoutInflater layoutInflater;
	public ArrayList<HashMap<String, String>> list_data;
	
	public FileFilterAdapter(final Context context,final ArrayList<HashMap<String, String>> listData) {
		// TODO Auto-generated constructor stub
		this.context = context;
		this.layoutInflater=LayoutInflater.from(context);
		this.list_data=listData;
		selectedPosition = 0;
	}

	public void setSelectedPosition(int position) {
		selectedPosition = position;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list_data.size();
	}
	
	class  FileData {
		TextView textView;
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.file_filter_list_info, null);
			file_data = new FileData();
			file_data.textView = (TextView) convertView.findViewById(R.id.file_text_id);
			convertView.setTag(file_data);			
		} else {
			file_data = (FileData) convertView.getTag();
		}
	
		file_data.textView.setText((String) list_data.get(position).get("FileTitle"));
		
		return convertView;
	}
};

