package com.linyou.lifeservice.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.model.AdviceModel;
import com.linyou.lifeservice.model.CommentModel;
import com.linyou.lifeservice.utils.PreferenceUtil;
import com.linyou.lifeservice.utils.PublicUtil;

/**
 * Created by 志强 on 2015/5/5 0005.
 */
@ContentView(R.layout.activity_comment)
public class CommentActivity extends TitleBarActivity {

    @ViewInject(R.id.editContent)
    private EditText editContent;
    @ViewInject(R.id.textCount)
    private TextView textCount;
    @ViewInject(R.id.buttonConfirm)
    private Button buttonConfirm;

    private CommentModel mCommentModel;

    private String goods_id;
    private String content;

    private PreferenceUtil mPrefUtil;  //用于配制信息的业务类  继成了各种方法

    @Override
    void RightButtonClicked() {
        // TODO Auto-generated method stub

    }
    @OnClick(R.id.buttonConfirm)
    void confirm(View v)
    {
        content = editContent.getText().toString().trim();
        if(TextUtils.isEmpty(content))
        {
            PublicUtil.ShowToast("评论不能为空");
            return;
        }

        mPrefUtil = new PreferenceUtil(this, Constant.prefName, MODE_PRIVATE);
        String orderId=mPrefUtil.getStrSetting(Constant.INTENT_ORDER_ID);
        mCommentModel.comment(goods_id,content,orderId,new DataListener(){

            @Override
            public void onSuccess() {
                PublicUtil.ShowToast("评论成功");
                finish();
            }

            @Override
            public void onFailed() {
                PublicUtil.ShowToast("评论失败，请重试");
            }
        });
    }

    @Override
    void LeftButtonClicked() {
        finish();
    }

    @Override
    void setListeners() {
        editContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textCount.setText("" + (200 - editContent.getText().length()) + "字");
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    void initDatas() {
        setTitle(R.drawable.title_comment);
        setButtonLeft("返回");
        goods_id = getIntent().getStringExtra(Constant.INTENT_GOODS_ID);
        mCommentModel = new CommentModel(this);
    }
}
