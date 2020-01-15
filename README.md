# BlinkCheckBox
这是一个简洁的三色CheckBox(像眨眼睛)
===
![Image text](https://github.com/carendule/BlinkCheckBox/blob/master/app/image/untitled.gif)

使用该库先在Gradle中添加
```JAVA
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  dependencies {
	        implementation 'com.github.carendule:BlinkCheckBox:V1.0.1'
	}
 ```
你可以只设定两种颜色和两个图片(box1)
```XML
  box:checkedImgInside="@drawable/site_download"
  box:uncheckedImgInside="@drawable/site_download_uncheck"
  box:normalBackgroundColor="@android:color/white"
  box:checkedColor="#FFA07A"
  box:uncheckedColor="@android:color/white"
  box:imgMarginTop="8dp"
  box:imgMarginLeft="8dp"
  box:imgMarginRight="8dp"
  box:imgMarginBottom="8dp"
  box:blinkDuration="600"
```
也可以分别内置两个图片和两个颜色在check和uncheck时显示(box2)
```XML
  box:checkedImgInside="@drawable/site_dlna"
  box:uncheckedImgInside="@drawable/site_dlna_uncheck"
  box:normalBackgroundColor="@android:color/white"
  box:checkedColor="#FFA07A"
  box:uncheckedColor="#87CEFA"
  box:imgMarginTop="8dp"
  box:imgMarginLeft="8dp"
  box:imgMarginRight="8dp"
  box:imgMarginBottom="8dp"
  box:blinkDuration="600"
```
或者不设置图片只用颜色作为普通checkbox使用(box3)
```XML
  box:normalBackgroundColor="@android:color/white"
  box:normalBackgroundImg="@drawable/star"
  box:checkedColor="#FFA07A"
  box:uncheckedColor="#87CEFA"
  box:blinkDuration="600"
```
支持使用任何图片作为box的背景形状，只需设定normalBackgroundImg属性，check和uncheck形状将自动匹配颜色
```XML
  box:normalBackgroundColor="@android:color/white"
  box:normalBackgroundImg="@drawable/star"
  box:checkedColor="#FFA07A"
  box:uncheckedColor="#87CEFA"
```
提供四种按钮动画效果
```XML
  box:effect="bounce"  //flipVer|flipHor|flipSur
  box:effectDuration="300"
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
        name="checkedImgInside" 选中时的内部图片
        name="uncheckedImgInside" 未选中时的内部图片
        name="normalBackgroundImg" 按钮形状
        name="normalBackgroundColor" 按钮切换时过渡颜色
        name="checkedColor" 选中时颜色
        name="uncheckedColor" 未选中时颜色
        name="imgMarginTop" 内部图片顶距
        name="imgMarginBottom" 内部图片底距
        name="imgMarginLeft" 内部图片左距
        name="imgMarginRight" 内部图片右距"
        name="blinkDuration" 选中与未选中效果切换时间
        name="effectDuration" 按钮效果时间
        name="effect"
            name="flipVer" 竖直翻转
            name="flipHor" 横向翻转
            name="flipSur" 平面旋转
            name="bounce"   弹跳
```
