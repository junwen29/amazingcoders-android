package com.amazingcoders_android.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.BaseActivity;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.api.VolleyErrorHelper;
import com.amazingcoders_android.api.requests.LoginRequest;
import com.amazingcoders_android.dialogs.ProgressDialogFactory;
import com.amazingcoders_android.helpers.Global;
import com.amazingcoders_android.helpers.PreferencesStore;
import com.amazingcoders_android.models.Owner;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by junwen29 on 9/20/2015.
 */
public class LoginActivity extends BaseActivity {
    @InjectView(R.id.input_email)
    EditText mEmailInput;
    @InjectView(R.id.input_password)
    EditText mPasswordInput;
    @InjectView(R.id.toolbar)
    Toolbar mAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        setSupportActionBar(mAppBar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(R.string.login);

        setProgressDialog(ProgressDialogFactory.create(this, R.string.progress_auth));
        // Trick to set password hint typeface from monospace to normal.
        mPasswordInput.setTypeface(Typeface.DEFAULT);
        mPasswordInput.setTransformationMethod(new PasswordTransformationMethod());
        mPasswordInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    login();
                    return true;
                }
                return false;
            }
        });

        // Bring up soft keyboard.
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @OnClick(R.id.text_forgot_password)
    public void forgotPassword() {
//        startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(Endpoint.FORGOT_PASSWORD)));
    }

    @OnClick(R.id.button_login)
    public void login() {
        String email = mEmailInput.getText().toString().trim();
        String password = mPasswordInput.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            showErrorMessage(R.string.error_empty_login);
            return;
        }

        Listener<Owner> listener = new Listener<Owner>() {
            @Override
            public void onResponse(Owner owner) {
                dismissProgressDialog();
                Global.with(LoginActivity.this).updateOwner(owner);
                PreferencesStore ps = new PreferencesStore(getBaseContext());
                if (ps.isNewbie()){
                    ps.setNotNewbie();
                }
                startActivity(new Intent(LoginActivity.this, DealsFeedActivity.class));
                finish();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                dismissProgressDialog();
                error.printStackTrace();

                if (error instanceof NoConnectionError) {
                    showErrorMessage(R.string.error_network);
                    return;
                }

                int statusCode = VolleyErrorHelper.getHttpStatusCode(error);
                if (statusCode == 0) {
                    showErrorMessage();
                    return;
                }

                switch (statusCode) {
                    case VolleyErrorHelper.UNAUTHORIZED:
                        showErrorMessage(R.string.error_login);
                        break;
                    default:
                        showErrorMessage(R.string.error_maintenance);
                        break;
                }
            }
        };

        showProgressDialog();
        getBurppleApi().enqueue(LoginRequest.email(email, password, listener), this);
    }
}
