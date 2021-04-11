package com.example.clothesvillage.home;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.clothesvillage.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private View view;

    private ArrayList<WData> mDataset = new ArrayList<WData>();
    private Context mContext;
    private WData Data;

    TextView weather_content_tv;
    TextView weather_highlowtemp_tv;
    TextView weather_temperature_tv;
    TextView recommend_style_tv;
    ImageView weather_picture;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        weather_content_tv = (TextView)view.findViewById(R.id.textview_weather_content);
        weather_highlowtemp_tv = (TextView)view.findViewById(R.id.textview_weather_highlowtemp);
        weather_temperature_tv = (TextView)view.findViewById(R.id.textview_weather_temperature);
        recommend_style_tv = (TextView) view.findViewById(R.id.textview_recomand_style);
        weather_picture = (ImageView)view.findViewById(R.id.imageview_weather_picture);

        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        //mContext = context;
        String URL = "https://search.naver.com/search.naver?where=nexearch&sm=top_sly.hst&fbm=0&acr=1&acq=%EC%98%A4%EB%8A%98%EB%82%A0&qdt=0&ie=utf8&query=오늘날씨";
        WeatherAsynTask task = new WeatherAsynTask();
        task.execute(URL);
    }

    private class WeatherAsynTask extends AsyncTask<String, Void, String> {

        WData data = new WData();
        String weather_state = "";
        ProgressDialog progressDialog = new ProgressDialog(getActivity());

        public WeatherAsynTask() {
            //
        }

        @Override
        protected void onPreExecute() {
            //super.onPreExecute();
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage("날씨를 로딩중입니다");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String URL = params[0];
            String E1 = "div[class=main_info]";

            try {
                Document document = Jsoup.connect(URL).get();
                Elements elements = document.select(E1);

                for(Element element : elements) {

                    E1 = "p[class=cast_txt]";
                    Elements e1 = document.select(E1);
                    data.setContent(e1.get(0).text());

                    E1 = "p[class=info_temperature]";
                    Elements e2 = document.select(E1);
                    data.setTemperature(e2.text().substring(0, 2) + "℃");
                    recommend_style(Integer.parseInt(e2.text().substring(0, 2)));

                    E1 = "span[class=merge]";
                    Elements e3 = document.select(E1);
                    data.setHigh_Low_temp(e3.text());

                    E1 = "div[class=main_info] span";
                    Elements e4 = document.select(E1);
                    weather_state= e4.attr("class");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            weather_content_tv.setText(data.getContent());
            weather_highlowtemp_tv.setText(data.getHigh_low_temp());
            weather_temperature_tv.setText(data.getTemperature());
            setWeather(weather_state);
            progressDialog.dismiss();
        }

        public void setWeather(String w_state) {
            switch(w_state) {
                case "ico_state ws1": //맑음(낮)
                    weather_picture.setImageResource(R.drawable.ws1);
                    break;
                case "ico_state ws2": //맑음(밤)
                    weather_picture.setImageResource(R.drawable.ws2);
                    break;
                case "ico_state ws3": //구름조금(낮)
                    weather_picture.setImageResource(R.drawable.ws3);
                    break;
                case "ico_state ws4": //구름조금(밤)
                    weather_picture.setImageResource(R.drawable.ws4);
                    break;
                case "ico_state ws5": //구름많음(낮)
                    weather_picture.setImageResource(R.drawable.ws5);
                    break;
                case "ico_state ws6": //구름많음(밤)
                    weather_picture.setImageResource(R.drawable.ws6);
                    break;
                case "ico_state ws7": //흐림
                    weather_picture.setImageResource(R.drawable.ws7);
                    break;
                case "ico_state ws8": //약한비
                    weather_picture.setImageResource(R.drawable.ws8);
                    break;
                case "ico_state ws9": //비
                    weather_picture.setImageResource(R.drawable.ws9);
                    break;
                case "ico_state ws10": //강한비
                    weather_picture.setImageResource(R.drawable.ws10);
                    break;
                case "ico_state ws11": //약한눈
                    weather_picture.setImageResource(R.drawable.ws11);
                    break;
                case "ico_state ws12": //눈
                    weather_picture.setImageResource(R.drawable.ws12);
                    break;
                case "ico_state ws13": //강한눈
                    weather_picture.setImageResource(R.drawable.ws13);
                    break;
                case "ico_state ws14": //진눈깨비
                    weather_picture.setImageResource(R.drawable.ws14);
                    break;
                case "ico_state ws15": //소나기
                    weather_picture.setImageResource(R.drawable.ws15);
                    break;
                case "ico_state ws16": //소낙눈
                    weather_picture.setImageResource(R.drawable.ws16);
                    break;
                case "ico_state ws17": //안개
                    weather_picture.setImageResource(R.drawable.ws17_40);
                    break;
                case "ico_state ws18": //번개, 뇌우
                    weather_picture.setImageResource(R.drawable.ws18);
                    break;
                case "ico_state ws19": //우박
                    weather_picture.setImageResource(R.drawable.ws19);
                    break;
                case "ico_state ws20": //황사
                    weather_picture.setImageResource(R.drawable.ws20_41);
                    break;
                case "ico_state ws21": //비 또는 눈
                    weather_picture.setImageResource(R.drawable.ws21);
                    break;
                case "ico_state ws22": //가끔비
                    weather_picture.setImageResource(R.drawable.ws22_31);
                    break;
                case "ico_state ws23": //가끔눈
                    weather_picture.setImageResource(R.drawable.ws23_32);
                    break;
                case "ico_state ws24": //가끔비 또는 눈
                    weather_picture.setImageResource(R.drawable.ws24_33);
                    break;
                case "ico_state ws25": //흐린후갬
                    weather_picture.setImageResource(R.drawable.ws25);
                    break;
                case "ico_state ws26": //뇌우후갬
                    weather_picture.setImageResource(R.drawable.ws26);
                    break;
                case "ico_state ws27": //비후갬
                    weather_picture.setImageResource(R.drawable.ws27);
                    break;
                case "ico_state ws28": //눈후갬
                    weather_picture.setImageResource(R.drawable.ws28);
                    break;
                case "ico_state ws29": //흐려져비
                    weather_picture.setImageResource(R.drawable.ws29);
                    break;
                case "ico_state ws30": //흐려져눈
                    weather_picture.setImageResource(R.drawable.ws30);
                    break;
                case "ico_state ws31": //가끔비(밤)
                    weather_picture.setImageResource(R.drawable.ws22_31);
                    break;
                case "ico_state ws32": //가끔눈(밤)
                    weather_picture.setImageResource(R.drawable.ws23_32);
                    break;
                case "ico_state ws33": //가끔비 또는 눈(밤)
                    weather_picture.setImageResource(R.drawable.ws24_33);
                    break;
                case "ico_state ws34": //흐린후갬(밤)
                    weather_picture.setImageResource(R.drawable.ws34);
                    break;
                case "ico_state ws35": //뇌우후갬(밤)
                    weather_picture.setImageResource(R.drawable.ws35);
                    break;
                case "ico_state ws36": //비후갬(밤)
                    weather_picture.setImageResource(R.drawable.ws36);
                    break;
                case "ico_state ws37": //눈후갬(밤)
                    weather_picture.setImageResource(R.drawable.ws37);
                    break;
                case "ico_state ws38": //흐려져비(밤)
                    weather_picture.setImageResource(R.drawable.ws38);
                    break;
                case "ico_state ws39": //흐려져눈(밤)
                    weather_picture.setImageResource(R.drawable.ws39);
                    break;
                case "ico_state ws40": //안개(밤)
                    weather_picture.setImageResource(R.drawable.ws17_40);
                    break;
                case "ico_state ws41": //황사(밤)
                    weather_picture.setImageResource(R.drawable.ws20_41);
                    break;
            }
        }
        public void recommend_style(int temperature) {
            if(temperature < -5) {
                recommend_style_tv.setText("두꺼운 코트, 롱패딩, 모자, 귀마개");
            } else if(temperature < 9) {
                recommend_style_tv.setText("가벼운 코트, 라이더 자켓");
            } else if(temperature < 11) {
                recommend_style_tv.setText("트렌치 코트, 면자켓");
            } else if(temperature < 16) {
                recommend_style_tv.setText("자켓, 셔츠, 가디건, 치마");
            } else if(temperature < 19) {
                recommend_style_tv.setText("니트, 가디건, 후드티, 맨투맨, 청바지, 면바지");
            } else if(temperature < 22) {
               recommend_style_tv.setText("긴팔, 가디건, 면바지, 슬랙스");
            } else if(temperature < 26) {
                recommend_style_tv.setText("반팔, 얇은 셔츠, 반바지");
            } else {
                recommend_style_tv.setText("민소매, 반바지, 원피스");
            }
        }
    }
}
