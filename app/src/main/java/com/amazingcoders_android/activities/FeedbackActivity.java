package com.amazingcoders_android.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazingcoders_android.R;
import com.amazingcoders_android.activities.base.BaseActivity;
import com.amazingcoders_android.api.requests.EmptyListener;
import com.amazingcoders_android.async_tasks.SendFeedbackTask;
import com.amazingcoders_android.views.FeedbackCard;
import com.android.volley.VolleyError;

import butterknife.ButterKnife;
import butterknife.InjectView;
import fr.ganfra.materialspinner.MaterialSpinner;

/**
 * Created by junwen29 on 10/28/2015.
 */
public class FeedbackActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.card_feedback)
    FeedbackCard mCard;
    @InjectView(R.id.feedback_progress)
    View mProgressView;
    @InjectView(R.id.btn_submit)
    Button mSubmitBtn;
    @InjectView(R.id.container)
    View mContainer;

    EditText mTitle, mDetails;
    MaterialSpinner mSpinner;

    private SendFeedbackTask mTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_feedback);
        ButterKnife.inject(this);

        setSupportActionBar(mToolbar);
        init();
    }

    private void init(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mSpinner = mCard.getSpinner();
        mTitle = mCard.getTitle();
        mDetails = mCard.getDetails();

        mDetails.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == R.id.desc || actionId == EditorInfo.IME_ACTION_SEND) {
                    attemptSendFeedback();

                    return true;
                }
                return false;
            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSendFeedback();
            }
        });
    }

    private void attemptSendFeedback() {
        if (mTask != null)
            return;

        //Reset errors
        mTitle.setError(null);
        mSpinner.setError(null);
        mDetails.setError(null);

        String title = mTitle.getText().toString();
        String category = mCard.getCategory();
        String desc = mDetails.getText().toString();
        View focusView = null;
        boolean cancel = false;

        // validate form inputs
        if (TextUtils.isEmpty(title)){
            mTitle.setError(getString(R.string.error_field_required));
            focusView = mTitle;
            cancel = true;
        }

        if (TextUtils.isEmpty(category)){
            mSpinner.setError(getString(R.string.error_field_select_category));
            focusView = mSpinner;
            cancel = true;
        }

        if (TextUtils.isEmpty(desc)){
            mDetails.setError(getString(R.string.error_field_required));
            focusView = mDetails;
            cancel = true;
        }

        if (cancel){
            focusView.requestFocus();
        } else{
            EmptyListener listener = new EmptyListener() {
                @Override
                public void onResponse() {
                    showProgress(false);
                    mTask = null;
                    mTitle.setText("");
                    mDetails.setText("");
                    Snackbar.make(mContainer,getString(R.string.feedback_success),Snackbar.LENGTH_LONG).show();
                }

                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    showProgress(false);
                    mTask = null;
                    Snackbar.make(mContainer,getString(R.string.error_default),Snackbar.LENGTH_LONG).show();
                }
            };
            showProgress(true);
            mTask = new SendFeedbackTask(this, title, category, desc, listener);
            mTask.execute((Void) null);
        }
    }

    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mCard.setVisibility(show ? View.GONE : View.VISIBLE);
            mCard.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mCard.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mCard.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
