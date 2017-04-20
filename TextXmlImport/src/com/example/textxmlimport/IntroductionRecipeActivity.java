package com.example.textxmlimport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.autoimportxmltodb.commmen.EventHandler;
import com.example.autoimportxmltodb.control.FormulaImportControl;
import com.exampleautoimportxmltodb.xmlimport.common.CompletedStatus;
import com.exampleautoimportxmltodb.xmlimport.common.FormulaImportCompletedEventArgs;
import com.exampleautoimportxmltodb.xmlimport.common.FormulaImportProgressingEventArgs;
import com.exampleautoimportxmltodb.xmlimport.common.ImportMode;
import com.exampleautoimportxmltodb.xmlimport.common.ImportProgressingStatus;


/**
 * 配方导入界面
 */
@SuppressLint("NewApi")
public class IntroductionRecipeActivity extends Activity
{

	ListView mFileInportListView = null;
	private LinearLayout layout;
	String Tag = "IntroductionRecipeActivity";
	ArrayList<HashMap<String, Object>> mFileInportListItem = null;

	FormulaImportControl mFormulaImportController = null;

	private TextView mLeadingInFirstTv, mLeadingInSecondtTv, mLeadingInThirdTv, mErrorText;
	private LinearLayout mCancelLayout;
	private Button mCancelButton;
	private ListView mMessageListView = null;
	private ImageView mCloseBtnTv;
	private Context mContext = null;

	SimpleAdapter mSimpleAdapter = null;
	List<HashMap<String, Object>> mMessageListItem = new ArrayList<HashMap<String, Object>>();

	private Builder mDialog;
	public static boolean mLeadingResult = false;
	private static boolean mSuccess = false;
	private String mIndex = "0";
	private int window_width = 0;
    private MyApplication myapplication;
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		setContentView(R.layout.menu_introduction_recipe_activity);
        myapplication=MyApplication.getInstance();
		mContext = IntroductionRecipeActivity.this;
		mIndex = this.getIntent().getStringExtra("mContext_item");

		this.setFinishOnTouchOutside(false);

		InitView();

