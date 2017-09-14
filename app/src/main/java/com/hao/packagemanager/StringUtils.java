package com.hao.packagemanager;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by long on 2017/9/13.
 */
public class StringUtils {
    /**
     * 复制内容到剪贴板
     *
     * @param label
     * @param content
     */
    public static void copy(Context context, String label, String content) {
        ClipboardManager clip = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText(label, content);
        clip.setPrimaryClip(clipData);
    }
}
