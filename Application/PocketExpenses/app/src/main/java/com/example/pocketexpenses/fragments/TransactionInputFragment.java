package com.example.pocketexpenses.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.FragmentTransactionInputBinding;
import com.example.pocketexpenses.databinding.FragmentTransactionsListBinding;
import com.example.pocketexpenses.viewmodels.TransactionViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionInputFragment extends Fragment implements View.OnClickListener {

    private FragmentTransactionInputBinding binding;
    private TransactionViewModel oTransactionViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public TransactionInputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionInputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionInputFragment newInstance(/*String param1, String param2*/) {
        TransactionInputFragment fragment = new TransactionInputFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTransactionInputBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.saveButton.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        checkInputFields();
        // if(no errors)
        //oTransactionViewModel.insertTransaction()
    }

    private void checkInputFields() {

    }
}