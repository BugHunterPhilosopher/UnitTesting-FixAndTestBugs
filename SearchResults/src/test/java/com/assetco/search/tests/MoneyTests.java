package com.assetco.search.tests;

import com.assetco.search.results.Money;
import org.junit.jupiter.api.*;

import java.math.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MoneyTests {
    private static final String anyAmount = "123.40";
    private Money money;

    @Test
    void hardcodedToUSD() {
        givenMoneyWithAmount(anyAmount);

        thenCurrencyIs(money, "USD");
    }

    @Test
    void returnsAmount(){
        givenMoneyWithAmount(anyAmount);

        thenAmountIs(anyAmount);
    }

    private void givenMoneyWithAmount(String amount) {
        money = new Money(new BigDecimal(amount));
    }

    private void thenCurrencyIs(Money money, String expectedCurrency) {
        assertEquals(Currency.getInstance(expectedCurrency), money.getCurrency());
    }

    private void thenAmountIs(String expectedAmount) {
        assertEquals(new BigDecimal(expectedAmount), money.getAmount());
    }
}
