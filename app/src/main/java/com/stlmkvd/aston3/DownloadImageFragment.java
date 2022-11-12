package com.stlmkvd.aston3;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.stlmkvd.aston3.databinding.FragmentDownloadImageBinding;

public class DownloadImageFragment extends Fragment {

    private static final String TAG = "DownloadImageFragment";
    FragmentDownloadImageBinding binding;
    DownloadImageFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(DownloadImageFragmentViewModel.class);
        viewModel.getDownloadResult().postValue(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDownloadImageBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        binding.edittextUrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.setUserInput(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        View.OnClickListener buttonsListener = v -> {
            Repository.DownloadMode mode;
            if (v.getId() == R.id.button_glide) mode = Repository.DownloadMode.GLIDE;
            else mode = Repository.DownloadMode.INPUTSTREAM;
            viewModel.downloadImage(viewModel.getUserInput(),
                    mode,
                    requireContext());
        };
        viewModel.getDownloadResult().observe(this, isDownloaded -> {
            if (isDownloaded) updateUI();
            else Toast.makeText(requireContext(),
                    "Download failed :(", Toast.LENGTH_SHORT).show();
        });
        binding.buttonGlide.setOnClickListener(buttonsListener);
        binding.buttonInputStream.setOnClickListener(buttonsListener);
    }

    private void updateUI() {
        Bitmap image = viewModel.getImage();
        if (image != null) binding.image.setImageBitmap(image);
        binding.edittextUrl.setText(viewModel.getUserInput());
    }
}