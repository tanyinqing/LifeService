package com.linyou.lifeservice.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.model.CommentModel;
import com.linyou.lifeservice.utils.PublicUtil;

@ContentView(R.layout.activity_comment_list)
public class CommentListActivity extends TitleBarActivity {

	@ViewInject(R.id.listComment)
	private ListView listComment;

	@ViewInject(R.id.mPullRefreshView)
	private AbPullToRefreshView mPullRefreshView;

//	@ViewInject(R.id.buttonPost)
//	private ImageButton buttonPost;
//
//	@ViewInject(R.id.editContent)
//	private EditText editContent;

	String goodsId;

	private CommentModel mCommentModel;
	
//	@OnClick(R.id.buttonPost)
//	void OnClick(View v)
//	{
//		mCommentModel.comment(goodsId, editContent.getText().toString(),new DataListener(){
//
//			@Override
//			public void onSuccess() {
//				PublicUtil.ShowToast("发表评论成功！");
//				editContent.setText("");
//				mPullRefreshView.headerRefreshing();
//			}
//
//		});
//	}

	@Override
	void RightButtonClicked() {
		// TODO Auto-generated method stub

	}

	@Override
	void LeftButtonClicked() {
		finish();
	}

	@Override
	void setListeners() {
		mPullRefreshView
				.setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

					@Override
					public void onHeaderRefresh(final AbPullToRefreshView view) {
						mCommentModel.refresh(goodsId, new DataListener() {
							@Override
							public void onSuccess() {
								view.onHeaderRefreshFinish();
							}
						});
					}
				});

		mPullRefreshView.setOnFooterLoadListener(new OnFooterLoadListener() {

			@Override
			public void onFooterLoad(final AbPullToRefreshView view) {

				mCommentModel.loadMore(goodsId, new DataListener() {
					@Override
					public void onSuccess() {
						view.onFooterLoadFinish();
					}
				});

			}
		});
	}

	@Override
	void initDatas() {
		setButtonLeft("返回");
		setTitle(R.drawable.title_comment);
		goodsId = getIntent().getStringExtra(Constant.INTENT_GOODS_ID);
		Log.d("评论的商品的Id是",goodsId);
		mCommentModel= new CommentModel(this);
		listComment.setAdapter(mCommentModel.getCommentAdapter());
		mPullRefreshView.headerRefreshing();
	}

}
