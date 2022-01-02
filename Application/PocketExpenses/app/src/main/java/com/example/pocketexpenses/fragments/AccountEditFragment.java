package com.example.pocketexpenses.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.activities.ChooseAccountTypeActivity;
import com.example.pocketexpenses.databinding.FragmentAccountEditBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.onclicklisteners.OnEditAccountListener;
import com.example.pocketexpenses.viewmodels.AccountInputViewModel;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountEditFragment extends Fragment implements View.OnClickListener{

    private FragmentAccountEditBinding binding;
    private AccountInputViewModel oAccountTypeInputVM;
    private AccountTypeViewModel oAccountTypeViewModel;
    private AccountType oAccountType;
    private OnEditAccountListener listener;
    private Account oAccount;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public AccountEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AccountInputFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountEditFragment newInstance(Account oAccount) {
        AccountEditFragment fragment = new AccountEditFragment();
        Bundle args = new Bundle();
        args.putParcelable("AccountItem", oAccount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            oAccount = getArguments().getParcelable("AccountItem");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAccountEditBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnEditAccountListener)
            listener = (OnEditAccountListener) context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LiveData<AccountType> oLiveDataAccountType = oAccountTypeViewModel.getAccountTypeByID(oAccount.getAccountTypeId());
        binding.accountTypeTextFieldEdit.setText(oLiveDataAccountType.getValue().getName());

        binding.balanceTextFieldEdit.setText(String.valueOf(oAccount.getBalance()));
        binding.nameTextFieldEdit.setText(oAccount.getName());

        oAccountTypeViewModel = new ViewModelProvider(requireActivity()).get(AccountTypeViewModel.class);
        oAccountTypeInputVM = new ViewModelProvider(requireActivity()).get(AccountInputViewModel.class);

        oAccountTypeInputVM.getAccountType().observe(getViewLifecycleOwner(), item -> {
            if(item != null) {
                binding.accountTypeTextFieldEdit.setText(item.getName());
                oAccountType = item;
            }
        });

        binding.accountTypeTextFieldEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChooseAccountTypeActivity.class);
                startActivity(intent);
            }
        });

        binding.saveButtonAccountEdit.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        checkInputFields();
        // if(no errors)

        double dBalance = Double.parseDouble(binding.balanceTextFieldEdit.getText().toString());
        String strAccountName = binding.nameTextFieldEdit.getText().toString();

        Account editAccount = new Account(dBalance, strAccountName, oAccountType.getId(), R.drawable.ic_input_account);
        oAccountTypeViewModel.updateAccount(editAccount);

        listener.EditAccount(editAccount);
        //dismiss();
    }

    private void checkInputFields() {
        //
        //
        //
    }
}