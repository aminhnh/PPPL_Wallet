package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WalletTest extends Wallet{

    @Test
    public void testOwner() {
        Wallet wallet = new Wallet(
                new Owner("Android", 63),
                null,
                0.0
        );
        assertEquals("Android", wallet.getOwner().getName());
    }
}