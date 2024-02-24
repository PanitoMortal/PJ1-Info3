package tests;

import java.text.DecimalFormat;

import transactions.*;

public class Test4 {
    public static boolean call(int testPoints) throws Exception {
        System.out.println("---------------------------------------- TEST 04 ----------------------------------------");
        Chain chain = new Chain("HOLA MUNDO");
        System.out.println("Initializing chain with key: 'HOLA MUNDO' ....................................... TEST OK");
        chain.addTransaction("DE", "2023-08-12 21:44:04", 100.00);
        chain.addTransaction("WH", "2023-08-13 21:44:04", 200.00);
        chain.addTransaction("DE", "2023-08-14 21:44:04", 11.51);
        chain.addTransaction("DE", "2023-08-15 21:44:04", 25.33);
        chain.addTransaction("DE", "2023-08-16 21:44:04", 2000.60);
        chain.addTransaction("WH", "2023-08-16 21:44:04", 67.15);
        System.out.println("Adding several transactions (6).................................................. TEST OK");
        if(chain.isValid()){
            System.out.println("Verifying chain validity. It should be valid .................................... TEST OK");
        }else {
            System.out.println("Verifying chain validity. It should be valid ................................ TEST FAILED");
            return fail();
        }

        DecimalFormat df = new DecimalFormat("#.##");      
        double balance = Double.valueOf(df.format(chain.getBalance()));
        if(balance == 1870.29){
            System.out.println("Verifying chain balance. It should be 1,870.29 .................................. TEST OK");
        }else{
            System.out.println("Verifying chain balance. It should be 1,870.29 .............................. TEST FAILED");
            return fail();
        }

        chain.transactionAt(1).setType("DE");
        if(chain.isValid()){
            System.out.println("Verifying chain validity after manual alteration. It should be invalid ...... TEST FAILED");
            return fail();
        }else{
            System.out.println("Verifying chain validity after manual alteration. It should be invalid .......... TEST OK");
        }

        Node inconsistentNode = chain.firstInconsistency();
        if(inconsistentNode.getKey().equals(chain.transactionAt(1).getKey())){
            System.out.println("Checking first inconsistent transaction found. It should be the 2nd ............. TEST OK");
        }else{
            System.out.println("Checking first inconsistent transaction found. It should be the 2nd ......... TEST FAILED");
            return fail();
        }
        
        String inconsistentField = chain.findInconsistentField(inconsistentNode);
        if(inconsistentField.equals("TYPE")){
            System.out.println("Validating inconsistent field name. It should be TYPE ........................... TEST OK");
        } else {
            System.out.println("Validating inconsistent field name. It should be TYPE ....................... TEST FAILED");
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
