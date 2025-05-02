package arbitraryarithmetic;
/*
 * Arbitrary precision integer class that supports addition,subtraction, multiplication and division.
 * SDF (CS1023 final project)
 * Author- Akshat Banzal
 */

public class AInteger {
    private boolean isNegative; // to store if number is negative or not

    private String Value;  // The absolute value of integer, with zeroes in the beginning truncated

    // Default constructor (Initializes to 0)
    public AInteger(){
        this.isNegative = false;
        this.Value = "0";
    }


/*Constructor AInteger(String s) that initializes the instance by the number whose string representation is given by ’s’. Eg: AInteger(“-34534536454”);        
 */
    public AInteger(String value) {
        // check for empty string
        if (value == null || value.isEmpty()){
            throw new IllegalArgumentException("Value of integer cannot be empty");
        }

        value = value.trim(); //remove trailing whitespaces
        // Check and assign the sign of string
        if (value.charAt(0) == '-'){
            this.isNegative = true;
            value = value.substring(1);
        }
        else{
            this.isNegative = false;
            if (value.charAt(0) == '+'){
                value = value.substring(1);
            }
        }
        value = removeLeadingZeroes(value);
        this.Value = value;
    }

    public AInteger(AInteger instance){
        if (instance==null || instance.Value == null){
            throw new IllegalArgumentException("Value cannot be null");
        }
        this.isNegative = instance.isNegative;
        this.Value = instance.Value;
    }

    public static AInteger parse (String value){
        return new AInteger(value);
    }

    private AInteger makePos(){
        // A function that takes in a negative integer String and removes the leading '-' to make it positive.

        return AInteger.parse(this.Value.substring(1));
    }

    private static String removeLeadingZeroes(String value){
        int i = 0 ;
        while (i < value.length() && value.charAt(i)=='0') {
            i++;
        }
        String result = value.substring(i);
        return result.isEmpty() ? "0" : result;
    }

    private boolean isZero(){
        // Checks if the value of the Object is zero or not.

        return (this.Value.equals("0") || this.Value.equals("-0"));
    }

    private boolean isNeg(){
        // A function that check whether the current value is negative.

        return this.Value.charAt(0) == '-';
    }

    private static int compareStrings(String a, String b) {
        boolean aNegative = a.startsWith("-");
        boolean bNegative = b.startsWith("-");
    
        // Remove signs before stripping leading zeros
        a = aNegative ? a.substring(1) : a;
        b = bNegative ? b.substring(1) : b;
    
        // Remove leading zeroes
        a = removeLeadingZeroes(a);
        b = removeLeadingZeroes(b);

        if (a.equals("0")) aNegative = false;
        if (b.equals("0")) bNegative = false;

        if (aNegative && !bNegative) return -1;
        if (!aNegative && bNegative) return 1;

        if (a.length() > b.length()) return aNegative ? -1 : 1;
        if (b.length() > a.length()) return aNegative ? 1 : -1;

    
        for (int i = 0; i < a.length(); i++) {
            int aDigit = Character.getNumericValue(a.charAt(i));
            int bDigit = Character.getNumericValue(b.charAt(i));
            if (aDigit > bDigit) return aNegative ? -1 : 1;
            if (aDigit < bDigit) return aNegative ? 1 : -1;

        }
        return 0;
    }

    public String getValue(){
        // Accessor function to return the value of the object.

        return this.Value;
    }

    private static String addStrings(String a, String b){
        if (a.length() < b.length()) {
            String temp = a;
            a = b;
            b = temp;
        }

        StringBuilder sum = new StringBuilder();
        int carry = 0;

        int p = a.length() - 1;
        int q = b.length() - 1;

        while (q >= 0) {
            int digitA = a.charAt(p) - '0';
            int digitB = b.charAt(q) - '0';
            int total = digitA + digitB + carry;
            sum.append(total % 10);
            carry = total / 10;
            p--;
            q--;
        }

        while (p >= 0) {
            int digitA = a.charAt(p) - '0';
            int total = digitA + carry;
            sum.append(total % 10);
            carry = total / 10;
            p--;
        }

        if (carry > 0) {
            sum.append(carry);
        }

        String resultStr = sum.reverse().toString();
        return resultStr;
    }

    private static String subtractStrings(String a, String b){
        
        a = removeLeadingZeroes(a);
        b = removeLeadingZeroes(b);

        // boolean negative = false;

        if (compareStrings(a, b) == -1) {
            // negative = true;
            String temp = a;
            a = b;
            b = temp;
        }

        int p = a.length() - 1;
        int q = b.length() - 1;
        StringBuilder result = new StringBuilder();
        int borrow = 0;

        while (q >= 0) {
            int aDigit = Character.getNumericValue(a.charAt(p));
            int bDigit = Character.getNumericValue(b.charAt(q));
            int diff = aDigit - bDigit - borrow;

            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }

            result.append(diff);
            p--;
            q--;
        }

        while (p >= 0) {
            int aDigit = Character.getNumericValue(a.charAt(p));
            int diff = aDigit - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result.append(diff);
            p--;
        }

        String output = removeLeadingZeroes(result.reverse().toString());
        if (output.isEmpty()) output = "0";

