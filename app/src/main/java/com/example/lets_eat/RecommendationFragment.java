package com.example.lets_eat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecommendationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecommendationFragment extends Fragment {
    FirebaseDatabase rootNode;
    DatabaseReference mDatabase;
    Button btn_search;
    EditText menu;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public RecommendationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecommendationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecommendationFragment newInstance(String param1, String param2) {
        RecommendationFragment fragment = new RecommendationFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_recommendation, container, false);
        btn_search = (Button) view.findViewById(R.id.search);
        menu = (EditText) view.findViewById(R.id.menu);
        //if(R.id.ratingBar)


        // 빈 데이터 리스트 생성.
        final ArrayList<String> items = new ArrayList<String>();

        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        final ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, items);
        final ListView listview = (ListView)view.findViewById(R.id.listview);
        listview.setAdapter(adapter);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // 파이어베이스 데이터베이스에 리뷰 경로로 들어가도록 함
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference databaseRef = database.getReference("review");


        // Read from the database
        // user경로의 자식으로 항목이 추가될 수 있도록 함
        databaseRef.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();

                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    //MyFiles filename = (MyFiles) fileSnapshot.getValue(MyFiles.class);
                    // reviw 안에 있는 user 안의 review를 listview에 넣기
                    String str = user.child("menuname").getValue(String.class);
                    String strs = user.child("review").getValue(String.class);
                    String strss = user.child("star").getValue(String.class);
                    Log.i("TAG: value is ", str);
                    items.add(str + "\n리뷰: " + strs + "\n별점: " + strss);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });




        menu.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if(keyCode==KeyEvent.KEYCODE_ENTER)
                {
                    btn_search.performClick();
                    return true;
                }
                return false;
            }
        });

        // 검색 버튼을 누르면 파이어베이스에 있는 항목들이 리스트뷰에 뜨도록 함
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = menu.getText().toString();
                // user경로에 있는 항목들을 사용함
                databaseRef.child("user").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        adapter.clear();
                        // 클래스 모델이 필요?
                        for (DataSnapshot user : dataSnapshot.getChildren()) {
                            //MyFiles filename = (MyFiles) fileSnapshot.getValue(MyFiles.class);
                            // reviw 안에 있는 user 안의 review를 listview에 넣기
                            String str = user.child("menuname").getValue(String.class);
                            String strs = user.child("review").getValue(String.class);
                            String strss = user.child("star").getValue(String.class);
                            // 검색한 단어가 포함되어있다면
                            if (str.contains(name)) {
                                // 포함된 단어를 갖고 있는 리뷰만 보여줌
                                Log.i("TAG: value is ", str);
                                items.add(str + "\n리뷰: " + strs + "\n별점: " + strss);
                            }
                            // 검색한 단어가 없다면
                            else if(name.length()==0){
                                // 전체를 보여줌
                                items.add(str + "\n리뷰: " + strs + "\n별점: " + strss);
                            }
                        }
                        // 배치를 조정함
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}