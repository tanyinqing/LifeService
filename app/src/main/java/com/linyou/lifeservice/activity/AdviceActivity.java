package com.linyou.lifeservice.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.model.AdviceModel;
import com.linyou.lifeservice.web.UserWeb;

@ContentView(R.layout.activity_advice)
public class AdviceActivity extends TitleBarActivity {
	@ViewInject(R.id.editContent)
	private EditText editContent;
	@ViewInject(R.id.textCount)
	private TextView textCount;
	@ViewInject(R.id.buttonConfirm)
	private Button buttonConfirm;
	
	private AdviceModel mAdviceModel;
	
	@Override
	void RightButtonClicked() {
		// TODO Auto-generated method stub

	}
	@OnClick(R.id.buttonConfirm)
	void confirm(View v)
	{
		mAdviceModel.advice(editContent.getText().toString());
	}
	
	@Override
	void LeftButtonClicked() {
		finish();
	}

	@Override
	void setListeners() {
		editContent.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                textCount.setText("" + (500 - editContent.getText().length()) + "字");
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {
                
            }
            
            @Override
            public void afterTextChanged(Editable s)
            {
            }
        });
	}

	@Override
	void initDatas() {
		setButtonLeft("返回");
		setTitle(R.drawable.title_advice);
		mAdviceModel = new AdviceModel(this);
	}

}
