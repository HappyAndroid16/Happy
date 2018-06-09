package com.example.jihu02.planet;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;

public class HomeActivity extends AppCompatActivity{

    boolean isPageOpen = false;

    TabLayout tabLayout;
    ViewPager viewPager;
    Animation translateLeftAnim;
    Animation translateRightAnim;
    LinearLayout page;
    Button slideBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        page = (LinearLayout)findViewById(R.id.page);
        slideBtn = findViewById(R.id.slidebtn);
        setViewPager();
        setTabLayout();
        setListener();
        // 애니메이션 객체 로딩
        translateLeftAnim = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.translate_left);
        translateRightAnim = AnimationUtils.loadAnimation(this.getApplicationContext(), R.anim.translate_right);

        // 애니메이션 객체에 리스너 설정
        SlidingPageAnimationListener animListener = new SlidingPageAnimationListener();
        translateLeftAnim.setAnimationListener(animListener);
        translateRightAnim.setAnimationListener(animListener);

    }
    public void onSlideClicked(View view) {
        if (isPageOpen) {
            page.startAnimation(translateRightAnim);
        } else {
            page.setVisibility(view.VISIBLE);
            page.startAnimation(translateLeftAnim);
        }
    }

    public void onCloseClicked(View view) {
        if (isPageOpen) {
            page.startAnimation(translateRightAnim);
        } else {
            page.setVisibility(view.VISIBLE);
            page.startAnimation(translateLeftAnim);
        }
    }

    private class SlidingPageAnimationListener implements Animation.AnimationListener {
        /**
         * 애니메이션이 끝날 때 호출되는 메소드
         */
        public void onAnimationEnd(Animation animation) {

            if (isPageOpen) {
                page.setVisibility(View.INVISIBLE);
                isPageOpen = false;
            } else {
                isPageOpen = true;
            }
        }

        public void onAnimationRepeat(Animation animation) {

        }

        public void onAnimationStart(Animation animation) {

        }

    }


    public void setTabLayout(){
        tabLayout = findViewById(R.id.tabLayout);
    }

    public void setViewPager(){
        viewPager = findViewById(R.id.viewPager);
        CustomAdapter adapter = new CustomAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
    }
    public void setListener(){
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }


}
