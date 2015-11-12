package com.amazingcoders_android.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.style.StyleSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.NoConnectionError;
import com.android.volley.VolleyError;
import com.google.gson.JsonParser;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.BaseActivity;
import com.amazingcoders_android.api.Listener;
import com.amazingcoders_android.api.VolleyErrorHelper;
import com.amazingcoders_android.api.requests.SignupRequest;
import com.amazingcoders_android.dialogs.ProgressDialogFactory;
import com.amazingcoders_android.helpers.Global;
import com.amazingcoders_android.models.Owner;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by junwen29 on 9/20/2015.
 */
public class SignupActivity extends BaseActivity{

    public static final String SIGNUP_EMAIL = "amazingcoders.app.SIGNUP_EMAIL";
    public static final String SIGNUP_FACEBOOK = "amazingcoders.app.SIGNUP_FACEBOOK";
    public static final String SIGNUP_GOOGLE = "amazingcoders.app.SIGNUP_GOOGLE";

    @InjectView(R.id.input_first_name)
    EditText mFirstNameField;
    @InjectView(R.id.input_last_name)
    EditText mLastNameField;
    @InjectView(R.id.input_username)
    EditText mUsernameField;
    @InjectView(R.id.input_email)
    EditText mEmailField;
    @InjectView(R.id.input_password)
    EditText mPasswordField;
    @InjectView(R.id.toolbar)
    Toolbar mAppBar;
    @InjectView(R.id.terms_of_service)
    TextView mTerms;

    private String mAction;
    private Owner mOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.inject(this);
        setSupportActionBar(mAppBar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setProgressDialog(ProgressDialogFactory.create(this, R.string.registering_account));

        mAction = getIntent().getAction();
        switch (mAction) {
            case SIGNUP_EMAIL:
                mOwner = new Owner();
                break;
            default:
                showErrorMessage();
                finish();
                return;
        }

        // Trick to set password hint typeface from monospace to normal
        mPasswordField.setTypeface(Typeface.DEFAULT);
        mPasswordField.setTransformationMethod(new PasswordTransformationMethod());
        mPasswordField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    signup();
                    return true;
                }
                return false;
            }
        });

        initTerms();
    }

    @OnClick(R.id.button_signup)
    public void signup() {
        if (!validateAndStoreFields()) {
            return;
        }

        Listener<Owner> listener = new Listener<Owner>() {
            @Override
            public void onResponse(Owner owner) {
                dismissProgressDialog();
                Global.with(SignupActivity.this).reset(); //reset application context to new owner setting
                Global.with(SignupActivity.this).updateOwner(owner);
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: proper error message
                dismissProgressDialog();
                error.printStackTrace();

                if (error instanceof NoConnectionError) {
                    showErrorMessage(R.string.error_network);
                    return;
                }

                String response = VolleyErrorHelper.getResponse(error);
                int statusCode = VolleyErrorHelper.getHttpStatusCode(error);
                if (statusCode == 0) {
                    showErrorMessage();
                    return;
                }

                switch (statusCode) {
                    case VolleyErrorHelper.BAD_REQUEST:
                        try {
                            String message = new JsonParser().parse(response)
                                    .getAsJsonObject()
                                    .get("error")
                                    .getAsJsonObject()
                                    .get("message")
                                    .getAsString();

                            if (TextUtils.isEmpty(message)) {
                                showErrorMessage(R.string.error_duplicate_mail);
                            } else {
                                showErrorMessage(message.replace(':', ' '));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            showErrorMessage(R.string.error_signup);
                        }

                        break;
                    default:
                        showErrorMessage(R.string.error_maintenance);
                        break;
                }
            }
        };

        showProgressDialog();
        switch (mAction) {
            case SIGNUP_EMAIL:
                getBurppleApi().enqueue(SignupRequest.email(mOwner, listener), this);
                break;
        }
    }

    private boolean validateAndStoreFields() {
        String firstName = mFirstNameField.getText().toString().trim();
        String lastName = mLastNameField.getText().toString().trim();
        String username = mUsernameField.getText().toString().trim();
        String email = mEmailField.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            showErrorMessage(R.string.error_signup_empty_first_name);
            return false;
        } else if (TextUtils.isEmpty(lastName)) {
            showErrorMessage(R.string.error_signup_empty_last_name);
            return false;
        } else if (TextUtils.isEmpty(username)) {
            showErrorMessage(R.string.error_signup_empty_username);
            return false;
        } else if (TextUtils.isEmpty(email)) {
            showErrorMessage(R.string.error_signup_empty_email);
            return false;
        }

        String password;
        if (mAction.equals(SIGNUP_EMAIL)) {
            password = mPasswordField.getText().toString().trim();
            if (password.length() < 8) {
                showErrorMessage(R.string.error_signup_invalid_password);
                return false;
            }
            mOwner.setPassword(password);
        }

        mOwner.setFirstName(firstName);
        mOwner.setLastName(lastName);
        mOwner.setUsername(username);
        mOwner.setEmail(email);

        return true;
    }

    private void initTerms(){
        int start, end;

        SpannableStringBuilder builder = new SpannableStringBuilder();
        String txt1 = "By tapping to continue, you are indicating that you have read the ";
        builder.append(txt1);

        String txt2 = "Privacy Policy";
        start = builder.length();
        builder.append(txt2);
        end = builder.length();
        builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        String txt3 = " and agree to the ";
        builder.append(txt3);

        String txt4 = "Terms of Service";
        start = builder.length();
        builder.append(txt4);
        end = builder.length();
        builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        builder.append(".");
        mTerms.setText(builder);

        mTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.burpple.com/terms"));
                startActivity(i);
            }
        });
    }
}
