package windowmanager.project.angel.com.windowmanagerexample;

import android.app.ActivityManager;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.List;

/**
 * @author suzhuning
 * @date 2017/2/8.
 * Description:
 */
public class WindowService extends Service {

    private WindowManager mWindowManager;
    private WindowManager.LayoutParams mWindowParams;
    private View mWindowView;
    private TextView mPercentTextView;
    private int mStartX;
    private int mStartY;
    private int mEndX;
    private int mEndY;

    @Override
    public void onCreate() {
        super.onCreate();
        initWindowParams();
        initView();
        addWindowView2Window();
        initClick();
    }

    private void initWindowParams() {
        mWindowManager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
        mWindowParams = new WindowManager.LayoutParams();
        mWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
        mWindowParams.format = PixelFormat.TRANSLUCENT;
        mWindowParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        mWindowParams.gravity = Gravity.LEFT|Gravity.TOP;
        mWindowParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        mWindowParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    private void initView() {
        mWindowView = LayoutInflater.from(getApplication()).inflate(R.layout.layout_window, null);
        mPercentTextView = (TextView) mWindowView.findViewById(R.id.percent_text);
    }

    private void addWindowView2Window(){
        mWindowManager.addView(mWindowView, mWindowParams);
    }

    private void initClick(){
        mPercentTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        mStartX = (int) motionEvent.getRawX();
                        mStartY = (int) motionEvent.getRawY();
                        break;

                    case MotionEvent.ACTION_MOVE:
                        mEndX = (int) motionEvent.getRawX();
                        mEndY = (int) motionEvent.getRawY();
                        Log.i("action_move---","x=" + mEndX + ",   y = " + mEndY);
                        if(needIntercept()){
                            //getRawX()是相对于屏幕的坐标， getX()是相对于按钮的坐标
                            mWindowParams.x = (int) motionEvent.getRawX() - mWindowView.getMeasuredWidth()/2;
                            mWindowParams.y = (int) motionEvent.getRawY() - mWindowView.getMeasuredHeight()/2;
                            mWindowManager.updateViewLayout(mWindowView, mWindowParams);
                            Log.i("updateWindowView---","x=" + mWindowParams.x + ",   y = " + mWindowParams.y);
//                            Log.i("position--", "width=" + mWindowView.getMeasuredWidth() + ",  height = " + mWindowView.getMeasuredHeight());
                            return true;
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        if(needIntercept()){
                            return true;
                        }
                        break;

                    default:
                        break;
                }
                return false;
            }
        });

        mPercentTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAppAtBackground(WindowService.this)){
                    Intent intent = new Intent(WindowService.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean isAppAtBackground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(1);
        if(!tasks.isEmpty()){
            ComponentName topActivity = tasks.get(0).topActivity;
            if(!topActivity.getPackageName().equals(context.getPackageName())){
                return true;
            }
        }
        return false;
    }

    /**
     * 是否拦截
     * @return  true拦截    false不拦截
     */
    private boolean needIntercept() {
        if(Math.abs(mStartX-mEndX) > 2 || Math.abs(mStartY - mEndY) > 2){
            return true;
        }
        return false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("WindowService---","onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("WindowService---","onDestroy");
        if(mWindowView != null){
            //移除悬浮窗口
            mWindowManager.removeView(mWindowView);
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
