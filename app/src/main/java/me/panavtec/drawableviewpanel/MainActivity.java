package me.panavtec.drawableviewpanel;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    private PaintView paintView;
    private PaintViewConfig config = new PaintViewConfig();
    
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        paintView = (PaintView) findViewById(R.id.paintView);
        config.setStrokeColor(getResources().getColor(android.R.color.black));
        config.setStrokeWidth(20.0f);
        config.setMinZoom(1.0f);
        config.setMaxZoom(3.0f);
        config.setCanvasHeight(1080);
        config.setCanvasHeight(1920);
        paintView.setConfig(config);
    }
}
