package com.udacity.gradle.builditbigger.backend;

import com.dongmyungahn.android.javajokeslib.Jokes;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;

import javax.inject.Named;

/** An endpoint class we are exposing */
@Api(
        name = "myApi",
        version = "v1",
        namespace = @ApiNamespace(
                ownerDomain = "backend.builditbigger.gradle.udacity.com",
                ownerName = "backend.builditbigger.gradle.udacity.com",
                packagePath = ""
        )
)
public class MyEndpoint {

    /** A simple endpoint method */
    @ApiMethod(name = "tellJoke")
    public MyBean getJoke() {
        MyBean response = new MyBean();
        Jokes jokes = new Jokes();
        response.setData(jokes.getJoke());

        return response;
    }


}
