package storekata;

import storekata.repositories.ItemRepositoryImpl;

public class App {
    public static void main(String[] args){
        String appResult = new AppLogic(
                new InputParser(new ItemRepositoryImpl()),
                new PurchaseTotalCalculator()
        ).run(args[0]);

        System.out.println(appResult);
    }
}
