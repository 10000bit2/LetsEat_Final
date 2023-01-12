package com.example.lets_eat;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.example.lets_eat.databinding.ActivitySubBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SubActivity extends AppCompatActivity {
    private static ActivitySubBinding binding;
    private ChipNavigationBar chipnavigationbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySubBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        chipnavigationbar = findViewById(R.id.chipbar);
        chipnavigationbar.setItemSelected(R.id.chipbar,true);

        // 처음 보여지는 프래그먼트
        getSupportFragmentManager()
                .beginTransaction()
                // fragment자리에 계정정보인 home 프래그먼트를 배치함
                .replace(R.id.fragment_home, new home())
                // 뒤로가기 버튼 누르면 프래그먼트가 전으로 돌아갈 수 있도록
                .addToBackStack(null)
                .commit();
        bottonMenu();

        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();

    }

    private void bottonMenu() {
        chipnavigationbar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch(i){
                    case R.id.item0:
                        Toast.makeText(SubActivity.this, "홈", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager()
                                .beginTransaction()
                                // fragment자리에 계정정보인 home 프래그먼트를 배치함
                                .replace(R.id.fragment_home, new home())
                                // 뒤로가기 버튼 누르면 프래그먼트가 전으로 돌아갈 수 있도록
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.item1:
                        Toast.makeText(SubActivity.this, "추천메뉴", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager()
                                .beginTransaction()
                                // fragment자리에 계정정보인 RecommendationFragment 프래그먼트를 배치함
                                .replace(R.id.fragment_home, new RecommendationFragment())
                                // 뒤로가기 버튼 누르면 프래그먼트가 전으로 돌아갈 수 있도록
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.item2:
                        Toast.makeText(SubActivity.this, "한줄 건의함", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager()
                                .beginTransaction()
                                // fragment자리에 계정정보인 suggestion 프래그먼트를 배치함
                                .replace(R.id.fragment_home, new suggestion())
                                // 뒤로가기 버튼 누르면 프래그먼트가 전으로 돌아갈 수 있도록
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.item3:
                        Toast.makeText(SubActivity.this, "알림", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager()
                                .beginTransaction()
                                // fragment자리에 계정정보인 Notification 프래그먼트를 배치함
                                .replace(R.id.fragment_home, new Notification())
                                // 뒤로가기 버튼 누르면 프래그먼트가 전으로 돌아갈 수 있도록
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.item4:
                        Toast.makeText(SubActivity.this, "리뷰", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager()
                                .beginTransaction()
                                // fragment자리에 계정정보인 RationFragment 프래그먼트를 배치함
                                .replace(R.id.fragment_home, new RationFragment())
                                // 뒤로가기 버튼 누르면 프래그먼트가 전으로 돌아갈 수 있도록
                                .addToBackStack(null)
                                .commit();
                        break;
                    case R.id.item5:
                        Toast.makeText(SubActivity.this, "계정 정보", Toast.LENGTH_SHORT).show();
                        getSupportFragmentManager()
                                .beginTransaction()
                                // fragment자리에 계정정보인 person 프래그먼트를 배치함
                                .replace(R.id.fragment_home, new person())
                                // 뒤로가기 버튼 누르면 프래그먼트가 전으로 돌아갈 수 있도록
                                .addToBackStack(null)
                                .commit();
                        break;

                }
            }
        });
    }

    static class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        private String htmlPageUrl = "https://www.hansung.ac.kr/web/www/life_03_01_t2"; //파싱할 홈페이지의 URL주소
        private String htmlContentInStringFormat = "";
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.ENGLISH);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {

            Document doc = null;
            try {
                doc = Jsoup.connect(htmlPageUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }


            // 요일에 맞게 나오도록.
            // for (Element e : titles) {
            String weekday = weekdayFormat.format(currentTime);


            if (weekday.equals("Mon")) {
                htmlContentInStringFormat =doc.select("td").get(0).text();
                System.out.println(htmlContentInStringFormat);
            } else if (weekday.equals("Tue")) {
                htmlContentInStringFormat = doc.select("td").get(1).text();
                System.out.println(htmlContentInStringFormat);
            } else if (weekday.equals("Wed")) {
                htmlContentInStringFormat = doc.select("td").get(2).text();
                System.out.println(htmlContentInStringFormat);
            } else if (weekday.equals("Thu")) {
                htmlContentInStringFormat = doc.select("td").get(3).text();
                System.out.println(htmlContentInStringFormat);
            } else if (weekday.equals("Fri")) {
                htmlContentInStringFormat = doc.select("td").get(4).text();
                System.out.println(htmlContentInStringFormat);
            } else if (weekday.equals("Sat")) {
                htmlContentInStringFormat = "운영하지 않습니다.";
                System.out.println("운영하지 않습니다.");
            } else if (weekday.equals("Sun")) {
                htmlContentInStringFormat = "운영하지 않습니다.";
                System.out.println("운영하지 않습니다.");
            }

            return null;

        }
        @Override
        protected void onPostExecute(Void result) {

            //binding.facultiesMenu.setText(htmlContentInStringFormat);
        }
    }

}