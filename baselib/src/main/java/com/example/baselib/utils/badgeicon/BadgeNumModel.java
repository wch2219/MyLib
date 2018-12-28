package com.example.baselib.utils.badgeicon;

import android.app.Application;
import android.app.Notification;
import android.support.annotation.NonNull;

public interface BadgeNumModel {
    Notification setIconBadgeNum(@NonNull Application context, Notification notification, int count) throws Exception;
}
