package com.example.jihu02.planet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OneFragment extends ListFragment implements View.OnClickListener{
    ListView listView2;
    FoodAdapter foodAdapter;
    Button buttonPlus,buttonGo;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;
    private ChildEventListener mChild;


    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();


    String str;
    private ArrayAdapter<String> adapter;
    List<Object> Array = new ArrayList<Object>();

    public OneFragment(){

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        listView2 = (ListView) this.getListView().findViewById(android.R.id.list);
        buttonPlus = view.findViewById(R.id.buttonPlus);
        initDatabase();
        // 어댑터 생성
        foodAdapter = new FoodAdapter();
        // 어댑터 통해서 아이템들을 추가
        foodAdapter.addItem(new ListItem("과자 3개 연속으로 먹기", "", 0));
        // 리스트뷰에 어댑터 설정
        listView2.setAdapter(foodAdapter);
        foodAdapter.notifyDataSetChanged();

        view.findViewById(R.id.buttonPlus).setOnClickListener(this);

        mReference = mDatabase.getReference("Mission");
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //adapter.clear();

                for(DataSnapshot messageData : dataSnapshot.getChildren()){
                    String str = messageData.getValue().toString();
                    Array.add(str);
                    foodAdapter.addItem(new ListItem(str,"",5));
                }
                foodAdapter.notifyDataSetChanged();
                listView2.setSelection(foodAdapter.getCount()-1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void initDatabase() {

        mDatabase = FirebaseDatabase.getInstance();

        mReference = mDatabase.getReference("log");
        mReference.child("log").setValue("check");

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
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container,false);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPlus:
                Intent intent = new Intent(v.getContext(), AddPopUpActivity.class);
                startActivityForResult(intent,200);
                break;

        }
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

            buttonGo = (Button)view.findViewById(R.id.GoButton);

            buttonGo.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {

                    //************************

                    View oParentView = (View)view.getParent();
                    TextView oTextTitle = (TextView)oParentView.findViewById(R.id.txtMission_T);
                    String position = (String)oParentView.getTag();

                    Toast.makeText(getContext(),""+position+" "+oTextTitle.getText(),Toast.LENGTH_LONG).show();

                    String gomission = oTextTitle.getText().toString();
                    databaseReference.child("Mission2").push().setValue(gomission);

                    /*int position = (Integer)view.getTag();
                    ListItem Gomission = items.get(position);
                    String strGomission = Gomission.getText();

                    Bundle bundle = new Bundle();
                    bundle.putString("pushMission",strGomission);*/



                }


            });

            ListItem item = items.get(position);
            view.setTextMission(item.getText());
            view.setTextMissions(item.getStext());
            return view;
        }


    }
}
