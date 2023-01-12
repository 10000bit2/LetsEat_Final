package com.example.lets_eat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.lets_eat.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private EditText email_login;
    private EditText pwd_login;
    static String email ;
    static String pwd;
    FirebaseAuth firebaseAuth;
    @Override
    public void onBackPressed() {
        //뒤로가기 막음
        //super.onBackPressed();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final Intent intent = new Intent(this, SubActivity.class);
        final Intent intent2 = new Intent(this, Join.class);
        firebaseAuth=FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //이미 로그인 되었다면 이 액티비티를 종료함
            finish();
            //그리고 profile 액티비티를 연다.
            startActivity(new Intent(getApplicationContext(),SubActivity.class)); //추가해 줄 ProfileActivity
        }
        email_login=binding.textId;
        pwd_login=binding.textPassword;
        binding.login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //로그인 버튼을 누르면, 학식메뉴 나오게하기
                //이곳에서 로그인 정보 확인하는 코드 필요
                email=email_login.getText().toString().trim();
                pwd=pwd_login.getText().toString().trim();
                //firebase.auth().setPersistence(firebase.auth.Auth.Persistence.LOCAL);

                if(email.length()==0) {
                    // 이메일이 비어있다면
                    Toast.makeText(MainActivity.this,"이메일을 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else if(pwd.length()==0) {
                    // 비밀번호가 비어있다면
                    Toast.makeText(MainActivity.this,"비밀번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                }
                else {
                    firebaseAuth.signInWithEmailAndPassword(email,pwd)
                         .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    // 로그인 성공하면 홈 창으로 접속
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(MainActivity.this,"로그인 오류",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                }
            }
        });
        binding.join.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //회원가입 버튼 누르면, 회원가입 액티비티 실행
                startActivity(intent2);
            }
        });


    }
}
