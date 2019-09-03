package com.cloud.model.common;

/**
 * @author bjy
 * @date 2019/3/28 0028 19:03
 */
public class CallType {
    public static final int INCOMING_TYPE = 1;
    /** Call log type for outgoing calls. */
    public static final int OUTGOING_TYPE = 2;
    /** Call log type for missed calls. */
    public static final int MISSED_TYPE = 3;
    /** Call log type for voicemails. */
    public static final int VOICEMAIL_TYPE = 4;
    /** Call log type for calls rejected by direct user action. */
    public static final int REJECTED_TYPE = 5;
    /** Call log type for calls blocked automatically. */
    public static final int BLOCKED_TYPE = 6;
}
