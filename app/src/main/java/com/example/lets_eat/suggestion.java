package com.example.lets_eat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link suggestion#newInstance} factory method to
 * create an instance of this fragment.
 */
public class suggestion extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference mDatabase;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn;
    private EditText editText;
    @Override
    public void onStart() {
        super.onStart();
    }

    public suggestion() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment suggestion.
     */
    // TODO: Rename and change types and number of parameters
    public static suggestion newInstance(String param1, String param2) {
        suggestion fragment = new suggestion();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_suggestion, container, false);
        btn=(Button)view.findViewById(R.id.button);
        editText=(EditText)view.findViewById(R.id.editTextTextName);

        final ArrayList<String> items = new ArrayList<String>() ;
        // ArrayAdapter 생성. 아이템 View를 선택(single choice)가능하도록 만듦.
        final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, items) ;
        final ListView listview = (ListView) view.findViewById(R.id.listview) ;
        listview.setAdapter(adapter) ;
        // 선택된 곳에 따른 화면이동
        ((SubActivity)getActivity()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        // 파이어베이스 데이터베이스에 있는 suggestion경로를 이용할 것 선언
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseRef = database.getReference("suggestion");

        // Read from the database
        // suggestion경로의 항목들(자식들)을 추가함
        databaseRef.child("suggestion").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adapter.clear();
                // 클래스 모델이 필요?
                for (DataSnapshot notification : dataSnapshot.getChildren()) {
                    // suggestion의 하위 항목들의 값을 얻어 str에 넣음
                    String str = notification.child("suggestion").getValue(String.class);
                    Log.i("TAG: value is ", str);
                    items.add(str);
                }
                // 조정함
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        // listview 생성 및 adapter 지정.
        btn.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                rootNode = FirebaseDatabase.getInstance();
                mDatabase = rootNode.getReference("suggestion");
                //모든 값 가져오기
                String suggestion= editText.getText().toString();
                suggest suggestions = new suggest(suggestion);
                mDatabase.child("suggestion").push().setValue(suggestions);
                // 아이템 추가.
                items.add(editText.getText().toString());
                // listview 갱신
                adapter.notifyDataSetChanged();
                editText.getText().clear();
            }
        }) ;
        return view;
    }
}