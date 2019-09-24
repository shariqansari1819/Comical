package com.codebosses.comical.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codebosses.comical.R;
import com.codebosses.comical.adapters.MoviesAdapter;
import com.codebosses.comical.databinding.FragmentMoviesBinding;
import com.codebosses.comical.endpoints.EndpointKeys;
import com.codebosses.comical.pojo.Comic;
import com.codebosses.comical.pojo.Movie;
import com.codebosses.comical.utils.ValidUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class FragmentMovies extends Fragment {

    //    Android fields....
    private FragmentMoviesBinding moviesBinding;

    //    Instance fields....
    private List<Movie> movieList = new ArrayList<>();
    private MoviesAdapter moviesAdapter;

    //    Firebase fields....
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;

    public FragmentMovies() {
    }

    public static FragmentMovies getInstance(Comic comicGroup) {
        FragmentMovies fragmentMovies = new FragmentMovies();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EndpointKeys.COMIC, comicGroup);
        fragmentMovies.setArguments(bundle);
        return fragmentMovies;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        moviesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false);

        if (getArguments() != null) {
            Comic comicGroup = getArguments().getParcelable(EndpointKeys.COMIC);

            firebaseAuth = FirebaseAuth.getInstance();
            firebaseFirestore = FirebaseFirestore.getInstance();

            if (ValidUtils.isNetworkAvailable(getActivity())) {
                moviesAdapter = new MoviesAdapter(getActivity(), movieList);
                moviesBinding.recyclerViewMovies.setLayoutManager(new LinearLayoutManager(getActivity()));
                moviesBinding.recyclerViewMovies.setAdapter(moviesAdapter);
                if (comicGroup != null) {
                    getAllMovies(comicGroup.getComicId());
                }
            }

        }

        return moviesBinding.getRoot();
    }

    private void getAllMovies(String comicId) {
        firebaseFirestore.collection("Comics")
                .document(comicId)
                .collection("Movies")
                .get()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots != null && queryDocumentSnapshots.size() > 0) {
                            for (DocumentSnapshot snapshot : queryDocumentSnapshots) {
                                if (snapshot != null && snapshot.exists()) {
                                    movieList.add(snapshot.toObject(Movie.class));
                                    moviesAdapter.notifyItemInserted(movieList.size() - 1);
                                }
                            }
                        }
                    }
                });
    }


}
