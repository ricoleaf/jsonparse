package com.parse.model;

public class Token {

    private TokenType tokenType;
    private String value;

    public TokenType getTokenType() {
        return this.tokenType;
    }

    public String getValue() {
        return value;
    }


    public Token(TokenType tokenType, String value) {
        this.tokenType = tokenType;
        this.value = value;
    }

    public Token(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", value='" + value + '\'' +
                '}';
    }
}
