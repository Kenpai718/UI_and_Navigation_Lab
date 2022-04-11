package edu.uw.tcss450.uiandnavigationlab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.auth0.android.jwt.JWT;

import android.os.Bundle;

import edu.uw.tcss450.uiandnavigationlab.model.UserInfoViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        MainActivityArgs args = MainActivityArgs.fromBundle(getIntent().getExtras());

        //Import com.auth0.android.jwt.JWT
        JWT jwt = new JWT(args.getJwt());

        // Check to see if the web token is still valid or not. To make a JWT expire after a
        // longer or shorter time period, change the expiration time when the JWT is
        // created on the web service.
        if(!jwt.isExpired(0)) {
/*            //take note that we are not using the constructor explicitly, the no-arg
            //constructor is called implicitly
            UserInfoViewModel model = new ViewModelProvider(this).get(UserInfoViewModel.class);
            //Take note of the need to use the setter, since we have to use a no-arg constructor
            model.setJWT(jwt);*/

            new ViewModelProvider(
                    this,
                    new UserInfoViewModel.UserInfoViewModelFactory(jwt))
                    .get(UserInfoViewModel.class);

        } else {
            //In production code, add in your own error handling/flow for when the JWT is expired
            throw new IllegalStateException("JWT is expired!");
        }

        //keep at end of oncreate
        setContentView(R.layout.activity_main);
    }

}
