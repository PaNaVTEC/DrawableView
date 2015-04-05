package me.panavtec.drawableview.sample;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Random;
import me.panavtec.drawableview.DrawableView;
import me.panavtec.drawableview.DrawableViewConfig;

public class MainActivity extends Activity {

  private float strokeWidth = 20.0f;
  private DrawableView drawableView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initUi();
  }

  private void initUi() {
    drawableView = (DrawableView) findViewById(R.id.paintView);
    Button strokeWidthMinusButton = (Button) findViewById(R.id.strokeWidthMinusButton);
    Button strokeWidthPlusButton = (Button) findViewById(R.id.strokeWidthPlusButton);
    Button changeColorButton = (Button) findViewById(R.id.changeColorButton);
    Button undoButton = (Button) findViewById(R.id.undoButton);

    drawableView.setConfig(new DrawableViewConfig.Builder(1920, 1080).
        maxZoom(3.0f).
        strokeWidth(strokeWidth).
        canvasBackgroundColor(Color.LTGRAY).
        canvasBorderColor(getResources().getColor(android.R.color.black)).
        strokeColor(getResources().getColor(android.R.color.black)).
        build());

    strokeWidthPlusButton.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View v) {
        strokeWidth += 10.0f;
        drawableView.setStrokeWidth(strokeWidth);
      }
    });
    strokeWidthMinusButton.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View v) {
        strokeWidth -= 10.0f;
        drawableView.setStrokeWidth(strokeWidth);
      }
    });
    changeColorButton.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View v) {
        Random random = new Random();
        drawableView.setStrokeColor(
            Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)));
      }
    });
    undoButton.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View v) {
        drawableView.undo();
      }
    });
  }
}
