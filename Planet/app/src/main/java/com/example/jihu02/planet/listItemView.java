package com.example.jihu02.planet;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class listItemView extends LinearLayout{

    ImageView imageView;
    TextView  textMission;
    TextView  textMissions;
    //Button go;

    public listItemView(Context context) {
        super(context);
        init(context);
    }

    public listItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        // food_item.xml 을 대상으로 inflation하는 코드 작성
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_item,this, true);

        textMission = (TextView)findViewById(R.id.txtMission_T);
        textMissions = (TextView)findViewById(R.id.txtMission_S);
        //go = (Button)findViewById(R.id.GoButton);
        //go.setVisibility(GONE);
    }


    public void setTextMission(String tm) {
        textMission.setText(tm);
    }

    public void setTextMissions(String stm) {
        this.textMissions.setText(stm);
    }

}