        return output; 
    }

    private static String singleDigitMultiply(String numStr, int digit) {
        StringBuilder result = new StringBuilder();
        int carry = 0;
        for (int i = numStr.length() - 1; i >= 0; i--) {
            int numDigit = Character.getNumericValue(numStr.charAt(i));
            int product = numDigit * digit + carry;
            result.append(product % 10);
            carry = product / 10;
        }
        if (carry > 0) {
            result.append(carry);
        }
        return result.reverse().toString();
    }

    private static String multiplyStrings(String a, String b){
        a = removeLeadingZeroes(a);
        b = removeLeadingZeroes(b);

        if (a.length() < b.length()) {
            String temp = a;
            a = b;
            b = temp;
        }

        String result = "0";
        for (int i = b.length() - 1; i >= 0; i--) {
            int bDigit = Character.getNumericValue(b.charAt(i));
            String partialProduct = singleDigitMultiply(a, bDigit);
            for (int j = 0; j < b.length() - 1 - i; j++) {
                partialProduct += "0";
            }
            result = addStrings(result, partialProduct);
        }
        return result;
    }

    private static String divideStrings(String a, String b){
        a = removeLeadingZeroes(a);
        b = removeLeadingZeroes(b);

        if (b.equals("0")) {
            return "Error: Division by zero";
        }
        if (compareStrings(a, b) == -1) {
            return "0";
        }

        StringBuilder quotient = new StringBuilder();
        StringBuilder remainder = new StringBuilder();

        for (int i = 0; i < a.length(); i++) {
            remainder.append(a.charAt(i));
            remainder = new StringBuilder(removeLeadingZeroes(remainder.toString()));
            int count = 0;
            while (compareStrings(remainder.toString(), b) >= 0) {
                remainder = new StringBuilder(subtractStrings(remainder.toString(), b));
                count++;
            }
            quotient.append(count);
        }

        return removeLeadingZeroes(quotient.toString());
    }

    public AInteger add(AInteger other){
        // Adds two AInteger objects to give another AInteger sum.

        if(this.isZero() && other.isZero()){
            return AInteger.parse("0");     // Zero case - return 0.
        }
        if(!(other.isNeg() || this.isNeg())){
            return AInteger.parse(addStrings(this.Value, other.Value));      // Both positive case.
        }
        else if(this.isNeg() && !other.isNeg()){
            return other.subtract(this.makePos());      // (-a) + b case where a,b >= 0.
        }
        else if(!this.isNeg()){
            return this.subtract(other.makePos());      // a + (-b) case here.
        }
        else{
            return AInteger.parse("-"+this.makePos().add(other.makePos()).Value);   // (-a) + (-b) case.
        }

    }

    public AInteger subtract(AInteger other){
        // Subtracts two AInteger objects to give their AInteger difference.

        if(this.isZero() && other.isZero()){
            return AInteger.parse("0");     // 0 case where a = b = 0.
        }
        if(!(other.isNeg() || this.isNeg())){           // a - b case.
            
            if(compareStrings(this.Value, other.Value)==1){
                return AInteger.parse(subtractStrings(this.Value, other.Value));     // if a > b return a - b.
            }
            else{
                return AInteger.parse("-"+subtractStrings(other.Value, this.Value));     // else return -(b - a).
            }
        }
        else if(this.isNeg() && !other.isNeg()){
            return AInteger.parse("-"+this.makePos().add(other).Value);     // (-a) - b case.
        }
        else if(!this.isNeg()){
            return this.add(other.makePos());           // a - (-b) case.
        }
        else{
            return other.makePos().subtract(this.makePos());        // (-a) - (-b) case.
        }
    }

    public AInteger multiply(AInteger other){
        // Multiplies two AIntegers to give their AInteger product.

        if(this.isZero() || other.isZero()){
            return AInteger.parse("0");     // If either a or b are 0, then return 0.
        }
        if((this.isNeg() && other.isNeg()) || !(this.isNeg() || other.isNeg())){    // If both have the same sign, return the positive product.

            if(!this.isNeg()){
                return AInteger.parse(multiplyStrings(this.Value, other.Value));     // a * b case.
            }
            else{
                return AInteger.parse(multiplyStrings(this.makePos().Value, other.makePos().Value));     // (-a) * (-b) case.
            }
        }
        else{                                                                       // Return the negative product if signs differ.
            
            if(this.isNeg()){
                return AInteger.parse("-" + other.multiply(this.makePos()).Value);      // (-a) * b case.
            }
            else{
                return AInteger.parse("-" + this.multiply(other.makePos()).Value);      // a * (-b) case.
            }
        }
    }

    public AInteger divide(AInteger other){
        // Divides two AIntegers to give their AInteger quotient.

        if(other.Value.equals("0")){
            throw new ArithmeticException("Error: Division by zero.");      // If b is 0, then throw an exception.
        }
        else if(this.Value.equals("0")){
            return AInteger.parse("0");                             // If a is 0, then return 0.
        }
        else{

            if((this.isNeg() && other.isNeg()) || !(this.isNeg() || other.isNeg())){    // If both have the same sign, return the positive coefficient.
                
                if(!this.isNeg()){
                    return AInteger.parse(divideStrings(this.Value, other.Value));   // a / b case.
                }
                else{
                    return AInteger.parse(divideStrings(this.makePos().Value, other.makePos().Value));   // (-a) / (-b) case.
                }
            }
            else{                   // Negative quotient if signs differ.

                if(this.isNeg()){
                    return AInteger.parse("-" + this.makePos().divide(other).Value);        // (-a) / b case.
                }
                else{
                    return AInteger.parse("-" + this.divide(other.makePos()).Value);        // a / (-b) case.
                }
            }
        }
    }

}
