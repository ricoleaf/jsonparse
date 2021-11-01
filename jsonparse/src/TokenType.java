/**
 * token enum
 *
 */
public enum TokenType {

    /**
     *  {
     */
    BEGIN_OBJECT(1),
    /**
     *  }
     */
    END_IBJECT(2),
    /**
     *  [
     */
    BEGIN_ARRAY(4),
    /**
     *  ]
     */
    END_ARRAY(8),
    /**
     *  NULL
     */
    NULL(16),
    /**
     *  number
     */
    NUMBER(32),
    /**
     *  string
     */
    STRING(64),
    /**
     * boolean
     */
    BOOLEAN(128),
    /**
     *  :
     */
    SEP_COLON(256),
    /**
     *  ,
     */
    SEP_COMMA(512),
    END_DOCUMENT(1024);


    TokenType(int code) {
        this.code = code;
    }

    private int code;

    public int getTokenCode() {
        return code;
    }

}
