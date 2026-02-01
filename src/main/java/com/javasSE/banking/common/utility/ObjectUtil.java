package com.javasSE.banking.common.utility;


import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ObjectUtil<T> {

    /**
     * @param obj             The object to check
     * @param defaultSupplier A lambda like () -> new Account()
     * @param logMessage      The message to print if null
     * @return The original object or the new default one
     */
    public static <T> T defaultAndLogIfNull(T obj, Supplier<T> defaultSupplier, String logMessage) {
        if (obj == null) {
            System.out.println("LOG: " + logMessage);
            return defaultSupplier.get();
        }
        return obj;
    }

/*
use-case:

TransactionRate rate = ObjectUtil.defaultAndLogIfNull(
    rateMap.get(id),
    () -> new TransactionRate(), // This is the "Supplier"
    "rate with ID " + id + " was null, returning empty rate."
);
 */

}
