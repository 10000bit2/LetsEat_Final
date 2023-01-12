package com.example.lets_eat;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lets_eat.databinding.FragmentPersonBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link person#newInstance} factory method to
 * create an instance of this fragment.
 */
public class person extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private FragmentPersonBinding binding;

    Button btnLogout;
    Button btnRevoke;
    private static final String TAG = "revokeActivity";


    // TODO: Rename and change types of parameters

    public person() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment person.
     */
    // TODO: Rename and change types and number of parameters
    public static person newInstance(String param1, String param2) {
        person fragment = new person();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        View view = inflater.inflate(R.layout.fragment_person, container, false);
        btnLogout = (Button)view.findViewById(R.id.btn_logout);
        btnRevoke = (Button)view.findViewById(R.id.btn_revoke);


        // MainActivity클래스로 넘어가게 해주는 인텐트 선언
        final Intent intent9 = new Intent(getActivity(), MainActivity.class);

        // 로그아웃 버튼을 누르면
        btnLogout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // 로그아웃이 된 후, 첫 화면으로 넘어감
                        FirebaseAuth.getInstance().signOut();
                        startActivity(intent9);
                    }
                }
        );


        // 탈퇴 버튼을 누르면
        btnRevoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                // 연동된 파이어베이스에서 계정이 삭제됨 (탈퇴)
                ((FirebaseUser) user).delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                // 탈퇴 성공 시
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User account deleted.");
                                    //Toast.makeText(person.getActivity(), "user account deleted!", Toast.LENGTH_SHORT).show();
                                    // 첫 화면으로 넘어감
                                    startActivity(intent9);

                                }
                            }
                        });

            }
        });
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_person, container, false);
        return view;
    }




    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //binding = null;
    }
}