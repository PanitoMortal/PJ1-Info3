package transactions;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import exceptions.*;

/* This class represents a single transaction. Each transaction will be 
* linked to the previous one in order to maintain consistency. Please modify
* only the specified methods
*/
public class Node {
    private static final int AMOUNT_LENGTH = 20;
    private static final int CHAR_UPPER_LIMIT = 126;
    private static final String dateRegex = "\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2}";
    private static final Pattern pattern = Pattern.compile(dateRegex);

    private String type;
    private String date;
    private double amount;
    private String key;
    private String previousNodeKey;
    private String transactionKey;

    public Node(String type, String date, double amount, Node previousNode) throws Exception{
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.previousNodeKey = previousKey;
        this.previousNodeKey = previousNode != null ? previousNode.transactionKey : null;
         this.transactionKey = generateTransactionKey();
        this.validateFields();
        this.link(previousNode.key);
    }

    public Node(String type, String date, double amount, String key) throws Exception{
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.previousNode = null;
        this.transactionKey = generateTransactionKey();
        this.validateFields();
        this.link(key);
    }

    public Node getPreviousNode(){
        return this.previousNode;
    }

   public String getTransactionKey() {
        return transactionKey;
    }

    public String getType(){
        return this.type;
    }

    public String getDate(){
        return this.date;
    }

    public double getAmount(){
        return this.amount;
    }

    public double getBalance(){
        if(this.previousNode == null){
            if(this.type.equals("DE")){
                return this.amount;
            }else{
                return - this.amount;
            }
        }


        if(this.type.equals("DE")){
            return this.amount + this.previousNode.getBalance();
        }else{
            return this.previousNode.getBalance() - this.amount;
        }
    }

    public void setAmount(double amount){
        this.amount = amount;
    }

    public void setType(String type) throws Exception{
        this.type = type;
        this.validateFields();
    }

    public void setDate(String date) throws Exception{
        this.date = date;
        this.validateFields();
    }

    public boolean isValid(String chainKey){
        // TODO: Implement this function. Return true if this and all previous nodes are valid.
        /*Verifica si el nodo actual es el primer nodo de la cadena*/
        if(previousNode == null){
            return chainKey.equals(this.key);
        }

        /*al usar la key del nodo anterior para codificar los valores del nodo
          actual, debe obtener la key del nodo actual. */
        String enconde_type = encodeString(this.type,previousNode.type);
        String enconde_date = encodeString(this.date, previousNode.date);
        String enconde_amount = encodeDouble(this.amount, previousNode.key);
        // Otherwise, return false. HINT: Try regenerating the node key with the current values.

        String newKey = enconde_type + enconde_date + enconde_amount;
        return newKey.equals(this.key);
    }

    public String findInconsistency(String chainString){
        // TODO: Implement this function. Navigate trhough the chain and look for a
        
        if (previousNode == null) {
            return "";
        }

        String encodedT = encodeString(this.type, previousNode.key);
        String encodedD = encodeString(this.date, previousNode.key);
        String encodedA = encodeDouble(this.amount, previousNode.key);

        String new_key = encodedT + encodedD + encodedA;

        if(!new_key.equals(this.key)){
            if(!encodedT.equals(this.key.substring(0, 2))){
                return "TYPE";
            }else if(!encodedD.equals(this.key.substring(2, 21))){
                return "DATE";
            }else if(!encodedA.equals(this.key.substring(21))){
                return "AMOUNT";
            }
        }


        // Node that is inconsistent. Then return which field was modified. Possible values are
        // 'TYPE', 'DATE', and 'AMOUNT'. If no inconsistency is found, return an empty string
        return "";
    }


    private

    void validateFields() throws Exception {
        if(! this.type.equals("DE") && ! this.type.equals("WH")){
            throw new NodeInvalidException("Invalid Transaction Type: " + this.type);
        }

        Matcher matcher = pattern.matcher(this.date);
        

        if(! matcher.matches()){
            throw new NodeInvalidException("Invalid Date: " + this.date);
        }
    }

    void link(String key) {
        this.key = this.generateNewKey(key);
    }

    String generateNewKey(String oldKey){
        // TODO: Implement this function. To implement just follow these instructions:
        // 1. Encode the type using the old key
        String encode_Type = encodeString(this.type, oldKey); 
        // 2. Encode the date using the old key
        String enconde_Date = encodeString(this.date, oldKey);
        // 3. Encode the amount using the oldKey
        String enconde_Amount = encodeDouble(this.amount, oldKey);
        // The new key to be returned is the concatenation of the encoded type, date and amount 
        String new_Key = encode_Type + enconde_Date + enconde_Amount;
        
        return new_Key;
    }

    private String encodeDouble(double number, String key){
        String string =  Double.toString(number);
        String paddedString = String.format("%" + AMOUNT_LENGTH + "s", string);  

        return this.encodeString(paddedString, key);
    }

    private String encodeString(String string, String key){
        char[] chars = string.toCharArray();
        char[] newChars = new char[chars.length];
        int keyIndex = 0;

        for(int i = 0; i < chars.length; i++){
            char c = chars[i];
            int nextKeyIndex = this.getNextKeyIndex(key, keyIndex);
            int newChar = c % key.charAt(keyIndex) + key.charAt(nextKeyIndex);

            if(newChar > CHAR_UPPER_LIMIT){
                newChar = newChar % CHAR_UPPER_LIMIT;
            }
            if(newChar < 35){
                newChar = newChar + 35;
            }
            newChars[i] = (char)newChar;
            keyIndex = nextKeyIndex;
        }

        return new String(newChars);
    }

    private int getNextKeyIndex(String key, int currentKeyIndex){
        if(key.length() == (currentKeyIndex + 1)){
            return 0;
        }

        return currentKeyIndex + 1;
    }
}
