package com.example.pocketexpenses.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pocketexpenses.activities.AccountsActivity;
import com.example.pocketexpenses.activities.ChooseAccountTypeActivity;
import com.example.pocketexpenses.databinding.FragmentAccountInputBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.viewmodels.AccountInputViewModel;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountInputFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountInputFragment extends Fragment implements View.OnClickListener {

    private FragmentAccountInputBinding binding;
    private AccountInputViewModel oAccountTypeInputVM;
    private AccountTypeViewModel oAccountTypeViewModel;

    private AccountType chosenAccountType;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public AccountInputFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AccountInputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountInputFragment newInstance() {
        AccountInputFragment fragment = new AccountInputFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountInputBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        oAccountTypeViewModel = new ViewModelProvider(requireActivity()).get(AccountTypeViewModel.class);
        oAccountTypeInputVM = new ViewModelProvider(requireActivity()).get(AccountInputViewModel.class);

        oAccountTypeInputVM.getAccountType().observe(getViewLifecycleOwner(), item -> {
            if (item != null) {
                binding.accountTypeTextField.setText(item.getName());
                chosenAccountType = item;
            }
        });

        binding.accountTypeTextField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChooseAccountTypeActivity.class);
                startActivity(intent);
            }
        });

        binding.saveButtonAccount.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        checkInputFields();

        if(noErrors())
        {
            double dBalance = Double.parseDouble(binding.balanceTextField.getText().toString());
            String strAccountName = binding.nameTextField.getText().toString();

            Account inputAccount = new Account(dBalance, strAccountName, chosenAccountType.getId());
            oAccountTypeViewModel.insertAccount(inputAccount);

            Intent intent = new Intent(getContext(), AccountsActivity.class);
            startActivity(intent);
        }
    }


    private void checkInputFields()
    {
        String balanceInput = binding.balanceTextField.getText().toString();
        if(balanceInput == null || balanceInput.isEmpty() || balanceInput.trim().isEmpty())
            binding.balanceLayout.setError("You have to enter an amount!");
        if(balanceInput.contains(","))
            binding.balanceLayout.setError("Use . instead of , as separator!");
        String[] splitter = balanceInput.split("\\.");
        if(balanceInput.contains(".") && splitter[1].length() > 2)
            binding.balanceLayout.setError("There can be only 2 digits after the separator!");

        String nameInput = binding.nameTextField.getText().toString();
        if(nameInput == null || nameInput.isEmpty() || nameInput.trim().isEmpty())
            binding.nameLayout.setError("You have to enter a name for the account!");

        String accountTypeInput = binding.accountTypeTextField.getText().toString();
        if(accountTypeInput == null || accountTypeInput.isEmpty() || accountTypeInput.trim().isEmpty())
            binding.accountTypeLayout.setError("You have to select a type!");
    }

    private boolean noErrors() {
        if(binding.balanceLayout.getError() == null && binding.nameLayout.getError() == null && binding.accountTypeLayout.getError() == null)
            return true;
        else
            return false;
    }
}