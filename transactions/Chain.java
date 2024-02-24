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
        // TODO: Implement this function. Return the balance
        //prueba
        // of the chain only if it is valid. If it is invalid, return 0.0
        if(!isValid()){
        return 0.0;
    }
    return transactions.stream().mapToDouble(Node::getAmount).sum();
    }
    
    public boolean isValid(){
        // TODO: Implement this function. Return true or false depending on
        // whether all nodes in the chain are valid or not. 
        if(this.transactions.isEmpty()){
            return true;
        }
        String expectedKey = this.chainKey;
        for(Node node : this.transactions){
            if(!node.isValid() || !node.getPreviousKey().equals(expectedKey)){
                return false;
            }
            expectedKey = node.getKey();
        }
        return true;
    }

    public Node firstInconsistency() {
        //TODO: Imeplement this function. Navigate through all nodes in the 
        // chain, finding the first one that has an inconsistency, and return it.
        // If no node is found, return null
         String expectedKey = this.chainKey;
         for(Node node : this.transactions){
             if(!node.isValid() || !node.getPreviousKey().equals(expectedKey)){
                return node;
            }
            expectedKey = node.getKey();
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
