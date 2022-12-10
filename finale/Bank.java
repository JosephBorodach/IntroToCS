package edu.yu.cs.intro.finalExam2021;

import java.util.*;
import java.util.Set;
import java.util.Map; 
import java.util.HashMap;
import java.util.HashSet;
public class Bank {

    private Map<Long, Account> bankAccounts;
    private Map<String,Double> stocksSymbolToPrice;

    public Bank(){
        this.bankAccounts = new HashMap<>();
        this.stocksSymbolToPrice = new HashMap<>();
    }

    /**
     * Lists a new stock with the given symbol at the given price
     * @return false if the stock was previously listed, true if it was added as a result of this call
     */
    protected boolean addNewStockToMarket(String tickerSymbol, double sharePrice){
        if (this.stocksSymbolToPrice.containsKey(tickerSymbol)) {
            return false; 
        } else {
            this.stocksSymbolToPrice.put(tickerSymbol, sharePrice);
            return true;
        }
    }

    /**
     * @return the stock price for the given stock ticker symbol. Return 0 if there is no such stock.
     */
    public double getStockPrice(String symbol){
        if (this.stocksSymbolToPrice.containsKey(symbol)) {
            return this.stocksSymbolToPrice.get(symbol);
        } else {
            return 0; 
        }
    }

    /**
     * @return a set the stock ticker symbols listed in this bank
     */
    public Set<String> getAllStockTickerSymbols(){
        Set<String> allStockTickerSymbols = new HashSet<>();
        if (!this.stocksSymbolToPrice.keySet().isEmpty()) {
            allStockTickerSymbols.addAll(this.stocksSymbolToPrice.keySet());
            return allStockTickerSymbols;
        } else {
            return allStockTickerSymbols;
        }
    }

    /**
     * @return the total number of shares of the given stock owned by all patrons combined. If there is no such Stock or if the tickerSymbol is empty or null, return 0
     */
    public int getNumberOfOutstandingShares(String tickerSymbol){
        int totalNumberOfOutstandingShares = 0;
        if (this.stocksSymbolToPrice.isEmpty() || !this.stocksSymbolToPrice.containsKey(tickerSymbol)) {
            return 0;
        } else if (this.stocksSymbolToPrice.get(tickerSymbol) == null) {
            return 0;
        } else {
            for (Account eachAccount : this.bankAccounts.values()) {
                totalNumberOfOutstandingShares += eachAccount.getNumberOfSharesOwned(tickerSymbol); 
            }
            return totalNumberOfOutstandingShares; 
        }
    }

    /**
     * @return the total number of shares of the given stock owned by all Accounts combined, multiplied by the price per share. 
     * If there is no such Stock or if the tickerSymbol is empty or null, return 0
     */
    public double getMarketCapitalization(String tickerSymbol){
        double totalMarketCapitalization = 0.0; 
        double totalNumberOfOutstandingShares = 0.0;
        if (this.stocksSymbolToPrice.isEmpty() || !this.stocksSymbolToPrice.containsKey(tickerSymbol)) {
            return 0;            
        } else if (this.stocksSymbolToPrice.get(tickerSymbol) == null) {
            return 0;            
        } else {
            for (Account eachAccount : this.bankAccounts.values()) {
                totalNumberOfOutstandingShares += (eachAccount.getNumberOfSharesOwned(tickerSymbol)); 
            }
            totalMarketCapitalization = totalNumberOfOutstandingShares * this.stocksSymbolToPrice.get(tickerSymbol);
            return totalMarketCapitalization;     
        }
    }

    /**
     * @return all the cash in all Accounts added up
     */
    public double getTotalCashInBank(){
        double allCash = 0.0;
        if (!this.bankAccounts.isEmpty()) {
            for (Account eachAccount : this.bankAccounts.values()) {
                allCash += eachAccount.getAvailableCash();
            }
            return allCash;
        } else {
            return 0.0;
        }
    }

