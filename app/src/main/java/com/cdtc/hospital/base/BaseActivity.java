package com.cdtc.hospital.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cdtc.hospital.MainActivity;
import com.cdtc.hospital.R;
import com.cdtc.hospital.util.LogUtil;
import com.cdtc.hospital.util.ToastUtil;

/**
 * Created by Sweven on 2018/10/12.
 * Email:sweventears@Foxmail.com
 */
@SuppressLint("Registered")
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 标记标题左右两边的类型:文字
     */
    protected final int BTN_TYPE_TEXT = 0;
    /**
     * 标记标题左右两边的类型:图片
     */
    protected final int BTN_TYPE_IMG = 1;


    public Activity activity;

    /**
     * 左边按键的父组件
     */
    private RelativeLayout layoutLeft;
    /**
     * 右边按键的父组件
     */
    private RelativeLayout layoutRight;

    protected final String TAG = this.getClass().getSimpleName();

    /**
     * Dialog提示框
     */
    private Dialog mDialog;
    /**
     * Dialog TextView
     */
    private TextView dialogLoadText;
    /**
     * Dialog imageView
     */
    private ImageView dialogLoadImage;
    /**
     * Dialog进度条
     */
    private ProgressBar dialogLoadProgress;
    /**
     * Alert提示框
     */
    protected AlertDialog.Builder mAlert;
    protected LogUtil log;
    protected ToastUtil toast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        init();
    }

    protected void startActivity(Class<?> cls){
        Intent intent=new Intent(activity,cls);
        startActivity(intent);
    }

    /**
     * 绑定id
     */
    protected void bindViewId() {
    }

    /**
     * 初始化数据
     */
    protected void initData() {
    }

    /**
     * BaseActivity初始配置
     */
    private void init() {

        log=new LogUtil(TAG);
        toast=new ToastUtil(activity);

        // 设置字体大小不随系统字体大小的改变而改变
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());

        // 设置自定义 actionbar
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_activity_base);

        layoutLeft = findViewById(R.id.layout_title_left);
        layoutRight = findViewById(R.id.layout_title_right);
        leftDo();
        rightDo();

        // 默认左右两边的按键都不显示
        hiddenLeftButton();
        hiddenRightButton();

        // 初始化左右按键
        setCustomerActionBar(KeyActionBarButtonKind.ACTIONBAR_LEFT, BTN_TYPE_IMG, R.drawable.ic_keyboard_arrow_left_black_24dp);
        setCustomerActionBar(KeyActionBarButtonKind.ACTIONBAR_RIGHT, BTN_TYPE_TEXT, "完成");

        initDialog();
    }

    /**
     * [设置标题]
     *
     * @param title 标题
     */
    protected void setActionBarTitle(Object title) {
        TextView textTitle = findViewById(R.id.text_title);
        if (title instanceof String && !TextUtils.isEmpty((String) title)) {
            textTitle.setText((String) title);
        } else if (title instanceof Integer) {
            textTitle.setText((Integer) title);
        }

    }

    /**
     * @param btnType 类型
     *                (Image、Text)
     * @param object  内容
     */
    protected void setCustomerActionBar(KeyActionBarButtonKind kind, int btnType, Object object) {
        TextView textView = new TextView(activity);
        ImageView imageView = new ImageView(activity);
        if (kind == KeyActionBarButtonKind.ACTIONBAR_LEFT) {
            textView = findViewById(R.id.text_title_left);
            imageView = findViewById(R.id.image_title_left);
        } else if (kind == KeyActionBarButtonKind.ACTIONBAR_RIGHT) {
            textView = findViewById(R.id.text_title_right);
            imageView = findViewById(R.id.image_title_right);
        }
        if (btnType == BTN_TYPE_IMG) {
            textView.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.VISIBLE);

            if (object instanceof Bitmap) {
                imageView.setImageBitmap((Bitmap) object);
            } else if (object instanceof Integer) {
                imageView.setImageResource((Integer) object);
            }
        } else {
            textView.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.INVISIBLE);

            if (object instanceof String) {
                textView.setText((String) object);
            } else if (object instanceof Integer) {
                textView.setText((Integer) object);
            }
        }
    }

    /**
     * 显示actionbar左边按键
     */
    protected void showLeftButton() {
        layoutLeft.setVisibility(View.VISIBLE);
    }

    /**
     * 显示actionbar右边按键
     */
    protected void showRightButton() {
        layoutRight.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏左边的按键
     */
    protected void hiddenLeftButton() {
        layoutLeft.setVisibility(View.INVISIBLE);
    }

    /**
     * 隐藏右边的按键
     */
    protected void hiddenRightButton() {
        layoutRight.setVisibility(View.INVISIBLE);
    }

    /**
     * 点击左边按键的监听器
     */
    private void leftDo() {
        layoutLeft.setOnClickListener(view -> leftDoWhat());
    }

    /**
     * 左边按键的监听事件
     */
    protected void leftDoWhat() {
        BaseActivity.this.finish();
    }

    /**
     * 点击右边按键的监听器
     */
    private void rightDo() {
        layoutRight.setOnClickListener(view -> rightDoWhat());
    }

    /**
     * 右边按键的监听事件
     */
    protected void rightDoWhat() {

    }

    /**
     * 初始化Dialog
     */
    private void initDialog() {
        mDialog = new Dialog(this, R.style.Theme_AppCompat_Dialog);
        View mDialogContentView = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null);
        dialogLoadText = mDialogContentView.findViewById(R.id.load_text);
        dialogLoadImage = mDialogContentView.findViewById(R.id.load_image);
        dialogLoadProgress = mDialogContentView.findViewById(R.id.load_progress);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setContentView(mDialogContentView);
        Window window = mDialog.getWindow();
        if (null != window) {
            window.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        }
    }

    /**
     * 显示Dialog
     */
    public void showProgressDialog() {
        if (mDialog != null && !mDialog.isShowing()) {
            dialogLoadProgress.setVisibility(View.VISIBLE);
            dialogLoadText.setVisibility(View.VISIBLE);
            dialogLoadImage.setVisibility(View.GONE);
            dialogLoadText.setText("加载中……");
            mDialog.show();
        }
    }

    /**
     * 关闭Dialog
     */
    public void dismissDialog() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    /**
     * [初始设置Alert]
     *
     * @param iconId   图标
     * @param title    标题
     * @param message  显示信息
     * @param isCancel 是否设置取消
     */
    protected void initAlert(int iconId, String title, String message, boolean isCancel) {
        mAlert = new AlertDialog.Builder(activity);
        mAlert.setIcon(iconId);
        mAlert.setTitle(title);
        mAlert.setMessage(message);

        mAlert.setCancelable(isCancel);
        mAlert.create();
    }

    /**
     * [初始设置Alert]
     *
     * @param title    标题
     * @param message  显示信息
     * @param isCancel 是否设置取消
     */
    protected void initAlert(String title, String message, boolean isCancel) {
        mAlert = new AlertDialog.Builder(activity);
        mAlert.setTitle(title);
        mAlert.setMessage(message);

        mAlert.setCancelable(isCancel);
        mAlert.create();
    }

    /**
     * 显示Alert
     */
    protected void showAlert() {
        if (mAlert != null) {
            mAlert.show();
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            onBack();
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void onBack() {

    }

    //---------------------------------------枚举----------------------------------------//
    /**
     * 枚举：左右按钮
     */
    protected enum KeyActionBarButtonKind {
        /**
         * 标记左边的按键
         */
        ACTIONBAR_LEFT,
        /**
         * 标记右边的按键
         */
        ACTIONBAR_RIGHT
    }


}
