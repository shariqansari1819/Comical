package com.codebosses.comical.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codebosses.comical.R;
import com.codebosses.comical.databinding.RowComicDetailComicsBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.pojo.Chapter;
import com.codebosses.comical.pojo.event_bus.EventBusAdapterClick;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ComicsDetailsAdapter extends RecyclerView.Adapter<ComicsDetailsAdapter.ComicsHomeHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Chapter> comicsList = new ArrayList<>();

    public ComicsDetailsAdapter(Context context, List<Chapter> comicsList) {
        this.context = context;
        this.comicsList = comicsList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ComicsHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComicsHomeHolder(DataBindingUtil.inflate(layoutInflater, R.layout.row_comic_detail_comics, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsHomeHolder holder, int position) {
        holder.comicsBinding.setComic(comicsList.get(position));
    }

    @Override
    public int getItemCount() {
        return comicsList.size();
    }

    class ComicsHomeHolder extends RecyclerView.ViewHolder {

        RowComicDetailComicsBinding comicsBinding;

        public ComicsHomeHolder(@NonNull RowComicDetailComicsBinding itemView) {
            super(itemView.getRoot());
            this.comicsBinding = itemView;

            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new EventBusAdapterClick(getAdapterPosition(), EndpointKeys.COMIC_DETAIL_COMIC_CLICK));
                }
            });
        }
    }
}