		initData();

	}

	private void initData()
	{
		mDialog = new AlertDialog.Builder(this);
		if (mIndex.equals("2"))
		{
			if (MainActivity.class != null && MainActivity.mFile != null)
			{
				window_width = MainActivity.window_width;
				new Thread()
				{
					@Override
					public void run()
					{
						InitControllerEventArgs();
					}
				}.start();
			} else
			{
				InitControllerEventArgs();
			}
		}
	}

	private void InitView()
	{
		mMessageListView = (ListView) findViewById(R.id.menu_introduction_data_list_id);
		mErrorText = (TextView) findViewById(R.id.menu_introduction_data_text_error_id);
		mCancelLayout = (LinearLayout) findViewById(R.id.menu_introduction_data_layout_cancel_id);
		mCancelButton = (Button) findViewById(R.id.menu_introduction_data_button_cancel_id);
		mCancelLayout.setVisibility(View.INVISIBLE);
		mErrorText.setVisibility(View.GONE);

		mCancelButton.setText("取消");
		((TextView) findViewById(R.id.menu_introduction_data_title_id)).setText("题目");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("Message", " ");
		mMessageListItem.add((HashMap<String, Object>) map);

		mSimpleAdapter = new SimpleAdapter(this, mMessageListItem, R.layout.menu_introduction_recipe_info, new String[] { "Message" }, new int[] { R.id.menu_introduction_recipe_text_id });

		mMessageListView.setAdapter(mSimpleAdapter);
	}

	private void InitControllerEventArgs()
	{
		try
		{
			mFormulaImportController = new FormulaImportControl();

			mFormulaImportController.FormulaImportCompleted = new EventHandler<FormulaImportCompletedEventArgs>()
			{ 
				@Override
				public void Deal(FormulaImportCompletedEventArgs e)
				{
					// TODO Auto-generated method stub
					BindFormulaImportCompleted(e);

				}
			};

			mFormulaImportController.FormulaImportProgressing = new EventHandler<FormulaImportProgressingEventArgs>()
			{
				@Override
				public void Deal(FormulaImportProgressingEventArgs e)
				{
					// TODO Auto-generated method stub
					BindFormulaImportProgressing(e);
				}
			};
			if (mIndex.equals("2"))
			{
				if (MainActivity.class != null && MainActivity.mFile != null)
				{
					mFormulaImportController.ImportFormula(MainActivity.mFile, true, this);
				}
			}
			// else
			// {
			// mFormulaImportController.ImportFormula(DriverCommonEvent.mFile,this);
			// }
		} catch (Exception e)
		{
			// TODO: handle exception
		}
	}

	private void BindFormulaImportCompleted(final FormulaImportCompletedEventArgs e)
	{
		try
		{

			IntroductionRecipeActivity.this.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					if (e.getCompletedStatus() == CompletedStatus.Success)
					{
						finish();
						myapplication.setStr("导入成功");
					} else if (e.getCompletedStatus() == CompletedStatus.NeedConfirm)
					{
						LayoutInflater layoutInflater = LayoutInflater.from(IntroductionRecipeActivity.this);

						View view = layoutInflater.inflate(R.layout.menu_introduction_recipe_dialog, null);

						final PopupWindow noticePopup = new PopupWindow(view, window_width * 5 / 6, LayoutParams.MATCH_PARENT);

						noticePopup.setFocusable(true);

						Button btnLow = (Button) view.findViewById(R.id.introduction_recipe_dialog_text_id);
						// btnLow.setText("导入配方版本较低，是否继续导入？");

						Button btnCancel = (Button) view.findViewById(R.id.introduction_recipe_dialog_button_cancel_id);
						Button btnOk = (Button) view.findViewById(R.id.introduction_recipe_dialog_button_ok_id);

						btnOk.setText("ok");
						btnCancel.setText("取消");
						try
						{
//							LanguageTranslate translate = LanguageTranslate.Instance(mContext, ApplicationSetting.Instance(mContext).getGeneral_App_Language());
//
//							btnLow.setText(translate.LanguageTranslateInfo(Enum.valueOf(LanguageModle.class, LanguageModle.Tint.toString()).name(), e.getCode(), e.getCode()));
						} catch (Exception e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						btnOk.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v)
							{
								try
								{
									noticePopup.dismiss();
									mFormulaImportController.ContinueImportFormula();
								} catch (Exception e2)
								{
									// TODO: handle exception
								}

							}
						});

						btnCancel.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v)
							{
								if (noticePopup != null)
								{
									noticePopup.dismiss();
									IntroductionRecipeActivity.this.finish();
								}
							}
						});
						noticePopup.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);

					} else if (e.getCompletedStatus() == CompletedStatus.NeedChoose)
					{
						mSuccess = true;

						LayoutInflater layoutInflater = LayoutInflater.from(IntroductionRecipeActivity.this);

						View view = layoutInflater.inflate(R.layout.menu_introduction_recipe_dialog, null);

						final PopupWindow noticePopup = new PopupWindow(view, window_width * 5 / 6, LayoutParams.MATCH_PARENT);

						noticePopup.setFocusable(true);

						Button btn = (Button) view.findViewById(R.id.introduction_recipe_dialog_text_id);
						btn.setText("导入");

						Button btnCancel = (Button) view.findViewById(R.id.introduction_recipe_dialog_button_cancel_id);
						Button btnOk = (Button) view.findViewById(R.id.introduction_recipe_dialog_button_ok_id);

						btnOk.setText("ok");
						btnCancel.setText("取消");

						btnOk.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v)
							{
								try
								{
									noticePopup.dismiss();
									mUpdateHandler.sendEmptyMessage(0);
								} catch (Exception e2)
								{
									// TODO: handle exception
								}

							}
						});

						btnCancel.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v)
							{
								if (noticePopup != null)
								{
									noticePopup.dismiss();
									mUpdateHandler.sendEmptyMessage(1);
								}
							}
						});

						noticePopup.showAtLocation(view, Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
					} else
					{
						mErrorText.setVisibility(View.VISIBLE);
						mCancelLayout.setVisibility(View.VISIBLE);
						mCancelButton.setOnClickListener(new OnClickListener()
						{

							@Override
							public void onClick(View v)
							{
								IntroductionRecipeActivity.this.finish();
							}
						});
						try
						{
//							mErrorText.setText(LanguageTranslate.Instance(mContext, ApplicationSetting.Instance(mContext).getGeneral_App_Language()).LanguageTranslateInfo(Enum.valueOf(LanguageModle.class, LanguageModle.Tint.toString()).name(), e.getCode(), e.getCode()));
						} catch (Exception e)
						{
							// TODO Auto-generated catch block
						}
					}
				}

			});

		} catch (Exception e2)
		{
			e2.printStackTrace();
		}
	}

	@SuppressLint("HandlerLeak")
	private Handler mUpdateHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{

			super.handleMessage(msg);

			switch (msg.what)
			{
			case 0:
				if (mSuccess)
				{
					mFormulaImportController.ContinueImportFormula(ImportMode.EmptyImport);
					mSuccess = false;
				}
				break;
			case 1:
				if (mSuccess)
				{
					mFormulaImportController.ContinueImportFormula(ImportMode.AdditionalImport);
					mSuccess = false;
				}
				break;
			}
		}
	};

	private void BindFormulaImportProgressing(final FormulaImportProgressingEventArgs e)
	{
		try
		{

			IntroductionRecipeActivity.this.runOnUiThread(new Runnable()
			{
				@Override
				public void run()
				{
					Map<String, Object> map = new HashMap<String, Object>();

					try
					{
						if ((e.getCode() == null || e.getCode().equals("")))
						{
							map.put("Message", e.getMessage());
						} else
						{
//							String messageStr = LanguageTranslate.Instance(mContext, ApplicationSetting.Instance(mContext).getGeneral_App_Language()).LanguageTranslateInfo(Enum.valueOf(LanguageModle.class, LanguageModle.Tint.toString()).name(), e.getCode(), e.getCode());
//
//							map.put("Message", messageStr);
						}
 
						if (e.getProgressingStatus() == ImportProgressingStatus.LoadData)
						{

							mMessageListItem.add((HashMap<String, Object>) map);

						} else if (e.getProgressingStatus() == ImportProgressingStatus.ImportBaseData)
						{

							mMessageListItem.add((HashMap<String, Object>) map);

						} else if (e.getProgressingStatus() == ImportProgressingStatus.ImportFormula)
						{

							mMessageListItem.remove(mMessageListView.getCount() - 1);

							mSimpleAdapter.notifyDataSetChanged();

							mMessageListItem.add((HashMap<String, Object>) map);

						}
						mSimpleAdapter.notifyDataSetChanged();

					} catch (Exception e)
					{
						e.printStackTrace();
					}

				}

			});

		} catch (Exception e2)
		{
			e2.printStackTrace();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO Auto-generated method stub
		if (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK)
		{
			return true;// 消费掉后退键
		}
		return super.onKeyDown(keyCode, event);
	}

}
