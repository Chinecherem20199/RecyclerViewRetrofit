package nigeriandailies.com.ng.recyclerviewretrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    //initailize variable
    NestedScrollView mNestedScrollView;
    RecyclerView mRecyclerView;
    ProgressBar mProgressBar;
    ArrayList<MainData> mMainDataArrayList = new ArrayList<MainData>();
    MainAdapter mAdapter;
    int page = 1, limit = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNestedScrollView = findViewById(R.id.scroll_view);
        mRecyclerView = findViewById(R.id.recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);

        //initialize adapter
        mAdapter = new MainAdapter(mMainDataArrayList, MainActivity.this);

        //set layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //set adapter
        mRecyclerView.setAdapter(mAdapter);

        //Create get data method
        getData(page, limit);

        mNestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                // Check condition
                if ( scrollY== v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()){
                    //when reach last item, increase page size
                    page++;
                    //show progress bar
                    mProgressBar.setVisibility(View.VISIBLE);
                    //call method
                    getData(page, limit);
                }
            }
        });
    }

    private void getData(int page, int limit) {
        //initialize retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://picsum.photos/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

        //Create main interface
        MainInterface mainInterface = retrofit.create(MainInterface.class);
//        //initialize call
        Call<String> call = mainInterface.STRING_CALL(page, limit);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //Check condition
                if (response.isSuccessful() && response.body() != null){
                    //when response is not empty and is successful
                    //Hide progress bar
                    mProgressBar.setVisibility(View.GONE);
                    try {
                        //initialize JSONArray
                        JSONArray jsonArray = new JSONArray(response.body());
                        //parse jsonArray
                        parseJSonArry(jsonArray);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }

    private void parseJSonArry(JSONArray jsonArray) {
        //loop through
        for (int i =0; i< jsonArray.length(); i++){

            try {
                // initialize json object
                JSONObject object = jsonArray.getJSONObject(i);
                //// initialize main data
                MainData data = new MainData();
                //set image
                data.setImage(object.getString("download_url"));
                //set name
                data.setName(object.getString("author"));
                //add data in arraylist
                mMainDataArrayList.add(data);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mAdapter = new MainAdapter(mMainDataArrayList, MainActivity.this);
           mRecyclerView.setAdapter(mAdapter);
        }
    }
}