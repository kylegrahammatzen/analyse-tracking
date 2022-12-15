package net.analyse.base.sdk.exception;

public class InvalidIPAddressException extends Exception {

    private final String ip;

    public InvalidIPAddressException(String ip) {
        this.ip = ip;
    }

    @Override
    public String getLocalizedMessage() {
        return String.format("The IP address %s doesn't exist.", ip);
    }
}