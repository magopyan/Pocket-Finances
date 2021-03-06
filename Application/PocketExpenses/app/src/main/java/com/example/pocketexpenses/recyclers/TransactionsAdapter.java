package com.example.pocketexpenses.recyclers;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocketexpenses.R;
import com.example.pocketexpenses.activities.TransactionInputActivity;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.TransactionDirection;
import com.example.pocketexpenses.entities.TransactionType;
import com.example.pocketexpenses.viewholders.AccountTypeViewHolder;
import com.example.pocketexpenses.viewmodels.AccountTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionInputViewModel;
import com.example.pocketexpenses.viewmodels.TransactionTypeViewModel;
import com.example.pocketexpenses.viewmodels.TransactionViewModel;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.viewholders.TransactionViewHolder;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionViewHolder> {

    private TransactionViewModel oTransactionViewModel;
    private TransactionInputViewModel oTransactionInputVM;
    private AccountTypeViewModel oAccountTypeVM;
    private TransactionTypeViewModel oTransactionTypeVM;

    private List<Transaction> oListTransactions;
    private List<Account> oListAccounts;
    private List<TransactionSubtype> oListTransactionSubtypes;
    private List<TransactionType> oListTransactionType;
    private List<TransactionDirection> oListTransactionDirection;

    public TransactionsAdapter()
    { }

    public TransactionsAdapter(TransactionViewModel oTransactionViewModel, TransactionInputViewModel oTransactionInputVM,
                               AccountTypeViewModel oAccountTypeVM, TransactionTypeViewModel oTransactionTypeVM) {
        this.oTransactionViewModel = oTransactionViewModel;
        this.oTransactionInputVM = oTransactionInputVM;
        this.oAccountTypeVM = oAccountTypeVM;
        this.oTransactionTypeVM = oTransactionTypeVM;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_viewholder, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position)
    {
        Transaction oTransaction = oListTransactions.get(position);

        String date = oTransaction.getDate();
        Date currentDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = df.format(currentDate);
        if(date.equals(formattedDate))
            holder.setTvDate("Today");
        else
            holder.setTvDate(date);


//        for(TransactionSubtype transactionSubtype : oListTransactionSubtypes)
//        {
//            for(TransactionType transactionType : oListTransactionType)
//            {
//                for(TransactionDirection transactionDirection : oListTransactionDirection)
//                {
//                    if(transactionSubtype.getId() == oTransaction.getTransactionSubtypeId()
//                        && transactionSubtype.getTransactionTypeId() == transactionType.getId()
//                        && transactionType.getTransactionDirectionId() == transactionDirection.getId()
//                        && transactionDirection.getCoefficient() > 0)
//                    {
//                        holder.setTvTransactionSumColor(ContextCompat.getColor(holder.getTransactionSumTextView().getContext(), R.color.green));
//                    }
//                    else
//                        holder.setTvTransactionSumColor(ContextCompat.getColor(holder.getTransactionSumTextView().getContext(), R.color.red));
//                }
//            }
//        }

        double transactionSum = oTransaction.getSum();
        if(transactionSum < 0)
            holder.setTvTransactionSumColor(ContextCompat.getColor(holder.getTransactionSumTextView().getContext(), R.color.dark_red));
        else if(transactionSum > 0)
            holder.setTvTransactionSumColor(ContextCompat.getColor(holder.getTransactionSumTextView().getContext(), R.color.dark_green));

        holder.setTvTransactionSum(String.valueOf(transactionSum));
        holder.setTvNote(oTransaction.getNote());
        holder.setImage(oTransaction.getImageId());

        if(oListAccounts != null) {
            for(Account oAccount : oListAccounts){
                if(oAccount.getId() == oTransaction.getAccountId())
                    holder.setTvAccountName(oAccount.getName());
            }
        }
        if(oListTransactionSubtypes != null) {
            for(TransactionSubtype oTransactionSubtype : oListTransactionSubtypes){
                if(oTransactionSubtype.getId() == oTransaction.getTransactionSubtypeId())
                    holder.setTvTransactionType(oTransactionSubtype.getName());
            }
        }


        // Popup menu
        holder.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                PopupMenu popup = new PopupMenu(v.getContext(), holder.itemView);
                popup.inflate(R.menu.on_long_click_menu_no_edit);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
//                            case R.id.edit:
//                                boolean isExpense = setTransactionInputData(v, oTransaction);
//                                Intent intent = new Intent(v.getContext(), TransactionInputActivity.class);
//                                intent.putExtra("topBarTitle", "Edit Expense"); // ???
//                                // How to find if expense or Income ???
//                                intent.putExtra("Edit Transaction", oTransaction);
//                                return true;
                            case R.id.delete:
                                oTransactionViewModel.deleteTransaction(oTransaction);
                                return true;
                            default:
                                return false;
                        }
                    }
                });

                Object menuHelper;
                Class[] argTypes;
                try {
                    Field fMenuHelper = PopupMenu.class.getDeclaredField("mPopup");
                    fMenuHelper.setAccessible(true);
                    menuHelper = fMenuHelper.get(popup);
                    argTypes = new Class[]{boolean.class};
                    menuHelper.getClass().getDeclaredMethod("setForceShowIcon", argTypes).invoke(menuHelper, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                popup.show();
                return true;
            }
        });
    }


//    private boolean setTransactionInputData(View view, Transaction transaction)
//    {
//        Account account = oAccountTypeVM.getAccountByID(transaction.getAccountId());
//        oTransactionInputVM.setAccount(account);
//        oTransactionInputVM.setTransaction(transaction);
//        return true;
//    }

    @Override
    public int getItemCount() {
        if(oListTransactions != null && oListTransactions.size() != 0) {
            return oListTransactions.size();
        }
        else return 0;
    }

    public void setTransactionSubtypesData(List<TransactionSubtype> oListTransactionSubtypes){
        this.oListTransactionSubtypes = oListTransactionSubtypes;
        notifyDataSetChanged();
    }
    public void setAccountsData(List<Account> oListAccounts){
        this.oListAccounts = oListAccounts;
        notifyDataSetChanged();
    }
    public void setTransactionsData(List<Transaction> oListTransactions){
        this.oListTransactions = oListTransactions;
        notifyDataSetChanged();
    }

    public void setTransactionTypeData(List<TransactionType> oListTransactionType){
        this.oListTransactionType = oListTransactionType;
        notifyDataSetChanged();
    }

    public void setTransactionDirectionData(List<TransactionDirection> oListTransactionDirection){
        this.oListTransactionDirection = oListTransactionDirection;
        notifyDataSetChanged();
    }

}
