package com.example.mausam;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    TextView temp;
    TextView climate;
    EditText cityname;
    Button button;
    TextView tempget;
    TextView date;
    TextView climateget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        temp=(TextView)findViewById(R.id.temp);
        button=(Button)findViewById(R.id.button);
        climate=(TextView)findViewById(R.id.climate);
        cityname=(EditText)findViewById(R.id.edit);
        date=(TextView)findViewById(R.id.date);
        climateget=(TextView)findViewById(R.id.climateget);
        tempget=(TextView)findViewById(R.id.tempget);
    }

    public void getdata(View view) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.openweathermap.org/data/2.5/").addConverterFactory(GsonConverterFactory.create()
        ).build();
        Api api = retrofit.create(Api.class);




            Call<PostList> postlist = api.getpostlist(cityname.getText().toString().trim(), "fa713db6413dccb8310b0941cd9ef2a8");
            postlist.enqueue(new Callback<PostList>() {
                @Override
                public void onResponse(Call<PostList> call, Response<PostList> response) {
                    PostList list = response.body();
                    Toast.makeText(MainActivity.this, "welcome", Toast.LENGTH_SHORT).show();
                    Main main = list.getMain();
                    Double temp = main.getTemp();
                    int temperaure = (int) (temp - 273);


                    //Date And TIme Concept
                    Integer dt = list.getDt();
                    Calendar calendar = Calendar.getInstance();
                    TimeZone tz = TimeZone.getDefault();
                    calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    Date currenTimeZone = new Date((long) dt * 1000);
                    //Toast.makeText(MainActivity.this, sdf.format(currenTimeZone), Toast.LENGTH_SHORT).show();
                    String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
                    String date1 = calendar.getDisplayName(Calendar.DATE, calendar.LONG, Locale.getDefault());
                    Date currentTime = Calendar.getInstance().getTime();
                    //Toast.makeText(MainActivity.this, dayLongName, Toast.LENGTH_SHORT).show();

                    List<Weather> weathers = list.getWeather();
                    String des = weathers.get(0).getDescription();


                    tempget.setText(String.valueOf(temperaure) + "°C");
                    climateget.setText(des);
                    date.setText(dayLongName);

                }


                @Override
                public void onFailure(Call<PostList> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
