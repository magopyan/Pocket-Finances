package com.example.pocketexpenses.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.activities.TransactionInputActivity;
import com.example.pocketexpenses.databinding.FragmentChooseAccountBinding;
import com.example.pocketexpenses.databinding.FragmentChooseAccountBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;
import com.example.pocketexpenses.onclicklisteners.OnAccountClickListener;
import com.example.pocketexpenses.recyclers.AccountsAdapter;
import com.example.pocketexpenses.recyclers.ChooseAccountAdapter;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionInputViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChooseAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChooseAccountFragment extends Fragment implements OnAccountClickListener {

    private FragmentChooseAccountBinding binding;
    private RecyclerView oAccountsRV;
    private AccountTypeViewModel oViewModel;
    private TransactionInputViewModel oTransactionInputVM;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public ChooseAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment ChooseAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChooseAccountFragment newInstance() {
        ChooseAccountFragment fragment = new ChooseAccountFragment();
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
        binding = FragmentChooseAccountBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oAccountsRV = binding.recyclerAccounts;

        oViewModel = new ViewModelProvider(this).get(AccountTypeViewModel.class);
        oTransactionInputVM = new ViewModelProvider(this).get(TransactionInputViewModel.class);

        ChooseAccountAdapter adapter = new ChooseAccountAdapter(oViewModel, this::onClickAccount);
        oViewModel.getAllAccountTypesWithAccounts().observe(getViewLifecycleOwner(), new Observer<List<AccountTypeWithAccounts>>() {
            @Override
            public void onChanged(@Nullable final List<AccountTypeWithAccounts> accTypesWithAccounts) {
                adapter.setData(accTypesWithAccounts);
            }
        });
        oAccountsRV.setAdapter(adapter);
        oAccountsRV.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onClickAccount(Account account) {
        oTransactionInputVM.setAccount(account);
//        Intent intent = new Intent(getContext(), TransactionInputActivity.class);
//        startActivity(intent);
        getActivity().onBackPressed();
    }
}