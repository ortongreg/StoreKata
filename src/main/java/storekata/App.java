package storekata;

import storekata.repositories.DiscountRepositoryImpl;
import storekata.repositories.ItemRepositoryImpl;

public class App {
    public static void main(String[] args){
        try{
            String appResult = new AppLogic(
                    new InputParser(new ItemRepositoryImpl()),
                    new PurchaseTotalCalculator(new DiscountRepositoryImpl())
            ).run(args[0]);

            System.out.println(appResult);
        }catch (Exception e){
            System.out.println("unable to calculate total");
        }
    }
}
