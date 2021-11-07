package com.parse;

import com.parse.model.CharReader;
import com.parse.model.TokenList;

import java.io.IOException;
import java.io.StringReader;

public class JSONParse {

    private Tokenizer tokenizer = new Tokenizer();

    private Parser parser = new Parser();

    public Object fromJSON(String json) throws IOException {
        CharReader charReader = new CharReader(new StringReader(json));
        TokenList tokens = tokenizer.tokenize(charReader);
        return parser.parse(tokens);
    }

}
