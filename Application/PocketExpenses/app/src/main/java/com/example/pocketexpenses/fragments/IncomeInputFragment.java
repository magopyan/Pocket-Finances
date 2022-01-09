package com.example.pocketexpenses.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.activities.AccountsActivity;
import com.example.pocketexpenses.activities.ChooseAccountActivity;
import com.example.pocketexpenses.activities.ChooseTransactionSubtypeActivity;
import com.example.pocketexpenses.activities.ChooseTransactionTypeActivity;
import com.example.pocketexpenses.databinding.FragmentIncomeInputBinding;
import com.example.pocketexpenses.databinding.FragmentTransactionInputBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.TransactionType;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionInputViewModel;
import com.example.pocketexpenses.viewmodels.TransactionViewModel;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IncomeInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IncomeInputFragment extends Fragment implements View.OnClickListener {

    private FragmentIncomeInputBinding binding;
    private TransactionViewModel oTransactionVM;
    private TransactionInputViewModel oTransactionInputVM;
    private AccountTypeViewModel oAccountTypeVM;

    private Account chosenAccount;
    private TransactionSubtype chosenTransactionSubtype;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public IncomeInputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     //     * @param param1 Parameter 1.
     //     * @param param2 Parameter 2.
     * @return A new instance of fragment IncomeInputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IncomeInputFragment newInstance(/*String param1, String param2*/) {
        IncomeInputFragment fragment = new IncomeInputFragment();
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
        binding = FragmentIncomeInputBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FragmentManager fragmentManager = getParentFragmentManager();

        oTransactionVM = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class);
        oTransactionInputVM = new ViewModelProvider(requireActivity()).get(TransactionInputViewModel.class);
        oAccountTypeVM = new ViewModelProvider(requireActivity()).get(AccountTypeViewModel.class);

        oTransactionInputVM.getAccount().observe(getViewLifecycleOwner(), item -> {
            if(item != null) {
                binding.accountTextField.setText(item.getName());
                chosenAccount = item;
            }
        });
        oTransactionInputVM.getTransactionSubtype().observe(getViewLifecycleOwner(), item -> {
            if(item != null) {
                binding.categoryTextField.setText(item.getName());
                chosenTransactionSubtype = item;
            }
        });


        TextInputEditText etDate = binding.dateTextField;
        Date currentDate = new Date();
        etDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(currentDate));

        MaterialDatePicker datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Select a date")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                Date date = new Date((Long) selection);
                etDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(date));
                binding.dateLayout.setError(null);
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fragmentManager.findFragmentByTag("datePicker") == null) {
                    datePicker.show(fragmentManager, "datePicker");
                }
            }
        });

        binding.amountTextField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(!s.toString().isEmpty() && s.toString() != null) {
                    binding.amountLayout.setError(null);
                }
            }
        });

        binding.accountTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.accountLayout.setError(null);
                Intent intent = new Intent(getContext(), ChooseAccountActivity.class);
                startActivity(intent);
            }
        });

        binding.categoryTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.categoryLayout.setError(null);
                Intent intent = new Intent(getContext(), ChooseTransactionSubtypeActivity.class);
                intent.putExtra("TransactionTypeID", -1);
                startActivity(intent);
            }
        });

        binding.saveButton.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        checkInputFields();

        if(noErrors()) {
            String date = binding.dateTextField.getText().toString();
            double amount = Double.parseDouble(binding.amountTextField.getText().toString());
            String note = binding.noteTextField.getText().toString();

            Transaction inputTransaction = new Transaction(date, amount, note, chosenAccount.getId(), chosenTransactionSubtype.getId(), chosenTransactionSubtype.getImageId());
            oTransactionVM.insertTransaction(inputTransaction);

            double newBalance = chosenAccount.getBalance() + amount;
            chosenAccount.setBalance(newBalance);
            oAccountTypeVM.updateAccount(chosenAccount);

            oTransactionInputVM.reset();

            Intent intent = new Intent(getContext(), AccountsActivity.class);
            startActivity(intent);
        }
    }

    private void checkInputFields() {
        if(binding.dateTextField.getText().toString().isEmpty() || binding.dateTextField.getText().toString() == null)
            binding.dateLayout.setError("You have to select a date!");
        if(binding.amountTextField.getText().toString().isEmpty() || binding.amountTextField.getText().toString() == null)
            binding.amountLayout.setError("You have to enter an amount!");
        if(binding.amountTextField.getText().toString().contains(","))
            binding.amountLayout.setError("Use . instead of , as separator!");

        String[] splitter = binding.amountTextField.getText().toString().split("\\.");
        if(binding.amountTextField.getText().toString().contains(".") && splitter[1].length() > 2)
            binding.amountLayout.setError("There can be only 2 digits after the separator!");

        if(binding.accountTextField.getText().toString().isEmpty() || binding.accountTextField.getText().toString() == null)
            binding.accountLayout.setError("You have to select an account!");
        if(binding.categoryTextField.getText().toString().isEmpty() || binding.categoryTextField.getText().toString() == null)
            binding.categoryLayout.setError("You have to select a category!");
    }

    private boolean noErrors() {
        if(binding.dateLayout.getError() == null && binding.amountLayout.getError() == null && binding.accountLayout.getError() == null
                && binding.categoryLayout.getError() == null)
            return true;
        else
            return false;
    }
}