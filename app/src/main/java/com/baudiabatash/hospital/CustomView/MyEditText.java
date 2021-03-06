package com.baudiabatash.hospital.CustomView;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;

import com.baudiabatash.hospital.R;


/**
 * Created by Sohel on 9/28/2016.
 */
public class MyEditText extends EditText implements View.OnFocusChangeListener {
    private Context context;

    public MyEditText(Context context) {
        super(context);

    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        this.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.edit_text_background_deselect,null));
        this.setOnFocusChangeListener(this);
        this.setPadding(16,5,5,5);


        //this.setHeight(80);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(view.hasFocus()){
            view.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.edit_text_back,null));
        }else{
            view.setBackground(ResourcesCompat.getDrawable(context.getResources(),R.drawable.edit_text_background_deselect,null));
        }
    }
}
