package com.example.pocketexpenses.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.databinding.FragmentStatisticsBinding;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.TransactionDirection;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.TransactionType;
import com.example.pocketexpenses.entities.relationships.TransactionDirectionWithTypesAndSubtypes;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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


    // Za Expense vs Income
    private double expenses = 0;
    private double income = 0;

    // Otdelno za drugite 2, inache nqma kak da sinhronizirame 2ta metoda koi purvi shte zavurshi izpulenie
    private double m_dExpenses = 0;
    private double m_dIncome = 0;

    private PieChart pieChart1;
    private PieChart pieChart2;
    private PieChart pieChart3;

    private ArrayList<PieEntry> m_entriesExpenses = new ArrayList<>();
    private ArrayList<PieEntry> m_entriesIncome = new ArrayList<>();
    private ArrayList<PieEntry> m_entriesIncomeVsExpenses = new ArrayList<>();

    private LifecycleOwner owner;
   // String addedTypesString = null;
    private HashMap<String, Double> incomeTypeWithSumMap = null;
    private HashMap<String, Double> expensesTypeWithSumMap = null;
    private Double oldSum;


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
        pieChart1 = view.findViewById(R.id.activity_main_piechart3);
        pieChart2 = view.findViewById(R.id.activity_main_piechart2);
        pieChart3 = view.findViewById(R.id.activity_main_piechart1);

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

        owner = getViewLifecycleOwner();

        // Pie Chart 1
        calculateExpensesVsIncome();

        // Pie Chart 2 & 3 - vika se shtom prikluchi gorniq metod
        //calculateExpensesAndIncomes();

    }



    private void calculateExpensesVsIncome() {
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
                                                                calculateExpensesAndIncomes();  /// !!! income i expenses double sa veche sumirani
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
    }



    private void calculateExpensesAndIncomes() {

        m_entriesIncome.clear();
        m_entriesExpenses.clear();

        incomeTypeWithSumMap = new HashMap<>();
        expensesTypeWithSumMap = new HashMap<>();

        oTransactionViewModel.getAllTransactions().observe(owner, new Observer<List<Transaction>>() {
            @Override
            public void onChanged(@Nullable final List<Transaction> oListTransactions) {
                if (oListTransactions != null) {
                    m_oListTransactions = oListTransactions;

                    for(Transaction oTransaction : m_oListTransactions)
                    {

                        oTransactionTypeViewModel.getAllTransactionSubtypes().observe(owner, new Observer<List<TransactionSubtype>>() {
                            @Override
                            public void onChanged(@Nullable final List<TransactionSubtype> oListTransactionSubtypes) {

                                if(oListTransactionSubtypes != null){
                                    m_oListTransactionSubtypes = oListTransactionSubtypes;

                                    for(TransactionSubtype oTransactionSubtype : m_oListTransactionSubtypes)
                                    {
                                        if(oTransactionSubtype.getId() == oTransaction.getTransactionSubtypeId())
                                        {
                                            oTransactionTypeViewModel.getTransactionTypeByID(oTransactionSubtype.getTransactionTypeId()).observe(owner, new Observer<TransactionType>() {
                                                @Override
                                                public void onChanged(TransactionType transactionType) {
                                                    if (transactionType != null) {
                                                        oTransactionTypeViewModel.getTransactionDirectionByID(transactionType.getTransactionDirectionId()).observe(owner, new Observer<TransactionDirection>() {
                                                            @Override
                                                            public void onChanged(TransactionDirection transactionDirection) {
                                                                if (transactionDirection != null) {
                                                                    int coefficient = transactionDirection.getCoefficient();
                                                                    String transactionTypeName = transactionType.getName();

                                                                    if (coefficient > 0)
                                                                    {
                                                                        if(incomeTypeWithSumMap.containsKey(transactionTypeName)) {
                                                                            Double oldValue = incomeTypeWithSumMap.get(transactionTypeName);
                                                                            oldValue += oTransaction.getSum();
                                                                            incomeTypeWithSumMap.put(transactionTypeName, oldValue);
                                                                        }
                                                                        else {
                                                                            incomeTypeWithSumMap.put(transactionTypeName, Double.valueOf(oTransaction.getSum()));
                                                                        }
                                                                    }
                                                                    else
                                                                    {
                                                                        if(expensesTypeWithSumMap.containsKey(transactionTypeName)) {
                                                                            Double oldValue = expensesTypeWithSumMap.get(transactionTypeName);
                                                                            oldValue += oTransaction.getSum();
                                                                            expensesTypeWithSumMap.put(transactionTypeName, oldValue);
                                                                        }
                                                                        else {
                                                                            expensesTypeWithSumMap.put(transactionTypeName, Double.valueOf(oTransaction.getSum()));
                                                                        }
                                                                    }


                                                                    if(oTransaction == m_oListTransactions.get(m_oListTransactions.size() - 1)) {
                                                                        setupPieChartExpense();
                                                                        loadPieChartDataExpense();

                                                                        setupPieChartIncome();
                                                                        loadPieChartDataIncome();
                                                                    }
                                                                }
                                                            }
                                                        });
                                                    }
                                                }
                                            });
                                        }


                                    }
                                }
                            }
                        });
                    }
                }
                }
            });
    }



    private void setupPieChartExpense() {

        for (Map.Entry<String, Double> entry : expensesTypeWithSumMap.entrySet())
            m_entriesExpenses.add(new PieEntry((float) ((entry.getValue() * 100) / expenses), entry.getKey()));


        pieChart1.setDrawHoleEnabled(true);
        pieChart1.setUsePercentValues(true);
        pieChart1.setEntryLabelTextSize(12);
        pieChart1.setEntryLabelColor(Color.BLACK);
        pieChart1.setCenterText("Expenses Categories");
        pieChart1.setCenterTextSize(22);
        pieChart1.getDescription().setEnabled(false);

        Legend l = pieChart1.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextSize(12);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartDataExpense() {

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(m_entriesExpenses, "Categories");
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

    private void setupPieChartIncome() {

        for (Map.Entry<String, Double> entry : incomeTypeWithSumMap.entrySet())
            m_entriesIncome.add(new PieEntry((float) ((entry.getValue() * 100) / income), entry.getKey()));

        pieChart2.setDrawHoleEnabled(true);
        pieChart2.setUsePercentValues(true);
        pieChart2.setEntryLabelTextSize(12);
        pieChart2.setEntryLabelColor(Color.BLACK);
        pieChart2.setCenterText("Income Categories");
        pieChart2.setCenterTextSize(22);
        pieChart2.getDescription().setEnabled(false);

        Legend l = pieChart2.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextSize(12);
        l.setDrawInside(false);
        l.setEnabled(true);
    }

    private void loadPieChartDataIncome() {

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(m_entriesIncome, "Categories");
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
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setTextSize(12);
        l.setDrawInside(false);
        l.setEnabled(true);
    }


    private void loadPieChartDataExpense3() {
        m_entriesIncomeVsExpenses.clear();

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
        m_entriesIncomeVsExpenses.add(new PieEntry(percentageIncome.floatValue(), "Income"));
        m_entriesIncomeVsExpenses.add(new PieEntry(percentageExpenses.floatValue(), "Expenses"));

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(m_entriesIncomeVsExpenses, "Transaction Direction");
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
}