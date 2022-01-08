package com.example.pocketexpenses.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.pocketexpenses.R;
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
            AccountType cash = new AccountType("Cash", R.drawable.ic_input_account);                     // id=1
            AccountType creditCard = new AccountType("Credit Card", R.drawable.ic_input_account);        // id=2
            AccountType debitCard = new AccountType("Debit Card", R.drawable.ic_input_account);          // id=3
            AccountType discountCard = new AccountType("Discount Card", R.drawable.ic_input_account);    // id=4
            oAccountTypeDao.insertAccountType(cash);
            oAccountTypeDao.insertAccountType(creditCard);
            oAccountTypeDao.insertAccountType(debitCard);
            oAccountTypeDao.insertAccountType(discountCard);

            // Accounts
            Account cashStartingAccount = new Account(0, "Cash", 1, R.drawable.ic_input_account); // id=1
            oAccountTypeDao.insertAccount(cashStartingAccount);
        }


        private void insertTransactionDirectionsAndTypes() {
            // Income & Expenses
            TransactionDirection income = new TransactionDirection("Income", 1);      // id=1
            TransactionDirection expense = new TransactionDirection("Expense", -1);   // id=2
            oTranTypeDao.insertTransactionDirection(income);
            oTranTypeDao.insertTransactionDirection(expense);

            // Expenses Types
            TransactionType foodAndDrink= new TransactionType("Food & Drinks", 2, R.drawable.ic_food_and_drinks);               // id=1
            TransactionType shopping = new TransactionType("Shopping", 2, R.drawable.ic_shopping_2);                              // id=2
            TransactionType entertainment = new TransactionType("Entertainment", 2, R.drawable.ic_entertainment);               // id=3
            TransactionType transport = new TransactionType("Transportation", 2, R.drawable.ic_transportation_2);                  // id=4
            TransactionType vehicle = new TransactionType("Vehicle", 2, R.drawable.ic_vehicle);                           // id=5
            TransactionType taxes = new TransactionType("Taxes & Charges", 2, R.drawable.ic_taxes_and_charges);                     // id=6
            TransactionType housing = new TransactionType("Housing", 2, R.drawable.ic_housing);                           // id=7
            TransactionType investments = new TransactionType("Investments", 2, R.drawable.ic_investment);                   // id=8
            TransactionType otherExpenses = new TransactionType("Other", 2, R.drawable.ic_other);                       // id=9
            oTranTypeDao.insertTransactionType(foodAndDrink);
            oTranTypeDao.insertTransactionType(shopping);
            oTranTypeDao.insertTransactionType(entertainment);
            oTranTypeDao.insertTransactionType(transport);
            oTranTypeDao.insertTransactionType(vehicle);
            oTranTypeDao.insertTransactionType(taxes);
            oTranTypeDao.insertTransactionType(housing);
            oTranTypeDao.insertTransactionType(investments);
            oTranTypeDao.insertTransactionType(otherExpenses);

            // Income Types
            TransactionType wage = new TransactionType("Wage", 1, R.drawable.ic_wage);                    // id=10
            TransactionType rentalIncome = new TransactionType("Rental Income", 1, R.drawable.ic_rental_income);   // id=11
            TransactionType interest = new TransactionType("Interest/Dividend", 1, R.drawable.ic_interest_and_divident);   // id=12
            TransactionType coupon = new TransactionType("Coupon/Voucher", 1, R.drawable.ic_coupon_voucher);        // id=13
            TransactionType gift = new TransactionType("Gift", 1, R.drawable.ic_gift);                    // id=14
            TransactionType welfare = new TransactionType("Welfare", 1, R.drawable.ic_welfare);              // id=15
            TransactionType refund = new TransactionType("Refund", 1, R.drawable.ic_refund);                // id=16
            TransactionType otherIncome = new TransactionType("Other", 1, R.drawable.ic_other);            // id=17
            oTranTypeDao.insertTransactionType(wage);
            oTranTypeDao.insertTransactionType(rentalIncome);
            oTranTypeDao.insertTransactionType(interest);
            oTranTypeDao.insertTransactionType(coupon);
            oTranTypeDao.insertTransactionType(gift);
            oTranTypeDao.insertTransactionType(welfare);
            oTranTypeDao.insertTransactionType(refund);
            oTranTypeDao.insertTransactionType(otherIncome);


        }


        private void insertTransactionSubtypes() {
            // Food & Drinks
            TransactionSubtype groceries = new TransactionSubtype("Groceries", 1, R.drawable.ic_shopping);                  // id=1
            TransactionSubtype restaurant = new TransactionSubtype("Restaurant & Fast Food", 1, R.drawable.ic_restaurant);    // id=2
            TransactionSubtype bar = new TransactionSubtype("Bar & Cafe", 1, R.drawable.ic_bar_and_cafe);                       // id=3
            TransactionSubtype restricted = new TransactionSubtype("Alcohol & Cigarettes", 1, R.drawable.ic_alcohol_and_cigarettes);      // id=4
            oTranTypeDao.insertTransactionSubtype(groceries);
            oTranTypeDao.insertTransactionSubtype(restaurant);
            oTranTypeDao.insertTransactionSubtype(bar);
            oTranTypeDao.insertTransactionSubtype(restricted);

            // Shopping
            TransactionSubtype clothes = new TransactionSubtype("Clothes & Shoes", 2, R.drawable.ic_clothes_and_shoes);              // id=5
            TransactionSubtype cosmetics = new TransactionSubtype("Cosmetics", 2, R.drawable.ic_cosmetics);                  // id=6
            TransactionSubtype medicine = new TransactionSubtype("Medicine", 2, R.drawable.ic_medicine);                    // id=7
            TransactionSubtype electronics = new TransactionSubtype("Electronics", 2, R.drawable.ic_electronics);              // id=8
            TransactionSubtype gift = new TransactionSubtype("Gifts", 2, R.drawable.ic_gift);                           // id=9
            TransactionSubtype furniture = new TransactionSubtype("Furniture & Garden", 2, R.drawable.ic_furniture_and_garden);         // id=10
            TransactionSubtype accessories = new TransactionSubtype("Accessories", 2, R.drawable.ic_accessories);              // id=11
            TransactionSubtype kids = new TransactionSubtype("Kids", 2, R.drawable.ic_kids_2);                            // id=12
            TransactionSubtype pets = new TransactionSubtype("Pets", 2, R.drawable.ic_pets);                            // id=13
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
            TransactionSubtype events = new TransactionSubtype("Culture & Sport Events", 3, R.drawable.ic_culture_and_sports_events);        // id=14
            TransactionSubtype travel = new TransactionSubtype("Holidays & Trips", 3, R.drawable.ic_holiday_trips);              // id=15
            TransactionSubtype sport = new TransactionSubtype("Sport & Fitness", 3, R.drawable.ic_fitness);                // id=16
            TransactionSubtype hobbies = new TransactionSubtype("Hobbies", 3, R.drawable.ic_entertainment);                      // id=17
            TransactionSubtype subscriptions = new TransactionSubtype("Subscriptions", 3, R.drawable.ic_subscriptions);          // id=18
            TransactionSubtype books = new TransactionSubtype("Books & Newsletters", 3, R.drawable.ic_newspaper);            // id=19
            TransactionSubtype education = new TransactionSubtype("Education & Courses", 3, R.drawable.ic_education);        // id=20
            TransactionSubtype charity = new TransactionSubtype("Charity", 3, R.drawable.ic_charity);                      // id=21
            TransactionSubtype hazart = new TransactionSubtype("Lottery & Gambling", 3, R.drawable.ic_lottery_and_gambling);            // id=22
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
            TransactionSubtype publicTransport = new TransactionSubtype("Public Transport", 4, R.drawable.ic_public_transportations);     // id=23
            TransactionSubtype taxi = new TransactionSubtype("Taxi", 4, R.drawable.ic_taxi);                            // id=24
            TransactionSubtype business = new TransactionSubtype("Business Trips", 4, R.drawable.ic_business_trip);              // id=25
            TransactionSubtype flights = new TransactionSubtype("Flights", 4, R.drawable.ic_bussiness_trip);                      // id=26
            TransactionSubtype sea = new TransactionSubtype("Sailing", 4, R.drawable.ic_sailing);                          // id=27
            TransactionSubtype hamali = new TransactionSubtype("Furniture Moving", 4, R.drawable.ic_furniture_moving_2);              // id=28
            TransactionSubtype spodeleno = new TransactionSubtype("Shared Travel", 4, R.drawable.ic_shared_travel);              // id=29
            oTranTypeDao.insertTransactionSubtype(publicTransport);
            oTranTypeDao.insertTransactionSubtype(taxi);
            oTranTypeDao.insertTransactionSubtype(business);
            oTranTypeDao.insertTransactionSubtype(flights);
            oTranTypeDao.insertTransactionSubtype(sea);
            oTranTypeDao.insertTransactionSubtype(hamali);
            oTranTypeDao.insertTransactionSubtype(spodeleno);

            // Vehicle
            TransactionSubtype fuel = new TransactionSubtype("Fuel", 5, R.drawable.ic_fuel);                            // id=30
            TransactionSubtype parking = new TransactionSubtype("Parking", 5, R.drawable.ic_parking);                      // id=31
            TransactionSubtype leasing = new TransactionSubtype("Leasing", 5, R.drawable.ic_leasing);                      // id=32
            TransactionSubtype insurance = new TransactionSubtype("Vehicle Insurance", 5, R.drawable.ic_vehicle_insurance);          // id=33
            TransactionSubtype remont = new TransactionSubtype("Maintenance & Repairs", 5, R.drawable.ic_maintainance_and_repairs);         // id=34
            TransactionSubtype rental = new TransactionSubtype("Rentals", 5, R.drawable.ic_rent);                       // id=35
            oTranTypeDao.insertTransactionSubtype(fuel);
            oTranTypeDao.insertTransactionSubtype(parking);
            oTranTypeDao.insertTransactionSubtype(leasing);
            oTranTypeDao.insertTransactionSubtype(insurance);
            oTranTypeDao.insertTransactionSubtype(remont);
            oTranTypeDao.insertTransactionSubtype(rental);

            // Taxes & Charges
            TransactionSubtype insurances = new TransactionSubtype("Insurances", 6, R.drawable.ic_insurances);                // id=36
            TransactionSubtype fines = new TransactionSubtype("Fines", 6, R.drawable.ic_fines);                          // id=37
            TransactionSubtype loan = new TransactionSubtype("Loans & Interests", 6, R.drawable.ic_loans_and_interests);               // id=38
            TransactionSubtype taxes= new TransactionSubtype("Taxes", 6, R.drawable.ic_taxes);                           // id=39
            TransactionSubtype childSupport = new TransactionSubtype("Child Support", 6, R.drawable.ic_child_support);           // id=40
            TransactionSubtype advice = new TransactionSubtype("Advisory", 6, R.drawable.ic_advisory);                      // id=41
            oTranTypeDao.insertTransactionSubtype(insurances);
            oTranTypeDao.insertTransactionSubtype(fines);
            oTranTypeDao.insertTransactionSubtype(loan);
            oTranTypeDao.insertTransactionSubtype(taxes);
            oTranTypeDao.insertTransactionSubtype(childSupport);
            oTranTypeDao.insertTransactionSubtype(advice);

            // Housing
            TransactionSubtype bills = new TransactionSubtype("Bills & Utilities", 7, R.drawable.ic_bills_and_utilities);              // id=42
            TransactionSubtype repairs = new TransactionSubtype("Repairs & Maintenance", 7, R.drawable.ic_repairs_and_maintainnance);        // id=43
            TransactionSubtype rent = new TransactionSubtype("Rent", 7, R.drawable.ic_rent);                            // id=44
            TransactionSubtype propertyInsurance = new TransactionSubtype("Property Insurance", 7, R.drawable.ic_property_insurance); // id=45
            TransactionSubtype mortgage = new TransactionSubtype("Mortgage", 7, R.drawable.ic_mortgage);                    // id=46
            TransactionSubtype services = new TransactionSubtype("Services", 7, R.drawable.ic_services);                    // id=47
            oTranTypeDao.insertTransactionSubtype(bills);
            oTranTypeDao.insertTransactionSubtype(repairs);
            oTranTypeDao.insertTransactionSubtype(rent);
            oTranTypeDao.insertTransactionSubtype(propertyInsurance);
            oTranTypeDao.insertTransactionSubtype(mortgage);
            oTranTypeDao.insertTransactionSubtype(services);

            // Investments
            TransactionSubtype savings = new TransactionSubtype("Savings", 8, R.drawable.ic_savings);                      // id=48
            TransactionSubtype investments = new TransactionSubtype("Financial Investments", 8, R.drawable.ic_financial_investments);    // id=49
            TransactionSubtype realty = new TransactionSubtype("Realty", 8, R.drawable.ic_realty);                        // id=50
            TransactionSubtype collection = new TransactionSubtype("Cash Collection", 8, R.drawable.ic_cash_collections);           // id=51
            oTranTypeDao.insertTransactionSubtype(savings);
            oTranTypeDao.insertTransactionSubtype(investments);
            oTranTypeDao.insertTransactionSubtype(realty);
            oTranTypeDao.insertTransactionSubtype(collection);

            // Other
            TransactionSubtype otherExpense = new TransactionSubtype("Other", 9, R.drawable.ic_other);            // 52
            oTranTypeDao.insertTransactionSubtype(otherExpense);


            // Income Subtypes
            TransactionSubtype wage = new TransactionSubtype("Wage", 10, R.drawable.ic_wage);                    // id=53
            TransactionSubtype rentalIncome = new TransactionSubtype("Rental Income", 11, R.drawable.ic_rental_income);   // id=54
            TransactionSubtype interest = new TransactionSubtype("Interest/Dividend", 12, R.drawable.ic_interest_and_divident);   // id=55
            TransactionSubtype coupon = new TransactionSubtype("Coupon/Voucher", 13, R.drawable.ic_coupon_voucher);        // id=56
            TransactionSubtype incomeGift = new TransactionSubtype("Gift", 14, R.drawable.ic_gift);              // id=57
            TransactionSubtype welfare = new TransactionSubtype("Welfare", 15, R.drawable.ic_welfare);              // id=58
            TransactionSubtype refund = new TransactionSubtype("Refund", 16, R.drawable.ic_refund);                // id=59
            TransactionSubtype otherIncome = new TransactionSubtype("Other", 17, R.drawable.ic_other);            // id=60
            oTranTypeDao.insertTransactionSubtype(wage);
            oTranTypeDao.insertTransactionSubtype(rentalIncome);
            oTranTypeDao.insertTransactionSubtype(interest);
            oTranTypeDao.insertTransactionSubtype(coupon);
            oTranTypeDao.insertTransactionSubtype(gift);
            oTranTypeDao.insertTransactionSubtype(welfare);
            oTranTypeDao.insertTransactionSubtype(refund);
            oTranTypeDao.insertTransactionSubtype(otherIncome);
        }

        private void insertSampleAccountsAndTransactions() {

            // Accounts
            Account account1 = new Account(1367.65, "credit card", 2, R.drawable.ic_input_account);  // id=1
            Account account2 = new Account(89.14, "credit card2", 2, R.drawable.ic_input_account);   // id=2
            oAccountTypeDao.insertAccount(account1);
            oAccountTypeDao.insertAccount(account2);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate localDate = LocalDate.now();
            String date = dtf.format(localDate);

            // Transactions
            Transaction tran1 = new Transaction(date, 51.24, "canned food", 1, 1, R.drawable.ic_shopping_2);
            Transaction tran2 = new Transaction(date, 20.99, "mcDonalds", 1, 2, R.drawable.ic_restaurant);
            oTransactionDao.insertTransaction(tran1);
            oTransactionDao.insertTransaction(tran2);
        }
    }
}
