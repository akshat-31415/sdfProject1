package MyInfArith;
import arbitraryarithmetic.*;

public class MyInfArith {
    public static void main(String[] args){
        String mode = args[0];
        String op = args[1];
        if(mode.equals("int")){
            AInteger x1 = AInteger.parse(args[2]);
            AInteger x2 = AInteger.parse(args[3]);
            AInteger y;

            if(op.equals("add")){
                y = x1.add(x2);
            }
            else if(op.equals("sub")){
                y = x1.subtract(x2);
            }
            else if(op.equals("mul")){
                y = x1.multiply(x2);
            }
            else if(op.equals("div")){
                y = x1.divide(x2);
            }
            else{
                y = new AInteger();
            }
    
            System.out.println(y.getValue());
        }
        else{
            AFloat x1 = AFloat.parse(args[2]);
            AFloat x2 = AFloat.parse(args[3]);
            AFloat y;

            if(op.equals("add")){
                y = x1.add(x2);
            }
            else if(op.equals("sub")){
                y = x1.subtract(x2);
            }
            else if(op.equals("mul")){
                y = x1.multiply(x2);
            }
            else if(op.equals("div")){
                y = x1.divide(x2);
            }
            else{
                y = new AFloat();
            }
    
            System.out.println(y.getValue());
        }
        

        
    }
}
