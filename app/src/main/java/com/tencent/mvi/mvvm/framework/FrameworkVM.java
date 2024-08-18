package com.tencent.mvi.mvvm.framework;

import com.tencent.mvi.api.runtime.MviContext;

import org.jetbrains.annotations.NotNull;

public class FrameworkVM {
    @NotNull
    public final MviContext h() {
        throw new RuntimeException();
    }
}
