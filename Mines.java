/*1η Εργασία Αλγορίθμων
* Για την λύση του προβλήματος εύρεσης κοντινότερου μονοπατιού που να μην περνάει μέσα απο το ναρκοπέδιο υλοποιήθηκε ο αλγόριθμος
* quickhull που παίρνει όρισμα ένα σύνολο σημειών (ναρκών στο πρόβλημα μας ) κι επιστρέφει όλα τα σημεία που σχηματίζουν το κυρτό περίβλημα .
* Για την αποθήκευση των σημείων χρησιμοποιήθηκε η έτοιμη δομή της java Point και η δομή Arraylist .
* Κομμάτι κώδικα απο τρίτους : Η ακρίβεια και στρογγυλοποίηση στο 5ο δεκαδικό πάρθηκε έτοιμη από :
*  https://stackoverflow.com/questions/153724/how-to-round-a-number-to-n-decimal-places-in-java.
*    */

import java.awt.*;
import java.io.File;
import java.util.*;

public class Mines {


    private static ArrayList<Point> data = new ArrayList<>(); // Περιέχει όλα τα αρχικά δεδομένα από το αρχείο
    private static ArrayList<Point> solution = new ArrayList<>(); // Περιέχει όλα τα στοιχεία της λύσης
   /* Δημιουργήθηκε συγκριτής για την ταξινόμηση των δεδομένων με βάση την χ μεταβλητή
      μέσω της συνάρτησης .sort() που περιέχεται στην κλάση Collections */
    private Comparator<Point> comp = new Comparator<Point>()
    {
        @Override
        public int compare(Point x1, Point x2)
        {
            return Integer.compare(x1.x, x2.x);
        }
    };
    /* Συνάρτηση που υπολογίζει την ευκλείδια απόσταση μεταξύ δυο σημείων ,
       χρησιμοποιείται για τον υπολογισμό του μήκους του μονοπατιού λύσης .*/
    private double getDistance(Point A, Point B){
        return Math.sqrt(Math.pow((B.y-A.y),2)+Math.pow((B.x-A.x),2));
    }
    /* Συνάρτηση για την εμφάνιση της τελικής λύσης . Βάζουμε όλα τα σημεία της λύσης στο μονοπάτι που ανήκει το καθένα
       βάσει της μεταβλητής y κι έπειτα υπολογίζουμε για κάθε μονοπάτι το μήκος του . Τελος , κάνουμε τις απαραίττητες
       μορφοποιήσεις και εμφανίσεις .
    *   */
   private void showSolution(){
        double leftdistance=0;
        double rightdistance=0;
       ArrayList<Point> rightsidesolution = new ArrayList<>(); // Περιέχει όλα τα στοιχεία του δεξιού (κάτω ) μονοπατιού
       ArrayList<Point> leftsidesolution = new ArrayList<>();  // Περιέχει όλα τα στοιχεία του αριστερού (πάνω ) μονοπατιού
       /*Ελέγχουμε αν είναι μικρότρερο το y της αρχής ή του τέλους ώστε να το χρησιμοποιήσουμε σαν μέτρο
       * σύγκρισης (για όλα τα σημεία που υπάρχουν στην Arraylist solution) για να διαπιστώσουμε σε ποιό από τα
       * δύο μονοπάτια ανήκουν .  */
        int y=solution.get(0).y;
        if(solution.get(1).y<y){
            y=solution.get(1).y;
        }
        // Διαχωρισμός των στοιχείων στο μονοπάτι που ανήκουν , το i=2 διότι αφήνουμε απ'έξω τα στοιχεία αρχής
       // και τέλους διότι ανήκουν και θα μπουν και στα δύο μονοπάτια  (γραμμές 64-67)

       for(int i=2;i<solution.size();i++){
           Point point = solution.get(i);
           if(point.y<y){
               rightsidesolution.add(point);
           }
           else{
               leftsidesolution.add(point);
           }
       }

       rightsidesolution.add(solution.get(0));
       rightsidesolution.add(solution.get(1));
       leftsidesolution.add(solution.get(0));
       leftsidesolution.add(solution.get(1));
       // Ταξινομούμε το κάθε μονοπάτι για να υπολογίσουμε το μήκος του κάθε μονοπατιού
       // και να το έχουμε έτοιμο για την εμφάνιση στην συνέχεια .
       leftsidesolution.sort(comp);
       rightsidesolution.sort(comp);
       for(int i=0;i<rightsidesolution.size()-1;i++){
           rightdistance+=getDistance(rightsidesolution.get(i),rightsidesolution.get(i+1));
       }
       for(int i=0;i<leftsidesolution.size()-1;i++){
           leftdistance+=getDistance(leftsidesolution.get(i),leftsidesolution.get(i+1));
       }

       //Έλεγχος ποιο μονοπάτι είναι μικρότερο και εμφάνιση του μήκους και του μονοπατιού

       if(rightdistance>leftdistance){
           System.out.println("The shortest distance is "+Math.floor(leftdistance*100000+0.5)/100000);
           System.out.print("The shortest path is:");
           for(int i=0;i<leftsidesolution.size();i++){
               Point point = leftsidesolution.get(i);
               if(i==leftsidesolution.size()-1){
                   System.out.print("("+point.x+","+point.y+")");
               }
               else {
                   System.out.print("(" + point.x + "," + point.y + ")-->");
               }
           }
       }
       else{
           System.out.println("The shortest distance is "+Math.floor(rightdistance*100000+0.5)/100000);
           System.out.print("The shortest path is:");
           for(int i=0;i<rightsidesolution.size();i++){
               Point point = rightsidesolution.get(i);
               if(i==rightsidesolution.size()-1){
                   System.out.print("("+point.x+","+point.y+")");
               }
               else {
                   System.out.print("(" + point.x + "," + point.y + ")-->");
               }
           }
       }


   }
    /* Πρώτη συνάρτηση για την υλοποιήση του αλγορίθμου , υλοποιούνται τα βήματα πριν την αναδρομή
    *  Παίρνει όρισμα όλα τα δεδομένα από το αρχείο τα οποία είναι ταξινομημένα και εισάγει στην λύση
    *  κατευθείαν την αρχή και το τέλος . Έπειτα σπάμε το συνόλο των υπόλοιπων στοιχείων σε αριστερά (πάνω)
    *  και δεξιά (κάτω) σε δύο σύνολα και καλούμε για το καθένα από αυτά την ανδρομική συνάρτηση . */
   private void quickhullalgorithm(ArrayList<Point> data){
       Point pointA=data.get(0);
       solution.add(pointA);
       data.remove(pointA);
       Point pointB=data.get(data.size()-1);
       solution.add(pointB);
       data.remove(pointB);

       ArrayList<Point> leftPoints = new ArrayList<>();
       ArrayList<Point> rightPoints = new ArrayList<>();

       for(int i =0; i <data.size();i++){
           Point Point = data.get(i);
           if(leftright(pointA,pointB,Point)==1){
               leftPoints.add(Point);
           }
           if(leftright(pointA,pointB,Point)==-1){
               rightPoints.add(Point);
           }
       }
        /*Σημαντική λεπτομέρεια : Καλούμε την συνάρτηση με την σωστή φόρα της ευθείας και το σύνολο των στοιχείων
        * που βρίσκονται αριστερά της φοράς . Α->Β με τα στοιχεία που βρίσκονται από αριστερά (πάνω )
        * και Β->Α με τα στοιχεία που βρίσκονται δεξιά (κάτω) από τον ΑΡΧΙΚΟ διαχωρισμό άρα τώρα αριστερά για την ευθεία Β->Α*/
       quickhullrec(pointA,pointB,leftPoints);
       quickhullrec(pointB,pointA,rightPoints);

   }
    /*Ανδρομική συνάρτηση του αλγορίθμου , παίρνει όρισμα τα δύο στοιχεία που ορίζουν την φορά της ευθείας
    * και το σύνολο που περιέχει τα στοιχεία που βρίσκονται αριστέρα (πάνω της ευθείας).
    * Βήματα αναδρομής : Πρώτα ελέγχονται οι δυο τερματικές συνθήκες ,
    * αν το σύνολο είναι άδειο ή αν το σύνολο έχει ένα στοιχείο το οποίο προφανώς αποτελρί κομμάτι της λύσης
    * και το εισάγουμε κατευθείαν στην λύση . Αλλιώς ψάχνουμε το στοιχείο που δημιουργεί το τρίγωνο μεγαλύτερου
    * εμβαδού σε σχέση με τα Α,Β το εισάγουμε στην λύση κι έπειτα δημιουργούμε δύο σύνολα τα οποία περιέχουν τα σημεία
    * αριστερά από τις άλλες δυο πλευρές του τριγώνου που δημιουργήσαμε . Τέλος καλούμε ξανά την συνάρτηση με ορίσματα τις
    * πλευρές και το σύνολο με τα σημεία αριστερά της καθεμιας . */
    private void quickhullrec(Point A, Point B , ArrayList<Point> narkes){
        int indexofmaxpoint=0;
        double maxarea=0;
        if(narkes.size()==0){
            return;
        }
        if(narkes.size()==1){
            Point point=narkes.get(0);
            solution.add(point);
            return;
        }
        for(int i=0;i<narkes.size();i++){
            Point point = narkes.get(i);
            if(getArea(A,B,point)>maxarea){
                maxarea=getArea(A,B,point);
                indexofmaxpoint=i;
            }
        }
        Point point=narkes.get(indexofmaxpoint);
        narkes.remove(point);
        solution.add(point);

        ArrayList<Point> leftAPoint = new ArrayList<>();

        for(int i=0;i<narkes.size();i++){
            Point Point = narkes.get(i);
            if(leftright(A,point,Point)==1){
                leftAPoint.add(Point);
            }
        }
        ArrayList<Point> leftPointB = new ArrayList<>();

        for(int i=0;i<narkes.size();i++){
            Point Point = narkes.get(i);
            if(leftright(point,B,Point)==1){
                leftPointB.add(Point);
            }
        }
        quickhullrec(A,point,leftAPoint);
        quickhullrec(point,B,leftPointB);
    }

