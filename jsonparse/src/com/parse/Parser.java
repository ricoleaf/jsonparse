package com.parse;

import com.parse.Exception.JsonParseException;
import com.parse.model.*;

public class Parser {

    private static final int BEGIN_OBJECT_TOKEN = 1;
    private static final int END_OBJECT_TOKEN = 2;
    private static final int BEGIN_ARRAY_TOKEN = 4;
    private static final int END_ARRAY_TOKEN = 8;
    private static final int NULL_TOKEN = 16;
    private static final int NUMBER_TOKEN = 32;
    private static final int STRING_TOKEN = 64;
    private static final int BOOLEAN_TOKEN = 128;
    private static final int SEP_COLON_TOKEN = 256;
    private static final int SEP_COMMA_TOKEN = 512;

    private TokenList tokens;

    public Object parse(TokenList tokens) {
        this.tokens = tokens;
        return parse();
    }

    private Object parse() {
        Token token = tokens.next();
        if (token == null) {
            return new JsonObject();
        } else if (token.getTokenType() == TokenType.BEGIN_OBJECT) {
            return parseJsonObject();
        } else if (token.getTokenType() == TokenType.BEGIN_ARRAY) {
            return parseJsonArray();
        } else {
            throw new JsonParseException("Parse error, invalid Token.");
        }
    }

