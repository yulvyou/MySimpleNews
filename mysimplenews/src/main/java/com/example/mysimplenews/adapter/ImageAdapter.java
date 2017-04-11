package com.example.mysimplenews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mysimplenews.R;
import com.example.mysimplenews.beans.ImageBean;
import com.example.mysimplenews.utitls.ImageLoaderUtils;
import com.example.mysimplenews.utitls.ToolsUtil;

import java.util.List;

/**
 * Created by lenovo on 2017/3/2.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ItemViewHolder>{

    private OnItemClickListener mOnItemClickListener;
    private List<ImageBean> mData;
    private Context mContext;
    private int mMaxWidth;
    private int mMaxHeight;

    public ImageAdapter(Context context){
        this.mContext = context;
        mMaxWidth = ToolsUtil.getWidthInPx(mContext) - 20;
        mMaxHeight = ToolsUtil.getHeightInPx(mContext) - ToolsUtil.getStatusHeight(mContext) -
                ToolsUtil.dip2px(mContext, 96);
    }

    @Override
    public ImageAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image,parent,false);
        ItemViewHolder viewHolder = new ItemViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ItemViewHolder holder, int position) {
        ImageBean imageBean = mData.get(position);
        if(imageBean == null){
            return;
        }
        holder.mTitle.setText(imageBean.getTitle());
        float scale = (float)imageBean.getWidth() / (float) mMaxWidth;
        int height = (int)(imageBean.getHeight() / scale);
        if(height > mMaxHeight) {
            height = mMaxHeight;
        }
        holder.mImage.setLayoutParams(new LinearLayout.LayoutParams(mMaxWidth, height));
        //使用图片加载工具类加载图片（图片加载工具类是使用Glide完成图像加载）
        ImageLoaderUtils.display(mContext, holder.mImage, imageBean.getThumburl());
    }

    @Override
    public int getItemCount() {
        if(mData == null) {
            return 0;
        }
        return mData.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTitle;
        public ImageView mImage;
        public ItemViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mImage = (ImageView) itemView.findViewById(R.id.ivImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mOnItemClickListener != null){
                mOnItemClickListener.onItemClick(v, this.getAdapterPosition());
            }
        }
    }

    public void setmDate(List<ImageBean> data) {
        this.mData = data;
        //通知UI数据发生变化
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    /**
     * 具体的实现由Fragment实现
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}//End
