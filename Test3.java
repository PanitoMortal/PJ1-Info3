package tests;

import java.text.DecimalFormat;

import transactions.*;

public class Test3 {
    public static boolean call(int testPoints) throws Exception {
        System.out.println("---------------------------------------- TEST 03 ----------------------------------------");
        Chain chain = new Chain("asdiuashdi38337asckn");
        System.out.println("Initializing chain with key: 'asdiuashdi38337asckn' ............................. TEST OK");
        chain.addTransaction("DE", "2023-08-12 21:44:04", 1000.00);
        chain.addTransaction("DE", "2023-08-13 21:44:04", 2000.00);
        chain.addTransaction("DE", "2023-08-14 21:44:04", 3000.50);
        chain.addTransaction("WH", "2023-08-15 21:44:04", 150.33);
        System.out.println("Adding several transactions (4).................................................. TEST OK");
        if(chain.isValid()){
            System.out.println("Verifying chain validity. It should be valid .................................... TEST OK");
        }else {
            System.out.println("Verifying chain validity. It should be valid ................................ TEST FAILED");
            return fail();
        }

        DecimalFormat df = new DecimalFormat("#.##");      
        double balance = Double.valueOf(df.format(chain.getBalance()));
        if(balance == 5850.17){
            System.out.println("Verifying chain balance. It should be 5,850.17 .................................. TEST OK");
        }else{
            System.out.println("Verifying chain balance. It should be 5,850.17 .............................. TEST FAILED");
            return fail();
        }

        chain.transactionAt(2).setAmount(3000.49);
        if(chain.isValid()){
            System.out.println("Verifying chain validity after manual alteration. It should be invalid ...... TEST FAILED");
            return fail();
        }else{
            System.out.println("Verifying chain validity after manual alteration. It should be invalid .......... TEST OK");
        }

        Node inconsistentNode = chain.firstInconsistency();
        if(inconsistentNode.getKey().equals(chain.transactionAt(2).getKey())){
            System.out.println("Checking first inconsistent transaction found. It should be the 3rd ............. TEST OK");
        }else{
            System.out.println("Checking first inconsistent transaction found. It should be the 3rd ......... TEST FAILED");
            return fail();
        }
        
        String inconsistentField = chain.findInconsistentField(inconsistentNode);
        if(inconsistentField.equals("AMOUNT")){
            System.out.println("Validating inconsistent field name. It should be AMOUNT ......................... TEST OK");
        } else {
            System.out.println("Validating inconsistent field name. It should be AMOUNT ..................... TEST FAILED");
            return fail();
        }

        return success(testPoints);
    }

    private static boolean success(int testPoints) {
        System.out.println("TEST PASSED ..................................................................... +" + testPoints + " pts");
        return true;
    }

    private static boolean fail(){
        System.out.println("TEST FAILED ...................................................................... +0 pts");
        return false;

    }
}
