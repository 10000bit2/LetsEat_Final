package com.example.lets_eat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.lets_eat.databinding.ActivitySubBinding;
import com.example.lets_eat.databinding.FragmentHomeBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.net.URL;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import uk.co.senab.photoview.PhotoViewAttacher;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters

    public home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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
        home.JsoupAsyncTask jsoupAsyncTask = new JsoupAsyncTask();
        jsoupAsyncTask.execute();


        View v= inflater.inflate(R.layout.fragment_home, container, false);
        ImageView imgView=(ImageView)v.findViewById(R.id.imageView);
        PhotoViewAttacher photoView = new PhotoViewAttacher(imgView);
        photoView.update();
        String imageUrl = "https://www.hansung.ac.kr/portlet-repositories/fckeditor/images/7qHyuRAiKcc=/1596542799929.png";
        Glide.with(this).load(imageUrl).into(imgView);
        return v;
    }
    /* public void sendImageRequest() {
         String url = "https://www.hansung.ac.kr/portlet-repositories/fckeditor/images/7qHyuRAiKcc=/1596542799929.png";

         ImageView imageView = (ImageView) getView().findViewById(R.id.imageView);
         ImageLoadTask task = new ImageLoadTask(url,imageView);
         task.execute();
     }*/
    class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {
        private String htmlPageUrl = "https://www.hansung.ac.kr/web/www/life_03_01_t2"; //파싱할 홈페이지의 URL주소
        private String htmlContentInStringFormat = "";
        private Image image;
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


            //테스트1
            // Elements titles = doc.select("td"); //2: 월요일 3: 화요일

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

            TextView textView = (TextView)getView().findViewById(R.id.facultiesMenu);
            textView.setText(htmlContentInStringFormat);
        }
    }

   /* public class ImageLoadTask extends AsyncTask<Void,Void, Bitmap> {

        private String urlStr;
        private ImageView imageView;
        private HashMap<String, Bitmap> bitmapHash = new HashMap<String, Bitmap>();

        public ImageLoadTask(String urlStr, ImageView imageView) {
            imageView = (ImageView) getView().findViewById(R.id.imageView);
            this.urlStr = "https://www.hansung.ac.kr/portlet-repositories/fckeditor/images/7qHyuRAiKcc=/1596542799929.png";
            this.imageView = imageView;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Bitmap doInBackground(Void... voids) {
            Bitmap bitmap = null;
            try {
                if (bitmapHash.containsKey(urlStr)) {
                    Bitmap oldbitmap = bitmapHash.remove(urlStr);
                    if(oldbitmap != null) {
                        oldbitmap.recycle();
                        oldbitmap = null;
                    }
                }
                URL url = new URL(urlStr);
                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                bitmapHash.put(urlStr,bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return bitmap;
        }
        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
           imageView = (ImageView) getView().findViewById(R.id.imageView);
            imageView.setImageBitmap(bitmap);
            imageView.invalidate();
        }
    }*/
}