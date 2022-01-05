package com.example.pocketexpenses.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
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
import com.example.pocketexpenses.databinding.FragmentStatisticsBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.TransactionDirection;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.TransactionType;
import com.example.pocketexpenses.entities.relationships.TransactionDirectionWithTypesAndSubtypes;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionInputViewModel;
import com.example.pocketexpenses.viewmodels.TransactionTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionViewModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    private FragmentStatisticsBinding binding;
    private TransactionViewModel oTransactionViewModel;
    private TransactionTypeViewModel oTransactionTypeViewModel;

    private AccountTypeViewModel oAccountTypeViewModel;
    private List<Transaction> m_oListTransactions;
    private List<TransactionSubtype> m_oListTransactionSubtypes;
    private List<Account> m_oListAccounts;

    private List<TransactionDirectionWithTypesAndSubtypes> m_oListTranDirWithTypesAndSubtypes;
    private List<TransactionDirection> m_oListTransactionDirections;
    private List<TransactionType> m_oListTransactionTypes;

    private double expenses = 0;
    private double income = 0;

    private PieChart pieChart1;
    private PieChart pieChart2;
    private PieChart pieChart3;

    private ArrayList<PieEntry> m_entriesExpense = new ArrayList<>();
    private ArrayList<PieEntry> m_entriesIncome = new ArrayList<>();

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

        // ne moje li s binding vmesto findViewById?
        pieChart1 = view.findViewById(R.id.activity_main_piechart1);
        pieChart2 = view.findViewById(R.id.activity_main_piechart2);
        pieChart3 = view.findViewById(R.id.activity_main_piechart3);

        oTransactionViewModel = new ViewModelProvider(this).get(TransactionViewModel.class);
        oTransactionTypeViewModel = new ViewModelProvider(this).get(TransactionTypeViewModel.class);
        oAccountTypeViewModel = new ViewModelProvider(this).get(AccountTypeViewModel.class);

        oTransactionTypeViewModel.getAllTransactionSubtypes().observe(getViewLifecycleOwner(), new Observer<List<TransactionSubtype>>() {
            @Override
            public void onChanged(@Nullable final List<TransactionSubtype> oListTransactionSubtypes) {
                m_oListTransactionSubtypes = oListTransactionSubtypes;
            }
        });

        oTransactionTypeViewModel.getAllTransactionDirectionWithTypesAndSubtypes().observe(getViewLifecycleOwner(), new Observer<List<TransactionDirectionWithTypesAndSubtypes>>() {
            @Override
            public void onChanged(List<TransactionDirectionWithTypesAndSubtypes> transactionDirectionWithTypesAndSubtypes) {
                m_oListTranDirWithTypesAndSubtypes = transactionDirectionWithTypesAndSubtypes;
            }
        });

        oTransactionTypeViewModel.getAllTransactionDirections().observe(getViewLifecycleOwner(), new Observer<List<TransactionDirection>>() {
            @Override
            public void onChanged(List<TransactionDirection> transactionDirections) {
                m_oListTransactionDirections = transactionDirections;
            }
        });

        oTransactionTypeViewModel.getAllTransactionTypes().observe(getViewLifecycleOwner(), new Observer<List<TransactionType>>() {
            @Override
            public void onChanged(List<TransactionType> transactionTypes) {
                m_oListTransactionTypes = transactionTypes;
            }
        });

        oAccountTypeViewModel.getAllAccounts().observe(getViewLifecycleOwner(), new Observer<List<Account>>() {
            @Override
            public void onChanged(@Nullable final List<Account> oListAccounts) {
                m_oListAccounts = oListAccounts;
            }
        });

        LifecycleOwner owner = getViewLifecycleOwner();
        oTransactionViewModel.getAllTransactions().observe(getViewLifecycleOwner(), new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable final List<Transaction> oListTransactions) {
                if(oListTransactions != null) {
                    m_oListTransactions = oListTransactions;
                    for(Transaction transaction : m_oListTransactions)
                    {
                        oTransactionTypeViewModel.getTransactionSubtypeByID(transaction.getTransactionSubtypeId()).observe(owner, new Observer<TransactionSubtype>() {
                            @Override
                            public void onChanged(TransactionSubtype transactionSubtype) {
                                if (transactionSubtype != null) {
                                    oTransactionTypeViewModel.getTransactionTypeByID(transactionSubtype.getTransactionTypeId()).observe(owner, new Observer<TransactionType>() {
                                        @Override
                                        public void onChanged(TransactionType transactionType) {
                                            if (transactionType != null) {
                                                oTransactionTypeViewModel.getTransactionDirectionByID(transactionType.getTransactionDirectionId()).observe(owner, new Observer<TransactionDirection>() {
                                                    @Override
                                                    public void onChanged(TransactionDirection transactionDirection) {
                                                        if (transactionDirection != null) {
                                                            int coefficient = transactionDirection.getCoefficient();
                                                            if (coefficient > 0)
                                                                income += transaction.getSum();
                                                            else
                                                                expenses += transaction.getSum();
                                                            // ako tova e poslednata tranzakciq ot spisuka, t.e veche income i expense sa != 0
                                                            if(transaction == m_oListTransactions.get(m_oListTransactions.size() - 1)) {
                                                                setupPieChartExpense3();
                                                                loadPieChartDataExpense3();
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            }
        });

        setupPieChartExpense();
        loadPieChartDataExpense();

        setupPieChartExpense2();
        loadPieChartDataExpense2();

        // Vikat se v ujasniq nested loop, sled kato se otchete sumata na poslednata tranzakciq ot spisuka
        //setupPieChartExpense3();
        //loadPieChartDataExpense3();
    }

    private void setupPieChart1() {
        for(int i=0;i<m_oListTransactions.size();i++)
        {
            Transaction oTransaction = m_oListTransactions.get(i);
            for(int j=0;j<m_oListTransactionSubtypes.size();j++)
            {
                TransactionSubtype oTransactionSubtype = m_oListTransactionSubtypes.get(j);
                if(/*oTransaction.getAccountId() == oAccount.getId() &&*/ oTransactionSubtype.getId() == oTransaction.getTransactionSubtypeId())
                {
                    m_entriesExpense.add(new PieEntry((float)oTransaction.getSum(), oTransactionSubtype.getName()));
                }
            }
        }
    }

    private void setupPieChartExpense() {
        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setUsePercentValues(true);
        pieChart1.setEntryLabelTextSize(12);
        pieChart1.setEntryLabelColor(Color.BLACK);
        pieChart1.setCenterText("Expenses total");
        pieChart1.setCenterTextSize(24);
        pieChart1.getDescription().setEnabled(false);

        Legend l = pieChart1.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartDataExpense() {
        m_entriesExpense.clear();
        m_entriesExpense.add(new PieEntry(0.15f, "Medical"));
        m_entriesExpense.add(new PieEntry(0.10f, "Entertainment"));
        m_entriesExpense.add(new PieEntry(0.25f, "Electricity and Gas"));
        m_entriesExpense.add(new PieEntry(0.3f, "Housing"));
        m_entriesExpense.add(new PieEntry(0.2f, "Food & Dining"));

        //setupPieChart1();

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(m_entriesExpense, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart1));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart1.setData(data);
        pieChart1.invalidate();

        pieChart1.animateY(1400, Easing.EaseInOutQuad);
    }

    private void setupPieChartExpense2() {
        pieChart2.setDrawHoleEnabled(true);
        pieChart2.setUsePercentValues(true);
        pieChart2.setEntryLabelTextSize(12);
        pieChart2.setEntryLabelColor(Color.BLACK);
        pieChart2.setCenterText("Expenses total");
        pieChart2.setCenterTextSize(24);
        pieChart2.getDescription().setEnabled(false);

        Legend l = pieChart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartDataExpense2() {
        m_entriesExpense.clear();
        m_entriesExpense.add(new PieEntry(0.15f, "Medical"));
        m_entriesExpense.add(new PieEntry(0.10f, "Entertainment"));
        m_entriesExpense.add(new PieEntry(0.25f, "Electricity and Gas"));
        m_entriesExpense.add(new PieEntry(0.3f, "Housing"));
        m_entriesExpense.add(new PieEntry(0.2f, "Food & Dining"));

        //setupPieChart1();

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(m_entriesExpense, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart2));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart2.setData(data);
        pieChart2.invalidate();

        pieChart2.animateY(1400, Easing.EaseInOutQuad);
    }

    private void setupPieChartExpense3() {
        pieChart3.setDrawHoleEnabled(true);
        pieChart3.setUsePercentValues(true);
        pieChart3.setEntryLabelTextSize(12);
        pieChart3.setEntryLabelColor(Color.BLACK);
        pieChart3.setCenterText("Income vs Expenses");
        pieChart3.setCenterTextSize(24);
        pieChart3.getDescription().setEnabled(false);

        Legend l = pieChart3.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(true);
    }


    private void loadPieChartDataExpense3() {
        m_entriesExpense.clear();

        // Primer kak da izchislqvame procenti i da convertirame polucheniq double v float
//        double total = 673.12;
//        double expenses = 273.12;
//        double income = 400.00;
//        Double percentageExpenses = Double.valueOf(exp * 100 / total);
//        Double percentageIncome = Double.valueOf(inc * 100 / total);
//        m_entriesExpense.add(new PieEntry(percentageIncome.floatValue(), "Income"));
//        m_entriesExpense.add(new PieEntry(percentageExpenses.floatValue(), "Expenses"));

        double total = expenses + income;
        Double percentageExpenses = Double.valueOf(expenses * 100 / total);
        Double percentageIncome = Double.valueOf(income * 100 / total);
        m_entriesExpense.add(new PieEntry(percentageIncome.floatValue(), "Income"));
        m_entriesExpense.add(new PieEntry(percentageExpenses.floatValue(), "Expenses"));

        //setupPieChart1();

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(m_entriesExpense, "Expense Category");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);
        data.setValueFormatter(new PercentFormatter(pieChart3));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart3.setData(data);
        pieChart3.invalidate();

        pieChart3.animateY(1400, Easing.EaseInOutQuad);
    }

//    private void loadPieChartDataExpense3() {
//        m_entriesExpense.clear();
//        m_entriesExpense.add(new PieEntry(0.15f, "Medical"));
//        m_entriesExpense.add(new PieEntry(0.10f, "Entertainment"));
//        m_entriesExpense.add(new PieEntry(0.25f, "Electricity and Gas"));
//        m_entriesExpense.add(new PieEntry(0.3f, "Housing"));
//        m_entriesExpense.add(new PieEntry(0.2f, "Food & Dining"));
//
//        //setupPieChart1();
//
//        ArrayList<Integer> colors = new ArrayList<>();
//        for (int color: ColorTemplate.MATERIAL_COLORS) {
//            colors.add(color);
//        }
//
//        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
//            colors.add(color);
//        }
//
//        PieDataSet dataSet = new PieDataSet(m_entriesExpense, "Expense Category");
//        dataSet.setColors(colors);
//
//        PieData data = new PieData(dataSet);
//        data.setDrawValues(true);
//        data.setValueFormatter(new PercentFormatter(pieChart3));
//        data.setValueTextSize(12f);
//        data.setValueTextColor(Color.BLACK);
//
//        pieChart3.setData(data);
//        pieChart3.invalidate();
//
//        pieChart3.animateY(1400, Easing.EaseInOutQuad);
//    }
}