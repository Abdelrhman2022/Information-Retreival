package data.storage.and.retrieval;

import java.text.DecimalFormat;
import java.util.List;

public class TfIdf {

    // Calculates the tf of term termToCheck
    // totalterms : Array of all the words under processing document
    // termToCheck : term of which tf is to be calculated.
    // returns tf(term frequency) of term termToCheck
    public double tfCalculator(String[] totalterms, String termToCheck, Boolean flag) {
        double count = 0;  //to count the overall occurrence of the term termToCheck
        for (String s : totalterms) {
            if (s.equalsIgnoreCase(termToCheck)) {
                count++;
            }
        }
        if (flag) {
            System.out.println(termToCheck + " : " + count + "\n");
        }
        if (count == 0) {
            return 0;
        }
        return 1 + Math.log10(count);
    }

    // Calculates idf of term termToCheck
    // allTerms : all the terms of all the documents
    // returns idf(inverse document frequency) score
    public double idfCalculator(List<String[]> allTerms, String termToCheck, Boolean flag) {
        double count = 0;
        //round the Number to 4 fraction
        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(4);
        double idfCalculator = 0.00;

        for (String[] ss : allTerms) {
            for (String s : ss) {
                if (s.equalsIgnoreCase(termToCheck)) {
                    count++;
                    break;
                }
            }
        }
        if (flag) {
            if (count == 0) {
                System.out.println(termToCheck + " : 0\n");
            } else {

                System.out.println(termToCheck + " :  " + df.format(Math.log10(10 / count)) + "\n");
            }
        }
        if (count == 0) {
            return 0;
        }
        idfCalculator = Math.log10(10 / count);
        return idfCalculator;
    }
}
