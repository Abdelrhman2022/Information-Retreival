/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data.storage.and.retrieval;

import java.io.IOException;
import static java.lang.System.exit;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Genius
 */
public class DataStorageAndRetrieval {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {

        //dp.tfIdfCalculator(); //calculates tfidf
        //dp.getCosineSimilarity(); //calculates cosine similarity
        Scanner in = new Scanner(System.in);
        Scanner input = new Scanner(System.in);
        int opt;
        int opt2;
        int opt3;

        System.out.print("\n1- Tokenization and normalization\n2- Positional index model\n3- Vector space model\n0- Exit\nEnter Your Choice: ");
        int list = in.nextInt();

        while (list != 0) {
            switch (list) {
                case 0:
                    exit(0);
                    break;
                case 1:
                    DocumentParser dp2 = new DocumentParser();
                    dp2.parseFiles("..\\text1");
                    System.out.println("\n**************************************\n    Tokenization and normalization\n**************************************\n");
                    dp2.Show_Tokens();
                    System.out.print("\n\nEnter Your Choice: \n1- Tokenization and normalization\n2- Positional index model\n3- Vector space model\n0- Exit\nEnter Your Choice: ");
                    list = in.nextInt();

                    break;
                case 2:
                    DocumentParser dp3 = new DocumentParser();
                    dp3.parseFiles("..\\text1"); // give the location of source file
                    System.out.println("\n1- Positional index structure\n2-Phrase query\n0- Back\nEnter Your Choice:  ");
                    int Positional = in.nextInt();

                    opt2 = 1;
                    while (opt2 != 0) {
                        switch (Positional) {
                            case 0:
                                System.out.print("\n\nEnter Your Choice: \n1- Tokenization and normalization\n2- Positional index model\n3- Vector space model\n0- Exit\nEnter Your Choice: ");
                                list = in.nextInt();
                                opt2 = 0;
                                break;
                            case 1:
                                dp3.Positional_Index();
                                System.out.println("\n1- Positional index structure\n2-Phrase query\n0- Back\nEnter Your Choice:  ");
                                Positional = in.nextInt();
                                break;
                            case 2:
                                System.out.println("\nEnter your phrase query :  ");
                                in.nextLine();
                                String Positionalquery = in.nextLine();

                                dp3.positionalQuery(Positionalquery);
                                dp3.PositionalQuery();
                                System.out.println("\n1- Positional index structure\n2-Phrase query\n0- Back\nEnter Your Choice:  ");
                                try {
                                    Positional = in.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.print(e.getMessage()); //try to find out specific reason.
                                }

                                break;
                            default:
                                System.out.println("\n*********************************************\nPlease Enter Number of right choice from below lsit\n*********************************************\n");
                                System.out.println("\n1- Positional index structure\n2-Phrase query\n0- Back\nEnter Your Choice:  ");
                                Positional = in.nextInt();
                        }
                    }

                    break;
                case 3:
                    DocumentParser dp = new DocumentParser();
                    dp.parseFiles("..\\text2"); // give the location of source file
                    System.out.print("Enter Your Query: ");
                    in.nextLine();
                    String query = input.nextLine();
                    System.out.println(query);
                    dp.tokenQuery(query);

                    System.out.print("\n1- Show Term frequency\n2- Show IDF\n3- Show TF-IFD\n4- Show Similarity between query and each document\n5- Show all of them\n0- Exit\nEnter Your Choice: ");
                    try {
                        opt = in.nextInt();
                        opt3 = 1;
                        while (opt3 != 0) {
                            switch (opt) {
                                case 0:
                                    opt3 = 0;
                                    System.out.print("\n\nEnter Your Choice: \n1- Tokenization and normalization\n2- Positional index model\n3- Vector space model\n0- Exit\nEnter Your Choice: ");
                                    list = in.nextInt();
                                    break;
                                case 1:
                                    System.out.print("\n*********************\n    Term frequency\n*********************\n");
                                    dp.show_TF();
                                    System.out.print("\n******************************************\n\n1- Show Term frequency\n2- Show IDF\n3- Show TF-IFD\n4- Show Similarity between query and each document\n5- Show all of them\n0- Back\nEnter Your Choice: ");
                                    opt = in.nextInt();
                                    break;
                                case 2:
                                    System.out.print("\n*********************\n    IDF\n*********************\n");
                                    dp.show_IDF();
                                    System.out.print("\n******************************************\n\n1- Show Term frequency\n2- Show IDF\n3- Show TF-IFD\n4- Show Similarity between query and each document\n5- Show all of them\n0- Back\nEnter Your Choice: ");
                                    opt = in.nextInt();
                                    break;
                                case 3:
                                    System.out.print("\n*********************\n     TFI.DF Matrix\n*********************\n");
                                    dp.Show_TFIDF(false);
                                    dp.show_TFIDF_Matrix();
                                    System.out.print("\n******************************************\n\n1- Show Term frequency\n2- Show IDF\n3- Show TF-IFD\n4- Show Similarity between query and each document\n5- Show all of them\n0- Back\nEnter Your Choice: ");
                                    opt = in.nextInt();
                                    break;
                                case 4:
                                    System.out.print("\n*********************************\n    Similarity between query and each document\n*********************************\n");
                                    dp.Show_TFIDF(false);
                                    dp.getCosineSimilarityQuery();
                                    System.out.print("\n******************************************\n\n1- Show Term frequency\n2- Show IDF\n3- Show TF-IFD\n4- Show Similarity between query and each document\n5- Show all of them\n0- Back\nEnter Your Choice: ");
                                    opt = in.nextInt();
                                    break;
                                case 5:
                                    System.out.print("\n*********************\n    Term frequency\n*********************\n");
                                    dp.show_TF();
                                    System.out.print("\n*********************\n    IDF\n*********************\n");
                                    dp.show_IDF();
                                    System.out.print("\n*********************\n    TFI.DF Matrix\n*********************\n");
                                    dp.Show_TFIDF(false);
                                    dp.show_TFIDF_Matrix();
                                    System.out.print("\n*********************************\n   Similarity between query and each document\n*********************************\n");
                                    dp.getCosineSimilarityQuery();
                                    System.out.print("\n******************************************\n\n1- Show Term frequency\n2- Show IDF\n3- Show TF-IFD\n4- Show Similarity between query and each document\n5- Show all of them\n0- Back\nEnter Your Choice: ");
                                    opt = in.nextInt();
                                    break;
                                default:
                                    System.out.println("\n*********************************************\nPlease Enter Number of right choice from below lsit\n*********************************************\n");
                                    System.out.print("\n1- Show Term frequency\n2- Show IDF\n3- Show TF-IFD\n4- Show Similarity between query and each document\n5- Show all of them\n0- Back\nEnter Your Choice: ");
                                    opt = in.nextInt();
                            }
                        }
                    } catch (InputMismatchException e) {
                        System.out.print(e.getMessage()); //try to find out specific reason.
                    }
                    break;
                default:
                    System.out.println("\n*********************************************\nPlease Enter Number of right choice from below lsit\n*********************************************\n");
                    System.out.print("\n1- Tokenization and normalization\n2- Positional index model\n3- Vector space model\n0- Exit\nEnter Your Choice: ");
                    list = in.nextInt();
            }

        }
    }
}