    private JsonObject parseJsonObject() {
        JsonObject jsonObject = new JsonObject();
        int expectToken = STRING_TOKEN  | END_OBJECT_TOKEN;
        String key = null;
        Object value = null;
        while (tokens.hasMore()) {
            Token token = tokens.next();
            TokenType tokenType = token.getTokenType();
            String tokenValue = token.getValue();
            switch(tokenType) {
                case BEGIN_OBJECT:
                    checkExpectToken(tokenType, expectToken);
                    jsonObject.put(key, parseJsonObject());
                    expectToken = SEP_COMMA_TOKEN | END_OBJECT_TOKEN;
                    break;
                case END_OBJECT:
                    checkExpectToken(tokenType, expectToken);
                    return jsonObject;
                case BEGIN_ARRAY:
                    checkExpectToken(tokenType, expectToken);
                    jsonObject.put(key, parseJsonArray());
                    expectToken = SEP_COMMA_TOKEN | END_OBJECT_TOKEN;
                    break;
                case NULL:
                    checkExpectToken(tokenType, expectToken);
                    jsonObject.put(key, null);
                    expectToken = SEP_COMMA_TOKEN | END_OBJECT_TOKEN;
                case NUMBER:
                    checkExpectToken(tokenType, expectToken);
                    if (tokenValue.contains(".") || tokenValue.contains("e") || tokenValue.contains("E")) {
                        jsonObject.put(key, Double.valueOf(tokenValue));
                    } else {
                        Long num = Long.valueOf(tokenValue);
                        if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
                            jsonObject.put(key, num);
                        } else {
                            jsonObject.put(key, num.intValue());
                        }
                    }
                    expectToken = SEP_COMMA_TOKEN | END_OBJECT_TOKEN;
                    break;
                case STRING:
                    checkExpectToken(tokenType, expectToken);
                    Token preToken = tokens.peekPrevious();

                    if(preToken.getTokenType() == TokenType.SEP_COLON) {
                        value = token.getValue();
                        jsonObject.put(key, value);
                        expectToken = SEP_COMMA_TOKEN | END_OBJECT_TOKEN;
                    } else {
                        key = token.getValue();
                        expectToken = SEP_COLON_TOKEN;
                    }
                    break;
                case BOOLEAN:
                    checkExpectToken(tokenType, expectToken);
                    jsonObject.put(key, Boolean.valueOf(token.getValue()));
                    expectToken = SEP_COMMA_TOKEN | END_OBJECT_TOKEN;
                    break;
                case SEP_COLON:
                    checkExpectToken(tokenType, expectToken);
                    jsonObject.put(key, Boolean.valueOf(token.getValue()));
                    expectToken = NULL_TOKEN | NUMBER_TOKEN | BOOLEAN_TOKEN | STRING_TOKEN
                            | BEGIN_OBJECT_TOKEN |BEGIN_ARRAY_TOKEN;
                    break;
                case SEP_COMMA:
                    checkExpectToken(tokenType, expectToken);
                    expectToken = STRING_TOKEN;
                    break;
                case END_DOCUMENT:
                    checkExpectToken(tokenType, expectToken);
                    return jsonObject;
                default:
                    throw new JsonParseException("Unexpected Token.");
            }
        }
        throw new JsonParseException("Parse error, invalid Token.");
    }

    private JsonArray parseJsonArray() {
        int expectToken = BEGIN_ARRAY_TOKEN | END_ARRAY_TOKEN | BEGIN_OBJECT_TOKEN |END_OBJECT_TOKEN
                | NUMBER_TOKEN | BOOLEAN_TOKEN | STRING_TOKEN;
        JsonArray jsonArray = new JsonArray();
        while (tokens.hasMore()) {
            Token token = tokens.next();
            TokenType tokenType = token.getTokenType();
            String tokenValue = token.getValue();
            switch(tokenType) {
                case BEGIN_OBJECT:
                    checkExpectToken(tokenType, expectToken);
                    jsonArray.add(parseJsonObject());
                    expectToken = SEP_COMMA_TOKEN | END_ARRAY_TOKEN;
                    break;
                case END_ARRAY:
                    checkExpectToken(tokenType, expectToken);
                    return jsonArray;
                case BEGIN_ARRAY:
                    checkExpectToken(tokenType, expectToken);
                    jsonArray.add(parseJsonObject());
                    expectToken = SEP_COMMA_TOKEN | END_ARRAY_TOKEN;
                    break;
                case NULL:
                    checkExpectToken(tokenType, expectToken);
                    jsonArray.add(null);
                    expectToken = SEP_COMMA_TOKEN | END_ARRAY_TOKEN;
                case NUMBER:
                    checkExpectToken(tokenType, expectToken);
                    if (tokenValue.contains(".") || tokenValue.contains("e") || tokenValue.contains("E")) {
                        jsonArray.add(Double.valueOf(tokenValue));

                    } else {
                        Long num = Long.valueOf(tokenValue);
                        if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
                            jsonArray.add(num);
                        } else {
                            jsonArray.add(num.intValue());
                        }
                    }
                    expectToken = SEP_COMMA_TOKEN | END_ARRAY_TOKEN;
                    break;
                case STRING:
                    checkExpectToken(tokenType, expectToken);
                    jsonArray.add(tokenValue);
                    expectToken = SEP_COMMA_TOKEN | END_ARRAY_TOKEN;
                    break;
                case BOOLEAN:
                    checkExpectToken(tokenType, expectToken);
                    jsonArray.add(Boolean.valueOf(token.getValue()));
                    expectToken = SEP_COMMA_TOKEN | END_ARRAY_TOKEN;
                    break;
                case SEP_COMMA:
                    checkExpectToken(tokenType, expectToken);
                    expectToken = STRING_TOKEN | NULL_TOKEN | NUMBER_TOKEN | BOOLEAN_TOKEN
                    | BEGIN_ARRAY_TOKEN | BEGIN_OBJECT_TOKEN;
                    break;
                case END_DOCUMENT:
                    checkExpectToken(tokenType, expectToken);
                    return jsonArray;
                default:
                    throw new JsonParseException("Unexpected Token.");
            }
        }


        throw new JsonParseException("Parse error, invalid Token");
    }

    private void checkExpectToken(TokenType tokenType, int expectToken) {
        if ((tokenType.getTokenCode() & expectToken) == 0) {
            throw new JsonParseException("Parse error, invalid Token.");
        }
    }
}
