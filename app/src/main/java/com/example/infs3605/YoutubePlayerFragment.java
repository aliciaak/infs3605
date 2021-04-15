package com.example.infs3605;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerUtils;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class YoutubePlayerFragment extends Fragment {
    YouTubePlayerView youTubePlayerView;
    LinearLayout backButton;
    TextView pageDesc,pageTitle;
    Bundle bundle;
    ImageView imgView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_youtube_player, container, false);
        youTubePlayerView  = view.findViewById(R.id.youtube_player_view);
        backButton  = view.findViewById(R.id.backButton);
        pageDesc  = view.findViewById(R.id.pageDesc);
//        pageDesc.setMovementMethod(new ScrollingMovementMethod());
        pageTitle  = view.findViewById(R.id.pageTitle);
        imgView  = view.findViewById(R.id.imgView);
        bundle = this.getArguments();
        initYouTubePlayerView();
        return view;
    }

    private void initYouTubePlayerView() {
        // The player will automatically release itself when the fragment is destroyed.
        // The player will automatically pause when the fragment is stopped
        // If you don't add YouTubePlayerView as a lifecycle observer, you will have to release it manually.
        getLifecycle().addObserver(youTubePlayerView);

        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
            @Override
            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                setPlayNextVideoButtonClickListener(youTubePlayer);
                String videoId = bundle.getString("youtubeLink", "");
                YouTubePlayerUtils.loadOrCueVideo(
                        youTubePlayer, getLifecycle(),
                        videoId,0f
                );
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bundle = this.getArguments();
        if (bundle != null) {
            String redcrossdesc = bundle.getString("redcrossdesc", "");
            String redcrosstitle = bundle.getString("redcrosstitle", "");
            pageDesc.setText(redcrossdesc);
            pageTitle.setText(redcrosstitle);
            if(bundle.getBoolean("isImageShow"))
                imgView.setVisibility(View.VISIBLE);
            else
                imgView.setVisibility(View.GONE);
        }

//        youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
//            @Override
//            public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                String videoId = bundle.getString("youtubeLink", "");
//                youTubePlayer.loadVideo(videoId, 0);
//            }
//        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
    }
}