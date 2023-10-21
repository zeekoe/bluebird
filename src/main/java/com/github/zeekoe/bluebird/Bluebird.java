package com.github.zeekoe.bluebird;

import com.github.zeekoe.bluebird.heatpump.Heatpump;

public class Bluebird {
    public static void main(String[] args) {
        new Retryer<>(Heatpump.class).startRunning();
    }
}
