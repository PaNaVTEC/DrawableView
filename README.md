#Android Drawable View ![Maven Central](https://img.shields.io/maven-central/v/me.panavtec/drawableview.svg)
![Logo](art/drawabledroid.png)

An Android view that allows to paint with a finger in the screen and saves the result as a Bitmap.

##Importing to your project
Add this dependency to your build.gradle file:

```java
dependencies {
    compile 'me.panavtec:drawableview:{Lib version, see the mvn central badge}'
}
```

##Basic usage
Add the view to your xml layout in this way:

```xml
<me.panavtec.drawableview.DrawableView
            android:id="@+id/paintView"
            android:layout_width="match_parent"
            android:layout_height="match_parent"/>
```

Now in your activity code set a config to this view:

```java
DrawableViewConfig config = new DrawableViewConfig();
config.setStrokeColor(getResources().getColor(android.R.color.black));
config.setStrokeWidth(20.0f);
config.setMinZoom(1.0f);
config.setMaxZoom(3.0f);
config.setCanvasHeight(1080);
config.setCanvasWidth(1920);
drawableView.setConfig(config);
```

Now the view is ready to paint! You can see the attached sample for more info
