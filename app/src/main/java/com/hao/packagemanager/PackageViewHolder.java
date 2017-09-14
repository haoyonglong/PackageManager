package com.hao.packagemanager;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by haoyonglong on 2017/7/5.
 */
public class PackageViewHolder extends RecyclerView.ViewHolder {

    public final ImageView selectedIv;
    public final ImageView iconIv;
    public final TextView nameTv;
    public final TextView packageTv;

    public PackageViewHolder(View itemView) {
        super(itemView);
        selectedIv = (ImageView) itemView.findViewById(R.id.select_iv);
        iconIv = (ImageView) itemView.findViewById(R.id.icon_iv);
        nameTv = (TextView) itemView.findViewById(R.id.name_tv);
        packageTv = (TextView) itemView.findViewById(R.id.package_tv);
    }
}
