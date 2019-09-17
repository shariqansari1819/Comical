package com.codebosses.comical.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codebosses.comical.R;
import com.codebosses.comical.databinding.RowSearchComicBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.pojo.Comic;
import com.codebosses.comical.pojo.event_bus.EventBusAdapterClick;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SearchComicAdapter extends RecyclerView.Adapter<SearchComicAdapter.SearchComicHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Comic> comicsList = new ArrayList<>();

    public SearchComicAdapter(Context context, List<Comic> comicsList) {
        this.context = context;
        this.comicsList = comicsList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SearchComicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SearchComicHolder(DataBindingUtil.inflate(layoutInflater, R.layout.row_search_comic, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SearchComicHolder holder, int position) {
        holder.searchComicBinding.setComic(comicsList.get(position));
    }

    @Override
    public int getItemCount() {
        return comicsList.size();
    }

    class SearchComicHolder extends RecyclerView.ViewHolder {

        RowSearchComicBinding searchComicBinding;

        public SearchComicHolder(@NonNull RowSearchComicBinding itemView) {
            super(itemView.getRoot());
            this.searchComicBinding = itemView;

            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new EventBusAdapterClick(getAdapterPosition(), EndpointKeys.SEARCH_COMIC_CLICK));
                }
            });
        }
    }
}
