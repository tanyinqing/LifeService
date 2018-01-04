package com.linyou.lifeservice.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ab.view.pullview.AbPullToRefreshView;
import com.ab.view.pullview.AbPullToRefreshView.OnFooterLoadListener;
import com.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.lidroid.xutils.view.annotation.event.OnCompoundButtonCheckedChange;
import com.lidroid.xutils.view.annotation.event.OnItemClick;
import com.lidroid.xutils.view.annotation.event.OnRadioGroupCheckedChange;
import com.linyou.lifeservice.Constant;
import com.linyou.lifeservice.R;
import com.linyou.lifeservice.ServiceApplication;
import com.linyou.lifeservice.ceshi.p;
import com.linyou.lifeservice.customview.ImageGallery;
import com.linyou.lifeservice.customview.MyListView;
import com.linyou.lifeservice.entity.Advertisement;
import com.linyou.lifeservice.entity.Classfication;
import com.linyou.lifeservice.entity.District;
import com.linyou.lifeservice.entity.User;
import com.linyou.lifeservice.listener.DataListener;
import com.linyou.lifeservice.model.DistrictSelectModel;
import com.linyou.lifeservice.model.ProdutListModel;
import com.linyou.lifeservice.utils.OrderUtil;
import com.linyou.lifeservice.utils.PublicUtil;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_productlist)
public class GoodsListActivity extends TitleBarActivity{

    @ViewInject(R.id.diZhi)
    private TextView diZhi;

    @ViewInject(R.id.tv_qiehuan)
    private TextView tv_qiehuan;

    @OnClick(R.id.tv_qiehuan)
    public void Select_position(View view){
    //PublicUtil.ShowToast("选择");
            p.L("点击了一次切换按钮","点击了一次切换按钮");
               if (popupwindow1!= null&&popupwindow1.isShowing()) {
                    popupwindow.dismiss();
                    return;
                } else {
                    initmPopupWindowView1();
                    popupwindow1.showAsDropDown(view, -5, 5);
                }
    }

    //ListView的滚动监听
    private boolean isScrolling = false;//是否正在滚动
    private boolean isloading = false;//判断是否正在加载中


    private boolean isPlay; // 标志变量,判断当前是否正在播放轮播图片
    private Handler handler = new Handler();
    int number;

