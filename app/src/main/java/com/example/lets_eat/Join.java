package com.example.lets_eat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.lets_eat.databinding.ActivityJoinBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Join extends AppCompatActivity {
    private ActivityJoinBinding binding;
    private FirebaseAuth mAuth;
    private static final String TAG = "signUpActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJoinBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        final Intent intent = new Intent(this, MainActivity.class);

        findViewById(R.id.joinBtn).setOnClickListener(onClickListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }


    // 회원가입 버튼을 누르면 signUp()함수가 불려지면서 등록됨
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.joinBtn:
                    signUp();
                    break;
            }
        }
    };


    // 파이어베이스 회원에 등록되도록 하는 함수
    private void signUp() {
        String email = binding.idText.getText().toString();
        String password = binding.passwordText.getText().toString();

        // 파이어베이스의 회원에 들어가는 이메일과 비밀번호
        if(email.length()==0) {
            // 이메일을 비어있다면
            Toast.makeText(Join.this,"이메일을 입력해주세요",Toast.LENGTH_SHORT).show();
        }
        else if(password.length()==0) {
            // 비밀번호가 비어있다면
            Toast.makeText(Join.this,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) { //회원가입 성공 시
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(Join.this, "회원가입 성공, 로그인해주세요", Toast.LENGTH_SHORT).show();
                                finish();

                            } else { //회원가입 실패 시
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Join.this, "중복된 정보가 있습니다", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
    }


}