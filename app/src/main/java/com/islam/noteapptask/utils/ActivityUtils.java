package com.islam.noteapptask.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;

import com.islam.noteapptask.R;
import com.islam.noteapptask.pojo.Task;

public final class ActivityUtils {

    public static void replaceFragment(FragmentManager fragmentManager, Fragment fragment, int layoutId, boolean addToBackStack) {
        FragmentTransaction transaction = fragmentManager.beginTransaction().replace(layoutId, fragment, null);
        if (addToBackStack)
            transaction.addToBackStack("");
        transaction.commit();
    }

    public static String calcTime(long time) {
        return (String) DateUtils.getRelativeTimeSpanString(time, System.currentTimeMillis(), 0);
    }

    public static int getTaskPriority(Task task){
        switch (task.getPriority()){
            case 1:
                return R.id.button1;
            case 2:
                return R.id.button2;
            case 3:
                return R.id.button3;
        }
        return 0;
    }
}
