package com.example.baselib.weight.weightimpl;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import com.example.baselib.weight.AbstractBaseDialog;

public abstract class AbstractBaseDialogHintImpl<T> implements AbstractBaseDialog<T> {


    abstract T setTitle(@StringRes int title);

    abstract T setTitle(@NonNull String title);
}
