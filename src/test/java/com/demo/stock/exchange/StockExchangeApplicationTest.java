/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.demo.stock.exchange;

import org.junit.Test;
import static org.junit.Assert.*;

public class StockExchangeApplicationTest {
    @Test public void testAppHasAGreeting() {
        StockExchangeApplication classUnderTest = new StockExchangeApplication();
        assertNotNull("app should have a greeting", classUnderTest.getGreeting());
    }
}