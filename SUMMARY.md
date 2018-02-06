更新用
- D:\studiospace\LifeService
- D:\studiospace\LifeService\app\src\main\res\layout
- [技术原理文档说明](README.md)
### 用户模块
- [人员登录](app/src/main/java/com/linyou/lifeservice/activity/LoginAcitvity.java)
- [查看购物车](app/src/main/java/com/linyou/lifeservice/activity/ShoppingCarActivity.java)
- [修改资料](app/src/main/java/com/linyou/lifeservice/activity/UserInfoActivity.java)
    - [性别底部弹出框](app/src/main/java/com/linyou/lifeservice/customdialog/PopSexDialog.java)
- [购物车商品统计的工具类](app/src/main/java/com/linyou/lifeservice/utils/OrderUtil.java)
    - [主导器](app/src/main/java/com/linyou/lifeservice/model/ShoppingCarModel.java)
    - [适配器](app/src/main/java/com/linyou/lifeservice/adapter/ShopCarAdapter.java)
### 订单模块
- [订单列表](app/src/main/java/com/linyou/lifeservice/activity/OrderListActivity.java)
    - [主导器](app/src/main/java/com/linyou/lifeservice/model/OrderListModel.java)
    - [适配器](app/src/main/java/com/linyou/lifeservice/adapter/OrderAdapter.java)
    - [大数据运算工具类](app/src/main/java/com/linyou/lifeservice/utils/ArithUtil.java)
    - [适配器布局](app/src/main/res/layout/order_item.xml)
- [订单详情](app/src/main/java/com/linyou/lifeservice/activity/OrderDetailActivity.java)
- [结算付款](app/src/main/java/com/linyou/lifeservice/activity/ConfirmActivity.java)
- [订单](app/src/main/java/com/linyou/lifeservice/activity/ConfirmActivity.java)
    - 选择地址 运用了跳转携带数据的方式
### 商品模块
- [商品列表](app/src/main/java/com/linyou/lifeservice/activity/GoodsListActivity.java)
    - 上拉加载，下滑刷新
### 其他模块
- [常量](app/src/main/java/com/linyou/lifeservice/Constant.java)
- [清单列表](app/src/main/AndroidManifest.xml)
### 技术模块
- [AbPullToRefreshView空指针](resource/AbPullToRefreshView空指针.md)
- [ScrollView只能有一个子控件](resource/ScrollView只能有一个子控件.md)


