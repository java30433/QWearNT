package com.tencent.watch.aio_impl.data;

import com.tencent.aio.data.msglist.IMsgItem;
import com.tencent.qqnt.kernel.nativeinterface.Contact;
import com.tencent.qqnt.kernel.nativeinterface.MsgRecord;

import org.jetbrains.annotations.NotNull;

public class WatchAIOMsgItem extends IMsgItem {
    @NotNull
    public final MsgRecord d = null;
    @NotNull
    public CharSequence k;
    @NotNull
    public CharSequence l;

    @NotNull
    public final Contact l() {
        throw new RuntimeException();
    }
}
