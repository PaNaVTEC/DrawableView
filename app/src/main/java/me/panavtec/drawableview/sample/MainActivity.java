package me.panavtec.drawableview.sample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import me.panavtec.drawableview.DrawableView;
import me.panavtec.drawableview.DrawableViewConfig;

import java.util.Random;

public class MainActivity extends Activity {

    private DrawableView drawableView;
    private Button changeColorButton;
    private Button strokeWidthMinusButton;
    private Button strokeWidthPlusButton;
    private Button undoButton;
    private DrawableViewConfig config = new DrawableViewConfig();
    
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();
    }

    private void initUi() {
        drawableView = (DrawableView) findViewById(R.id.paintView);
        strokeWidthMinusButton = (Button) findViewById(R.id.strokeWidthMinusButton);
        strokeWidthPlusButton = (Button) findViewById(R.id.strokeWidthPlusButton);
        changeColorButton = (Button) findViewById(R.id.changeColorButton);
        undoButton = (Button) findViewById(R.id.undoButton);
        
        config.setStrokeColor(getResources().getColor(android.R.color.black));
        config.setStrokeWidth(20.0f);
        config.setMinZoom(1.0f);
        config.setMaxZoom(3.0f);
        config.setCanvasHeight(1080);
        config.setCanvasHeight(1920);
        drawableView.setConfig(config);

        strokeWidthPlusButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                config.setStrokeWidth(config.getStrokeWidth() + 10);                
            }
        });
        strokeWidthMinusButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                config.setStrokeWidth(config.getStrokeWidth() - 10);
            }
        });
        changeColorButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Random random = new Random();
                config.setStrokeColor(Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
            }
        });
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                drawableView.undo();
            }
        });
        
    }
}
