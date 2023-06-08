package com.application_3.halkaarzlar.DetailPages;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application_3.halkaarzlar.R;
import com.application_3.halkaarzlar.databinding.FragmentCommentBinding;
import com.application_3.halkaarzlar.databinding.FragmentPersonBinding;

public class personFragment extends Fragment {

    private FragmentPersonBinding design;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_person,container,false);
        return design.getRoot();
    }
}