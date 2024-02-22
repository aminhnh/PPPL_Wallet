package org.example;

import java.util.ArrayList;
import java.util.List;

public class Wallet {
    private Owner owner;
    private List<Card> cards = new ArrayList<>();
    private double cash;

    public Wallet() {
    }
    public Wallet(Owner owner, List<Card> cards, double cash) {
        this.setOwner(owner);
        this.setCards(cards);
        this.setCash(cash);
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public double withdraw(double amount) {
        if (this.cash < amount) {
            System.out.println("Uang kurang");
            return 0.0;
        }
        else {
            this.cash -= amount;
            return amount;
        }
    }

    public double deposit(double amount) {
        this.cash += amount;
        return this.cash;
    }

    public void addCards(String bank, int accountNumber) {
        Card card = new Card();
        card.setOwnerName(this.owner.getName());
        card.setBank(bank);
        card.setAccountNumber(accountNumber);
        this.cards.add(card);
    }

    public void removeCard(int accountNumber) {
        this.cards.forEach(card -> {
            if (card.getAccountNumber() == accountNumber) {
                this.cards.remove(card);
            }
        });
    }

}
