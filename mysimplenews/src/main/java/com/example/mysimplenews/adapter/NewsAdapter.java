package com.example.mysimplenews.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mysimplenews.R;
import com.example.mysimplenews.beans.NewsBean;
import com.example.mysimplenews.utitls.ImageLoaderUtils;

import java.util.List;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/19
 */
public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    private List<NewsBean> mData;
    private boolean mShowFooter = true;
    private Context mContext;

    private OnItemClickListener mOnItemClickListener;

    public NewsAdapter(Context context) {
        this.mContext = context;
    }

    public void setmDate(List<NewsBean> data) {
        this.mData = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if(!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        if(viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_news, parent, false);
            ItemViewHolder vh = new ItemViewHolder(v);
            return vh;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footer, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ItemViewHolder) {//如果类型是Item而不是footer的时候

            NewsBean news = mData.get(position);
            if(news == null) {
                return;
            }
            //给各个组件赋值
            ((ItemViewHolder) holder).mTitle.setText(news.getTitle());
            ((ItemViewHolder) holder).mDesc.setText(news.getDigest());
//            Uri uri = Uri.parse(news.getImgsrc());
//            ((ItemViewHolder) holder).mNewsImg.setImageURI(uri);
            ImageLoaderUtils.display(mContext, ((ItemViewHolder) holder).mNewsImg, news.getImgsrc());
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter?1:0;
        if(mData == null) {
            return begin;
        }
        return mData.size() + begin;
    }

    public NewsBean getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    
    
    
    /**
     * Item 的holder
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView mTitle;
        public TextView mDesc;
        public ImageView mNewsImg;

        public ItemViewHolder(View v) {
            super(v);
            mTitle = (TextView) v.findViewById(R.id.tvTitle);
            mDesc = (TextView) v.findViewById(R.id.tvDesc);
            mNewsImg = (ImageView) v.findViewById(R.id.ivNews);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(mOnItemClickListener != null) {//当调用setOnItemClickListener方法的时候对mOnItemClickListener进行赋值
                mOnItemClickListener.onItemClick(view, this.getPosition());
            }
        }
    }//ItemViewHolder
    
    /**
     * 最后一个Item（Footer）的Holder
     */
    public class FooterViewHolder extends RecyclerView.ViewHolder {
        
        public FooterViewHolder(View view) {
            super(view);
        }
        
    }//FooterViewHolder
    
    
    public boolean setShowFooter() {
        return this.mShowFooter;
    }
    public void setShowFooter(boolean showFooter) {
        this.mShowFooter = showFooter;
    }
    
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }
    
    /**
     * 设置Item的事件监听，mOnItemClickListener在此方法中被赋值
     * @param onItemClickListener 接口
     * @Use ：NewsListFragment 有调用
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    

}//End
