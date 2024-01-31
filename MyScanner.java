import java.io.FileReader;
import java.io.IOException;
import java.io.PushbackReader;
import java.util.HashMap;
import java.util.Map;

class MyScanner {

    private FileReader reader0;
    private PushbackReader reader;
    private String tokenStr;
    private Core coreToken;

    // Initialize the scanner
    MyScanner(String filename) {
        try {
            reader0 = new FileReader(filename);
            reader = new PushbackReader(reader0);
        } catch (IOException e) {
            coreToken = Core.ERROR;
        }
        nextToken();
    }

    // wrap read in to handle exception
    private char readChar(){
        try {
            int c = reader.read();
            if (c != -1) {
                return (char) c;
            }
        } catch (IOException e) {
            coreToken = Core.ERROR;
            System.out.println("ERROR: cannot open the file");
            System.exit(1);
        }
        return '\0';
    }

    private void unreadLastChar(char ch){
        try {
            reader.unread(ch);
        } catch (IOException e) {
            coreToken = Core.ERROR;
            System.exit(1);
        }         
    }
    
    // Advance to the next token
    public void nextToken(){
        char ch;
        StringBuilder tokenBuilder = new StringBuilder();
        Map<Character, Core> symbolMap = createSymbolMap();
        Map<String, Core> keywordMap = createKeywordMap();
        ch = readChar();

        // skip any leading white space
        while (ch != '\0' && Character.isWhitespace(ch)){
            ch = readChar();
        }

        if (ch == '\0') {
            // Reached the end of the file
            coreToken = Core.EOS;
        } else if (Character.isLetter(ch)) {
            // when the token is ID
            while(ch!='\0' && !Character.isWhitespace(ch) && Character.isLetter(ch) || Character.isDigit(ch)){
                tokenBuilder.append(ch);
                ch = readChar();
            }
            // the above while loop only terminates when we encounter a symbol, and we want to put that back
            unreadLastChar(ch);
            tokenStr = tokenBuilder.toString();
            if(keywordMap.containsKey(tokenStr)){
                coreToken = keywordMap.get(tokenStr);
            } else {
                coreToken = Core.ID;
            }
        }
        else if (Character.isDigit(ch)) {
            // when the token is a number
            while(ch!='\0' && Character.isDigit(ch)){
                tokenBuilder.append(ch);
                ch = readChar();
            }
            tokenStr = tokenBuilder.toString();
            // the above while loop only terminates when we encounter a non-number and we want to put it back
            unreadLastChar(ch);
            if(Integer.parseInt(tokenStr) > 100003){
                coreToken = Core.ERROR;
                System.out.println("ERROR: number is too large");
                System.exit(1);
            }
            else{
                coreToken = Core.CONST;
            }
        }
        // when ch is a symbol
        else{
            if(ch==':'){
                ch = readChar();
                if(ch=='='){
                    coreToken = Core.ASSIGN;
                }
                else{
                    coreToken = Core.COLON;
                    unreadLastChar(ch);
                }
            }
            else if(symbolMap.containsKey(ch)){
                coreToken = symbolMap.get(ch);
            }
            else{
                coreToken = Core.ERROR;
                System.out.println("ERROR: invalid character " + ch);
                System.exit(1);
            }
        }
    }

    // Return the current token
    public Core currentToken() {
        return coreToken;
    }

	// Return the identifier string
    public String getId() {
        return tokenStr;
    }

	// Return the constant value
    public int getConst() {
        return Integer.parseInt(tokenStr);
    }

    private Map<Character, Core> createSymbolMap() {
        Map<Character, Core> symbolMap = new HashMap<>();
        symbolMap.put('+', Core.ADD);
        symbolMap.put('-', Core.SUBTRACT);
        symbolMap.put('*', Core.MULTIPLY);
        symbolMap.put('/', Core.DIVIDE);
        symbolMap.put('=', Core.EQUAL);
        symbolMap.put('<', Core.LESS);
        symbolMap.put(':', Core.COLON);
        symbolMap.put(';', Core.SEMICOLON);
        symbolMap.put('.', Core.PERIOD);
        symbolMap.put(',', Core.COMMA);
        symbolMap.put('(', Core.LPAREN);
        symbolMap.put(')', Core.RPAREN);
        symbolMap.put('[', Core.LBRACE);
        symbolMap.put(']', Core.RBRACE);
        return symbolMap;
    }

    private Map<String, Core> createKeywordMap() {
        Map<String, Core> keywordMap = new HashMap<>();
        keywordMap.put("procedure", Core.PROCEDURE);
        keywordMap.put("begin", Core.BEGIN);
        keywordMap.put("end", Core.END);
        keywordMap.put("if", Core.IF);
        keywordMap.put("else", Core.ELSE);
        keywordMap.put("in", Core.IN);
        keywordMap.put("integer", Core.INTEGER);
        keywordMap.put("return", Core.RETURN);
        keywordMap.put("do", Core.DO);
        keywordMap.put("new", Core.NEW);
        keywordMap.put("not", Core.NOT);
        keywordMap.put("and", Core.AND);
        keywordMap.put("or", Core.OR);
        keywordMap.put("out", Core.OUT);
        keywordMap.put("array", Core.ARRAY);
        keywordMap.put("then", Core.THEN);
        keywordMap.put("while", Core.WHILE);
        keywordMap.put("is", Core.IS);
        return keywordMap;
    }
}