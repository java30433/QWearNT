package com.tencent.qqnt.watch.ui.componet;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class AbsItem {
    private Switch curButton;
    @Nullable
    private final String elementId;

    public AbsItem() {
        this(null, 1, null);
    }

    @NotNull
    public View createView(@NotNull Context context, @NotNull ViewGroup viewGroup) {
        throw new RuntimeException();
    }

    public AbsItem(@Nullable String str) {
        this.elementId = str;
    }
    public /* synthetic */ AbsItem(String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str);
    }
    public abstract int getIconResId();
    public abstract int getText();
    public abstract boolean needSwitch();
    public abstract void updateSwitchStatus(@NotNull Switch r1);
    @Nullable
    public final String getElementId() {
        return this.elementId;
    }
}