    /**
     * Creates a new Account in the bank.
     * @throws IllegalArgumentException if a patron with that social security number already exists in the bank
     */
    public void createNewAccount(String firstName, String lastName, long socialSecurityNumber, String userName, String password){
        if (this.bankAccounts.containsKey(socialSecurityNumber)) {
            throw new IllegalArgumentException ("");
        } else {
            Account newAccount = new Account(firstName, lastName, socialSecurityNumber, userName, password, this);
            this.bankAccounts.put(socialSecurityNumber, newAccount);  
        }
    }

    /**
     * @throws AuthenticationException if the SS#, username, and password don't match an Account
     * @return the account
     */
    protected Account getAccount(long socialSecurityNumber, String userName, String password)throws AuthenticationException{
        if (!this.bankAccounts.containsKey(socialSecurityNumber)) {
            throw new AuthenticationException ();
        } else { //The account exists
            Account account = this.bankAccounts.get(socialSecurityNumber);
            if ((account.getSocialSecurityNumber() != socialSecurityNumber) || (account.getUserName() != userName) || (account.getPassword() != password)) {
                throw new AuthenticationException ();
            } else {
                return account;
            }
        }
    }

    /**
     * Deposit cash into the given account
     * @param amount the amount of cash to deposit
     * @throws AuthenticationException if the SS#, username, and password don't match an Account
     * @return the cash balance in the account after the amount was added to it
     */
    public double depositCash(long socialSecurityNumber, String userName, String password, double amount) throws AuthenticationException{
        if (!this.bankAccounts.containsKey(socialSecurityNumber)) {
            throw new AuthenticationException ();
        } else { //The account exists
            Account account = this.bankAccounts.get(socialSecurityNumber);
            if ((account.getSocialSecurityNumber() != socialSecurityNumber) || (account.getUserName() != userName) || (account.getPassword() != password)) {
                throw new AuthenticationException ();
            } else {
                account.depositCash(amount); 
                return account.getAvailableCash();
            }
        }
    }

    /**
     * withdraw cash from the account; reduce the cash by that amount.
     * @throws AuthenticationException if the SS#, username, and password don't match an Account
     * @throws InsufficientAssetsException if that amount of money is not present the savings account
     */
    public void withdrawCash(long socialSecurityNumber, String userName, String password, double amount) throws AuthenticationException, InsufficientAssetsException {
        if (!this.bankAccounts.containsKey(socialSecurityNumber)) {
            throw new AuthenticationException ();
        } else { //The account exists
            Account account = this.bankAccounts.get(socialSecurityNumber);
            if ((account.getSocialSecurityNumber() != socialSecurityNumber) || (account.getUserName() != userName) || (account.getPassword() != password)) {
                throw new AuthenticationException ();
            } else if (amount > account.getAvailableCash()) { 
                throw new InsufficientAssetsException ();
            } else {
                account.depositCash(-amount);
            }
        }
    }

    /**
     * check the total value of the Account's stocks
     * @throws AuthenticationException if the SS#, username, and password don't match an Account
     */
    public double checkTotalStockWorth(long socialSecurityNumber, String userName, String password) throws AuthenticationException {
        if (!this.bankAccounts.containsKey(socialSecurityNumber)) {
            throw new AuthenticationException ();
        } else { //The account exists
            Account account = this.bankAccounts.get(socialSecurityNumber);
            if ((account.getSocialSecurityNumber() != socialSecurityNumber) || (account.getUserName() != userName) || (account.getPassword() != password)) {
                throw new AuthenticationException ();
            } else {    
                return account.getStockWorth();
            }
        }
    }
    /**
     * check how much cash the patron has in his savings account
     * @throws AuthenticationException if the SS#, username, and password don't match an Account
     */
    public double checkCashBalance(long socialSecurityNumber, String userName, String password) throws AuthenticationException {
        if (!this.bankAccounts.containsKey(socialSecurityNumber)) {
            throw new AuthenticationException ();
        } else { //The account exists
            Account account = this.bankAccounts.get(socialSecurityNumber);
            if ((account.getSocialSecurityNumber() != socialSecurityNumber) || (account.getUserName() != userName) || (account.getPassword() != password)) {
                throw new AuthenticationException ();
            } else { 
                return account.getAvailableCash();
            }
        }
    }

