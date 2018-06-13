package com.example.jihu02.planet;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ThreeFragment extends ListFragment {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();


    ListView listView;
    MissionAdapter missionAdapter;
    String Getmission;
    private ArrayAdapter<String> adapter;
    List<Object> Array = new ArrayList<Object>();

    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_3, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = (ListView) this.getListView().findViewById(android.R.id.list);
        // 어댑터 생성
        missionAdapter = new MissionAdapter();
        // 어댑터 통해서 아이템들을 추가
        missionAdapter.addItem(new ListItem("과자 3개 연속으로 먹기", "", 0));
        // 리스트뷰에 어댑터 설정
        listView.setAdapter(missionAdapter);
        missionAdapter.notifyDataSetChanged();

        initDatabase();
       /* if(getArguments() !=null){
            Getmission = getArguments().getString("pushMission");
            Array.add(Getmission);
            missionAdapter.addItem(new ListItem(Getmission,"",1));

            missionAdapter.notifyDataSetChanged();
            listView.setSelection(missionAdapter.getCount()-1);

        }*/


        mReference = mDatabase.getReference("Mission2");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //adapter.clear();

                for(DataSnapshot messageData : dataSnapshot.getChildren()){
                    String str = messageData.getValue().toString();
                    Array.add(str);
                    missionAdapter.addItem(new ListItem(str,"",1));
                }
                missionAdapter.notifyDataSetChanged();
                listView.setSelection(missionAdapter.getCount()-1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void initDatabase() {

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("log2");
        mReference.child("log2").setValue("check");

        mChild = new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mReference.addChildEventListener(mChild);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mReference.removeEventListener(mChild);
    }

    class MissionAdapter extends BaseAdapter {
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
