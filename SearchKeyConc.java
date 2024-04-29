package AES;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SearchKeyConc {

    public static ThreadGroup gruppo = new ThreadGroup("Gruppo A");     //Gruppo di Thread
    public static  long startTime = System.currentTimeMillis();     //Start del timer

    public static void main(String[] args) throws Exception {
        startSearch();
    }

    public static void startSearch() throws Exception{
        int num_Threads = 48;       //Numero di Thread
        Thread[] threads = new Thread[num_Threads];

        byte[] byteCriptati = leggiFile();      //Leggo il file criptato

        int rangeSize = Integer.MAX_VALUE / num_Threads;        //mi calcolo il range di esecuzione del singolo thread

        for (int i = 0; i < num_Threads; i++) {
            int startRange = i * rangeSize;     //start
            int endRange = (i + 1) * rangeSize;     //end
            threads[i] = new Thread(gruppo, new Decripta(startRange, endRange, byteCriptati), "T" + (i + 1));
            threads[i].start();     //faccio partire i thread
        }
    }


    private static byte[] leggiFile() throws IOException {
        byte[] byteCriptati = Files.readAllBytes(Path.of("/home/luca/IdeaProjects/Sistemi Operativi/src/AES/document2024_B.encrypted"));        //leggo il file criptato
        return byteCriptati;
    }
}