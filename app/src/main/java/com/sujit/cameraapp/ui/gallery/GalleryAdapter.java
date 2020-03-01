package com.sujit.cameraapp.ui.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sujit.cameraapp.databinding.ImageItemBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.ImageViewHolder> {

    private Context context;
    private List<ImageEntity> imageEntityList;

    public interface OnItemClickListener {
        void onItemClickListener(ImageEntity imageEntity);
    }

    private OnItemClickListener onItemClickListener;

    public GalleryAdapter(Context context) {
        this.context = context;
        this.imageEntityList = new ArrayList<>();
    }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ImageItemBinding itemBinding = ImageItemBinding.inflate(layoutInflater, parent, false);
        return new ImageViewHolder(itemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public void setItems(List<ImageEntity> entities) {
        this.imageEntityList.addAll(entities);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return imageEntityList == null ? 0 : imageEntityList.size();
    }


    public ImageEntity getItem(int position) {
        return imageEntityList.get(position);
    }


    protected class ImageViewHolder extends RecyclerView.ViewHolder {

        private ImageItemBinding binding;

        public ImageViewHolder(ImageItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(final ImageEntity imageEntity) {

            Glide.with(context)
                    .load(imageEntity.getPath())
                    .apply(new RequestOptions().centerCrop())
                    .into(binding.ivImage);

            binding.ivImage.setOnClickListener(view -> {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(imageEntity);
                }
            });
        }
    }
}
