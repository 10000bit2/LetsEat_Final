package com.example.lets_eat;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Notification#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Notification extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Notification() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Notification.
     */
    // TODO: Rename and change types and number of parameters
    public static Notification newInstance(String param1, String param2) {
        Notification fragment = new Notification();
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
        // Inflate the layout for this fragment
        JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        // 학교 홈페이지에 있는 안내말을 가져옴
        private String htmlPageUrl = "https://www.hansung.ac.kr/web/www/life_03_01_t1"; //파싱할 홈페이지의 URL주소
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


            // 요일에 맞게 가져옴
            // for (Element e : titles) {
            String weekday = weekdayFormat.format(currentTime);
            // span태그로 되어있는 것들을 가져옴
            htmlContentInStringFormat = doc.select("span").get(204).text();
            Log.i("TAG: value is ",htmlContentInStringFormat);

            return null;


        }

        @Override
        protected void onPostExecute(Void result) {
            TextView textView4 = (TextView)getView().findViewById(R.id.textView4);
            textView4.setText(htmlContentInStringFormat);
        }
    }
}