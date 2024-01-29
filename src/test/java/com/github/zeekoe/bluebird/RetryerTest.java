package com.github.zeekoe.bluebird;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

class RetryerTest {
  @Test
  void retryerTest() {
    final Retryer<InstantThrower> spy = Mockito.spy(new Retryer<>(InstantThrower.class));
    doNothing().when(spy).sleep(anyLong());
    spy.startRunning();
    verify(spy).sleep(30_000);
    verify(spy).sleep(120_000);
    verify(spy).sleep(270_000);
    verify(spy).sleep(480_000);
    verify(spy).sleep(750_000);
  }

  @Test
  void retryerTest2() {
    int counter = 0;
    final Retryer<AfterAWhileThrower> spy = Mockito.spy(new Retryer<>(AfterAWhileThrower.class));
    doNothing().when(spy).sleep(anyLong());
    spy.startRunning();
  }


  static class InstantThrower implements Runnable {

    @Override
    public void run() {
      throw new RuntimeException("Boom!");
    }
  }

  static class AfterAWhileThrower implements Runnable {
    static boolean hasRecoveredYet = false;
    static int tryCounter = 0;

    @Override
    public void run() {
      System.out.println("don't except");
      tryCounter++;
      if (tryCounter > 2 || hasRecoveredYet) {
        System.out.println("except");
        hasRecoveredYet = true;
        throw new RuntimeException("Boom!");
      }
    }
  }
}