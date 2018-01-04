package com.linyou.lifeservice.adapter;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.entity.Advertisement;
import com.linyou.lifeservice.entity.GoodsImage;
import com.linyou.lifeservice.utils.DensityUtil;
import com.linyou.lifeservice.utils.PublicUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Gallery;
import android.widget.ImageView;

public class GoodsImageAdapter extends AdapterBase<GoodsImage>
{

    private static ImageLoader imageLoader;
    public GoodsImageAdapter(Context context)
    {
        super(context);
		imageLoader = PublicUtil.getImageLoader();
    }
    
    @Override
    protected View getExView(int position, View view, ViewGroup parent)
    {
        ViewHolder holder;
        if (view == null)
        {
            holder = new ViewHolder();
            view = mInflater.inflate(R.layout.ads_item, null);
            ViewUtils.inject(holder, view);
            int width = PublicUtil.getDeviceWidth();
            int height=PublicUtil.getDeviceHeight();

//            Gallery.LayoutParams params = new Gallery.LayoutParams(width, DensityUtil.dip2px(context, 200));
            Gallery.LayoutParams params = new Gallery.LayoutParams(width, width);
            view.setLayoutParams(params);
            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }
        GoodsImage mGoodsImage = (GoodsImage) getItem(position);
        //holder.imageAds.setImageResource(R.drawable.ic_ads);
        imageLoader.displayImage(Constant.URLBASE + mGoodsImage.getPic(), holder.imageAds,PublicUtil.getAdsOption());
        return view;
    }
    
    class ViewHolder
    {
    	@ViewInject(R.id.imageAds)
        ImageView imageAds;
    }
}
