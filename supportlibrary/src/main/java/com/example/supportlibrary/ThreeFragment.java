package com.example.supportlibrary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ThreeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //R.layout.fragment_three 부분만 출력할 레이아웃으로 변경하면 됨
        //( ,출력할 곳, root에 붙일 건지)
        return inflater.inflate(R.layout.fragment_three, container, false);
    }
}
