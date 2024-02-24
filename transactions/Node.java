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
    private Node previousNode;

    public Node(String type, String date, double amount, Node previousNode) throws Exception{
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.previousNode = previousNode;

        this.validateFields();
        this.link(previousNode.key);
    }

    public Node(String type, String date, double amount, String key) throws Exception{
        this.type = type;
        this.date = date;
        this.amount = amount;
        this.previousNode = null;

        this.validateFields();
        this.link(key);
    }

    public Node getPreviousNode(){
        return this.previousNode;
    }

    public String getKey(){
        return this.key;
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
      String regeneratedKey = generateNewKey(chainKey);
      return this.key.equals(regeneratedKey);
        return true;
    }

    public String findInconsistency(String chainString){
      String regeneratedKey = generateNewKey(chainString);
  if (!this.key.equals(regeneratedKey)) {
      // Compare each field by regenerating the key with only one field changed at a time
      if (!generateNewKeyBasedOnField(chainString, "type").equals(this.key)) {
          return "TYPE";
      }
      if (!generateNewKeyBasedOnField(chainString, "date").equals(this.key)) {
          return "DATE";
      }
      if (!generateNewKeyBasedOnField(chainString, "amount").equals(this.key)) {
          return "AMOUNT";
      }
  }
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
      String encodedType = encodeString(this.type, oldKey);
      String encodedDate = encodeString(this.date, oldKey);
      String encodedAmount = encodeDouble(this.amount, oldKey);
      return encodedType + encodedDate + encodedAmount;
}

private String generateNewKeyBasedOnField(String oldKey, String field) {
   String encodedType = field.equals("type") ? encodeString(this.type, oldKey) : encodeString("TYPE", oldKey);
   String encodedDate = field.equals("date") ? encodeString(this.date, oldKey) : encodeString("2020-01-01 00:00:00", oldKey);
   String encodedAmount = field.equals("amount") ? encodeDouble(this.amount, oldKey) : encodeDouble(0.0, oldKey);
   return encodedType + encodedDate + encodedAmount;
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
