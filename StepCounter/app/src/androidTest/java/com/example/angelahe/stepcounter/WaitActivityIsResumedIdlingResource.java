package com.example.angelahe.stepcounter;

import android.app.Activity;
import android.support.test.espresso.IdlingResource;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitor;
import android.support.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;
import android.support.test.runner.lifecycle.Stage;

import java.util.Collection;

public class WaitActivityIsResumedIdlingResource implements IdlingResource {
    private final ActivityLifecycleMonitor instance;
    private final String activityToWaitClassName;
    private volatile ResourceCallback resourceCallback;
    boolean resumed = false;
    public WaitActivityIsResumedIdlingResource(String activityToWaitClassName) {
        instance = ActivityLifecycleMonitorRegistry.getInstance();
        this.activityToWaitClassName = activityToWaitClassName;
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        resumed = isActivityLaunched();
        if(resumed && resourceCallback != null) {
            resourceCallback.onTransitionToIdle();
        }

        return resumed;
    }

    private boolean isActivityLaunched() {
        Collection<Activity> activitiesInStage = instance.getActivitiesInStage(Stage.RESUMED);
        for (Activity activity : activitiesInStage) {
            if(activity.getClass().getName().equals(activityToWaitClassName)){
                return true;
            }
        }
        return false;
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback resourceCallback) {
        this.resourceCallback = resourceCallback;
    }
}