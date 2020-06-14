package com.mservice.demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.mservice.demo.adapter.UserAdapter;
import com.mservice.demo.model.Result;
import com.mservice.demo.model.UserResponse;
import com.mservice.demo.rest.ApiClient;
import com.mservice.demo.rest.ApiInterface;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    UserAdapter userAdapter;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        apiInterface = ApiClient.getInstance().create(ApiInterface.class);

        Call<UserResponse> call = apiInterface.getUserData("1", "1", "20");
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();
                Log.i("HCMUS_OK", response.body().getInfo().getVersion());
                Toast.makeText(MainActivity.this, "Vừa có thay đổi trên database", Toast.LENGTH_SHORT).show();
//                Toast.makeText(MainActivity.this, "Get data success with version: " +response.body().getInfo().getVersion(), Toast.LENGTH_SHORT).show();
                handleUserResponseData(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.i("HCMUS_FAIL", t.toString());
                Toast.makeText(MainActivity.this, "Something wrong :: " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    void handleUserResponseData(UserResponse userResponse) {
        RecyclerView userListView = findViewById(R.id.userList);
        List<Result> userList = userResponse.getResults();
        userAdapter = new UserAdapter(this, userList, userListView);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        userListView.setLayoutManager(layoutManager);
        userListView.setAdapter(userAdapter);
    }
}