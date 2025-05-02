package arbitraryarithmetic;

public class AFloat {
    private boolean isNegative; // to store if number is negative or not

    private String value;  // The absolute value of integer, with zeroes in the beginning truncated

    // Default constructor (Initializes to 0)
    public AFloat(){
        this.isNegative = false;
        this.value = "0.0";
    }

/*Constructor AFloat(String s) that initializes the instance by the number whose string representation is given by ’s’. Eg: AFloat(“-34534536454.68475”);        
 */
    public AFloat(String value) {
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
        this.value = value;
    }

    public AFloat(AFloat instance){
        if (instance==null || instance.value == null){
            throw new IllegalArgumentException("Value cannot be null");
        }
        this.isNegative = instance.isNegative;
        this.value = instance.value;
    }

    public static AFloat parse (String value){
        return new AFloat(value);
    }

    private static String removeLeadingZeroes(String value){
        int i = 0 ;
        while (i < value.length() && value.charAt(i)=='0') {
            i++;
        }
        String result = value.substring(i);
        return result.isEmpty() ? "0" : result;
    }

    private String trimZeroes(String s){
        // Function to trim the leading zeroes from the integer part and trailing zeroes from the fractional part in a float.
        
        String[] x = s.split("\\.");        // Store the integer and fractional parts separately,
        String result = "";

        boolean isNeg = false;  // Boolean to check if the number is negative.

        // Trim leading zeroes from the integer part (Not using trimIntZeros directly as it will return 0 for a number of the type -0.x).
        if(x[0].equals("0")){
            result = "0";           // Zero case.
        }
        else{
            // Check for negativity.
            if(x[0].charAt(0)=='-'){
                isNeg = true;
                x[0] = x[0].substring(1);       // Start trimming from the next character.
            }

            // Again, remove the leading zeroes.
            int index=0;
            while(index < x[0].length() && x[0].charAt(index)=='0'){
                index++;
            }
            if(index==x[0].length()){
                result = "0";
            }
            else{
                result = x[0].substring(index);
            }
        }

        if (x.length == 2) {
            result += ".";
            result += x[1];
        } else {
            result += ".0";  // or handle it in a way that fits your app's logic
        }
        
        return (isNeg && !result.equals("0.0")?"-":"") + result;        // Negativity check.
    }

    private boolean isZero(){
        // Function to check if the value of the current object is zero.

        String s = trimZeroes(this.value);
        return (s.equals("0.0") || s.equals("-0.0"));
    }

    private boolean isNeg(){
        // Function to check if the value of the curent object is negative.

        return this.value.charAt(0) == '-';
    }

