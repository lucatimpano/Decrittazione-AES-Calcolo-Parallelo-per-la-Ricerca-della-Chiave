package AES;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;



public class Decripta implements Runnable{
    long start, end;
    static byte[] data;

    public Decripta(long start, long end, byte[] data){
        this.start = start;
        this.end = end;
        this.data = data;
    }

    @Override
    public void run() {
        try {
            Thread currentThread = Thread.currentThread();

            for (long i = start; i < end && !currentThread.isInterrupted(); i++) {      //Controllo anche la Interrupt flag
                String keyText = generateKey(i);        //genero la chiave in base al valore di i come String
                SecretKey key = Key(keyText);       //converto la chiave in SecretKey

                if (decripta(data, key)) {      //se decripta() == true ho trovato la chiave
                    endTimer();     //fermo il timer
                    System.out.println("Decriptato con successo");
                    System.out.println("Chiave: " + keyText);       //stampo la chiave
                    SearchKeyConc.gruppo.interrupt();       //interrompo il gruppo e modifico la flag
                    return;
                }

                double progress = (double) i / Integer.MAX_VALUE * 100;     //calcolo il progresso
                if(i % 1000000 == 0){       //il calcolo viene effettuato ogni milione di operazioni
                    System.out.printf("%.2f%% completato", progress);
                    System.out.print(" Nome Thread: " + currentThread.getName() + "\n");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static boolean decripta(byte[] byteCriptati, SecretKey secretKey) throws Exception{
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] byteDecriptati = null;
        try{
            byteDecriptati = cipher.doFinal(byteCriptati);      //se viene lanciata l'eccezione BadPaddingException viene catturata e ritorna false
        }catch (BadPaddingException e){
            return false;
        }

        String decryptedText = new String(byteDecriptati);      //Converto in stringa i byte decriptati

        if(decryptedText.contains("SISOP-corsoB")){     //effettuo il controllo
            System.out.println("Testo decriptato: " + decryptedText);
            return true;
        }
        return false;
    }

    private static String generateKey(long key){
        int keyLength = 16;
        StringBuilder sb = new StringBuilder();     //uso StringBuilder
        String x = String.valueOf(key);     //converto il numero in String
        int lenght = x.length();        //calcolo la lunghezza e la sottraggo alla lunghezza della chiave
        while(sb.length() < keyLength - lenght){
            sb.append("0");     //riempo i caratteri mancanti con 0
        }
        sb.append(x);       //inserisco il numero
        return sb.toString();
    }

    private static SecretKey Key(String customKey) throws NoSuchAlgorithmException {        //converto la chiave da String in SecretKey
        byte[] customKeyBytes = customKey.getBytes();
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        SecretKey secretKey = new SecretKeySpec(customKeyBytes, "AES");
        return secretKey;
    }

    private void endTimer(){        //metodo per fermare il timer
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - SearchKeyConc.startTime;
        long seconds = (elapsedTime / 1000) % 60;
        long minutes = ((elapsedTime - seconds) / 1000) / 60;
        System.out.println("Tempo trascorso: " + minutes + " minuti");
    }
}
