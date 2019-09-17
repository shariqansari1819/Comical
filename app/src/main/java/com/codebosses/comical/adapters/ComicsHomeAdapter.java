package com.codebosses.comical.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.codebosses.comical.R;
import com.codebosses.comical.databinding.RowHomeComicsBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.pojo.Comic;
import com.codebosses.comical.pojo.event_bus.EventBusAdapterClick;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class ComicsHomeAdapter extends RecyclerView.Adapter<ComicsHomeAdapter.ComicsHomeHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Comic> comicsList = new ArrayList<>();

    public ComicsHomeAdapter(Context context, List<Comic> comicsList) {
        this.context = context;
        this.comicsList = comicsList;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ComicsHomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ComicsHomeHolder(DataBindingUtil.inflate(layoutInflater, R.layout.row_home_comics, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsHomeHolder holder, int position) {
        holder.homeComicsBinding.setComic(comicsList.get(position));
    }

    @Override
    public int getItemCount() {
        return comicsList.size();
    }

    class ComicsHomeHolder extends RecyclerView.ViewHolder {

        RowHomeComicsBinding homeComicsBinding;

        public ComicsHomeHolder(@NonNull RowHomeComicsBinding itemView) {
            super(itemView.getRoot());
            this.homeComicsBinding = itemView;

            itemView.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new EventBusAdapterClick(getAdapterPosition(), EndpointKeys.HOME_COMIC_CLICK));
                }
            });
        }
    }
}
