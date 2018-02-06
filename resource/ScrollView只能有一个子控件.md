
> 注意

- ScrollView只能有一个子控件
- http://blog.csdn.net/qq_26420489/article/details/51181758
- ScrollView只能添加一个子控件，如果添加了多个子控件，则会出现“ScrollView can host only one direct child”异常
  解决办法：子控件外面再套一层LinearLayout,注意只能是Linearlayout，布局中不能出现RelativeLayout

- scrollview内部组件android:layout_height="fill_parent"无效的解决办法
  解决办法：
  需要设置scrollview的fillViewport属性为"true"才能使其子组件可以扩展
- 在ViewPager 外面嵌套ScrollView 时,发现ViewPager中的元素显示不出来。
  解决办法：
  在ScrollView节点指定android:fillviewport="true"
- ScrollView嵌套ListView,ListView不能完全正确的显示
  解决办法：
  1）自定义一个ListView,重写onMeasure方法