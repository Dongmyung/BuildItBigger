package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import android.support.test.espresso.IdlingResource;

import com.dongmyungahn.android.javajokeslib.Jokes;
import com.dongmyungahn.android.jokedisplay.JokeDisplayActivity;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.idlingResouces.SimpleIdlingResource;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if(mIdlingResource == null) mIdlingResource = new SimpleIdlingResource();

        return mIdlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getIdlingResource();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
//        Jokes jokes = new Jokes();
//        Intent intent =new Intent(this, JokeDisplayActivity.class);
//        intent.putExtra(JokeDisplayActivity.EXTRA_JOKE, jokes.getJoke());
//        startActivity(intent);
        new JokeAsyncTask().execute(new Pair<Context, SimpleIdlingResource>(this,mIdlingResource));
    }




    class JokeAsyncTask extends AsyncTask<Pair<Context, SimpleIdlingResource>, Void, String> {
        private MyApi jokeService = null;
        private Context context;
        private SimpleIdlingResource idlingResource;

        @Override
        protected String doInBackground(Pair<Context, SimpleIdlingResource>... params) {
            if(jokeService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                jokeService = builder.build();
            }

            context = params[0].first;
            idlingResource = params[0].second;

            if (idlingResource != null) {
                idlingResource.setIdleState(false);
            }


            try {
                return jokeService.tellJoke().execute().getData();
            } catch (IOException e) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if(result == null) {
                Toast.makeText(context, "Could'nt fetch a joke.", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(context, JokeDisplayActivity.class);
                intent.putExtra(JokeDisplayActivity.EXTRA_JOKE, result);
                context.startActivity(intent);
            }

            if (idlingResource != null) {
                idlingResource.setIdleState(true);
            }
        }
    }
}
