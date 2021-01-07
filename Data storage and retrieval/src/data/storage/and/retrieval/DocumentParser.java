package data.storage.and.retrieval;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DocumentParser {

    private List<String[]> termsDocsArray = new ArrayList<String[]>();
    //This variable will hold all terms of each document in an array.
    private List<String> allTerms = new ArrayList<String>();
    //to hold all terms
    private List<String> removeStopWord = new ArrayList<String>();
    //to hold term without Stop Words
    private List<String> queryTerms = new ArrayList<String>();
    private String[] Positional_query;
    private List<double[]> tfidfDocsVector = new ArrayList<double[]>();

    private ArrayList<ArrayList<ArrayList<String>>> Positional_CommanDoc = new ArrayList<ArrayList<ArrayList<String>>>();
    private ArrayList<String> index = new ArrayList<String>();
    private List<double[]> tfidfQueryVector = new ArrayList<double[]>();
    private ArrayList<ArrayList<String>> Positional = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> Positional_data = new ArrayList<ArrayList<ArrayList<String>>>();
    private ArrayList<ArrayList<Double>> tf_idfMatrix = new ArrayList<ArrayList<Double>>();

    // Method to read files and store in array.
    // filePath : source file path --  Generally a folder with the required set of documents
    public void parseFiles(String filePath) throws FileNotFoundException, IOException {
        File[] allfiles = new File(filePath).listFiles();
        BufferedReader in = null;
        for (File f : allfiles) {
            if (f.getName().endsWith(".txt")) {
                in = new BufferedReader(new FileReader(f));
                StringBuilder sb = new StringBuilder();
                String s = null;
                while ((s = in.readLine()) != null) {
                    sb.append(s);
                }
                String[] tokenized = sb.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");   //to get individual terms
                String[] Clear_Term = stopWords(tokenized);
                for (String term : Clear_Term) //avoid duplicate terms
                {
                    if (!allTerms.contains(term)) {
                        allTerms.add(term);
                    }
                }
                termsDocsArray.add(Clear_Term);
            }
        }
    }

    // tokenization for new Query  
    public void tokenQuery(String Q) {
        String[] tokenized = Q.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");//to get individual terms
        String[] Clear_Term = stopWords(tokenized);
        termsDocsArray.add(Clear_Term);
        for (String[] doc : termsDocsArray) {
            for (String term : Clear_Term) //avoid duplicate terms after add query
            {
                if (!allTerms.contains(term)) {
                    allTerms.add(term);
                }
            }
        }
    }

    public void Show_Tokens() {
        for (String term : allTerms) {
            System.out.println(term);
        }
    }

    public String[] stopWords(String[] term) {
        String words = "a about after before all also alot few little lot always am an as and any are at be been being but by can could did didnt do does doesnt doing dont else for from get give goes going had happen has have having how i if ill im in into is isnt it its ive just keep let like many may me more most much no not now of only or our really say see some something than that the their them then there they thing this to up us very want was way we what when where which who why will with without you your youre";

        String[] Stop_word = words.split(" ");
        removeStopWord.clear();
        int count = 0;
        String[] clearTerm = null;
        for (String t : term) {
            count = 0;
            for (int x = 0; x < Stop_word.length; x++) {
                if (t.equalsIgnoreCase(Stop_word[x])) {
                    count++;
                    break;
                }

            }
            if (count == 0) {
                removeStopWord.add(t);
            }
        }

        // ArrayList to Array Conversion 
        String arr[] = new String[removeStopWord.size()];
        arr = removeStopWord.toArray(arr);
        clearTerm = arr;

        return clearTerm;
    }

    /**
     * Method to create termVector according to its tfidf score.
     */
    public void Show_TFIDF(boolean flag) {
        tf_idfMatrix.clear();
        tfidfQueryVector.clear();
        tfidfDocsVector.clear();
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(4);
        double tf; //term frequency
        double idf; //inverse document frequency
        double tfidf; //term frequency inverse document frequency 
        int x = 0; //counter for find last element of array -> Query
        int z = 0;
       
        for (String[] docTermsArray : termsDocsArray) {
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            if (flag) {

                z++;
                if ((z) == (termsDocsArray.size())) {
                    System.out.println("\n**************************\n    TF-IDF for Query\n**************************\n");
                } else {
                    System.out.println("\n**************************\n        Document" + z + "\n**************************\n");
                }
            }
                    
            ArrayList<Double> col = new ArrayList();
            for (String terms : allTerms) {

                tf = new TfIdf().tfCalculator(docTermsArray, terms, false);
                idf = new TfIdf().idfCalculator(termsDocsArray, terms, false);

                tfidf = tf * idf;
                col.add(tfidf);
                if (flag) {
                    System.out.println(terms + " : " + df.format(tfidf) + "\n");
                }

                tfidfvectors[count] = tfidf;
                count++;
            }
            tf_idfMatrix.add(col);
            x++;

            if (termsDocsArray.size() == x) {
                tfidfQueryVector.add(tfidfvectors); //storing Query vectors; 
            } else {
                tfidfDocsVector.add(tfidfvectors);  //storing document vectors;            
            }
        }
    }

    public void show_TFIDF_Matrix() {
        int c = 0;
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(4);
        for(int x = 0 ; x < termsDocsArray.size()-1 ; x++){
          System.out.print("Doc"+ (x+1)+"\t\t");  
        }System.out.println("Query\t\tTerms");
        for(String term : allTerms){
           
            
            for(int i = 0; i < tf_idfMatrix.size(); i++){
                System.out.print(df.format(tf_idfMatrix.get(i).get(c))+"\t\t");
            }
            c++;
            System.out.println( ":"+term );
            
        }
    }

    public void show_TF() {

        double tf;
        int i = 0;
        for (String[] docTermsArray : termsDocsArray) {
            if (termsDocsArray.size() == (i + 1)) {
                System.out.println("\n************************\n    TF for Query\n************************\n");
            } else {
                System.out.println("\n************************\n      Document " + ++i + "\n************************\n");
            }
            double[] tfidfvectors = new double[allTerms.size()];
            int count = 0;
            for (String terms : allTerms) {
                tf = new TfIdf().tfCalculator(docTermsArray, terms, true);
            }
        }
    }

    public void show_IDF() {
        double idf; //inverse document frequency
        int i = 0;
        System.out.println("TDF  \n");
        for (String[] docTermsArray : termsDocsArray) {
            if (termsDocsArray.size() == (i + 1)) {
                System.out.println("\n***********************\n   IDF for Query\n*************************\n");
            } else {
                System.out.println("\n***********************\n      Document " + ++i + "\n***********************\n");
            }
            for (String terms : allTerms) {

                idf = new TfIdf().idfCalculator(termsDocsArray, terms, true);
            }
        }
    }

    public void getCosineSimilarityQuery() {
        for (int i = 0; i < tfidfQueryVector.size(); i++) {
            for (int j = 0; j < tfidfDocsVector.size(); j++) {

                System.out.println("between Query and Document" + (j + 1) + "  =  " + new CosineSimilarity().cosineSimilarity(tfidfQueryVector.get(i), tfidfDocsVector.get(j)));

            }
        }
    }

//    public void Positional_Index() {
//        int count = 0;
//        for (String term : allTerms) {
//            for (String[] doc : termsDocsArray) {
//                for (String t : doc) {
//                    if (term.equalsIgnoreCase(t)) {
//                        if (count == 0) {
//                            index.add(String.valueOf(termsDocsArray.indexOf(doc)+1));
//                            index.add(term);
//                        }
//                        index.add(String.valueOf(Arrays.asList(doc).indexOf(t)+1));
//                        count++;
//                    }
//                }
//            }
//        }index.add(String.valueOf(count));
//        count = 0;
//        Positional.add((ArrayList<String>) index);
//        index.clear();
//    }
    public void positionalQuery(String Q) {

        String[] tokenized = Q.toString().replaceAll("[\\W&&[^\\s]]", "").split("\\W+");//to get individual terms
        String[] Clear_Term = stopWords(tokenized);
        Positional_query = Clear_Term;
    }

    public void Positional_Index() {
        int count = 0, count2 = 0;
        boolean flag = true;

        for (String term : allTerms) {
            System.out.print("\n< " + term + " ");
            for (String[] doc : termsDocsArray) {
                flag = true;
                int c = -1;
                ArrayList<String> copy = new ArrayList<String>();
                for (String t : doc) {

                    c++;
                    if (term.equalsIgnoreCase(t)) {
                        if (flag) {
                            copy.add(String.valueOf(termsDocsArray.indexOf(doc) + 1));
                            flag = false;
                        }
                        copy.add(String.valueOf(find(doc, term, c) + 1));

                        count++;
                    }
                }
                Positional.add(copy);
            }

            System.out.print(" " + count + "\n\n");

            for (ArrayList<String> i : Positional) {
                count2 = 0;
                for (String ind : i) {
                    if (count2 == 0) {
                        System.out.print("D" + (Integer.parseInt(ind)) + " : ");
                        count2++;
                    } else if (ind == i.get(i.size() - 1)) {
                        System.out.print(ind + ";\n\n");
                    } else {
                        System.out.print(ind + ", ");
                    }
                }
            }
            Positional.clear();
            count = 0;

            System.out.println("********************************************");
        }
    }

    public void PositionalQuery() {
        int c = 0;
        for (String[] doc : termsDocsArray) {
            int count = 0;
            boolean flag = true;
            for (int i = 0; i < doc.length; i++) {
                if (Positional_query[0].equalsIgnoreCase(doc[i])) {
                     count = 0;
                    for (int x = 1; x < Positional_query.length; x++) {
                        if(++i>=doc.length)
                            break;
                        if (Positional_query[x].equalsIgnoreCase(doc[i])) {
                            count++; 
                        } else {
                            break;
                        }
                    }
                    if (count == (Positional_query.length - 1)) {
                        if (flag) {
                            c++;
                            System.out.println("D" + (termsDocsArray.indexOf(doc) + 1) + " :-");
                            flag = false;
                        }
                        System.out.println("[ " + (i - (Positional_query.length - 2)) + " ]");
                    }
                }

            }
        }
        if (c == 0) {
            System.out.println("\nNot found");
        }
    }

    public void Positional_Index_Query() {
        int count = 0, count2 = 0;
        boolean flag = true;

        for (String term : Positional_query) {
            ArrayList<ArrayList<String>> Pos = new ArrayList<ArrayList<String>>();

            for (String[] doc : termsDocsArray) {
                flag = true;
                int c = -1;
                ArrayList<String> copy = new ArrayList<String>();
                for (String t : doc) {
                    c++;
                    if (term.equalsIgnoreCase(t)) {
                        if (flag) {
                            copy.add(String.valueOf(termsDocsArray.indexOf(doc) + 1));
                            flag = false;
                        }
                        copy.add(String.valueOf(find(doc, term, c) + 1));

                        count++;
                    }
                }
                if (!copy.isEmpty()) {
                    Pos.add(copy);
                }
            }

            Positional_data.add(Pos);
        }
        Positional_CommanDoc.clear();
        for (int i = 0; i < Positional_data.size() - 1; i++) {
            intersection(Positional_data.get(i), Positional_data.get(i + 1));
        }
        if (Positional_CommanDoc.size() == 1) {
            for (ArrayList<ArrayList<String>> Doc : Positional_CommanDoc) {
                int c = 0;
                for (ArrayList<String> docid : Doc) {
                    System.out.println(docid.get(c));
                    c++;
                }
            }
        }
        for (int x = 0; x < Positional_CommanDoc.size() - 1; x++) {
            intersection(Positional, Positional);
        }
    }

    public void intersection(ArrayList<ArrayList<String>> p1, ArrayList<ArrayList<String>> p2) {
        for (int i = 0, z = 0; i <= p1.size() - 1 && z <= p2.size() - 1;) {
            ArrayList<ArrayList<String>> CommanDoc = new ArrayList<ArrayList<String>>();

            if (p1.get(i).get(0).equals(p2.get(z).get(0))) {
                for (int x = 0, y = 0; x < p1.get(i).size() - 1 && y < p2.get(z).size() - 1; x++, y++) {
                    System.out.println("i = " + i + ": z = " + z);
                    ArrayList<String> addresses = new ArrayList<String>();
                    if (p1.get(i).get(x) == p2.get(z).get(y)) {
                        addresses.add(p1.get(i).get(0));
                        addresses.add(p2.get(z).get(y));
                        CommanDoc.add(addresses);
                    } else if (Integer.parseInt(p1.get(i).get(x)) < Integer.parseInt(p2.get(z).get(y))) {
                        x++;
                    } else {
                        y++;
                    }
                    i++;
                    z++;
                }

            } else if (Integer.parseInt(p1.get(i).get(0)) < Integer.parseInt(p2.get(z).get(0))) {
                i++;
            } else {
                z++;
            }
            Positional_CommanDoc.add(CommanDoc);
        }
    }

    public static int find(String[] a, String target, int start) {
        for (int i = start; i < a.length; i++) {
            if (a[i].equalsIgnoreCase(target)) {
                return i;
            }
        }
        return -1;
    }
}
