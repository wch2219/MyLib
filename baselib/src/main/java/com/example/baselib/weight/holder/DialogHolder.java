package com.example.baselib.weight.holder;

import android.view.View;

public class DialogHolder extends AbstractViewHolder<DialogHolder> {
    public DialogHolder(View view) {
        super(view);
    }

    @Override
    public DialogHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        return null;
    }

    @Override
    public DialogHolder setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        return null;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}
