package com.example.pocketexpenses.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pocketexpenses.dao.AccountTypeDao;
import com.example.pocketexpenses.dao.TransactionDao;
import com.example.pocketexpenses.dao.TransactionTypeDao;
import com.example.pocketexpenses.entities.Account;
import com.example.pocketexpenses.entities.AccountType;
import com.example.pocketexpenses.entities.Transaction;
import com.example.pocketexpenses.entities.TransactionDirection;
import com.example.pocketexpenses.entities.TransactionSubtype;
import com.example.pocketexpenses.entities.TransactionType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@Database(
        entities = { Account.class, AccountType.class, Transaction.class, TransactionDirection.class, TransactionType.class, TransactionSubtype.class},
        version = 2, exportSchema = false) // ima li smisul exportSchema da e true? kakvi drugi argumenti da priema?
public abstract class AppDatabase extends RoomDatabase {


    // Class Variables
    ////////////////////////////////////////////////////////
    private static volatile AppDatabase instance;
    private static final String DB_NAME = "Pocket_Expenses";

    // DAOs
    public abstract AccountTypeDao accountTypeDao();
    public abstract TransactionDao transactionDao();
    public abstract TransactionTypeDao transactionTypeDao();



    // Class Methods
    ////////////////////////////////////////////////////////

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, AppDatabase.class, DB_NAME)
                    .addCallback(roomCallBack)
                    .build();  // dobavi opcii?
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallBack = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };


    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private AccountTypeDao oAccountTypeDao;
        private TransactionTypeDao oTranTypeDao;
        private TransactionDao oTransactionDao;

        public PopulateDbAsyncTask(AppDatabase oAppDatabase) {
            this.oAccountTypeDao = oAppDatabase.accountTypeDao();
            this.oTranTypeDao = oAppDatabase.transactionTypeDao();
            this.oTransactionDao = oAppDatabase.transactionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            insertAccountsAndAccountTypes();
            insertTransactionDirectionsAndTypes();
            insertTransactionSubtypes();

            insertSampleAccountsAndTransactions();

            return null;
        }

        private void insertAccountsAndAccountTypes() {
            // Account Types
            AccountType cash = new AccountType("Cash");                     // id=1
            AccountType creditCard = new AccountType("Credit Card");        // id=2
            AccountType debitCard = new AccountType("Debit Card");          // id=3
            AccountType discountCard = new AccountType("Discount Card");    // id=4
            oAccountTypeDao.insertAccountType(cash);
            oAccountTypeDao.insertAccountType(creditCard);
            oAccountTypeDao.insertAccountType(debitCard);
            oAccountTypeDao.insertAccountType(discountCard);

            // Accounts
            Account cashStartingAccount = new Account(0, "Cash", 1); // id=1
            oAccountTypeDao.insertAccount(cashStartingAccount);
        }


        private void insertTransactionDirectionsAndTypes() {
            // Income & Expenses
            TransactionDirection income = new TransactionDirection("Income", 1);      // id=1
            TransactionDirection expense = new TransactionDirection("Expense", -1);   // id=2
            oTranTypeDao.insertTransactionDirection(income);
            oTranTypeDao.insertTransactionDirection(expense);

            // Income Types
            TransactionType wage = new TransactionType("Wage", 1);                    // id=1
            TransactionType rentalIncome = new TransactionType("Rental Income", 1);   // id=2
            TransactionType interest = new TransactionType("Interest/Dividend", 1);   // id=3
            TransactionType coupon = new TransactionType("Coupon/Voucher", 1);        // id=4
            TransactionType gift = new TransactionType("Gift", 1);                    // id=5
            TransactionType welfare = new TransactionType("Welfare", 1);              // id=6
            TransactionType refund = new TransactionType("Refund", 1);                // id=7
            TransactionType otherIncome = new TransactionType("Other", 1);            // id=8
            oTranTypeDao.insertTransactionType(wage);
            oTranTypeDao.insertTransactionType(rentalIncome);
            oTranTypeDao.insertTransactionType(interest);
            oTranTypeDao.insertTransactionType(coupon);
            oTranTypeDao.insertTransactionType(gift);
            oTranTypeDao.insertTransactionType(welfare);
            oTranTypeDao.insertTransactionType(refund);
            oTranTypeDao.insertTransactionType(otherIncome);

            // Expenses Types
            TransactionType foodAndDrink= new TransactionType("Food & Drinks", 2);                 // id=9
            TransactionType shopping = new TransactionType("Shopping", 2);                         // id=10
            TransactionType entertainment = new TransactionType("Entertainment", 2);               // id=11
            TransactionType transport = new TransactionType("Transportation", 2);                  // id=12
            TransactionType vehicle = new TransactionType("Vehicle", 2);                           // id=13
            TransactionType taxes = new TransactionType("Taxes & Charges", 2);                     // id=14
            TransactionType housing = new TransactionType("Housing", 2);                           // id=15
            TransactionType investments = new TransactionType("Investments", 2);                   // id=16
            TransactionType otherExpenses = new TransactionType("Other", 2);                       // id=17
            oTranTypeDao.insertTransactionType(foodAndDrink);
            oTranTypeDao.insertTransactionType(shopping);
            oTranTypeDao.insertTransactionType(entertainment);
            oTranTypeDao.insertTransactionType(transport);
            oTranTypeDao.insertTransactionType(vehicle);
            oTranTypeDao.insertTransactionType(taxes);
            oTranTypeDao.insertTransactionType(housing);
            oTranTypeDao.insertTransactionType(investments);
            oTranTypeDao.insertTransactionType(otherExpenses);
        }


        private void insertTransactionSubtypes() {
            // Food & Drinks
            TransactionSubtype groceries = new TransactionSubtype("Groceries", 1);                  // id=1
            TransactionSubtype restaurant = new TransactionSubtype("Restaurant & Fast Food", 1);    // id=2
            TransactionSubtype bar = new TransactionSubtype("Bar & Cafe", 1);                       // id=3
            TransactionSubtype restricted = new TransactionSubtype("Alcohol & Cigarettes", 1);      // id=4
            oTranTypeDao.insertTransactionSubtype(groceries);
            oTranTypeDao.insertTransactionSubtype(restaurant);
            oTranTypeDao.insertTransactionSubtype(bar);
            oTranTypeDao.insertTransactionSubtype(restricted);

            // Shopping
            TransactionSubtype clothes = new TransactionSubtype("Clothes & Shoes", 2);              // id=5
            TransactionSubtype cosmetics = new TransactionSubtype("Cosmetics", 2);                  // id=6
            TransactionSubtype medicine = new TransactionSubtype("Medicine", 2);                    // id=7
            TransactionSubtype electronics = new TransactionSubtype("Electronics", 2);              // id=8
            TransactionSubtype gift = new TransactionSubtype("Gifts", 2);                           // id=9
            TransactionSubtype furniture = new TransactionSubtype("Furniture & Garden", 2);         // id=10
            TransactionSubtype accessories = new TransactionSubtype("Accessories", 2);              // id=11
            TransactionSubtype kids = new TransactionSubtype("Kids", 2);                            // id=12
            TransactionSubtype pets = new TransactionSubtype("Pets", 2);                            // id=13
            oTranTypeDao.insertTransactionSubtype(clothes);
            oTranTypeDao.insertTransactionSubtype(cosmetics);
            oTranTypeDao.insertTransactionSubtype(medicine);
            oTranTypeDao.insertTransactionSubtype(electronics);
            oTranTypeDao.insertTransactionSubtype(gift);
            oTranTypeDao.insertTransactionSubtype(furniture);
            oTranTypeDao.insertTransactionSubtype(accessories);
            oTranTypeDao.insertTransactionSubtype(kids);
            oTranTypeDao.insertTransactionSubtype(pets);

            // Entertainment
            TransactionSubtype events = new TransactionSubtype("Culture & Sport Events", 3);        // id=14
            TransactionSubtype travel = new TransactionSubtype("Holidays & Trips", 3);              // id=15
            TransactionSubtype sport = new TransactionSubtype("Sport & Fitness", 3);                // id=16
            TransactionSubtype hobbies = new TransactionSubtype("Hobbies", 3);                      // id=17
            TransactionSubtype subscriptions = new TransactionSubtype("Subscriptions", 3);          // id=18
            TransactionSubtype books = new TransactionSubtype("Books & Newsletters", 3);            // id=19
            TransactionSubtype education = new TransactionSubtype("Education & Courses", 3);        // id=20
            TransactionSubtype charity = new TransactionSubtype("Charity", 3);                      // id=21
            TransactionSubtype hazart = new TransactionSubtype("Lottery & Gambling", 3);            // id=22
            oTranTypeDao.insertTransactionSubtype(events);
            oTranTypeDao.insertTransactionSubtype(travel);
            oTranTypeDao.insertTransactionSubtype(sport);
            oTranTypeDao.insertTransactionSubtype(hobbies);
            oTranTypeDao.insertTransactionSubtype(subscriptions);
            oTranTypeDao.insertTransactionSubtype(books);
            oTranTypeDao.insertTransactionSubtype(education);
            oTranTypeDao.insertTransactionSubtype(charity);
            oTranTypeDao.insertTransactionSubtype(hazart);

            // Transport
            TransactionSubtype publicTransport = new TransactionSubtype("Public Transport", 4);     // id=23
            TransactionSubtype taxi = new TransactionSubtype("Taxi", 4);                            // id=24
            TransactionSubtype business = new TransactionSubtype("Business Trips", 4);              // id=25
            TransactionSubtype flights = new TransactionSubtype("Flights", 4);                      // id=26
            TransactionSubtype sea = new TransactionSubtype("Sailing", 4);                          // id=27
            TransactionSubtype hamali = new TransactionSubtype("Furniture Moving", 4);              // id=28
            TransactionSubtype spodeleno = new TransactionSubtype("Shared Travel", 4);              // id=29
            oTranTypeDao.insertTransactionSubtype(publicTransport);
            oTranTypeDao.insertTransactionSubtype(taxi);
            oTranTypeDao.insertTransactionSubtype(business);
            oTranTypeDao.insertTransactionSubtype(flights);
            oTranTypeDao.insertTransactionSubtype(sea);
            oTranTypeDao.insertTransactionSubtype(hamali);
            oTranTypeDao.insertTransactionSubtype(spodeleno);

            // Vehicle
            TransactionSubtype fuel = new TransactionSubtype("Fuel", 5);                            // id=30
            TransactionSubtype parking = new TransactionSubtype("Parking", 5);                      // id=31
            TransactionSubtype leasing = new TransactionSubtype("Leasing", 5);                      // id=32
            TransactionSubtype insurance = new TransactionSubtype("Vehicle Insurance", 5);          // id=33
            TransactionSubtype remont = new TransactionSubtype("Maintenance & Repairs", 5);         // id=34
            TransactionSubtype rental = new TransactionSubtype("Rentals", 5);                       // id=35
            oTranTypeDao.insertTransactionSubtype(fuel);
            oTranTypeDao.insertTransactionSubtype(parking);
            oTranTypeDao.insertTransactionSubtype(leasing);
            oTranTypeDao.insertTransactionSubtype(insurance);
            oTranTypeDao.insertTransactionSubtype(remont);
            oTranTypeDao.insertTransactionSubtype(rental);

            // Taxes & Charges
            TransactionSubtype insurances = new TransactionSubtype("Insurances", 6);                // id=36
            TransactionSubtype fines = new TransactionSubtype("Fines", 6);                          // id=37
            TransactionSubtype loan = new TransactionSubtype("Loans & Interests", 6);               // id=38
            TransactionSubtype taxes= new TransactionSubtype("Taxes", 6);                           // id=39
            TransactionSubtype childSupport = new TransactionSubtype("Child Support", 6);           // id=40
            TransactionSubtype advice = new TransactionSubtype("Advisory", 6);                      // id=41
            oTranTypeDao.insertTransactionSubtype(insurances);
            oTranTypeDao.insertTransactionSubtype(fines);
            oTranTypeDao.insertTransactionSubtype(loan);
            oTranTypeDao.insertTransactionSubtype(taxes);
            oTranTypeDao.insertTransactionSubtype(childSupport);
            oTranTypeDao.insertTransactionSubtype(advice);

            // Housing
            TransactionSubtype bills = new TransactionSubtype("Bills & Utilities", 7);              // id=42
            TransactionSubtype repairs = new TransactionSubtype("Repairs & Maintenance", 7);        // id=43
            TransactionSubtype rent = new TransactionSubtype("Rent", 7);                            // id=44
            TransactionSubtype propertyInsurance = new TransactionSubtype("Property Insurance", 7); // id=45
            TransactionSubtype mortgage = new TransactionSubtype("Mortgage", 7);                    // id=46
            TransactionSubtype services = new TransactionSubtype("Services", 7);                    // id=47
            oTranTypeDao.insertTransactionSubtype(bills);
            oTranTypeDao.insertTransactionSubtype(repairs);
            oTranTypeDao.insertTransactionSubtype(rent);
            oTranTypeDao.insertTransactionSubtype(propertyInsurance);
            oTranTypeDao.insertTransactionSubtype(mortgage);
            oTranTypeDao.insertTransactionSubtype(services);

            // Investments
            TransactionSubtype savings = new TransactionSubtype("Savings", 8);                      // id=48
            TransactionSubtype investments = new TransactionSubtype("Financial Investments", 8);    // id=49
            TransactionSubtype realty = new TransactionSubtype("Realty", 8);                        // id=50
            TransactionSubtype collection = new TransactionSubtype("Cash Collection", 8);           // id=51
            oTranTypeDao.insertTransactionSubtype(savings);
            oTranTypeDao.insertTransactionSubtype(investments);
            oTranTypeDao.insertTransactionSubtype(realty);
            oTranTypeDao.insertTransactionSubtype(collection);

            // Other
            // N/A
        }

        private void insertSampleAccountsAndTransactions() {

            // Accounts
            Account account1 = new Account(1367.65, "credit card", 2);  // id=1
            Account account2 = new Account(89.14, "credit card2", 2);   // id=2
            oAccountTypeDao.insertAccount(account1);
            oAccountTypeDao.insertAccount(account2);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.now();
            String date = dtf.format(localDate); //2016/11/16

            // Transactions
            Transaction tran1 = new Transaction(date, 51.24, "canned food", 1, 1);
            Transaction tran2 = new Transaction(date, 20.99, "mcDonalds", 1, 2);
            oTransactionDao.insertTransaction(tran1);
            oTransactionDao.insertTransaction(tran2);
        }
    }
}
