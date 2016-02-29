package cmput301w16t15.shareo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private String fullName;
    private String userNameSignup;
    private String email;
    private String motto;
    private String userNameLogin;

    private Button buttonSignup;
    private Button buttonLogin;

    private EditText editTextFullName;
    private EditText editTextUserNameSignup;
    private EditText editTextEmail;
    private EditText editTextMotto;
    private EditText editTextUserNameLogin;

    private static final String TAG = "TAGLoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonSignup = (Button) findViewById(R.id.buttonSignup);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);

        editTextFullName = (EditText) findViewById(R.id.editTextFullName);
        editTextUserNameSignup = (EditText) findViewById(R.id.editTextUserNameSignup);
        editTextUserNameLogin = (EditText) findViewById(R.id.editTextUserNameLogin);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextMotto = (EditText) findViewById(R.id.editTextMotto);
        editTextUserNameLogin = (EditText) findViewById(R.id.editTextUserNameLogin);

        buttonSignup.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        Log.d(TAG + "Cycle", "Called the onResume method for " + TAG);
        super.onResume();
        // The activity has become visible (it is now "resumed").
    }

    @Override
    protected void onPause() {
        Log.d(TAG + "Cycle", "Called the onPause method for " + TAG);
        super.onPause();
        // Another activity is taking focus (this activity is about to be "paused").
    }

    @Override
    protected void onStop() {
        Log.d(TAG + "Cycle", "Called the onStop method for " + TAG);
        super.onStop();
        // The activity is no longer visible (it is now "stopped")
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG + "Cycle", "Called the onDestroy method for " + TAG);
        super.onDestroy();
        // The activity is about to be destroyed.
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the user's current game state
        Log.d(TAG + "Cycle", "Entering onSaveInstanceInstance");


        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);

    }

    public void onClick(View v) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        switch (v.getId()) {
            case R.id.buttonSignup:
                Log.d(TAG, "Clicked Button Signup");
                parseSignUp();
                this.startActivity(mainIntent);
                break;

            case R.id.buttonLogin:
                Log.d(TAG, "Clicked Button Login");
                parseLogin();
                this.startActivity(mainIntent);
                break;

        }
    }

    /**
     * TODO : Make the sign up and login work over a network
     * Currently, clicking login or sign up just transfers over to the main activity intent
     * and we don't really handle the signup or login.
     */
    private void parseSignUp() {
        fullName = editTextFullName.getText().toString();
        userNameSignup = editTextUserNameSignup.getText().toString();
        email = editTextEmail.getText().toString();
        motto = editTextMotto.getText().toString();

    }

    private void parseLogin() {
        userNameLogin = editTextUserNameLogin.getText().toString();
    }

}