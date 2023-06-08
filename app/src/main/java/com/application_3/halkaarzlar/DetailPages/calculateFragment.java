package com.application_3.halkaarzlar.DetailPages;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application_3.halkaarzlar.R;
import com.application_3.halkaarzlar.databinding.FragmentCalculateBinding;

public class calculateFragment extends Fragment {

    private FragmentCalculateBinding design;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        design = DataBindingUtil.inflate(inflater,R.layout.fragment_calculate,container,false);
        return design.getRoot();
    }
}