package com.flightagencyclient.dto;

public class PaymentDto {
    /**
     * کد پیگیری
     */
    private String tracingCode;

    public PaymentDto() {
    }

    public PaymentDto(String tracingCode) {
        this.tracingCode = tracingCode;
    }

    public String getTracingCode() {
        return tracingCode;
    }

    public void setTracingCode(String tracingCode) {
        this.tracingCode = tracingCode;
    }

    @Override
    public String toString() {
        return "PaymentDto{" + "\n" +
               "    tracingCode=" + tracingCode + "\n" +
               '}' + "\n";
    }
}