    // 图片轮播线程
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (!isPlay)
                return ;
            number=(adsGallery.getSelectedItemPosition() + 1);
            if (number==pointsCount){
                number=0;
            }
            adsGallery.setSelection(number);
            handler.postDelayed(this, 4000);
           // Log.d("图片轮播", "下一张");
        }
    };

    @ViewInject(R.id.adsGallery)
    private ImageGallery adsGallery;
    @ViewInject(R.id.adimage_point)
    private LinearLayout adimage_point;
    @ViewInject(R.id.mPullRefreshView)
    private AbPullToRefreshView mPullRefreshView;
    @ViewInject(R.id.editContent)
    private EditText editContent;

    @ViewInject(R.id.textCount)
    private TextView textCount;

    @ViewInject(R.id.radioOrder)
    private RadioGroup radioOrder;

    @ViewInject(R.id.radioPrice)
    private RadioButton radioPrice;

    @ViewInject(R.id.radioAmount)
    private RadioButton radioAmount;

    @ViewInject(R.id.radioTime)
    private RadioButton radioTime;

    @ViewInject(R.id.listProducts)//商品列表的显示
    private MyListView listProducts;

    @ViewInject(R.id.buttonShoppingCar)
    private ImageButton buttonShoppingCar;

    private PopupWindow popupwindow;//弹出类别
    private PopupWindow popupwindow1;//弹出店铺

    private int pointsCount;

    private ProdutListModel mProdutListModel;

    private static final String order_by_price = "price";
    private static final String order_by_time = "saleTime";
    private static final String order_by_amount = "sales";

    private String order_type;

    private String store_id;//这个是地址的分类
    private String cate_id;//这个是商品的分类的信息

    private String ASC = "asc";
    private String DESC = "desc";

    private String price_sort_type;
    private String time_sort_type;
    private String amount_sort_type;

    private String sort_type;

    private int count;

    private District district;
    //请求地址的数据请求类
    private DistrictSelectModel mDistrictSelectModel;
    private List <District> listDistrict;


    private List<String> data;//店铺的数据集合

    private List<Classfication> list;

    @OnItemClick(R.id.listProducts)
    public void SelectedGoods(AdapterView<?> parent, View view,
                              int position, long id) {
        mProdutListModel.selectGoods(position);
    }



    @OnClick(R.id.buttonShoppingCar)
    void gotoShoppingCar(View v) {

        User user = ServiceApplication.getInstance().readUser();
        if (null == user) {
            PublicUtil.ShowToast("请先登录");
            startActivityByClass(LoginAcitvity.class);
            return;
        }
        startActivityByClass(ShoppingCarActivity.class);
    }

    @OnClick({R.id.radioPrice, R.id.radioTime, R.id.radioAmount})
    void OnClick(View v) {
        switch (v.getId()) {

            case R.id.radioPrice:
                if (!order_type.equals(order_by_price)) {
                    break;
                }
                if (price_sort_type.equals(ASC)) {
                    price_sort_type = DESC;
                    radioPrice.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_checked_down,0);
                    radioTime.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_nor,0);
                    radioAmount.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_nor,0);
                } else {
                    price_sort_type = ASC;
                    radioPrice.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_checked_up,0);
                    radioTime.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_nor,0);
                    radioAmount.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_nor,0);
                }
                sort_type = price_sort_type;
                break;

            case R.id.radioAmount:
                if (!order_type.equals(order_by_amount)) {
                    break;
                }
                if (amount_sort_type.equals(ASC)) {
                    amount_sort_type = DESC;
                    radioAmount.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_checked_down,0);
                    radioTime.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_nor,0);
                    radioPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_nor, 0);
                } else {
                    amount_sort_type = ASC;
                    radioAmount.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_checked_up,0);
                    radioTime.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_nor,0);
                    radioPrice.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_nor,0);
                }
                sort_type = amount_sort_type;
                break;

            case R.id.radioTime:
                if (!order_type.equals(order_by_time)) {
                    break;
                }
                if (time_sort_type.equals(ASC)) {
                    time_sort_type = DESC;
                    radioTime.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_checked_down,0);
                    radioAmount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_nor, 0);
                    radioPrice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_sort_nor, 0);
                } else {
                    time_sort_type = ASC;
                    radioTime.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_checked_up,0);
                    radioPrice.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_nor,0);
                    radioAmount.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sort_nor,0);
                }
                sort_type = time_sort_type;
                break;
            default:
                break;
        }
        mPullRefreshView.headerRefreshing();
    }

    @OnRadioGroupCheckedChange(R.id.radioOrder)
    public void onCheckedChanged(RadioGroup arg0, int arg1) {
        switch (arg1) {

            case R.id.radioPrice:
                order_type = order_by_price;
                if (price_sort_type.equals(ASC)) {
                    price_sort_type = DESC;
                } else {
                    price_sort_type = ASC;
                }
                sort_type = price_sort_type;
                break;

            case R.id.radioAmount:
                order_type = order_by_amount;
                if (amount_sort_type.equals(ASC)) {
                    amount_sort_type = DESC;
                } else {
                    amount_sort_type = ASC;
                }
                sort_type = amount_sort_type;
                break;

            case R.id.radioTime:

                order_type = order_by_time;
                if (time_sort_type.equals(ASC)) {
                    time_sort_type = DESC;
                } else {
                    time_sort_type = ASC;
                }
                sort_type = time_sort_type;
                break;
            default:
                break;
        }
        mPullRefreshView.headerRefreshing();
    }

    @Override
    void RightButtonClicked() {
        if (popupwindow != null&&popupwindow.isShowing()) {
            popupwindow.dismiss();
            return;
        } else {
            initmPopupWindowView();
            popupwindow.showAsDropDown(mButtonRight, -5, 5);//这个是父类中定义的
        }
    }

    //右侧按钮点击后的事件响应
    @Override
    protected void onPause() {
        super.onPause();
        // PopupWindow 就像 Dialog,如果 Activity 结束时还让它可见的话,会导致内存泄漏
        if (popupwindow != null && popupwindow.isShowing()) {
            popupwindow.dismiss();
            popupwindow = null;
        }


        isPlay = false;
    }

    //初始化弹出框的对象   弹出类别的选项框
    public void initmPopupWindowView() {
        // // 获取自定义布局文件pop.xml的视图
        View customView = getLayoutInflater().inflate(R.layout.popview_item,
                null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow = new PopupWindow(customView, 3000, WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        popupwindow.setAnimationStyle(R.style.AnimationFade);

        // 调用 setFocusable()让 PopupWindow 中的元素可以获得焦点,
        // 这还将使得任何 PopupWindow 之外的触屏都会引起 PopupWindow 消失
        popupwindow.setFocusable(true);
        // 调用 setOutsideTouchable(),让外部的触屏引起 PopupWindow 自动消失,
        // 但是不会让位于 PopupWindow 内部的元素获得焦点
        popupwindow.setOutsideTouchable(true);
        //必须设置背景
        popupwindow.setBackgroundDrawable(new BitmapDrawable());
        // 自定义view添加触摸事件  选择产品列表时的操作
        customView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow != null && popupwindow.isShowing()) {
                    popupwindow.dismiss();
                    popupwindow = null;
                }

                return false;
            }
        });
        //初始化弹出框的对象   弹出类别的选项框
        ListView xiaLa = (ListView) customView.findViewById(R.id.xiaLa);
        xiaLa.setAdapter(new ArrayAdapter<String>(GoodsListActivity.this, R.layout.simple_expandable_list_item_1, getData()));
        xiaLa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(GoodsListActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
              /*  if (store_id.equals("10")){
                    application.mPrefUtil.putSetting(Constant.leiXing_numbe,i);//保存的i是int类型的
                    cate_id = read_cate_id(i);
                }else {
                    application.mPrefUtil.putSetting(Constant.leiXing_numbe,i+2);//保存的i是int类型的
                    cate_id = read_cate_id(i+2);
                }*/



                editContent.setText("");
                int leiXing_numbe = application.mPrefUtil.getIntSetting(Constant.leiXing_numbe); // 接下来的工作是对商品类型的I
               // p("store_id="+store_id+"  "+"cate_id="+cate_id+"  "+"leiXing_numbe="+leiXing_numbe);
               // mPullRefreshView.headerRefreshing();
                //初始化新的产品列表
                cate_id=list.get(i).getCate_id();
             /*   if (cate_id.equals("0")) {
                    PublicUtil.ShowToast("该区域下没有该商品分类");
                    return;
                }*/
               mProdutListModel.refreshProducts(store_id, cate_id,
                        order_type, editContent.getText().toString(), sort_type, new DataListener() {

                            @Override
                            public void onSuccess() {

                            }
                        });
                popupwindow.dismiss();
            }
        });
    }

    //初始化地址弹出框的对象   弹出店铺的选项框  该店铺会在配置文件中改写店铺ID
    public void initmPopupWindowView1() {
        // // 获取自定义布局文件pop.xml的视图
        View customView = getLayoutInflater().inflate(R.layout.popview_item,
                null, false);
        // 创建PopupWindow实例,200,150分别是宽度和高度
        popupwindow1 = new PopupWindow(customView, 3000, WindowManager.LayoutParams.WRAP_CONTENT);
        // 设置动画效果 [R.style.AnimationFade 是自己事先定义好的]
        popupwindow1.setAnimationStyle(R.style.AnimationFade);

        // 调用 setFocusable()让 PopupWindow 中的元素可以获得焦点,
        // 这还将使得任何 PopupWindow 之外的触屏都会引起 PopupWindow 消失
        popupwindow1.setFocusable(true);
        // 调用 setOutsideTouchable(),让外部的触屏引起 PopupWindow 自动消失,
        // 但是不会让位于 PopupWindow 内部的元素获得焦点
        popupwindow1.setOutsideTouchable(true);
        //必须设置背景
        popupwindow1.setBackgroundDrawable(new BitmapDrawable());
        // 自定义view添加触摸事件
        customView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupwindow1 != null && popupwindow1.isShowing()) {
                    popupwindow1.dismiss();
                    popupwindow1 = null;
                }

                return false;
            }
        });
        //初始化地址弹出框的对象   弹出店铺的选项框  该店铺会在配置文件中改写店铺ID
        ListView xiaLa = (ListView) customView.findViewById(R.id.xiaLa);
        xiaLa.setAdapter(new ArrayAdapter<String>(GoodsListActivity.this, R.layout.simple_expandable_list_item_1, getData1()));
        xiaLa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(GoodsListActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                if ("".equals(data.get(i))) {
                    return;
                }

                district = listDistrict.get(i);
                application.saveDistrict(district);
                diZhi.setText(district.getName());
                store_id = district.getId();//重新给商铺Id赋值
              /*  int leiXing_numbe = application.mPrefUtil.getIntSetting(Constant.leiXing_numbe); // 接下来的工作是对商品类型的Id重新赋值和刷新页面
                cate_id = read_cate_id(leiXing_numbe);
                int s=Integer.valueOf(store_id);//店铺号
                int m=0;//类别号*/
//                int m=Integer.valueOf(cate_id);//类别号
                popupwindow1.dismiss();
               /* if (m==0){
                    PublicUtil.ShowToast("请重新选择商品分类");*/

                    initmPopupWindowView();
                    popupwindow.showAsDropDown(mButtonRight, -5, 5);
              /*  }else {
                    //这个是刷新的控件
                   // p("store_id=" + store_id + "  " + "cate_id=" + cate_id + "  " + "leiXing_numbe="+leiXing_numbe);
               // mPullRefreshView.headerRefreshing();
                    //初始化新的产品列表
                  mProdutListModel.refreshProducts(store_id, cate_id,
                            order_type, editContent.getText().toString(), sort_type, new DataListener() {

                                @Override
                                public void onSuccess() {

                                }
                            });
                }
*/


            }
        });
    }

    //获得商品的分类的数据
    private List<String> getData(){
        List<String> data = new ArrayList<String>();
        list= (List<Classfication>) ServiceApplication.mPrefUtil.readObject("classlist");
        for (Classfication c:list){
            data.add(c.getClass_name());
        }
       /* int i=Integer.valueOf(store_id);
        if (i==10){
            data.add("餐饮/卤菜");
            data.add("蔬菜水果/净菜/肉类");
        }

        data.add("冷冻食品");
        data.add("饮料饮品");
        data.add("休闲零食");
        data.add("奶制品");
        data.add("饼干");
        data.add("方便速食");
        data.add("蛋糕/卷、糕点");
        data.add("糖果/巧克力");
        data.add("冲饮谷物");
        data.add("酒类");
        data.add("个人护理用品");
        data.add("生活日用品");
        data.add("粮油副食");
        data.add("办公用品");
        if (i==425||i==436){
          data.add("香烟展示");
        }*/
        return data;
    }

    //获得地址的分类的数据
    private List<String> getData1(){
         data = new ArrayList<String>();
       // mDistrictSelectModel.requestDistrict();
        listDistrict=mDistrictSelectModel.getDistrictAdapter().getList();
        for (District d:listDistrict){
            data.add(d.getName());
        }
       // p(data.toString());
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        data.add("");
        return data;

    }


    /**
     * 定义一个方法  来读取分类的值 这个方法和主页面中的方法采用的数据是不一致的
     *
     */
   /* public  String read_cate_id(int j){
        int c=0;
        int i=Integer.valueOf(store_id);

        int k1[]={1398,1324,1461,1258,1323,1280,1290,1274,1301,1315,1321,1439,1449,1411,1419,1440};
        int k2[]={0,0,1677,1564,1688,1695,1701,1708,1713,1718,1723,1729,1735,1742,1751,1757};
        int k3[]={0,0,1479,1483,1492,1499,1505,1512,1517,1522,1527,1533,1539,1546,1555,1561};
        int k4[]={0,0,1758,1759,1760,1761,1762,1763,1764,1765,1766,1767,1768,1769,1770,1771,2324};
        int k5[]={0,0,2227,2228,2229,2230,2231,2232,2233,2234,2235,2236,2237,2238,2239,2240,2326};
        if (i==10){
            c=k1[j];
        }else if (i==28){
            c=k2[j];
        }else if (i==352){
            c=k3[j];
        }else if (i==425){
            c=k4[j];
        }else if (i==436){
            c=k5[j];
        }else {
            c=0;
        }
        return String.valueOf(c);
    }*/
    @Override
    void LeftButtonClicked() {
        finish();

    }

    @Override
    void setListeners() {
        //利用关键字来搜索商品调用的方法
        editContent.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    PublicUtil.closeKeyBoard(context);
                    //mPullRefreshView.headerRefreshing();
                    mProdutListModel.refreshProducts(store_id,cate_id,
                            order_type, editContent.getText().toString(), sort_type, new DataListener() {

                                @Override
                                public void onSuccess() {
                                    //view.onHeaderRefreshFinish();
                                }
                            });
                    return true;
                }
                return false;
            }
        });

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
                Advertisement ad = (Advertisement)mProdutListModel.getAdImageAdapter().getItem(i);
                if("0".equals(ad.getIsChain()))
                {
                    Intent intent = new Intent();
                    intent.setClass(context,AdDetailActivity.class);
                    intent.putExtra(Constant.INTENT_ADS,ad);
                    startActivity(intent);
                }
            }
        });

        //调动数据 连接网络 初始化数据  sort_type是排列方式是升序还是降序  order_type是排序条件
        mPullRefreshView
                .setOnHeaderRefreshListener(new OnHeaderRefreshListener() {

                    @Override
                    public void onHeaderRefresh(final AbPullToRefreshView view) {
                        view.onHeaderRefreshFinish();//取消上面的进度条
                        mProdutListModel.refreshProducts(store_id,cate_id,
                                order_type, editContent.getText().toString(), sort_type, new DataListener() {

                                    @Override
                                    public void onSuccess() {
                                    //网络访问成功了 结束上面的进度条
                                      view.onHeaderRefreshFinish();
                                    }
                                });
                    }
                });


        mPullRefreshView.setOnFooterLoadListener(new OnFooterLoadListener() {

            @Override
            public void onFooterLoad(final AbPullToRefreshView view) {

                //mProdutListModel.loadMoreGoods(store_id, cate_id, order_type,"", sort_type, new DataListener() {
                //之所以修改是 增加的内容也加入了关键字
                mProdutListModel.loadMoreGoods(store_id, cate_id, order_type, editContent.getText().toString(), sort_type, new DataListener() {
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
       // action_type = getIntent().getAction();//这个就是goodTypeId 查询的参照条件
        //取得从上一个Activity当中传递过来的Intent对象
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        store_id= intent.getStringExtra("store_id");

        cate_id = intent.getStringExtra("cate_id");
        //order_type = order_by_price;
        order_type = "";
        setTitle(R.drawable.title_logo);
        setButtonLeft("返回");
        setButtonRight("产品列表", 0);

        district=application.readDistrict();
        diZhi.setText(district.getName());

        price_sort_type = ASC;
        time_sort_type = ASC;
        amount_sort_type = ASC;

        sort_type =DESC;
        // sort_type = "";

        mProdutListModel = new ProdutListModel(this);
        //这个是广告的控件
        adsGallery.setAdapter(mProdutListModel.getAdImageAdapter());
        //这个是商品的控件
        listProducts.setAdapter(mProdutListModel.getGoodsAdapter());

        //这个是刷新的控件
        mPullRefreshView.headerRefreshing();

        mProdutListModel.requestAds(new DataListener() {
            @Override
            public void onSuccess() {
                pointsCount = mProdutListModel.getAdImageAdapter().getCount();
                loadPositionImage();
            }

        });

        //这个是订单的控件
        mOrderUtil.registerHandler(mHandler);

        //对地址对象实例化
        mDistrictSelectModel = new DistrictSelectModel(this);
        listDistrict=new ArrayList<District>();
        mDistrictSelectModel.requestDistrict();

    }



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

   //控制购物车商品的数量显示
    private void updateCount() {

        //从订单工具类中读取购物车中商品的数量
        count = mOrderUtil.getCount();

        System.out.println("do what while get msg Constant.ORDER_CHANGED ---> count = " + count);
        if (count > 0) {
            textCount.setVisibility(View.VISIBLE);
            textCount.setText("" + count);
        } else {
            textCount.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onDestroy() {  //本activity销毁时  清除所有的handler
        super.onDestroy();
        if (popupwindow != null&&popupwindow.isShowing()) {
            popupwindow.dismiss();}
        mOrderUtil.unRegisterHandler(mHandler);
    }

    OrderUtil mOrderUtil = OrderUtil.getInstance();  //获得工具类的单例模式


    private Handler mHandler = new Handler() {

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
    protected void onResume() { //当Activity生成是，显示购物车里商品的数量
        super.onResume();
        mHandler.sendEmptyMessage(Constant.ORDER_CHANGED);
       // isPlay = true;
       // runnable.run();
    }

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        tt("返回键被点击了");
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//        if (popupwindow!=null&&popupwindow.isShowing()){
//            popupwindow.dismiss();
//        }
//            if (popupwindow1!=null&&popupwindow1.isShowing()){
//                popupwindow1.dismiss();
//            }
//        }
//        if (!popupwindow.isShowing()&&!popupwindow1.isShowing()){
//            finish();
//        }
//       return true;
//    }
}
