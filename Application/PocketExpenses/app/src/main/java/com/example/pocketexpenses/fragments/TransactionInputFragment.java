package com.example.pocketexpenses.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.activities.AccountsActivity;
import com.example.pocketexpenses.activities.ChooseAccountActivity;
import com.example.pocketexpenses.databinding.FragmentTransactionInputBinding;
import com.example.pocketexpenses.databinding.FragmentTransactionsListBinding;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.viewmodels.TransactionViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        FragmentManager fragmentManager = getParentFragmentManager();
        oTransactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);

        binding.saveButton.setOnClickListener(this::onClick);
        TextInputEditText etDate = binding.dateTextField;

        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a date").setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                Date varDate = null;
                try {
                    varDate = dateFormat.parse(datePicker.getHeaderText());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                etDate.setText(dateFormat.format(varDate));
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(fragmentManager, "someTag");
            }
        });

        binding.accountTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChooseAccountActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        checkInputFields();
        // if(no errors)

        String date = binding.dateTextField.getText().toString();
        double amount = Double.parseDouble(binding.amountTextField.getText().toString());
        String note = binding.noteTextField.getText().toString();

        Transaction inputTransaction = new Transaction(date, amount, note, 1, 1);
        oTransactionViewModel.insertTransaction(inputTransaction);

        Intent intent = new Intent(getContext(), AccountsActivity.class);
        startActivity(intent);
    }

    private void checkInputFields() {
        //
        //
        //
    }
}