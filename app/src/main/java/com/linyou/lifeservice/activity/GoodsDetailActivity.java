package com.linyou.lifeservice.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.ServiceApplication;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.customdialog.PopShowImageDialog;
import com.linyou.lifeservice.customview.ImageGallery;
import com.linyou.lifeservice.entity.Goods;
import com.linyou.lifeservice.entity.GoodsImage;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.model.GoodsDetailModel;
import com.linyou.lifeservice.utils.OrderUtil;
import com.linyou.lifeservice.utils.PublicUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

@ContentView(R.layout.activity_goods_detail)
public class GoodsDetailActivity extends TitleBarActivity {

	private GoodsDetailModel mGoodsDetailModel;
	
	@ViewInject(R.id.buttonShoppingCar)
	private ImageButton buttonShoppingCar;

    @ViewInject(R.id.textCount)
    private TextView textCount;

    @ViewInject(R.id.editCount)
    private EditText editCount;

	@ViewInject(R.id.buttonAddCar)
	private Button buttonAddCar;
	

	@ViewInject(R.id.buttonCollect)
	private Button buttonCollect;

	@ViewInject(R.id.textStoke)
	private TextView textStoke;

	@ViewInject(R.id.textDescription)
	private TextView textDescription; 
	@ViewInject(R.id.textPrice)
	private TextView textPrice; 
	@ViewInject(R.id.textTime)
	private TextView textTime;
	@ViewInject(R.id.textName)
	private TextView textName;

    @ViewInject(R.id.framelayout)
    private FrameLayout framelayout;
	@ViewInject(R.id.adsGallery)
	private ImageGallery adsGallery;
	@ViewInject(R.id.adimage_point)
	private LinearLayout adimage_point;
	
	private String goods_id;
	
	private int pointsCount;
	
	boolean isCollect;

    int count;

    private Goods goods;

    private String action_from;

	@OnClick(R.id.buttonShoppingCar)
	void gotoShoppingCar(View v) {
        PublicUtil.closeKeyBoard(this);
        User user = ServiceApplication.getInstance().readUser();
        if (null == user) {
            PublicUtil.ShowToast("请先登录");
            startActivityByClass(LoginAcitvity.class);
            return;
        }
        if(null != action_from)
        {
            Intent intent = new Intent();
            intent.setAction(action_from);
            intent.setClass(context,ShoppingCarActivity.class);
            startActivity(intent);

        }else
        {
            startActivityByClass(ShoppingCarActivity.class);
        }

	}

	@OnClick({ R.id.buttonPlus, R.id.buttonSub, R.id.buttonAddCar ,R.id.buttonCollect})
	void onClick(View v) {
        PublicUtil.closeKeyBoard(this);
		switch (v.getId()) {
		case R.id.buttonAddCar:
            User user = ServiceApplication.getInstance().readUser();
            if (null == user) {
                PublicUtil.ShowToast("请先登录");
                startActivityByClass(LoginAcitvity.class);
                return;
            }

            String s = editCount.getText().toString().trim();
            if(!"".equals(s))
            {
                count = Integer.parseInt(s);
            }
            else
            {
                PublicUtil.ShowToast("请选择购买数量");
                return;
            }
            if(0 == count)
            {
                PublicUtil.ShowToast("请选择购买数量");
                return;
            }
            if(count > Integer.parseInt(goods.getAmount()))
            {
                PublicUtil.ShowToast("库存只有"+goods.getAmount());
                return;
            }
            mGoodsDetailModel.add2Car(goods_id,new DataListener(){
                @Override
                public void onSuccess() {
                    PublicUtil.ShowToast("加入购物车成功");
                    goods.setCount(count);
                    OrderUtil.getInstance().addGoods(goods);
                    int stoke = Integer.parseInt(goods.getAmount()) - count;
                    textStoke.setText(""+stoke);
                    updateCount();
                }
            });
			break;
		case R.id.buttonCollect:
			collect();
			break;
            case R.id.buttonPlus:
                count+=1;
                editCount.setText(""+count);
                break;
            case R.id.buttonSub:
                if(count-1 > 0)
                {
                    count--;
                }
                editCount.setText(""+count);
                break;
		}
	}
	
	
	void collect()
	{
        User user = ServiceApplication.getInstance().readUser();
        if (null == user) {
            PublicUtil.ShowToast("请先登录");
            startActivityByClass(LoginAcitvity.class);
            return;
        }
		if(isCollect)
		{
			mGoodsDetailModel.removeCollection(goods_id,new DataListener() {

				@Override
				public void onSuccess() {
					isCollect = false;
					PublicUtil.ShowToast("已取消收藏");
					buttonCollect.setText("加入收藏");
				}

			});
		}
		else
		{
			mGoodsDetailModel.add2Collection(goods_id,new DataListener<String>(){
				
				@Override
				public void onSuccess() {
					isCollect = true;
					PublicUtil.ShowToast("收藏成功");
					buttonCollect.setText("取消收藏");
				}
			});
		}
	}
	
