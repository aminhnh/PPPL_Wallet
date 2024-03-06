package org.example;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WalletTest {
    private Wallet wallet;
    private final String ownerName = "Kenshi Yonezu";
    private final String bankName = "Mandiri";
    private final String cardAccountNumber = "1234-5678-9012";

    @BeforeAll
    void initClass() {
        System.out.println("@BeforeAll is called");
    }
    @BeforeEach
    void initMethod() {
        wallet = new Wallet(ownerName);
        wallet.addCard(bankName, cardAccountNumber);
        System.out.println("@BeforeEach is called");
    }
    @AfterEach
    void cleanMethod() {
        wallet = null;
        System.out.println("@AfterEach is called");
    }
    @AfterAll
    void cleanClass() {
        System.out.println("@AfterAll is called");
    }

    @Test
    public void testSetDataOwner() {
        assertAll(
                () ->  assertNotNull(wallet.getOwnerName()),
                () -> assertEquals(ownerName, wallet.getOwnerName())
        );
    }
    @Test
    public void testChangeOwnerName() {
        wallet.setOwnerName("Himalaya");
        assertAll(
                () ->  assertNotNull(wallet.getOwnerName()),
                () -> assertEquals("Himalaya", wallet.getOwnerName())
        );
        wallet.setOwnerName("");
        assertAll(
                () ->  assertNotNull(wallet.getOwnerName()),
                () -> assertEquals("", wallet.getOwnerName())
        );
    }
    @Test
    public void testAddCard() {
        Card addedCard = wallet.getCards().get(0);
        assertAll(
                () -> assertEquals(1, wallet.getCards().size()),
                () -> assertEquals(ownerName, addedCard.getOwnerName()),
                () -> assertEquals(bankName, addedCard.getBank()),
                () -> assertEquals(cardAccountNumber, addedCard.getAccountNumber())
        );
    }
    @Test
    public void testRemoveCard() {
        wallet.removeCard(cardAccountNumber);
        assertAll(
                () -> assertEquals(0, wallet.getCards().size()),
                () -> assertNull(findCardByAccountNumber(wallet, cardAccountNumber))
        );
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
    void testDepositValidPositiveAmount() {
        int positiveAmount = 5000;
        assertAll(
                () -> assertEquals(positiveAmount, wallet.depositCash(positiveAmount)),
                () -> assertEquals(1, wallet.getCashMap().get(positiveAmount).intValue())
        );
    }
    @Test
    void testDepositNegativeAmount() {
        int negativeAmount = -1000;
        assertAll(
                () -> assertEquals(0, wallet.depositCash(negativeAmount))
        );
    }
    @Test
    void testDepositZeroAmount() {
        int zeroAmount = 0;
        assertAll(
                () -> assertEquals(0, wallet.depositCash(zeroAmount))
        );
    }
    @Test
    void testDepositInvalidDenomination() {
        int invalidDenomination = 13000;
        assertAll(
                () -> assertEquals(0, wallet.depositCash(invalidDenomination))
        );
    }
    @Test
    void testWithdrawValidPositiveAmount() {
        int positiveAmount = 2000;
        wallet.depositCash(positiveAmount);
        assertAll(
                () -> assertEquals(positiveAmount, wallet.withdrawCash(positiveAmount)),
                () -> assertEquals(0, wallet.getCashMap().get(positiveAmount).intValue())
        );
    }
    @Test
    void testWithdrawNegativeAmount() {
        int negativeAmount = -1000;
        assertAll(
                () -> assertEquals(0, wallet.withdrawCash(negativeAmount)),
                () -> assertNull(wallet.getCashMap().get(negativeAmount))
        );
    }
    @Test
    void testWithdrawZeroAmount() {
        int zeroAmount = 0;
        assertAll(
                () -> assertEquals(0, wallet.withdrawCash(zeroAmount)),
                () -> assertNull(wallet.getCashMap().get(zeroAmount))
        );
    }
    @Test
    void testWithdrawUnavailableAmount() {
        int unavailableAmount = 100000;
        assertEquals(0, wallet.withdrawCash(unavailableAmount));
    }
    @Test
    void testWithdrawInvalidDenomination() {
        int invalidDenomination = 13000;
        assertEquals(0, wallet.withdrawCash(invalidDenomination));
    }
    @Test
    void testCalculateTotalCash() {
        wallet.depositCash(100);
        wallet.depositCash(500);
        wallet.depositCash(1000);

        assertEquals(1600, wallet.calculateTotalCash());
    }
}