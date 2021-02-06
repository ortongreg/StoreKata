package storekata;

public class App {
    public static void main(String[] args){
        String appResult = new AppLogic().run(args[0]);
        System.out.println(appResult);
    }
}
