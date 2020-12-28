
// public, private, protected
// "Access control"
// public if you want to be accessible everywhere
// private if you want to access it only inside
// protected... dont worry ab it

// class, void, int, float, String, char, double
// "Type"
// class is an object, holds "methods" and "attributes"
// void refers to a method which doesn't return anything
// int integer (value from -2^31,2^31)

// functions vs variables
// int a = 3 is a variable because a has no parentheses after it
// int a() {return 3;} is a function because it has parentheses after it

// variable "exists" e.g. apple
// function is "just instructions", doesn't exist e.g. gravity

// ACCESS RETURN NAME(INPUTS) {
//
// }
//
// e.g. public static void main(String[] args) {...

// static applies to a class
// non-static (implied) applies to an instance
// instances are specific "instances" of a class (blueprint)
// e.g. Julian is an instance of a Human

public class TestClass {
    //this is a comment
    //das ist epic comment
    
    /*int a = 2;
    double b = 3.4;
    String name;
    
    public static int doStuff(int a) {
        //bunch of code that draws a circle
        return 2 * a;
    }
    
    public int doStuff2() {
        
    }*/
    
    public static void main(String[] args) {
        boolean initializingWorld = false;
        boolean angry = true;
        boolean override = true;
        int numTimes = 4;
        // booleans represent true/false, on/off, 1/0
        // = is assignment
        // == is equality (for primitives, things with lowercase types)
        // something.equals(somethingelse) is equality for objects/classes
        // === is...something else
        // && is "short-circuiting"
        // & is not short-circuiting (usually not-preferred)
        // ^ is xor
        if ((initializingWorld && angry==false) || override==true) {
            //loops:
            //for and while (and a do while)
            
            //for (starting condition; continuation condition; what happens at the end of each loop) {}
            //place a block for the number of blocks in your inventory
            //++ means increment by 1
            // i++ and i+=1 and i=i+1 same thing
            for (int i = 0; i < numTimes; i += 1) {
                System.out.println("Hello world!");
            }
            
            //two things which do the same thing
            //{
            int[] listOfInts = {2,3,4,5,65};
            for (int i = 0; i < listOfInts.length; i++) {
                System.out.println(listOfInts[i]);
            }
            //}
            
            
            //works best for when you want to loop over everything in a collection
            //{
            for (int intInList : listOfInts) {
                System.out.println(intInList);
            }
            //}
            
            
            //while (condition) usually used for repeating something until a condition occurs
            //door stays closed while dialog is running
            while (override) {
                System.out.println("Overridden!");
                override = false;
            }
            
            
            //same as while, but runs the code before checking
            do {
                System.out.println("Overridden!");
                override = false;
            } while (override);
            
            
        } else if(angry) {
            System.out.println("Goodbye world! >:(");
        } else {
            System.out.println("Goodbye world!");
        }
        
        
        //switch is a special if checking one thing againt a variety of possibilities
        int numChocolate = 2;
        switch (numChocolate) {
        case 0:
            //code
            //code
            break;
        case 1:
            break;
        case 2:
            break;
        case 3:
            break;
        default:
            System.out.println("Lot of chocolate!");
        }
    }
}





//public class Human {
//    public int height;
//    
//    public Human(int height) {
//        this.height = height;
//    }
//    
//    public int getHeight() {
//        return this.height;
//    }
//    
//    //static function corresponds to the class
//    public static String getGeneralName(Human thisHuman) {
//        return thisHuman.getHeight();
//    }
//    
//    a = Human(30);
//    a.getHeight();
//    Human.getGeneralName();
//    
//    
//}
