package co.edu.unab.gti.thesistelediagclient.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import co.edu.unab.gti.thesistelediagclient.R;
import co.edu.unab.gti.thesistelediagclient.data.database.model.Session;
import co.edu.unab.gti.thesistelediagclient.data.database.query.SessionQuery;
import co.edu.unab.gti.thesistelediagclient.data.model.User;
import co.edu.unab.gti.thesistelediagclient.device.HttpClientService;
import co.edu.unab.gti.thesistelediagclient.device.NetworkService;
import co.edu.unab.gti.thesistelediagclient.service.ServiceHandler;
import co.edu.unab.gti.thesistelediagclient.util.Parameters;
import co.edu.unab.gti.thesistelediagclient.util.UIHelper;
import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.client.HttpClient;

public class LoginActivity extends AppCompatActivity {
    ImageView imageView_login_logo;
    EditText editText_login_email;
    EditText editText_login_password;
    Button button_login_signin;
    Button button_login_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        initUI();




    }

    private void initUI(){
        button_login_register = (Button) findViewById(R.id.button_login_register);
        imageView_login_logo = (ImageView) findViewById(R.id.imageView_login_logo);
        editText_login_email = (EditText) findViewById(R.id.editText_login_email);
        editText_login_password = (EditText) findViewById(R.id.editText_login_password);
        button_login_signin = (Button) findViewById(R.id.button_login_signin);

        UIHelper.loadImage(imageView_login_logo,R.drawable.logo);

        button_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegistryActivity.class));
            }
        });

        button_login_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {

        String email = editText_login_email.getText().toString();
        String password = editText_login_password.getText().toString();

        RequestParams params = new RequestParams();
        params.add("email",email);
        params.add("password",password);

        if (NetworkService.hasInternetAccess()) {
            UIHelper.showProgressDialog(this,"Iniciando Sesion");
            HttpClientService.post(Parameters.LOGIN_ENDPOINT, params, new TextHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    if (statusCode == 401) {
                        UIHelper.hideProgressDialog();
                        UIHelper.showInformationDialog(LoginActivity.this,
                                "Informacion", "Tus datos de acceso son incorrectos o tu cuenta no existe", null);
                    }
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {

                    UIHelper.hideProgressDialog();
                    Session u = HttpClientService.getGsonService().fromJson(responseString, Session.class);
                    Session existingSession = SessionQuery.getSessionByEmail(u.getEmail());
                    if (existingSession != null) {
                        existingSession.delete();
                    }
                    u.save();

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            });
        } else {

            Session storedSession = SessionQuery.getSessionByEmailAndPassword(email,password);
            if (storedSession != null){
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            } else {
                UIHelper.showInformationDialog(LoginActivity.this,
                        "Informacion", "Tus datos de acceso son incorrectos o tu cuenta no existe", null);
            }
        }


    }

}
