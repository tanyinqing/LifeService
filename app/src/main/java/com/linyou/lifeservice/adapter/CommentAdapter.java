package com.linyou.lifeservice.adapter;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.customview.CircularImage;
import com.linyou.lifeservice.entity.Comment;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.utils.PublicUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class CommentAdapter extends AdapterBase<Comment> {


    private static ImageLoader imageLoader;
	public CommentAdapter(Context mContext) {
		super(mContext);
		imageLoader = PublicUtil.getImageLoader();
	}

	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		
		Comment mComment = (Comment) getItem(position);
		ViewHolder holder;
		if(convertView == null)
		{
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.comment_item, parent, false);
			ViewUtils.inject(holder, convertView);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		
		holder.update(mComment);
		return convertView;
	}
	
	private static class ViewHolder{
		@ViewInject(R.id.imageHead)
		private CircularImage imageHead;
		@ViewInject(R.id.textName)
		private TextView textName;
		@ViewInject(R.id.textTime)
		private TextView textTime;
		@ViewInject(R.id.textContent)
		private TextView textContent;
		void update(Comment mComment)
		{
			User u = mComment.getUser();
			//String nick = u.getNickName();
			String nick = u.getNickName();
			String nick1="";
			if(null == nick || "".equals(nick))
			{
				nick1 = "匿名";
			}
			else {
				nick1=PublicUtil.tiHuan(nick);
			}
			textName.setText(""+nick1);

//			String shi_jian=mComment.getCreateTime().replace("T", " ");
//			textTime.setText(""+PublicUtil.zhuanhuan(shi_jian));

			textTime.setText(""+mComment.getCreateTime().replace("T"," "));
			textContent.setText(""+mComment.getContent());
			imageHead.setImageResource(R.drawable.default_head);
			imageLoader.displayImage(Constant.URLBASE+u.getHeadportrait(), imageHead,PublicUtil.getHeadOption());
		}
	}
	
}
