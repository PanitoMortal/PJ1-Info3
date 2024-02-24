package transactions;

import java.util.LinkedList;

/* This class represents a chain of transactions. Each transaction will be 
* linked to the previous one in order to maintain consistency. Please modify
* only the specified methods
*/
public class Chain {
    LinkedList<Node> transactions;
    private String chainKey;

    public Chain(String chainKey){
        this.chainKey = chainKey;
        this.transactions = new LinkedList<Node>();
    }

    public Node findByIndex(int index){
        if(index >= 0 && this.transactions.size() < index){
            this.transactions.get(index);
        }

        return null;
    }

    public Node transactionAt(int index){
        return this.transactions.get(index);
    }
    
    public Node findByKey(String key){
        for(int i = 0; i < this.transactions.size(); i++){
            Node node = this.transactions.get(i);

            if(node.getKey().equals(key)){
                return node;
            }
        }

        return null;
    }

    public double getBalance(){
      if (!isValid()) {
        return 0.0;
    }

    double balance = 0.0;
    for (Node node : this.transactions) {
        if(node.getType().equals("DE")){ // Assuming "DE" stands for deposit
            balance += node.getAmount();
        } else if(node.getType().equals("WH")){ // Assuming "WH" stands for withdrawal
            balance -= node.getAmount();
        }
    }
    return balance;
}

    public boolean isValid(){
      Node previousNode = null;
    for (Node node : this.transactions) {
        if (previousNode != null && !node.getPreviousKey().equals(previousNode.getKey())) {
            return false;
        }
        previousNode = node;
    }
    return true;
}

    public Node firstInconsistency() {
      Node previousNode = null;
 for (Node node : this.transactions) {
     if (previousNode != null && !node.getPreviousKey().equals(previousNode.getKey())) {
         return node;
     }
     previousNode = node;
 }
 return null;
}

    public String findInconsistentField(Node node){
        return node.findInconsistency(this.chainKey);
    }

    public void addTransaction(String type, String date, double amount) throws Exception{
        if(this.transactions.size() == 0){
            this.transactions.add(new Node(type, date, amount, this.chainKey));
        }else{
            Node previousNode = this.transactions.getLast();
            this.transactions.add(new Node(type, date, amount, previousNode));
        }
    }
}
