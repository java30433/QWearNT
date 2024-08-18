package com.tencent.mvi.api.runtime;

import androidx.annotation.MainThread;

import com.tencent.mvi.base.route.IVMessenger;

public class MviContext {
    @MainThread
    public IVMessenger d() {
        throw new RuntimeException();
    }
}
