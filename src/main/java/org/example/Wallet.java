package org.example;

import java.util.*;

public class Wallet {
    private String ownerName;
    private List<Card> cards = new ArrayList<>();
    private HashMap<Integer, Integer> cashMap = new HashMap<Integer, Integer>();

    public Wallet(String ownerName) {
        this.setOwnerName(ownerName);
        initializeCash();
    }
    public Wallet(String ownerName, List<Card> cards, HashMap<Integer, Integer> cash) {
        this.setOwnerName(ownerName);
        this.setCards(cards);
        this.setCashMap(new HashMap<>());
        initializeCash();
    }
    private void initializeCash() {
        this.cashMap.put(100, 0);
        this.cashMap.put(200, 0);
        this.cashMap.put(500, 0);
        this.cashMap.put(1000, 0);
        this.cashMap.put(2000, 0);
        this.cashMap.put(5000, 0);
        this.cashMap.put(10000, 0);
        this.cashMap.put(20000, 0);
        this.cashMap.put(50000, 0);
        this.cashMap.put(100000, 0);
    }

    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void addCard(String bank, String accountNumber) {
        Card card = new Card();
        card.setOwnerName(this.ownerName);
        card.setBank(bank);
        card.setAccountNumber(accountNumber);
        this.cards.add(card);
    }
    public void removeCard(String accountNumber) {
        // Previous code: leads to `ConcurrentModificationException`
//        for (Card card : getCards()) {
//            if (card.getAccountNumber().equals(accountNumber)) {
//                this.cards.remove(card);
//            }
//        }

        // New code
        Iterator<Card> iterator = getCards().iterator();
        while (iterator.hasNext()) {
            Card card = iterator.next();
            if (card.getAccountNumber().equals(accountNumber)) {
                iterator.remove();
            }
        }

    }
    public void removeAllCards() {
        cards.clear();
    }
    public Integer depositCash(Integer amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount. Amount must be greater than zero.");
            return 0;
        }

        Integer currentCashAmount = cashMap.get(amount);
        if (currentCashAmount == null) {
            System.out.println("Invalid deposit denomination. Please deposit a valid cash denomination.");
            return 0;
        }

        cashMap.put(amount, currentCashAmount + 1);
        System.out.println("Successfully deposited Rp." + amount);
        return amount;
    }
    public Integer withdrawCash(Integer amount) {
        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount. Amount must be greater than zero.");
            return 0;
        }

        if (cashMap.containsKey(amount) && cashMap.get(amount) > 0) {
            Integer currentCashAmount = cashMap.get(amount);
            cashMap.put(amount, currentCashAmount - 1);
            System.out.println("Successful withdrawal of Rp." + amount);
            return amount;
        } else {
            System.out.println("Money unavailable for withdrawal or insufficient funds.");
            return 0;
        }
    }

    public Integer calculateTotalCash() {
        Integer totalCash = 0;

        for (Map.Entry<Integer, Integer> entry : cashMap.entrySet()) {
            Integer denomination = entry.getKey();
            Integer count = entry.getValue();
            totalCash += denomination * count;
        }

        System.out.println("Total cash: Rp." + totalCash);
        return totalCash;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
    public HashMap<Integer, Integer> getCashMap() {
        return cashMap;
    }
    public void setCashMap(HashMap<Integer, Integer> cashMap) {
        this.cashMap = cashMap;
    }
}
