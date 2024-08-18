package com.tencent.qqnt.kernel.nativeinterface;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public final class ReplyElement {
    @Nullable
    public String mySenderName;

    public String anonymousNickName;
    public Integer originalMsgState;
    public long replayMsgId;
    public Long replayMsgRootCommentCnt;
    public Long replayMsgRootMsgId;
    public Long replayMsgRootSeq;
    public Long replayMsgSeq;
    public Long replyMsgClientSeq;
    public int replyMsgRevokeType;
    public Long replyMsgTime;
    public Long senderUid;
    public String senderUidStr;
    public long serialVersionUID;
    public boolean sourceMsgExpired;
    public Long sourceMsgIdInRecords;
    public boolean sourceMsgIsIncPic;
    public String sourceMsgText;
    public ArrayList<ReplyAbsElement> sourceMsgTextElems;
}
