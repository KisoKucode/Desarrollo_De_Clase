package modelos;

public class Main {
    public static void main(String[] args) {
        Ave c = new Canario();
        Ave a = new AvesTruz();
        
        System.out.print("Canario: ");
        c.realizarVuelo();
        
        System.out.print("Avestruz: ");
        a.realizarVuelo();
    }
}




