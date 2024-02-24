package transactions;

import java.util.LinkedList;

/* This class represents a chain of transactions. Each transaction will be 
* linked to the previous one in order to maintain consistency. Please modify
* only the specified methods
*/

public class Chain {
    private LinkedList<Node> transactions;
    private String chainKey;

    public Chain(String chainKey){
        this.chainKey = chainKey;
        this.transactions = new LinkedList<>();
    }

    public Node findByIndex(int index){
        // Corrección: Verificar correctamente el rango del índice
        if(index >= 0 && index < this.transactions.size()){
            return this.transactions.get(index);
        }
        return null;
    }

    public Node transactionAt(int index){
        return this.transactions.get(index);
    }

    public Node findByKey(String key){
        for(Node node : this.transactions){
            if(node.getKey().equals(key)){
                return node;
            }
        }
        return null;
    }

    public double getBalance(){
        if(!isValid()){
            return 0.0;
        }
        return transactions.stream().mapToDouble(Node::getAmount).sum();
    }

    public boolean isValid(){
        String currentKey = chainKey;
        for (Node node : transactions) {
            if (!node.isValid(currentKey)) {
                return false;
            }
            currentKey = node.getTransactionKey();
        }
        return true;
    }

    public Node firstInconsistency() {
        String currentKey = chainKey;
        for (Node node : transactions) {
            if (!node.isValid(currentKey)) {
                return node;
            }
            currentKey = node.getTransactionKey();
        }
        return null;
    }

    public String findInconsistentField(Node node){
        return node.findInconsistency(this.chainKey);
    }

    public void addTransaction(String type, String date, double amount) throws Exception{
        if(this.transactions.isEmpty()){
            this.transactions.add(new Node(type, date, amount, this.chainKey));
        }else{
            Node previousNode = this.transactions.getLast();
            this.transactions.add(new Node(type, date, amount, previousNode.getKey()));
        }
    }
}
