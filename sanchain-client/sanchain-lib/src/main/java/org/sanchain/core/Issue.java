package org.sanchain.core;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Represents a currency/issuer pair
 */
public class Issue implements Comparable<Issue> {

    public static final Issue SAN = fromString("SAN");
    Currency currency;
    AccountID issuer;

    public Issue(Currency currency, AccountID issuer) {
        this.currency = currency;
        this.issuer = issuer;
    }

    public static Issue fromString(String pair) {
        String[] split = pair.split("/");
        return getIssue(split);
    }

    private static Issue getIssue(String[] split) {
        if (split.length == 2) {
            return new Issue(Currency.fromString(split[0]), AccountID.fromString(split[1]));
        } else if (split[0].equals("SAN")) {
            return new Issue(Currency.SAN, AccountID.SAN_ISSUER);
        } else {
            throw new RuntimeException("Issue string must be SAN or $currency/$issuer");
        }
    }

    public Currency currency() {
        return currency;
    }

    public AccountID issuer() {
        return issuer;
    }

    @Override
    public String toString() {
        if (isNative()) {
            return "SAN";
        } else {
            return String.format("%s/%s", currency, issuer);
        }
    }

    public JSONObject toJSON() {
        JSONObject o = new JSONObject();
        try {
            o.put("currency", currency);
            if (!isNative()) {
                o.put("issuer", issuer);
            }
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return o;
    }

    public Amount amount(BigDecimal value) {
        return new Amount(value, currency, issuer, isNative());
    }

    private boolean isNative() {
        return this == SAN || currency.equals(Currency.SAN);
    }

    public Amount amount(Number value) {
        return new Amount(BigDecimal.valueOf(value.doubleValue()), currency, issuer, isNative());
    }

    @Override
    public int compareTo(Issue o) {
        int ret = issuer.compareTo(o.issuer);
        if (ret != 0) {
            return ret;
        }
        ret = currency.compareTo(o.currency);
        return ret;
    }

    public Amount roundedAmount(BigDecimal amount) {
        return amount(Amount.roundValue(amount, isNative()));
    }
}
