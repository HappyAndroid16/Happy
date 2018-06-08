package com.example.jihu02.planet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class OneFragment extends ListFragment implements View.OnClickListener {
    ListView listView2;
    FoodAdapter foodAdapter;
    Button buttonPlus;
    public OneFragment(){

    }
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_1, container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView2 = (ListView) this.getListView().findViewById(android.R.id.list);
        buttonPlus = view.findViewById(R.id.buttonPlus);
        // 어댑터 생성
        foodAdapter = new FoodAdapter();
        // 어댑터 통해서 아이템들을 추가
        foodAdapter.addItem(new ListItem("국문학과 남**", "기숙사 A동에서 치킨 같이 드실분", 0));
        foodAdapter.addItem(new ListItem("컴퓨터공학과 조**", "정문에서 고양이 본 썰 푼다", 1));
        foodAdapter.addItem(new ListItem("통계학과 이**", "집에서 구운 수제쿠키 무나해요", 2));
        foodAdapter.addItem(new ListItem("사회학과 김**", "역에서 택시 같이 타실분", 3));
        foodAdapter.addItem(new ListItem("문화인류학과 최**", "사회학과 김ㅈㅅ 남자친구 있나요", 4));
        // 리스트뷰에 어댑터 설정
        listView2.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();

        view.findViewById(R.id.button).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //switch (v.getId()) {
           // case R.id.button:
                Intent intent = new Intent(v.getContext(), AddPopUpActivity.class);
                startActivityForResult(intent,200);
                //break;

       // }
    }

    class FoodAdapter extends BaseAdapter {
        ArrayList<ListItem> items = new ArrayList<ListItem>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        public void addItem(ListItem item) {
            items.add(item);
        }

        @Override // ******중요*******
        public View getView(int position, View convertView, ViewGroup parent) {
            listItemView view = new listItemView(getContext());

            ListItem item = items.get(position);
            view.setTextMission(item.getText());
            view.setTextMissions(item.getStext());
            return view;
        }
    }
}
