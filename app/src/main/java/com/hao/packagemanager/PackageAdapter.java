package com.hao.packagemanager;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by long on 2017/9/13.
 */
public class PackageAdapter extends RecyclerView.Adapter<PackageViewHolder> {

    private static final String TAG = PackageAdapter.class.getSimpleName();
    private static final String DELIMITER = "\r\n";

    private final LayoutInflater mInflater;
    private final Context mContext;

    private List<ApplicationInfo> items = new ArrayList<>();
    private Set<String> mPackageSet = new HashSet<>();

    public PackageAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void addAll(java.util.Collection<? extends ApplicationInfo> applicationInfos) {
        if (applicationInfos != null && !applicationInfos.isEmpty()) {
            items.addAll(applicationInfos);
            notifyDataSetChanged();
        }
    }

    @Override
    public PackageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PackageViewHolder(mInflater.inflate(R.layout.item_package, parent, false));
    }

    @Override
    public void onBindViewHolder(final PackageViewHolder holder, int position) {
        final int p = holder.getAdapterPosition();
        final ApplicationInfo item = items.get(p);
        if (item == null) {
            return;
        }
        final String appName = item.loadLabel(mContext.getPackageManager()).toString();
        final String appInfo = appName + DELIMITER + item.packageName;
        holder.iconIv.setImageDrawable(item.loadIcon(mContext.getPackageManager()));
        holder.nameTv.setText(appName);
        holder.packageTv.setText(item.packageName);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringUtils.copy(mContext, "packages", appInfo);
                Toast.makeText(mContext, "名称：" + appName + DELIMITER + "包名：" + item.packageName, Toast.LENGTH_SHORT).show();
            }
        });
        if (mPackageSet.contains(appInfo)) {
            holder.selectedIv.setSelected(true);
        } else {
            holder.selectedIv.setSelected(false);
        }

        holder.selectedIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPackageSet.contains(appInfo)) {
                    mPackageSet.remove(appInfo);
                } else {
                    mPackageSet.add(appInfo);
                }
                notifyItemChanged(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public String getPackageInfo() {
        if (!mPackageSet.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String app : mPackageSet) {
                builder.append(app).append(DELIMITER);
            }
            String result = builder.toString();
            return result.substring(0, result.length() - DELIMITER.length());
        }
        return "";
    }
}