    /*Υπολογίζει με την ορίζουσα που υπάρχει στις διαφάνειες το εμβαδό του τριγώνου ,
    * επίσης ανάλογα του προσήμο μας δείχνει αν το C βρίσκεται αριστερά ή δεξιά της
    * ευθείας που σχηματίζουν τα Α και Β .*/
   private double getArea(Point A, Point B , Point C){
       double area=0;
       return area=(A.x*B.y+C.x*A.y+B.x*C.y-C.x*B.y-B.x*A.y-A.x*C.y)/2.0;
   }


    /*Καλέι την συνάρτηση getArea() κι ανάλογα με το πρόσημο επιστρέφει τον κατάλληλο ακέραιο
    * για να διαχωρίζουμε τα στοιχεία στα κατάλληλα σύνολα , δεξιά ή αριστερά της ευθείας A->B που εξετάζουμε. */
    private int leftright(Point A, Point B , Point C){
       double area = getArea(A,B,C);
        if(area>0){
            return 1; // αριστέρα της ευθείας
        }
        else if(area<0){
            return -1; // δεξιά της ευθείας
        }
        return 0; // πάνω στην ευθεία
    }


    /*Παίρνει σαν όρισμα το όνομα του αρχείου και εισάγει τα στοιχεία που περιέχει στην
    * Arraylist data . Έπειτα τα ταξινομούμε .   */
    void adddata(String filename) {
        int x;
        try {
            Scanner scanner = new Scanner(new File("./" + filename)); // σχετικό μονοπάτι για εύρεση τοποθεσίας του αρχείου
            while (scanner.hasNextInt()) {
                x = scanner.nextInt();
                data.add(new Point(x, scanner.nextInt()));
            }
            data.sort(comp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Mines mines = new Mines();
        mines.adddata(args[0]); // παίρνουμε σαν όρισμα το όνομα του αρχείου
        mines.quickhullalgorithm(data); // καλούμε τον αλγόριθμο με όρισμα τα στοιχεία του αρχείου
        mines.showSolution(); // Εμφανίζουμε την λύση


    }
}
