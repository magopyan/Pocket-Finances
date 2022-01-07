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

import com.example.pocketexpenses.activities.AccountsActivity;
import com.example.pocketexpenses.activities.ChooseAccountActivity;
import com.example.pocketexpenses.activities.ChooseTransactionSubtypeActivity;
import com.example.pocketexpenses.activities.ChooseTransactionTypeActivity;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TransactionInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionInputFragment extends Fragment implements View.OnClickListener {

    private FragmentTransactionInputBinding binding;
    private TransactionViewModel oTransactionVM;
    private TransactionInputViewModel oTransactionInputVM;
    private AccountTypeViewModel oAccountTypeVM;

    private Account chosenAccount;
    private TransactionType chosenTransactionType;
    private TransactionSubtype chosenTransactionSubtype;

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

        oTransactionVM = new ViewModelProvider(requireActivity()).get(TransactionViewModel.class);
        oTransactionInputVM = new ViewModelProvider(requireActivity()).get(TransactionInputViewModel.class);
        oAccountTypeVM = new ViewModelProvider(requireActivity()).get(AccountTypeViewModel.class);

        oTransactionInputVM.getAccount().observe(getViewLifecycleOwner(), item -> {
            if(item != null) {
                binding.accountTextField.setText(item.getName());
                chosenAccount = item;
            }
        });
        oTransactionInputVM.getTransactionType().observe(getViewLifecycleOwner(), item -> {
            if(item != null) {
                binding.categoryTextField.setText(item.getName());
                chosenTransactionType = item;
            }
        });
        oTransactionInputVM.getTransactionSubtype().observe(getViewLifecycleOwner(), item -> {
            if(item != null) {
                binding.subcategoryTextField.setText(item.getName());
                chosenTransactionSubtype = item;
            }
        });


        TextInputEditText etDate = binding.dateTextField;

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
                Intent intent = new Intent(getContext(), ChooseTransactionTypeActivity.class);
                startActivity(intent);
            }
        });

        binding.subcategoryTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chosenTransactionType == null) {
                    binding.subcategoryLayout.setError("You need to select a Category first!");
                }
                else {
                    binding.subcategoryLayout.setError(null);
                    Intent intent = new Intent(getContext(), ChooseTransactionSubtypeActivity.class);
                    intent.putExtra("TransactionTypeID", chosenTransactionType.getId());
                    startActivity(intent);
                }
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

            double newBalance = -1 * chosenAccount.getBalance() - amount;
            //String toastMessage = "You have exceeded " + chosenAccount.getName() + "'s balance, it is now negative.";
            chosenAccount.setBalance(newBalance);
            oAccountTypeVM.updateAccount(chosenAccount);
            oTransactionInputVM.reset();

            getActivity().onBackPressed();
//            Intent intent = new Intent(getContext(), AccountsActivity.class);
//            if(newBalance < 0)
//                intent.putExtra("Toast Message", toastMessage);
//            startActivity(intent);
        }
    }

    private void checkInputFields()
    {
        String dateInput = binding.dateTextField.getText().toString();
        if(dateInput == null || dateInput.isEmpty() || dateInput.trim().isEmpty())
            binding.dateLayout.setError("You have to select a date!");

        String amountInput = binding.amountTextField.getText().toString();
        if(amountInput.isEmpty() || amountInput == null || amountInput.trim().isEmpty())
            binding.amountLayout.setError("You have to enter an amount!");
        if(amountInput.contains(","))
            binding.amountLayout.setError("Use . instead of , as separator!");

        String[] splitter = amountInput.split("\\.");
        if(amountInput.contains(".") && splitter[1].length() > 2)
            binding.amountLayout.setError("There can be only 2 digits after the separator!");

        if(binding.accountTextField.getText().toString().isEmpty() || binding.accountTextField.getText().toString() == null)
            binding.accountLayout.setError("You have to select an account!");
        if(binding.categoryTextField.getText().toString().isEmpty() || binding.categoryTextField.getText().toString() == null)
            binding.categoryLayout.setError("You have to select a category!");
        if(binding.subcategoryTextField.getText().toString().isEmpty() || binding.subcategoryTextField.getText().toString() == null)
            binding.subcategoryLayout.setError("You have to select a subcategory!");
    }

    private boolean noErrors() {
        if(binding.dateLayout.getError() == null && binding.amountLayout.getError() == null && binding.accountLayout.getError() == null
            && binding.categoryLayout.getError() == null && binding.subcategoryLayout.getError() == null)
            return true;
        else
            return false;
    }
}