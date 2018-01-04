package com.linyou.lifeservice.model;

import java.util.List;

import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.adapter.CommentAdapter;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.entity.Comment;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.utils.PublicUtil;
import com.linyou.lifeservice.web.CommonWeb;
import com.linyou.lifeservice.web.UserWeb;

import android.content.Context;
import android.util.Log;

public class CommentModel extends BaseModel {

	private UserWeb mUserWeb;
	private CommonWeb mCommonWeb;
	private CommentAdapter mCommentAdapter;
	private int start;
	public CommentModel(Context mContext) {
		super(mContext);
		mUserWeb = new UserWeb(mContext);
		mCommonWeb = new CommonWeb(mContext);
		mCommentAdapter = new CommentAdapter(mContext);
		start = 1;
	}
	
	public CommentAdapter getCommentAdapter(){
		return mCommentAdapter;
	}
	
	public void comment(String goodsId,String content,String orderId,final DataListener dataListener)
	{
		content = content.trim();
		if("".equals(content))
		{
			PublicUtil.ShowToast("请输入您的评价！");
			return;
		}
		mUserWeb.addGoodsComment(goodsId, content, orderId, dataListener);
	}
	
	public void refresh(String goodsId,final DataListener dataListener)
	{
		mCommonWeb.findGoodsCommentsByCon(goodsId, Constant.DEFAULT_LIMIT, "0", new DataListener<List<Comment>>(){

			@Override
			public void onFailed() {
				dataListener.onSuccess();
			}

			@Override
			public void onSuccess(List<Comment> data) {

				if(null != data && data.size() > 0)
				{
					for (Comment c:data){
						p.L("返回的评论对象是",c.toString());
					}
					Log.d("评论的数量为",data.size()+"");
					mCommentAdapter.appendToListAndClear(data);
					start = data.size();
				}
				dataListener.onSuccess();
			}
			
		});
	}
	
	public void loadMore(String goodsId,final DataListener dataListener)
	{
		mCommonWeb.findGoodsCommentsByCon(goodsId, Constant.DEFAULT_LIMIT, ""+start, new DataListener<List<Comment>>(){

			@Override
			public void onFailed() {
				dataListener.onSuccess();
			}

			@Override
			public void onSuccess(List<Comment> data) {
				if(null != data && data.size() > 0)
				{
					mCommentAdapter.appendToListAndClear(data);
					start += data.size();
				}
				dataListener.onSuccess();
			}
			
		});
	}
}
