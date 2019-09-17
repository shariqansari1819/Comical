package com.codebosses.comical.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codebosses.comical.R;
import com.codebosses.comical.activities.ReadComicActivity;
import com.codebosses.comical.adapters.ComicsDetailsAdapter;
import com.codebosses.comical.databinding.FragmentComicsBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.pojo.Chapter;
import com.codebosses.comical.pojo.Comic;
import com.codebosses.comical.pojo.event_bus.EventBusAdapterClick;
import com.codebosses.comical.utils.L;
import com.codebosses.comical.utils.ValidUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class FragmentComics extends Fragment {

    //    Android fields....
    private FragmentComicsBinding comicsBinding;

    //    Adapter fields....
    private ComicsDetailsAdapter comicsDetailsAdapter;
    private List<Chapter> comicsList = new ArrayList<>();

    //    Firebase fields....
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public static FragmentComics getInstance(Comic comicGroup) {
        FragmentComics fragmentComics = new FragmentComics();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EndpointKeys.COMIC, comicGroup);
        fragmentComics.setArguments(bundle);
        return fragmentComics;
    }

    public FragmentComics() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        comicsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_comics, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (getArguments() != null) {
            Comic comicGroup = getArguments().getParcelable(EndpointKeys.COMIC);
            if (comicGroup != null) {
                comicsDetailsAdapter = new ComicsDetailsAdapter(getActivity(), comicsList);
                comicsBinding.recyclerViewComics.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                comicsBinding.recyclerViewComics.setAdapter(comicsDetailsAdapter);
                if (ValidUtils.isNetworkAvailable(getActivity())) {
                    getComics(comicGroup.getComicId());
                }
            }
        }

        return comicsBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    private void getComics(String comicId) {
        firebaseFirestore.collection(EndpointKeys.COMICS)
                .document(comicId)
                .collection(EndpointKeys.CHAPTER)
                .get()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots != null && queryDocumentSnapshots.size() > 0) {
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                if (snapshot != null && snapshot.exists()) {
                                    comicsList.add(snapshot.toObject(Chapter.class));
                                    comicsDetailsAdapter.notifyItemInserted(comicsList.size() - 1);
                                }
                            }
                        }
                    }
                }).addOnFailureListener(getActivity(), new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                L.showToast(getActivity(), e.getMessage());
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onComicClick(EventBusAdapterClick eventBusAdapterClick) {
        if (eventBusAdapterClick.getClickType().equals(EndpointKeys.COMIC_DETAIL_COMIC_CLICK)) {
            Intent intent = new Intent(getActivity(), ReadComicActivity.class);
            intent.putStringArrayListExtra(EndpointKeys.COMIC_URL, comicsList.get(eventBusAdapterClick.getPosition()).getImages());
            startActivity(intent);
        }
    }

}