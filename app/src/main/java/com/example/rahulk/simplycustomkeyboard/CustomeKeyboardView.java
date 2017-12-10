package com.example.rahulk.simplycustomkeyboard;

import android.content.Context;
import android.graphics.Canvas;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

/**
 * Created by ADMIN on 12/3/2017.
 */

public class CustomeKeyboardView extends KeyboardView {

    Context context;

    public CustomeKeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CustomeKeyboardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       /* @SuppressLint("DrawAllocation") Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(68);
        List<Keyboard.Key> keys = getKeyboard().getKeys();
        for (Keyboard.Key key : keys) {

            if (key.codes[0] != -4 && key.codes[0] != 12 && key.codes[0] != 32 && key.codes[0] != 40 && key.codes[0] != -5) {
                if (key.icon != null) {

                    key.icon.setBounds(key.x-2, key.y-2, key.width-2,  key.height-2 );
                    key.icon.draw(canvas);
                }
            }
        }*/
    /*        if (key.codes[0] == 97) {

                Log.e("KEY", "Drawing key with code " + key.codes[0]);
                Drawable dr = (Drawable) context.getResources().getDrawable(R.drawable.yellow_drawable_vowels);
                dr.setBounds(key.x, key.y, key.width, key.height);
                dr.draw(canvas);


                if (key.label != null) {

                    canvas.drawText(key.label.toString(), key.width / 2, (key.height / 2) + 15, paint);
                } *//*else {
                    key.icon.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
                    key.icon.draw(canvas);
                }*//*

            }
            else if (key.codes[0] == 101) {
                Drawable dr = (Drawable) context.getResources().getDrawable(R.drawable.green_drawable_vowels);
                dr.setBounds(key.x, key.y, key.width, key.height);
                dr.draw(canvas);

                if (key.label != null) {
                    canvas.drawText(key.label.toString(), key.width / 2, (key.height / 2) + 15, paint);
                }
            }

        }*/
    }
}
