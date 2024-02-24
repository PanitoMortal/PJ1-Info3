package tests;

import java.text.DecimalFormat;

import transactions.*;

public class Test2 {
    public static boolean call(int testPoints) throws Exception {
        System.out.println("---------------------------------------- TEST 02 ----------------------------------------");
        Chain chain = new Chain("kdkdkejejeixhddhd0s*");
        System.out.println("Initializing chain with key: 'asdiuashdi38337asckn' ............................. TEST OK");
        chain.addTransaction("DE", "2023-08-12 21:44:04", 10.00);
        chain.addTransaction("WH", "2023-08-13 21:44:04", 15.00);
        System.out.println("Adding several transactions (2).................................................. TEST OK");
        if(chain.isValid()){
            System.out.println("Verifying chain validity. It should be valid .................................... TEST OK");
        }else {
            System.out.println("Verifying chain validity. It should be valid ................................ TEST FAILED");
            return fail();
        }

        DecimalFormat df = new DecimalFormat("#.##");      
        double balance = Double.valueOf(df.format(chain.getBalance()));
        if(balance == -5.00){
            System.out.println("Verifying chain balance. It should be -5.00 ..................................... TEST OK");
        }else{
            System.out.println("Verifying chain balance. It should be -5.00 ................................. TEST FAILED");
            return fail();
        }

        Node inconsistentNode = chain.firstInconsistency();
        if(inconsistentNode == null){
            System.out.println("Finding inconsistent node. It should be null .................................... TEST OK");
        }else{
            System.out.println("Finding inconsistent node. It should be null ................................. TEST FAILED");
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