    /**
     * Buy shares of the given stock - reduce the account's cash by the number of shares multiplied by the stock price, and add the number of shares of the given stock to the Account
     * @throws AuthenticationException if the SS#, username, and password don't match an Account
     * @throws InsufficientAssetsException if the required amount of CASH is not present in the account
     * @throws IllegalArgumentException if there is no such stock listed in this Bank
     */
    public void purchaseStock(long socialSecurityNumber, String userName, String password, String tickerSymbol, int shares) throws AuthenticationException, InsufficientAssetsException {
        if (!this.bankAccounts.containsKey(socialSecurityNumber)) {
            throw new AuthenticationException ();
        } else { //The account exists
            Account account = this.bankAccounts.get(socialSecurityNumber);
            if ((account.getSocialSecurityNumber() != socialSecurityNumber) || (account.getUserName() != userName) || (account.getPassword() != password)) {
                throw new AuthenticationException ();
            } else if (!this.stocksSymbolToPrice.containsKey(tickerSymbol)) {    
                throw new IllegalArgumentException ("");
            } else {
                double totalPrice = this.stocksSymbolToPrice.get(tickerSymbol) * shares;
                if (totalPrice > account.getAvailableCash()) {
                    throw new InsufficientAssetsException ();
                } else {
                    account.depositCash(-totalPrice);
                    account.depositStockShares(tickerSymbol, shares); 
                    return;
                }
            }
        }
    }

    /**
     * Sell shares of the given stock - increase the account's cash by the number of shares multiplied by the stock price, and remove the number of shares of the given stock from the Account
     * @throws AuthenticationException if the SS#, username, and password don't match an Account
     * @throws InsufficientAssetsException if the required number of shares of the given stock is not present in the account
     * @throws IllegalArgumentException if there is no such stock listed in this Bank
     */
    public void sellStock(long socialSecurityNumber, String userName, String password, String tickerSymbol, int shares) throws AuthenticationException, InsufficientAssetsException {
        if (!this.bankAccounts.containsKey(socialSecurityNumber)) {
            throw new AuthenticationException ();
        } else { //The account exists
            Account account = this.bankAccounts.get(socialSecurityNumber);
            if ((account.getSocialSecurityNumber() != socialSecurityNumber) || (account.getUserName() != userName) || (account.getPassword() != password)) {
                throw new AuthenticationException ();
            } else if (!this.stocksSymbolToPrice.containsKey(tickerSymbol)) {    
                throw new IllegalArgumentException ("");
            } else {
                if (account.getNumberOfSharesOwned(tickerSymbol) < shares) {
                    throw new InsufficientAssetsException ();
                } else {
                    account.depositCash(this.stocksSymbolToPrice.get(tickerSymbol) * shares);
                    account.removeStockShares(tickerSymbol, shares);
                    return;
                }
            }
        }
    }

    /**
     * Check the net worth of the patron.
     * @throws AuthenticationException if the SS#, username, and password don't match a bank patron
     * @return 0 if the patron doesn't exist, otherwise the patrons net worth
     */
    public double getNetWorth(long socialSecurityNumber, String userName, String password) throws AuthenticationException {
        if (!this.bankAccounts.containsKey(socialSecurityNumber)) {
            throw new AuthenticationException ();
        } else { //The account exists
            Account account = this.bankAccounts.get(socialSecurityNumber);
            if ((account.getSocialSecurityNumber() != socialSecurityNumber) || (account.getUserName() != userName) || (account.getPassword() != password)) {
                throw new AuthenticationException ();
            } else { 
                return account.getNetWorth();
            }
        }
    }
}