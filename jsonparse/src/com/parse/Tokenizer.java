package com.parse;

import com.parse.CharReader;
import com.parse.Exception.JsonParseException;
import com.parse.Token;

import java.io.IOException;

import static com.sun.org.apache.xml.internal.utils.XMLCharacterRecognizer.isWhiteSpace;
import static java.lang.Character.isDigit;

public class Tokenizer {

    private CharReader charReader;
    private TokenList tokens;

    public TokenList tokenize(CharReader charReader) {
        this.charReader = charReader;
        tokens = new TokenList();
        try {
            tokenize();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tokens;
    }

    private void tokenize()  throws IOException{
        Token token;
        do {
            token = start();
            tokens.add(token);
        } while (token.getTokenType() != TokenType.END_DOCUMENT);
    }

    private Token start() throws IOException{
        char ch;
        for(;;) {
            if (!charReader.hasMore()) {
                return new Token(TokenType.END_DOCUMENT);
            }
            ch = charReader.next();
            if (!isWhiteSpace(ch)) {
                break;
            }
        }
        switch (ch) {
            case '{':
                return new Token(TokenType.BEGIN_OBJECT, String.valueOf(ch));
            case '}':
                return new Token(TokenType.END_OBJECT, String.valueOf(ch));
            case '[':
                return new Token(TokenType.BEGIN_ARRAY, String.valueOf(ch));
            case ']':
                return new Token(TokenType.END_ARRAY, String.valueOf(ch));
            case ',':
                return new Token(TokenType.SEP_COMMA, String.valueOf(ch));
            case ':':
                return new Token(TokenType.SEP_COLON, String.valueOf(ch));
            case 'n':
                return readNull();
            case 't':
            case 'f':
                return readBoolean();
            case '"':
                return readString();
            case '-':
                return readNumber();
        }
        if (isDigit(ch)) {
            return readNumber();
        }

        throw new JsonParseException("Illegal character");
    }
    private Token readNull() {
        return null;
    }

    private Token readBoolean() {
        return null;
    }

    private Token readString() {
        return null;
    }

    private Token readNumber() {
        return null;
    }


}
