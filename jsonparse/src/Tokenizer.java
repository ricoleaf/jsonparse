import java.io.IOException;

public class Tokenizer {

    private CharReader charReader;
    private TokenList tokens;

    public TokenList tokenize(CharReader charReader) {
        this.charReader = charReader;
        tokens = new TokenList();
        tokenize();
        return tokens;
    }

    private void tokenize()  {
        Token token;
        do {
            token = start();
            tokens.add(token);
        } while (token.getTokenType() != TokenType.END_DOCUMENT);
    }

    private Token start() {
        return null;

    }


}
