package com.cdtc.hospital.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import android.content.Intent;
import android.database.Cursor;
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
import com.cdtc.hospital.local.SQLite;
import com.cdtc.hospital.network.dao.UserDao;
import com.cdtc.hospital.network.dao.impl.UserDaoImpl;
import com.cdtc.hospital.network.entity.User;

import java.util.HashMap;
import java.util.Map;

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
     * 验证是否为空
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
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return -1;
            }
            log.i("连接服务器，查询User");
            UserDao dao = new UserDaoImpl();
            user = dao.selectByLoginName(mLoginName);

            if (user != null) {
                if (user.getU_passWord().equals(mPassword)) {
                    log.i(user.toString());
                    return 1;
                }
                return 0;
            }
            return -2;
        }

        @Override
        protected void onPostExecute(final Integer result) {
            mAuthTask = null;
            showProgress(false);

            if (result == 1) {
                finish();
                startActivity(HosRegisterActivity.class);
                App.loginState=App.LOG_IN;
                SQLite lite=new SQLite(activity,App.DATA_BASE,App.TABLE_USER,SQLite.UPDATE_DATABASE);
                String[] columns=new String[]{"u_trueName"};
                Cursor cursor =lite.query(columns,"u_loginName=?",new String[]{mLoginName});
                if (cursor.moveToNext()){
                    Map<String,Object> map=new HashMap<>();
                    map.put("u_state",App.LOG_IN);
                    lite.update(map,"u_loginName",new String[]{mLoginName});
                }else{
                    Map<String,Object> map=new HashMap<>();
                    map.put("u_loginName",mLoginName);
                    map.put("u_passWord",mPassword);
                    map.put("u_trueName",user.getU_trueName());
                    map.put("u_email",user.getU_email());
                    map.put("u_state",App.LOG_IN);
                    lite.insert(map);
                }
                App.trueName=user.getU_trueName();
            } else if (result == 0) {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.setText("");
                mPasswordView.requestFocus();
            } else if (result == -1) {
                toast.showError("网络开了点小差~");
            } else if (result == -2) {
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

