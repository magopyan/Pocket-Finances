package com.example.pocketexpenses.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.pocketexpenses.R;
import com.example.pocketexpenses.activities.ChooseAccountActivity;
import com.example.pocketexpenses.databinding.FragmentAccountsListBinding;
import com.example.pocketexpenses.databinding.FragmentStatisticsBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.recyclers.TransactionsAdapter;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionInputViewModel;
import com.example.pocketexpenses.viewmodels.TransactionTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    private FragmentStatisticsBinding binding;
    private AnyChartView oAnyChartView1;
    private AnyChartView oAnyChartView2;
    private TransactionViewModel oTransactionViewModel;
    private TransactionTypeViewModel oTransactionTypeViewModel;
    private AccountTypeViewModel oAccountTypeViewModel;
    private List<Transaction> m_oListTransactions;
    private List<TransactionSubtype> m_oListTransactionSubtypes;
    private List<Account> m_oListAccounts;
    private Account oAccount;
    private Button createCharts;
    private TransactionInputViewModel oTransactionInputVM;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters


    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
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
        binding = FragmentStatisticsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        oTransactionInputVM = new ViewModelProvider(requireActivity()).get(TransactionInputViewModel.class);

        oTransactionInputVM.getAccount().observe(getViewLifecycleOwner(), item -> {
            if(item != null) {
                binding.accountTextField2.setText(item.getName());
                oAccount = item;
            }
        });

        binding.accountTextField2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChooseAccountActivity.class);
                startActivity(intent);
            }
        });

        oAnyChartView1 = binding.diagram1;
        oAnyChartView2 = binding.diagram2;

        oTransactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        oTransactionTypeViewModel = new ViewModelProvider(this).get(TransactionTypeViewModel.class);
        oAccountTypeViewModel = new ViewModelProvider(this).get(AccountTypeViewModel.class);

        oTransactionViewModel.getAllTransactions().observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable final List<Transaction> oListTransactions) {
                m_oListTransactions = oListTransactions;
            }
        });

        oTransactionTypeViewModel.getAllTransactionSubtype().observe(getViewLifecycleOwner(), new Observer<List<TransactionSubtype>>() {
            @Override
            public void onChanged(@Nullable final List<TransactionSubtype> oListTransactionSubtypes) {
                m_oListTransactionSubtypes = oListTransactionSubtypes;
            }
        });

        oAccountTypeViewModel.getAllAccounts().observe(getViewLifecycleOwner(), new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable final List<Account> oListAccounts) {
                m_oListAccounts = oListAccounts;
            }
        });

        createCharts = binding.createChartsButton;
        createCharts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(oAccount.getId() > 0)
                {
                    setupPieChart1();
                    setupPieChart2();
                }
                else
                {

                }
            }
        });

    }

    private void setupPieChart1() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        //Income

        for(int i=0;i<m_oListTransactions.size();i++)
        {
            Transaction oTransaction = m_oListTransactions.get(i);
            for(int j=0;j<m_oListTransactionSubtypes.size();j++)
            {
                TransactionSubtype oTransactionSubtype = m_oListTransactionSubtypes.get(j);
                if(oTransaction.getAccountId() == oAccount.getId() && oTransactionSubtype.getId() == oTransaction.getTransactionSubtypeId())
                {
                    dataEntries.add(new ValueDataEntry(oTransactionSubtype.getName(), oTransaction.getSum()));
                }
            }
        }

        pie.data(dataEntries);
        oAnyChartView1.setChart(pie);
    }

    private void setupPieChart2() {
        Pie pie = AnyChart.pie();
        List<DataEntry> dataEntries = new ArrayList<>();

        // Expenses

        pie.data(dataEntries);
        oAnyChartView2.setChart(pie);
    }
}