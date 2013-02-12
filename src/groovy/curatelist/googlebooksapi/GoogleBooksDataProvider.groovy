package curatelist.googlebooksapi;/*
 * Copyright (c) 2011 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */


import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.books.Books;
import com.google.api.services.books.BooksRequestInitializer;
import com.google.api.services.books.Books.Volumes.List;
import com.google.api.services.books.model.Volume;
import com.google.api.services.books.model.Volumes;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.NumberFormat
import curatelist.books.BooksModel
import org.slf4j.LoggerFactory
import org.slf4j.Logger

/**
 * A sample application that demonstrates how Google Books Client Library for
 * Java can be used to query Google Books. It accepts queries in the command
 * line, and prints the results to the console.
 *
 * $ java com.google.sample.books.curatelist.googlebooksapi.BooksSample [--author|--isbn|--title] "<query>"
 *
 * Please start by reviewing the Google Books API documentation at:
 * http://code.google.com/apis/books/docs/getting_started.html
 */
public class GoogleBooksDataProvider {

   // transient Logger logme =   LoggerFactory.getLogger(GoogleBooksDataProvider.class)
    /**
     * Be sure to specify the name of your application. If the application name is {@code null} or
     * blank, the application will log a warning. Suggested format is "MyCompany-ProductName/1.0".
     */
    private static final String APPLICATION_NAME = "CurateList";

    private static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance();
    private static final NumberFormat PERCENT_FORMATTER = NumberFormat.getPercentInstance();


    public static ArrayList<BooksModel> queryGoogleBooks(String query) throws Exception {
        JsonFactory jsonFactory = new JacksonFactory();
        return queryGoogleBooks(jsonFactory,query)
    }

    private static ArrayList<BooksModel> queryGoogleBooks(JsonFactory jsonFactory, String query) throws Exception {
        ClientCredentials.errorIfNotSpecified();


        ArrayList<BooksModel>  listOfBooksFound = new ArrayList<BooksModel>()

        // Set up Books client.
        final Books books = new Books.Builder(new NetHttpTransport(), jsonFactory, null)
                .setApplicationName(APPLICATION_NAME)
                .setGoogleClientRequestInitializer(new BooksRequestInitializer(ClientCredentials.API_KEY))
                .build();
        // Set query string and filter only Google eBooks.
        //logme.debug("Query: [" + query + "]");
        List volumesList = books.volumes().list(query);
        volumesList.setFilter("ebooks");

        // Execute the query.
        Volumes volumes = volumesList.execute();
        if (volumes.getTotalItems() == 0 || volumes.getItems() == null) {
            //logme.debug("No matches found.");
            return listOfBooksFound;
        }

        // Output results.
        for (Volume volume : volumes.getItems()) {

            BooksModel booksModel = new BooksModel()

            Volume.VolumeInfo volumeInfo = volume.getVolumeInfo();
            Volume.SaleInfo saleInfo = volume.getSaleInfo();
            //logme.debug("==========");
            // Title.
            //logme.debug("Title: " + volumeInfo.getTitle());
            booksModel.setTitle(volumeInfo.getTitle())
            booksModel.setSubtitle(volumeInfo.getSubtitle())
            // Author(s).
            java.util.List<String> authors = volumeInfo.getAuthors();
            if (authors != null && !authors.isEmpty()) {
                //logme.debug("Author(s): ");
                StringBuffer authorsBuffer = new StringBuffer()
                for (int i = 0; i < authors.size(); ++i) {
                    //logme.debug(authors.get(i));
                    authorsBuffer.append(authors.get(i))
                    if (i < authors.size() - 1) {
                        //logme.debug(", ");
                        authorsBuffer.append(", ")
                    }
                }
                //logme.debug();
                booksModel.setAuthors(authorsBuffer.toString())
            }
            // Description (if any).
            if (volumeInfo.getDescription() != null && volumeInfo.getDescription().length() > 0) {
                //logme.debug("Description: " + volumeInfo.getDescription());
                booksModel.setDescription(volumeInfo.getDescription())
            }

            //Images of Book Cover
           /* booksModel.getImageLinks().setExtraLarge(volumeInfo.getImageLinks().getExtraLarge())
            booksModel.getImageLinks().setLarge(volumeInfo.getImageLinks().getLarge())
            booksModel.getImageLinks().setSmall(volumeInfo.getImageLinks().getSmall())
            booksModel.getImageLinks().setThumbnail(volumeInfo.getImageLinks().getThumbnail())
            booksModel.getImageLinks().setSmallThumbnail(volumeInfo.getImageLinks().getSmallThumbnail())
             */
            listOfBooksFound.add(booksModel)

        }
        /*// Price (if any).
           if (saleInfo != null && "FOR_SALE".equals(saleInfo.getSaleability())) {
               double save = saleInfo.getListPrice().getAmount() - saleInfo.getRetailPrice().getAmount();
               if (save > 0.0) {
                   //logme.debug("List: " + CURRENCY_FORMATTER.format(saleInfo.getListPrice().getAmount())
                           + "  ");
               }
               //logme.debug("Google eBooks Price: "
                       + CURRENCY_FORMATTER.format(saleInfo.getRetailPrice().getAmount()));
               if (save > 0.0) {
                   //logme.debug("  You Save: " + CURRENCY_FORMATTER.format(save) + " ("
                           + PERCENT_FORMATTER.format(save / saleInfo.getListPrice().getAmount()) + ")");
               }
               //logme.debug();
           }
           // Access status.
           String accessViewStatus = volume.getAccessInfo().getAccessViewStatus();
           String message = "Additional information about this book is available from Google eBooks at:";
           if ("FULL_PUBLIC_DOMAIN".equals(accessViewStatus)) {
               message = "This public domain book is available for free from Google eBooks at:";
           } else if ("SAMPLE".equals(accessViewStatus)) {
               message = "A preview of this book is available from Google eBooks at:";
           }
           //logme.debug(message);
           // Link to Google eBooks.
           //logme.debug(volumeInfo.getInfoLink());


       } */
        //logme.debug("==========");
        //logme.debug(volumes.getTotalItems() + " total results at http://books.google.com/ebooks?q=" + URLEncoder.encode(query, "UTF-8"));


        return listOfBooksFound
    }

    public static void main(String[] args) {
        JsonFactory jsonFactory = new JacksonFactory();
        try {
            String query = "Founder's Dilemma";
            /*// Verify command line parameters.
            if (args.length == 0) {
                System.err.println("Usage: curatelist.googlebooksapi.BooksSample [--author|--isbn|--title] \"<query>\"");
                System.exit(1);
            }
            // Parse command line parameters into a query.
            // Query format: "[<author|isbn|intitle>:]<query>"
            String prefix = null;
            String query = "";
            for (String arg : args) {
                if ("--author".equals(arg)) {
                    prefix = "inauthor:";
                } else if ("--isbn".equals(arg)) {
                    prefix = "isbn:";
                } else if ("--title".equals(arg)) {
                    prefix = "intitle:";
                } else if (arg.startsWith("--")) {
                    System.err.println("Unknown argument: " + arg);
                    System.exit(1);
                } else {
                    query = arg;
                }
            }
            if (prefix != null) {
                query = prefix + query;
            }*/
            try {
                queryGoogleBooks(jsonFactory, query);
                // Success!
                return;
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
        System.exit(0);
    }
}