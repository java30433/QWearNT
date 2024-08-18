package com.tencent.qqnt.watch.selftab.item;

import android.view.View;

import androidx.fragment.app.Fragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SelfOperationItem implements View.OnClickListener {
    @NotNull
    public final Fragment b;

    public SelfOperationItem(@NotNull Fragment fragment) {
        this.b = fragment;
    }

    /**
     * Icon
     */
    public abstract int a();

    /**
     * Label
     */
    public abstract int b();

    @Override // android.view.View.OnClickListener
    public void onClick(@Nullable View view) {
    }
}
