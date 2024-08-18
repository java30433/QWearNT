package com.tencent.qqnt.msg.api.impl;

import com.tencent.qqnt.kernel.nativeinterface.MsgElement;
import com.tencent.qqnt.kernel.nativeinterface.MsgRecord;

import org.jetbrains.annotations.NotNull;

public class MsgUtilApiImpl {
    public static MsgUtilApiImpl instance;
    public MsgElement createReplyElement(long msgSeq) {
        throw new RuntimeException();
    }
    @NotNull
    public MsgElement createAtTextElement(@NotNull String content, @NotNull String uid, int atType) {
        throw new RuntimeException();
    }
    @NotNull
    public String getElementSummary(@NotNull MsgRecord msgRecord) {
        throw new RuntimeException();
    }
}