	@Override
	void RightButtonClicked() {
        PublicUtil.closeKeyBoard(this);
		Intent intent = new Intent();
		intent.setClass(this, CommentListActivity.class);
		intent.putExtra(Constant.INTENT_GOODS_ID, goods_id);
		startActivity(intent);
	}

	@Override
	void LeftButtonClicked() {
		finish();
	}

    PopShowImageDialog mPopShowImageDialog;

	@Override
	void setListeners() {
		adsGallery.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				changePositionImage(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

        adsGallery.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                GoodsImage goodsImage = (GoodsImage)mGoodsDetailModel.getGoodsImageAdapter().getItem(i);
                mPopShowImageDialog = new PopShowImageDialog(context,goodsImage.getPic());
                mPopShowImageDialog.showAtLocation(view, Gravity.CENTER_VERTICAL,0,0);
            }
        });
	}

	@Override
	void initDatas() {
        action_from = getIntent().getAction();
		goods_id = getIntent().getStringExtra(Constant.INTENT_GOODS_ID);

		setButtonLeft("返回");
		setTitle(R.drawable.title_goods_info);
		setButtonRight("", R.drawable.comment_selector);
		isCollect = false;
		
		mGoodsDetailModel = new GoodsDetailModel(this);
		mGoodsDetailModel.getGoodsDetail(goods_id, new DataListener<Goods>(){

			@Override
			public void onSuccess(Goods data) {

                p.L("商品详情返回的对象",data.toString());
                textName.setText("" + data.getName());
				textDescription.setText(""+data.getDescribes());
				textPrice.setText("￥"+PublicUtil.priceFormat(data.getPrice()));
				textTime.setText(""+data.getSaleTime().replace("T", " "));
				textStoke.setText(""+data.getAmount());

                //这一步是给商品详情的图层赋值的操作 应该做一个判断  如果为空应该怎么操作 就不执行适配器赋值的操作
                //List为空，判断后不太准确   就得出它的第一个元素做判断
                List<GoodsImage> picList=data.getPicList();
                if (null!=picList.get(0)&&picList.size()>0) {
                    adsGallery.setAdapter(mGoodsDetailModel.getGoodsImageAdapter());
                    mGoodsDetailModel.getGoodsImageAdapter().appendToListAndClear(picList);
                    pointsCount = data.getPicList().size();
                    loadPositionImage();
                }

                goods = data;
			}
			
		});

        editCount.addTextChangedListener(watcher);

        count = 1;
        editCount.setText("" + count);

        mOrderUtil.registerHandler(mHandler);
	}

    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String s = editCount.getText().toString().trim();
            if(!"".equals(s))
            {
                count = Integer.parseInt(s);
            }
            else
            {
                count = 0;
            }
        }
    };

	protected void loadPositionImage() {

		ImageView aImageView = null;
		adimage_point.removeAllViews();
		for (int i = 0; i < pointsCount; i++) {
			aImageView = new ImageView(this);
			if (i == 0) {
				aImageView.setImageResource(R.drawable.point1);
			} else {
				aImageView.setImageResource(R.drawable.point2);
			}

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					30, 30);
			params.leftMargin = 10;
			params.rightMargin = 10;
			adimage_point.addView(aImageView, params);
		}
	}

	public void changePositionImage(int aPos) {
		ImageView aImageView = (ImageView) adimage_point.getChildAt(aPos);
		if (aImageView != null) {
			aImageView.setImageResource(R.drawable.point1);
		}
		for (int i = 0; i < pointsCount; i++) {
			if (i != aPos) {
				aImageView = (ImageView) adimage_point.getChildAt(i);
				aImageView.setImageResource(R.drawable.point2);
			}
		}
	}

    private void updateCount()
    {
        int count_all = mOrderUtil.getCount();

        if(count_all > 0)
        {
            textCount.setVisibility(View.VISIBLE);
            textCount.setText(""+count_all);
        }
        else
        {
            textCount.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOrderUtil.unRegisterHandler(mHandler);
    }

    OrderUtil mOrderUtil = OrderUtil.getInstance();



    private Handler mHandler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constant.ORDER_CHANGED:
                    updateCount();
                    break;

                default:
                    break;
            }
        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        mHandler.sendEmptyMessage(Constant.ORDER_CHANGED);
    }
}