    private AFloat makePos(){
        // Function which removes the leading '-' from the string and returns a new AFloat.

        return AFloat.parse(this.value.substring(1));
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

    private static int numDecimals(String value) {
        int index = value.indexOf('.');
        if (index == -1 || index == value.length() - 1) {
            return 0;  // No decimal point or nothing after it
        }
        return value.length() - index - 1;
    }

    private static String fillZeroes(String value, int required_digits){
        int i = required_digits-numDecimals(value);
        StringBuilder sb = new StringBuilder(value);
        while(i>=0){
            sb.append('0');
            i--;
        }
        return sb.toString();
    }

    public static String removeTrailingZeros(String numberStr) {
        if (!numberStr.contains(".")) {
            return numberStr; // No decimal point, return as-is
        }

        // Remove trailing zeros and optional trailing decimal point
        numberStr = numberStr.replaceAll("0+$", ""); // remove trailing zeroes
        numberStr = numberStr.replaceAll("\\.$", ""); // remove trailing dot if any

        return numberStr;
    }

    private static String addFloatStrings(String a, String b) {
        a = removeLeadingZeroes(a);
        b = removeLeadingZeroes(b);
    
        int numDecimalsA = numDecimals(a);
        int numDecimalsB = numDecimals(b);
        int resultDecimal = Math.max(numDecimalsA, numDecimalsB);
    
        // Pad both to have the same number of decimal digits
        if(numDecimalsA!=numDecimalsB){
            a = fillZeroes(a, resultDecimal);
            b = fillZeroes(b, resultDecimal);
        }
        // Remove decimal points
        int indexA = a.indexOf('.');
        if (indexA != -1) {
            a = a.substring(0, indexA) + a.substring(indexA + 1);
        }
    
        int indexB = b.indexOf('.');
        if (indexB != -1) {
            b = b.substring(0, indexB) + b.substring(indexB + 1);
        }
    
        // Add as integers
        String result = addStrings(a, b);
    
        // Insert decimal point
        if (resultDecimal > 0) {
            int insertPos = result.length() - resultDecimal;
            if (insertPos <= 0) {
                // Pad with leading zeros if needed
                while (insertPos < 0) {
                    result = "0" + result;
                    insertPos++;
                }
                insertPos = 1; // "0.xxx"
            }
            result = result.substring(0, insertPos) + "." + result.substring(insertPos);
        }
    
        return removeTrailingZeros(removeLeadingZeroes(result));
    }
    
    private static String subtractFloatString(String a, String b){
        a = removeLeadingZeroes(a);
        b = removeLeadingZeroes(b);

        boolean negative = false;
        
        int numDecimalsA = numDecimals(a);
        int numDecimalsB = numDecimals(b);
        int resultDecimal = Math.max(numDecimalsA, numDecimalsB);
    
        // Pad both to have the same number of decimal digits
        if(numDecimalsA!=numDecimalsB){
            a = fillZeroes(a, resultDecimal);
            b = fillZeroes(b, resultDecimal);
        }
        if(a.length()<b.length()){
            negative = true;
        }

        int indexA = a.indexOf('.');
        if (indexA != -1) {
            a = a.substring(0, indexA) + a.substring(indexA + 1);
        }
    
        int indexB = b.indexOf('.');
        if (indexB != -1) {
            b = b.substring(0, indexB) + b.substring(indexB + 1);
        }

        if (compareStrings(a,b)==-1){
            negative = true;
        }

        if (compareStrings(a,b)==0){
            return "0.0";
        }

        String result = subtractStrings(a, b);

        if (resultDecimal > 0) {
            int insertPos = result.length() - resultDecimal;
            if (insertPos <= 0) {
                // Pad with leading zeros if needed
                while (insertPos < 0) {
                    result = "0" + result;
                    insertPos++;
                }
                insertPos = 1; // "0.xxx"
            }
            result = result.substring(0, insertPos) + "." + result.substring(insertPos);
        }
    
        return negative ? "-" + removeTrailingZeros(removeLeadingZeroes(result)) : removeTrailingZeros(removeLeadingZeroes(result));
    }
    
    private static String multiplyFloatString(String a,String b){
        a = removeLeadingZeroes(a);
        b = removeLeadingZeroes(b);

        int resultDecimal = numDecimals(a) + numDecimals(b);

        int indexA = a.indexOf('.');
        if (indexA != -1) {
            a = a.substring(0, indexA) + a.substring(indexA + 1);
        }
    
        int indexB = b.indexOf('.');
        if (indexB != -1) {
            b = b.substring(0, indexB) + b.substring(indexB + 1);
        }

        String result = multiplyStrings(a,b);

        if (resultDecimal > 0) {
            int insertPos = result.length() - resultDecimal;
            if (insertPos <= 0) {
                // Pad with leading zeros if needed
                while (insertPos < 0) {
                    result = "0" + result;
                    insertPos++;
                }
                insertPos = 1; // "0.xxx"
            }
            result = result.substring(0, insertPos) + "." + result.substring(insertPos);
        }

        return removeTrailingZeros(removeLeadingZeroes(result));
    }

    private static String divideFloatStrings(String a, String b){
        a = removeLeadingZeroes(a);
        b = removeLeadingZeroes(b);

        if (b.equals("0.0") || b.equals("0")) {
            return "Error: Division by zero";
        }

        int numDecimalsA = numDecimals(a);
        int numDecimalsB = numDecimals(b);
        int balance = numDecimalsB - numDecimalsA;
        int indexA = a.indexOf('.');
        if (indexA != -1) {
            a = a.substring(0, indexA) + a.substring(indexA + 1);
        }
    
        int indexB = b.indexOf('.');
        if (indexB != -1) {
            b = b.substring(0, indexB) + b.substring(indexB + 1);
        }
        int precision = 50;
        a = fillZeroes(a, precision-1);
        balance-=precision;

        String result = divideStrings(a, b);
        // Insert decimal point according to balance
        int insertAt = result.length() + balance;  // shift from end

        if (insertAt <= 0) {
            // Need to prepend "0." and pad zeroes
            result = "0." + "0".repeat(-insertAt) + result;
        } else if (insertAt < result.length()) {
            result = result.substring(0, insertAt) + "." + result.substring(insertAt);
        } else {
            // Append zeroes if needed
            while (result.length() < insertAt) {
                result += "0";
            }
            result = result + ".0";  // Decimal for uniformity
        }
        int dotIndex = result.indexOf('.');
        if (dotIndex != -1 && result.length() > dotIndex + 31) {  // 1 for the dot + 30 digits
        result = result.substring(0, dotIndex + 31); // keep up to 30 digits after '.'
        }

        return removeTrailingZeros(removeLeadingZeroes(result));
    } 

    public AFloat add(AFloat other){
        // Adds two AFloat objects to give another AFloat sum.

        if(this.isZero() && other.isZero()){
            return new AFloat();                // Zero case - return 0.
        }
        else if(!(this.isNeg() || other.isNeg())){
            return AFloat.parse(addFloatStrings(this.value, other.value));        // a + b case, a,b >= 0.
        }
        else if(this.isNeg() && !other.isNeg()){
            return other.subtract(this.makePos());              // (-a) + b case.
        }
        else if(!this.isNeg()){
            return this.subtract(other.makePos());              // a + (-b) case.
        }
        else{
            return AFloat.parse("-" + this.makePos().add(other.makePos()).value);       // (-a) + (-b) case.
        }
    }

    public AFloat subtract(AFloat other){
        // Subtracts two AFloat objects to give thir AFloat difference.

        if(this.isZero() && other.isZero()){
            return new AFloat();                // Zero case - a = b = 0.
        }
        else if(!(this.isNeg() || other.isNeg())){      // Both positive case.

            return AFloat.parse(subtractFloatString(this.value, other.value));       // a > b : return a - b.
            
        }
        else if(this.isNeg() && !other.isNeg()){
            return AFloat.parse("-" + this.makePos().add(other).value);     // (-a) - b case.
        }
        else if(!this.isNeg()){
            return this.add(other.makePos());           // a - (-b) case.
        }
        else{
            return other.makePos().subtract(this.makePos());        // (-a) - (-b) case.
        }
    
    }

    public AFloat multiply(AFloat other){
        // Multiplies two AFloat objects and returns their AFloat product.

        if(this.isZero() || other.isZero()){
            return new AFloat();        // Zero case - return 0 if either a or b are zero.
        }
        else if(!(this.isNeg() || other.isNeg())){
            return AFloat.parse(multiplyFloatString(this.value, other.value));       // a * b case.
        }
        else if(this.isNeg() && !other.isNeg()){
            return AFloat.parse("-" + this.makePos().multiply(other).value);    // (-a) * b case.
        }
        else if(!this.isNeg()){
            return AFloat.parse("-" + this.multiply(other.makePos()).value);       // a * (-b) case.
        }
        else{
            return this.makePos().multiply(other.makePos());        // (-a) * (-b) case.
        }
    }

    public AFloat divide(AFloat other){
        // Divides the two AFloat objects and returns their AFloat result.

        if(this.isZero() || other.isZero()){
            if(other.isZero()){
                throw new ArithmeticException("Error: Division by zero.");      // if b = 0, then throw an exception.
            }
            else{
                return new AFloat();        // if a = 0, then return 0.
            }
        }
        else if(!(this.isNeg() || other.isNeg())){
            return AFloat.parse(divideFloatStrings(this.value, other.value));     // a / b case.
        }
        else if(this.isNeg() && !other.isNeg()){
            return AFloat.parse("-" + this.makePos().divide(other).value);      // (-a) / b case.
        }
        else if(!this.isNeg()){
            return AFloat.parse("-" + this.divide(other.makePos()).value);      // a / (-b) case.
        }
        else{
            return this.makePos().divide(other.makePos());      // (-a) / (-b) case.
        }
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

        boolean negative = false;

        if (compareStrings(a, b) == -1) {
            negative = true;
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

        return negative ? "-" + output : output;
    }

    public String getValue(){
        // Accessor function to return the value of the object.

        return this.value;
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

}

