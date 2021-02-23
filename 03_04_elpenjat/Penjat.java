//El joc del penjat~

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Penjat {
    
    static int intents = 10;
    static int figura = 0;
    static int encertades = 0;
    static int jugades = 0;
    static int fallades = 0;
    static int cancelades = 0;
    static int lletres = 0;
    
    static String lletra = "";
    static String paraula = "";
    static String usades = "";
    static String adivinades = "";
    
    static boolean ans = false;
    
    public static void main(String[] args) throws IOException {
        System.out.println("Comencem a jugar");
        String cami = "text.txt";
        FileReader fileReader = new FileReader(cami);
        BufferedReader input = new BufferedReader(fileReader);
         
        while (!lletra.equals("prou")) {
            
            paraula = input.readLine();
            if (paraula == null) {
                System.out.println("No queden més paraules");
                System.out.println("Paraules jugades: " + jugades);
                System.out.println("Paraules encertades: " + encertades);
                System.out.println("Paraules fallades: " + fallades);
                System.out.println("Paraules cancel·lades: " + cancelades);
                System.out.println("Espero que t'hagis divertit");
                return;
            }
            intents = 10;
            figura = 0;
            usades = "";
            ans = false;
            juga(paraula);
            adivinades = "";
        }
        input.close();
        
        jugades++;
        cancelades++;
        System.out.println("Paraules jugades: " + jugades);
        System.out.println("Paraules encertades: " + encertades);
        System.out.println("Paraules fallades: " + fallades);
        System.out.println("Paraules cancel·lades: " + cancelades);
        System.out.println("Espero que t'hagis divertit");
    }
    public static String encripta(String text, String adivinades) {
        //Recursivamente ya que parece la forma mas simple.
        String text2 = "";
        for (int i = 0; i < text.length(); i++) {
            text2 = text2 + "*";
        }
        return text2;
    }
    public static void mostraFigura(int intent) throws IOException {
    
        String cami = "figura" + (figura-1) + ".txt";
        FileReader fileReader = new FileReader(cami);
        BufferedReader input = new BufferedReader(fileReader);
        while (true) {
            String linia = input.readLine();
            if (null == linia) break;
            System.out.println(linia);
        }
        input.close();
    }
    public static void juga(String text) throws IOException {
        
        boolean enc = false;
        boolean cancel = false;
        
        while ((enc != true || cancel != true) && !lletra.equals("prou")) {
 
            if (figura != 0) {
                mostraFigura(intents);
            }
            if (intents == 0 && (ans == false)) {
                System.out.println("Has fallat :( escriu \"prou\" per sortir o qualsevol cosa per continuar");
                fallades++;
                jugades++;
                lletra = Entrada.readLine();
                return;
            } else if (ans == true) {
                System.out.println("Has encertat :) escriu \"prou\" per sortir o qualsevol cosa per continuar");
                encertades++;
                jugades++;
                lletra = Entrada.readLine();
                return;
            }
            String adivina = encripta(text, adivinades);
            
            System.out.println("Paraula: " + adivina);
            if (usades.length() == 0) {
               System.out.println("Utilitzades: cap"); 
            } else {
            System.out.println("Utilitzades: " + probades(usades));
            }
            System.out.println("Intents disponibles: " + intents);
            System.out.println("Introdueix una lletra");

            Penjat.lletra = Entrada.readLine();
            if (lletra.equals("glups")) {
                cancelades++;
                jugades++;
                return;
            } else if ((lletra.length() != 1 && !lletra.equals("prou")) || Character.isDigit(lletra.charAt(0))) {
                 System.out.println("Error: cal una lletra entre 'a' i 'z', 'prou' o 'glups'");
              }
            else if (Character.isLetter(lletra.charAt(0)) && lletra.length() == 1) {
                if (!es(lletra,text)) {
                    intents--;
                    figura++;
                    if (noRepetida(lletra)) {
                      usades = usades + lletra;  
                    } else if (!noRepetida(lletra)) {
                        intents++;
                        figura--;
                        System.out.println("Aquesta lletra ja la vas utilitzar");
                    }
                } else if (noRepetida(lletra)) {
                    adivinades = adivinades + lletra;
                    usades = usades + lletra;
                } else if (!noRepetida(lletra)) {
                        System.out.println("Aquesta lletra ja la vas utilitzar");
                    }
            }
        }
    }
    public static boolean es(String lletra, String text) {
        
        boolean resposta = false;
        
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == lletra.charAt(0)) {
                resposta = true;
            }
        }
        return resposta;
    }
    public static String probades(String text) {
        
        String torna = "";
        char[] textEnC = text.toCharArray();
        
        if (textEnC.length == 1) {
            textEnC[0] = Character.toUpperCase(textEnC[0]);
            torna = textEnC[0] + "";
        } else if (textEnC.length == 2) {
            textEnC[0] = Character.toUpperCase(textEnC[0]);
            textEnC[1] = Character.toUpperCase(textEnC[1]);
            torna = textEnC[0] + " i " + textEnC[1];
        } else if (textEnC.length > 2) {
            for (int i = 0; i < textEnC.length; i++) {
                textEnC[i] = Character.toUpperCase(textEnC[i]);
                if (i != textEnC.length -2 && i != textEnC.length -1) {
                    torna = torna + textEnC[i] + ", ";
                }
                else if (i == textEnC.length - 2) {
                    torna = torna + textEnC[i] + " i ";
                }
            } torna = torna + textEnC[textEnC.length-1];
        }
        return torna;
    }
    public static boolean noRepetida(String lletra) {
        
        boolean rep = true;
        
        for (int i = 0; i < usades.length(); i++) {
            if (usades.charAt(i) == lletra.charAt(0)) {
                rep = false;
            }
        }
        return rep;
    }
}
