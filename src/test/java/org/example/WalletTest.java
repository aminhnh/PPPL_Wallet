package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest {
    private final String ownerName = "Kenshi Yonezu";
    private final String bankName = "Mandiri";
    private final String cardAccountNumber = "1234-5678-9012";

    @Test
    public void testSetDataOwner() {
        // Arrange
        Wallet wallet = new Wallet(ownerName);
        // Act & Assert
        assertOwnerNameNotNull(wallet);
        assertEquals(ownerName, wallet.getOwnerName());

        // Test setting new owner name
        String newOwnerName = "Himalaya";
        wallet.setOwnerName(newOwnerName);
        assertOwnerNameNotNull(wallet);
        assertEquals(newOwnerName, wallet.getOwnerName());

        // Test setting an empty owner name
        wallet.setOwnerName("");
        assertOwnerNameNotNull(wallet);
        assertEquals("", wallet.getOwnerName());
    }
    private void assertOwnerNameNotNull(Wallet wallet) {
        assertNotNull(wallet.getOwnerName());
    }

    @Test
    public void testAddCard() {
        // Arrange
        Wallet wallet = new Wallet(ownerName);

        // Act & Assert
        wallet.addCard(bankName, cardAccountNumber);
        assertEquals(1, wallet.getCards().size());

        // Assert
        Card addedCard = wallet.getCards().get(0);
        assertEquals(ownerName, addedCard.getOwnerName());
        assertEquals(bankName, addedCard.getBank());
        assertEquals(cardAccountNumber, addedCard.getAccountNumber());
    }

    @Test
    public void testRemoveCard() {
        // Arrange
        Wallet wallet = new Wallet(ownerName);

        // Add a card to the wallet
        Card cardToRemove = new Card();
        cardToRemove.setOwnerName(ownerName);
        cardToRemove.setBank(bankName);
        cardToRemove.setAccountNumber(cardAccountNumber);
        wallet.getCards().add(cardToRemove);

        // Act
        wallet.removeCard(cardAccountNumber);

        // Assert
        assertEquals(0, wallet.getCards().size());
        assertNull(findCardByAccountNumber(wallet, cardAccountNumber));
    }
    private Card findCardByAccountNumber(Wallet wallet, String accountNumber) {
        for (Card card : wallet.getCards()) {
            if (card.getAccountNumber().equals(accountNumber)) {
                return card;
            }
        }
        return null;
    }

    @Test
    public void testDeposit() {
        // Arrange
        Wallet wallet = new Wallet(ownerName);

        // Act & Assert: Deposit a valid amount
        int validAmount = 5000;
        int depositedAmount = wallet.depositCash(validAmount);

        assertEquals(validAmount, depositedAmount);
        assertEquals(1, wallet.getCashMap().get(validAmount).intValue());
        System.out.println("Current Cash Map: " + wallet.getCashMap());


        // Act & Assert: Deposit an invalid amount
        int invalidAmount = 0;
        depositedAmount = wallet.depositCash(invalidAmount);

        assertEquals(0, depositedAmount);
        assertNull(wallet.getCashMap().get(invalidAmount));
        System.out.println("Current Cash Map: " + wallet.getCashMap());


        // Act & Assert: Deposit using an invalid denomination
        int invalidDenomination = 3000;
        depositedAmount = wallet.depositCash(invalidDenomination);

        assertEquals(0, depositedAmount);
        assertNull(wallet.getCashMap().get(invalidDenomination));
        System.out.println("Current Cash Map: " + wallet.getCashMap());
    }

    @Test
    public void testWithdrawCash() {
        // Arrange
        Wallet wallet = new Wallet(ownerName);
        int validAmount = 1000;
        wallet.depositCash(validAmount);

        // Act & Assert: Withdraw a valid amount
        int withdrawnAmount = wallet.withdrawCash(validAmount);

        assertEquals(validAmount, withdrawnAmount);
        assertEquals(0, wallet.getCashMap().get(validAmount).intValue());
        System.out.println("1 - Current Cash Map: " + wallet.getCashMap());


        // Act & Assert: Withdraw an invalid amount
        int invalidAmount = 0;
        withdrawnAmount = wallet.withdrawCash(invalidAmount);

        assertEquals(0, withdrawnAmount);
        assertNull(wallet.getCashMap().get(invalidAmount));
        System.out.println("2 - Current Cash Map: " + wallet.getCashMap());


        // Act & Assert: Withdraw using an invalid denomination
        int invalidDenomination = 3000;
        withdrawnAmount = wallet.withdrawCash(invalidDenomination);

        assertEquals(0, withdrawnAmount);
        assertNull(wallet.getCashMap().get(invalidDenomination));
        System.out.println("3 - Current Cash Map: " + wallet.getCashMap());


        // Act & Assert: Withdraw from a valid but empty denomination
        int emptyDenomination = 100000;
        withdrawnAmount = wallet.withdrawCash(emptyDenomination);

        assertEquals(0, withdrawnAmount);
        assertNotNull(wallet.getCashMap().get(emptyDenomination));
        System.out.println("4 - Current Cash Map: " + wallet.getCashMap());
    }

    @Test
    public void testCalculateTotalCash() {
        // Arrange
        Wallet wallet = new Wallet(ownerName);
        Integer amount1 = 10000;
        wallet.depositCash(amount1);

        // Assert
        assertEquals(amount1, wallet.calculateTotalCash());

        // Act & Assert: Sum multiple cash
        Integer amount2 = 20000;
        Integer amount3 = 1000;
        Integer amount4 = 100;
        wallet.depositCash(amount2);
        wallet.depositCash(amount3);
        wallet.depositCash(amount4);
        assertEquals(amount1+amount2+amount3+amount4, wallet.calculateTotalCash());
    }
}