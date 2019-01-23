package com.cdtc.hospital.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.cdtc.hospital.R;
import com.cdtc.hospital.base.App;
import com.cdtc.hospital.base.BaseActivity;
import com.cdtc.hospital.entity.User;
import com.cdtc.hospital.local.dao.BaseLocalDao;
import com.cdtc.hospital.local.dao.UserLocalDao;
import com.cdtc.hospital.local.dao.impl.UserLocalDaoImpl;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity {
    private UserLoginTask mAuthTask = null;

    private AutoCompleteTextView mLoginUserView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setActionBarTitle("登录");

        mLoginUserView = findViewById(R.id.login_name);

        mPasswordView = findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin();
                return true;
            }
            return false;
        });

        Button mUserSignInButton = findViewById(R.id.login_name_sign_in_button);
        mUserSignInButton.setOnClickListener(view -> attemptLogin());

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }


    /**
     * 验证是否为空,然后验证用户名与密码是否匹配
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mLoginUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String loginName = mLoginUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        if (TextUtils.isEmpty(loginName)) {
            mLoginUserView.setError(getString(R.string.error_field_required));
            focusView = mLoginUserView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            mAuthTask = new UserLoginTask(loginName, password);
            mAuthTask.execute();
        }
    }

    /**
     * 进度条
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
    }


    /**
     * 登录 的异步请求
     */
    @SuppressLint("StaticFieldLeak")
    public class UserLoginTask extends AsyncTask<Integer, Void, Integer> {

        private final String mLoginName;
        private final String mPassword;
        private User user;

        UserLoginTask(String email, String password) {
            mLoginName = email;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Integer... params) {
            try {
                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.getStackTrace();
            }
            UserLocalDao userLocalDao = new UserLocalDaoImpl(activity, BaseLocalDao.QUERY);
            user = userLocalDao.queryByLoginName(mLoginName);

            if (user != null) {
                if (user.getU_passWord().equals(mPassword)) {
                    log.i(user.toString());
                    return 1;
                }
                return 0;
            }
            return -1;
        }

        @Override
        protected void onPostExecute(final Integer result) {
            mAuthTask = null;
            showProgress(false);

            if (result == 1) {
                UserLocalDao userLocalDao = new UserLocalDaoImpl(activity, BaseLocalDao.UPDATE);
                userLocalDao.updateLogSate(App.LOG_IN, mLoginName);
                App.trueName = userLocalDao.queryByLoginName(mLoginName).getU_trueName();

                finish();
                startActivity(ListActivity.class);
            } else if (result == 0) {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.setText("");
                mPasswordView.requestFocus();
            } else if (result == -1) {
                mLoginUserView.setError("用户名不存在");
                mLoginUserView.setText("");
                mLoginUserView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

