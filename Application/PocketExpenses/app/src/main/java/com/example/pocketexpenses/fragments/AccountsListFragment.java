package com.example.pocketexpenses.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.ViewModel.AccountTypeViewModel;
import com.example.pocketexpenses.databinding.FragmentAccountsListBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.relationships.AccountTypeWithAccounts;
import com.example.pocketexpenses.recyclers.AccountsAdapter;
import com.example.pocketexpenses.viewholders.AccountViewHolder;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountsListFragment extends Fragment {

    private FragmentAccountsListBinding binding;
    private RecyclerView oAccountsRV;
    private AccountTypeViewModel oViewModel;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public AccountsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment AccountsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountsListFragment newInstance() {
        AccountsListFragment fragment = new AccountsListFragment();
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
        // Inflate the layout for this fragment
        binding = FragmentAccountsListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        oAccountsRV = binding.recyclerAccounts;

        oViewModel = new ViewModelProvider(this).get(AccountTypeViewModel.class);

        AccountsAdapter adapter = new AccountsAdapter(oViewModel);
        oAccountsRV.setAdapter(adapter);
        oAccountsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        oViewModel.getAllAccountTypesWithAccounts().observe(getViewLifecycleOwner(), new Observer<List<AccountTypeWithAccounts>>() {
            @Override
            public void onChanged(@Nullable final List<AccountTypeWithAccounts> accTypesWithAccounts) {
                // Update the cached copy of the words in the adapter.
                adapter.setData(accTypesWithAccounts);
            }
        });
    }
}