package tests;

import transactions.*;

public class Test1 {
    public static boolean call(int testPoints) throws Exception {
        System.out.println("---------------------------------------- TEST 01 ----------------------------------------");
        Chain chain = new Chain("asdiuashdi38337asckn");
        System.out.println("Initializing chain with key: 'xxxajshswwkwwhwhwjwj' ............................. TEST OK");
        chain.addTransaction("DE", "2023-08-12 21:44:04", 1000.00);
        System.out.println("Adding a single transaction...................................................... TEST OK");
        String nodeKey = chain.transactionAt(0).getKey();
        if(nodeKey.equals("9+'9@*3%E6?eXed>:)B@$8).:&8-).SXSSW7%8@A6")){
            System.out.println("Verifying node key. It should be 9+'9@*3%E6?eXed>:)B@$8).:&8-).SXSSW7%8@A6 ...... TEST OK");
        }else {
            System.out.println("Verifying node key. It should be 9+'9@*3%E6?eXed>:)B@$8).:&8-).SXSSW7%8@A6 .. TEST FAILED");
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
