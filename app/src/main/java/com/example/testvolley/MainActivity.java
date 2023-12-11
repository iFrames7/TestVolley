package com.example.testvolley;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;
public class MainActivity extends AppCompatActivity {
    // on below line we are creating variables
    private EditText nameEdt, jobEdt;
    private Button postDataBtn;
    private ProgressBar loadingPB;
    private TextView responseTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // on below line initializing variable with id.
        nameEdt = findViewById(R.id.idEdtName);
        jobEdt = findViewById(R.id.idEdtJob);
        postDataBtn = findViewById(R.id.idBtnPostData);
        loadingPB = findViewById(R.id.idPBLoading);
        responseTV = findViewById(R.id.idTVResponse);
        // on below line adding click listner for our post data button.
        postDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line checking of the edit text is empty or not.
                if (nameEdt.getText().toString().isEmpty() && jobEdt.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "Pplease enter name and job..", Toast.LENGTH_SHORT).show();
                } else {
                    // on below line calling method to post the data.
                    postDataUsingVolley(nameEdt.getText().toString(), jobEdt.getText().toString());
                }
            }
        });
    }
    private void postDataUsingVolley(String name, String job) {
        // on below line specifying the url at which we have to make a post request
        String url = "https://reqres.in/api/users";
        // setting progress bar visibility on below line.
        loadingPB.setVisibility(View.VISIBLE);
        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        // making a string request on below line.
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // channing progress bar visibility on below line.
                loadingPB.setVisibility(View.GONE);
                // setting response to text view.
                responseTV.setText("Response from the API is :" + response);
                // displaying toast message.
                Toast.makeText(MainActivity.this, "Data posted succesfully..", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling error on below line.
                loadingPB.setVisibility(View.GONE);
                responseTV.setText(error.getMessage());
                Toast.makeText(MainActivity.this, "Fail to get response..", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("name", name);
                params.put("job", job);
                return params;
            }
        };
        // adding request to queue to post the data.
        queue.add(request);
    }
}