# BlinkCheckBox
blinkcheckbox v1.0
====
这是一个简洁的三色CheckBox(眨眼的效果)
---
![Image text](https://github.com/carendule/BlinkCheckBox/blob/master/app/image/untitled.gif)
你可以只设定两种颜色和两个图片(box1)
```XML
  box:checkedImgInside="@drawable/site_download"
  box:uncheckedImgInside="@drawable/site_download_uncheck"
  box:normalBackgroundColor="@android:color/white"
  box:checkedColor="#FFA07A"
  box:uncheckedColor="@android:color/white"
```
也可以分别内置两个图片和两个颜色在check和uncheck时显示(box2)
```XML
  box:checkedImgInside="@drawable/site_dlna"
  box:uncheckedImgInside="@drawable/site_dlna_uncheck"
  box:normalBackgroundColor="@android:color/white"
  box:checkedColor="#FFA07A"
  box:uncheckedColor="#87CEFA"
```
或者不设置图片只用颜色作为普通checkbox使用(box3)
```XML
  box:normalBackgroundColor="@android:color/white"
  box:checkedColor="#FFA07A"
  box:uncheckedColor="#87CEFA"
```

不要忘了给每个checkbox加上监听
```JAVA
blinkCheckBox1.setOnCheckedChangeListener(new BlinkCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(BlinkCheckBox view, boolean isChecked) {
                Toast.makeText(mainActivity.this,"box1 clicked",Toast.LENGTH_SHORT).show();
            }
        });
```

ATTRS
```XML
 name="checkedImgInside" 选中的图片drawable
 name="uncheckedImgInside" 未选中的图片drawable
 name="normalBackgroundColor" 切换时中间色
 name="checkedColor" 选中颜色
 name="uncheckedColor" 未选中颜色
 name="imgMargin" 图片与外框距离
 name="effectDuration" 动画时长
```
