package com.juude.recyclerview;

import java.util.List;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.TextView;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    private PackageManager mPackageManager;
    private List<ApplicationInfo> mApplicationInfos;
    public static DisplayMetrics sOutMetrics = new DisplayMetrics();

    public RecyclerAdapter(Context context) {
        super();
        mPackageManager = context.getPackageManager();
        mApplicationInfos = mPackageManager.getInstalledApplications(0);
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(sOutMetrics);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = View.inflate(viewGroup.getContext(), R.layout.app_item, null);
        View container = view.findViewById(R.id.contain);
        LayoutParams lp = container.getLayoutParams();
        lp.width = sOutMetrics.widthPixels/2;
        lp.height = sOutMetrics.heightPixels/5;
        ((ViewGroup)view).updateViewLayout(container, lp);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.mTextView.setText(mApplicationInfos.get(i).loadLabel(mPackageManager));
        Drawable icon = mApplicationInfos.get(i).loadIcon(mPackageManager);
        icon.setBounds(0, 0, icon.getMinimumWidth(), icon.getMinimumHeight());
        viewHolder.mTextView.setCompoundDrawables(icon, 
        		null, null, null);
        int color = (i%3 == 0)  ? Color.RED : (i%3 == 1 ? Color.GREEN : Color.BLUE );
        viewHolder.mContainer.setBackgroundColor(color);
    }

    @Override
    public int getItemCount() {
        return mApplicationInfos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
    	
        public TextView mTextView;
        public View mContainer;

        public ViewHolder(View itemView) {
            super(itemView);
            mContainer = itemView.findViewById(R.id.contain);
            mTextView = (TextView) (itemView.findViewById(R.id.appInfo));
        }
    }
}
