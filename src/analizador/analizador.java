/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Ignacio
 */
public class analizador {
    
    private static ArrayList<String> txtError;
    private static ArrayList<String> variable;
    private static ArrayList<String> tipoVar;
    private static ArrayList<String> valorVar;
    private static ArrayList<String> tokens;
    private static ArrayList<String> valortokens;
    private static tripleta tripleta;
    private static Boolean errores;
    private static int numIDE, numOA, numPR, numCE, numCF, numOR, numOB;
    private static String n;
    
    /**
     * @param codigo
     * @return 
     */
    public static Boolean analizar(String codigo) {
        
        // Objeto para la construcción de strings.
        StringBuilder stb = new StringBuilder();
        
        txtError = new ArrayList<>();
        variable = new ArrayList<>();
        tipoVar = new ArrayList<>();
        valorVar = new ArrayList<>();
        tokens = new ArrayList<>();
        valortokens = new ArrayList<>();
        numIDE = 0; numOA = 0; numPR = 0; numCE = 0; numCF = 0; numOR = 0; numOB = 0;
        n = System.getProperty("line.separator");
        
        // Variable que se encarga de identificar si hay errores para desplegar.
        errores = false;
        
        // Variable que almacena la posición.
        int pos = 0;
        
        // Antes de iniciar, se deben eliminar los tabs del código para evitar errores adicionales.
        codigo = codigo.replace("\t", "");
        
        // Se dívide el código por lineas.
        String[] lineaCodigo = codigo.split("\\n");
        
        // En caso de que el código se encuentre en una sola línea, se divide con el delimitador.
        if (lineaCodigo.length == 1) {
            lineaCodigo = codigo.split(";");
            
            // Ahora se debe volver a agregar el delimitador
            for (int i = 0; i < lineaCodigo.length; i++) {
                stb = new StringBuilder();
                stb.append(lineaCodigo[i]).append(";");
                
                // Se elimina el espacio en blanco al principio.
                if (i > 0) {
                    stb.deleteCharAt(0);
                }
                lineaCodigo[i] = stb.toString();
            }
            
        }
        
        // La verificación línea por línea empieza en esta parte
        for (int i = 0; i < lineaCodigo.length; i++) {
            
            // Variable que almacena el número de línea.
            int numLinea = i + 1;
            
            // Ahora la verificación por línea se checa por palabras divididas por espacios.
            String[] palabra = lineaCodigo[i].split("\\s");
            
            // Variable encargada de almacenar la variable base.
            String var = "";
            
            // Variable encargada de guardar el tipo de variable de la línea.
            String tipo = "null";
            
            // Variable encargada de guardar el valor de la variable.
            String valor = "null";
            
            // Bandera para saber si el tipo base ha sido asignado anteriormente.
            Boolean tipoBase = false;
            
            // Bandera para saber si la variable base ha sido asignada anteriormente.
            Boolean varBase = false;
            
            // Bandera para saber que parte del código define el valor de la variable.
            Boolean asignacion = false;
            
            // Bandera para saber si un error fue encontrado.
            Boolean error = false;
            
            for (int j = 0; j < palabra.length; j++) {
                
                // Bandera para saber si el token es nuevo.
                Boolean nuevo = true;
                
                // El tipo de variable base sólo es leída y almacenada con la primera palabra.
                if (j == 0) {
                    
                    // Se declara la expresión regular para encontrar palabras reservadas que definen el tipo.
                    Pattern patron = Pattern.compile("int$|float$|double$");
                    Matcher coincidencia = patron.matcher(palabra[j]);
                    
                    // Sí coincide, el tipo es almacenado y se continua con la siguiente variable.
                    if (coincidencia.matches()) {
                        tipo = palabra[j];
                        tipoBase = true;
                        
                        // Se agrega el token.
                        if (numPR > 0) {
                            // Primero debemos verificar que el token no exista.
                            // t guarda la posición del token a analizar.
                            int t = 0;
                            // tokensReg guarda el total de tokens registrados hasta el momento.
                            int tokensReg = tokens.size();
                            while (t < tokensReg) {
                                
                                // Si el token existe.
                                if (valortokens.get(t).equals(palabra[j])) {
                                    // Se guarda el token existente.
                                    tokens.add(tokens.get(t));
                                    valortokens.add(valortokens.get(t));
                                    nuevo = false;
                                    break;
                                }
                                
                                t++;
                                
                            }
                            
                            if (nuevo) {
                                // Se guarda el nuevo token.
                                numPR++;
                                tokens.add("PR" +numPR);
                                valortokens.add(palabra[j]);
                            }
                        }
                        // Si no hay ningún token, se registra el primero
                        else {
                            numPR++;
                            tokens.add("PR" +numPR);
                            valortokens.add(palabra[j]);
                        }
                        
                        pos++;
                        continue;
                    }
                }
                
                // Se verifica si se trata de las palabras reservadas do o while.
                Pattern patron = Pattern.compile("do$|while$");
                Matcher coincidencia = patron.matcher(palabra[j]);
                
                // Si coinicide, se verifica la siguiente palabra.
                if (coincidencia.matches()) {
                    
                    // Se agrega el token.
                        if (numPR > 0) {
                            // Primero debemos verificar que el token no exista.
                            // t guarda la posición del token a analizar.
                            int t = 0;
                            // tokensReg guarda el total de tokens registrados hasta el momento.
                            int tokensReg = tokens.size();
                            while (t < tokensReg) {
                                
                                // Si el token existe.
                                if (valortokens.get(t).equals(palabra[j])) {
                                    // Se guarda el token existente.
                                    tokens.add(tokens.get(t));
                                    valortokens.add(valortokens.get(t));
                                    nuevo = false;
                                    break;
                                }
                                
                                t++;
                                
                            }
                            
                            if (nuevo) {
                                // Se guarda el nuevo token.
                                numPR++;
                                tokens.add("PR" +numPR);
                                valortokens.add(palabra[j]);
                            }
                        }
                        // Si no hay ningún token, se registra el primero
                        else {
                            numPR++;
                            tokens.add("PR" +numPR);
                            valortokens.add(palabra[j]);
                        }
                    
                        pos++;
                    continue;
                }
                
                // Se verifica si es el operador de asignación.
                patron = Pattern.compile("^[=]$");
                coincidencia = patron.matcher(palabra[j]);
                
                // Se activa la bandera que almacena el valor de la variable.
                if (coincidencia.matches()) {
                    asignacion = true;
                    
                    // Se inicia StringBuilder para empezar almacenar el valor de la variable.
                    stb = new StringBuilder();
                    
                    // Se registra el token de asignación.
                    tokens.add("OAS");
                    valortokens.add("=");
                    
                    pos++;
                    continue;
                }
                
                // Se verifica si es el delimitador.
                patron = Pattern.compile("^[;]$");
                coincidencia = patron.matcher(palabra[j]);
                
                if (coincidencia.matches()) {
                    
                    // Se registra el token de delimitador.
                    tokens.add("DEL");
                    valortokens.add(";");
                    
                    pos++;
                    continue;
                }
                
                // Se verifica si es un parentesis.
                patron = Pattern.compile("[(]$");
                coincidencia = patron.matcher(palabra[j]);
                
                if (coincidencia.matches()) {
                    
                    // Al tratarse de una asignación, el parentesis se almacena.
                    if (asignacion) {
                        stb.append(palabra[j]).append(" ");
                    }
                    
                    // Se registra el token de parentesis.
                    tokens.add("PAR1");
                    valortokens.add("(");
                    
                    pos++;
                    continue;
                }
                
                // Se verifica si es un parentesis.
                patron = Pattern.compile("[)]$");
                coincidencia = patron.matcher(palabra[j]);
                
                if (coincidencia.matches()) {
                    
                    // Al tratarse de una asignación, el parentesis se almacena.
                    if (asignacion) {
                        stb.append(palabra[j]).append(" ");
                    }
                    
                    // Se registra el token de parentesis.
                    tokens.add("PAR2");
                    valortokens.add(")");
                    
                    pos++;
                    continue;
                }
                
                // Se verifica si es un corchete.
                patron = Pattern.compile("[{]$");
                coincidencia = patron.matcher(palabra[j]);
                
                if (coincidencia.matches()) {
                    
                    // Se registra el token de delimitador.
                    tokens.add("COR1");
                    valortokens.add("{");
                    
                    pos++;
                    continue;
                }
                
                // Se verifica si es un corchete.
                patron = Pattern.compile("[}]$");
                coincidencia = patron.matcher(palabra[j]);
                
                if (coincidencia.matches()) {
                    
                    // Se registra el token de delimitador.
                    tokens.add("COR2");
                    valortokens.add("}");
                    
                    pos++;
                    continue;
                }
                
                // Se verifica si se trata de una constante entera.
                patron = Pattern.compile("^[-]?(\\d)+$");
                coincidencia = patron.matcher(palabra[j]);
                
                // Se activa la bandera que almacena la constante entera.
                if (coincidencia.matches()) {
                    
                    // Al tratarse de una asignación, la constante se almacena.
                    if (asignacion) {
                        stb.append(palabra[j]).append(" ");
                    }
                    
                    // Se agrega el token.
                        if (numCE > 0) {
                            // Primero debemos verificar que el token no exista.
                            // t guarda la posición del token a analizar.
                            int t = 0;
                            // tokensReg guarda el total de tokens registrados hasta el momento.
                            int tokensReg = tokens.size();
                            while (t < tokensReg) {
                                
                                // Si el token existe.
                                if (valortokens.get(t).equals(palabra[j])) {
                                    // Se guarda el token existente.
                                    tokens.add(tokens.get(t));
                                    valortokens.add(valortokens.get(t));
                                    nuevo = false;
                                    break;
                                }
                                
                                t++;
                                
                            }
                            
                            if (nuevo) {
                                // Se guarda el nuevo token.
                                numCE++;
                                tokens.add("CE" +numCE);
                                valortokens.add(palabra[j]);
                            }
                        }
                        // Si no hay ningún token, se registra el primero
                        else {
                            numCE++;
                            tokens.add("CE" +numCE);
                            valortokens.add(palabra[j]);
                        }
                    
                    pos++;
                    continue;
                }
                
                // Se verifica si se trata de una constante flotante.
                patron = Pattern.compile("^[-]?(\\d)+(.(\\d)+)$");
                coincidencia = patron.matcher(palabra[j]);
                
                // Se activa la bandera que almacena la constante flotante.
                if (coincidencia.matches()) {
                    
                    // Se verifica que si existe incompatibilidad de tipos.
                    if (tipo.equalsIgnoreCase("int")) {
                        txtError.add("Línea " +numLinea+ ": Incompatibilidad de tipos: " +var+ " es un int y " +palabra[j]+ " es un float.");
                        
                        error = true;
                        errores = true;
                    }
                    
                    // En caso contrario, almacenar la constante flotante.
                    else if (asignacion) {
                        stb.append(palabra[j]).append(" ");
                    }
                    
                    // Se agrega el token.
                        if (numCF > 0) {
                            // Primero debemos verificar que el token no exista.
                            // t guarda la posición del token a analizar.
                            int t = 0;
                            // tokensReg guarda el total de tokens registrados hasta el momento.
                            int tokensReg = tokens.size();
                            while (t < tokensReg) {
                                
                                // Si el token existe.
                                if (valortokens.get(t).equals(palabra[j])) {
                                    // Se guarda el token existente.
                                    tokens.add(tokens.get(t));
                                    valortokens.add(valortokens.get(t));
                                    nuevo = false;
                                    break;
                                }
                                
                                t++;
                                
                            }
                            
                            if (nuevo) {
                                // Se guarda el nuevo token.
                                numCF++;
                                tokens.add("CF" +numCF);
                                valortokens.add(palabra[j]);
                            }
                        }
                        // Si no hay ningún token, se registra el primero
                        else {
                            numCF++;
                            tokens.add("CF" +numCF);
                            valortokens.add(palabra[j]);
                        }
                    
                    pos++;
                    continue;
                }
                
                // Se verifica si se trata de un operador aritmético.
                patron = Pattern.compile("^[+-/*]$");
                coincidencia = patron.matcher(palabra[j]);
                
                // Se activa la bandera que almacena al operador aritmético.
                if (coincidencia.matches()) {
                    
                    // Al tratarse de una asignación, el operador aritmético se almacena.
                    if (asignacion) {
                        stb.append(palabra[j]).append(" ");
                    }
                    
                    // Si se intenta dividir con una variable base int.
                    if (tipo.equalsIgnoreCase("int") && palabra[j].contains("/")) {
                        txtError.add("Línea " +numLinea+ ": Pérdida de información al dividir un int.");

                        error = true;
                        errores = true;
                    }
                    
                    // Se agrega el token.
                        if (numOA > 0) {
                            // Primero debemos verificar que el token no exista.
                            // t guarda la posición del token a analizar.
                            int t = 0;
                            // tokensReg guarda el total de tokens registrados hasta el momento.
                            int tokensReg = tokens.size();
                            while (t < tokensReg) {
                                
                                // Si el token existe.
                                if (valortokens.get(t).equals(palabra[j])) {
                                    // Se guarda el token existente.
                                    tokens.add(tokens.get(t));
                                    valortokens.add(valortokens.get(t));
                                    nuevo = false;
                                    break;
                                }
                                
                                t++;
                                
                            }
                            
                            if (nuevo) {
                                // Se guarda el nuevo token.
                                numOA++;
                                tokens.add("OA" +numOA);
                                valortokens.add(palabra[j]);
                            }
                        }
                        // Si no hay ningún token, se registra el primero
                        else {
                            numOA++;
                            tokens.add("OA" +numOA);
                            valortokens.add(palabra[j]);
                        }
                    
                    pos++;
                    continue;
                }
                
                // Se verifica si se trata de un operador relacional.
                patron = Pattern.compile("([<>][=]?)$|([!=][=])$");
                coincidencia = patron.matcher(palabra[j]);
                
                // Se activa la bandera que almacena al operador relacional.
                if (coincidencia.matches()) {
                    
                    // Al tratarse de una asignación, el operador relacional se almacena.
                    if (asignacion) {
                        stb.append(palabra[j]).append(" ");
                    }
                    
                    // Se agrega el token.
                        if (numOR > 0) {
                            // Primero debemos verificar que el token no exista.
                            // t guarda la posición del token a analizar.
                            int t = 0;
                            // tokensReg guarda el total de tokens registrados hasta el momento.
                            int tokensReg = tokens.size();
                            while (t < tokensReg) {
                                
                                // Si el token existe.
                                if (valortokens.get(t).equals(palabra[j])) {
                                    // Se guarda el token existente.
                                    tokens.add(tokens.get(t));
                                    valortokens.add(valortokens.get(t));
                                    nuevo = false;
                                    break;
                                }
                                
                                t++;
                                
                            }
                            
                            if (nuevo) {
                                // Se guarda el nuevo token.
                                numOR++;
                                tokens.add("OR" +numOR);
                                valortokens.add(palabra[j]);
                            }
                        }
                        // Si no hay ningún token, se registra el primero
                        else {
                            numOR++;
                            tokens.add("OR" +numOR);
                            valortokens.add(palabra[j]);
                        }
                    
                    pos++;
                    continue;
                }
                
                // Se verifica si se trata de un operador booleano.
                patron = Pattern.compile("([&][&])$|([|][|])$");
                coincidencia = patron.matcher(palabra[j]);
                
                // Se activa la bandera que almacena al operador booleano
                if (coincidencia.matches()) {
                    
                    // Al tratarse de una asignación, el operador booleano se almacena.
                    if (asignacion) {
                        stb.append(palabra[j]).append(" ");
                    }
                    
                    // Se agrega el token.
                        if (numOB > 0) {
                            // Primero debemos verificar que el token no exista.
                            // t guarda la posición del token a analizar.
                            int t = 0;
                            // tokensReg guarda el total de tokens registrados hasta el momento.
                            int tokensReg = tokens.size();
                            while (t < tokensReg) {
                                
                                // Si el token existe.
                                if (valortokens.get(t).equals(palabra[j])) {
                                    // Se guarda el token existente.
                                    tokens.add(tokens.get(t));
                                    valortokens.add(valortokens.get(t));
                                    nuevo = false;
                                    break;
                                }
                                
                                t++;
                                
                            }
                            
                            if (nuevo) {
                                // Se guarda el nuevo token.
                                numOB++;
                                tokens.add("OB" +numOB);
                                valortokens.add(palabra[j]);
                            }
                        }
                        // Si no hay ningún token, se registra el primero
                        else {
                            numOB++;
                            tokens.add("OB" +numOB);
                            valortokens.add(palabra[j]);
                        }
                    
                    pos++;
                    continue;
                }
                
                // Se verifica si es una variable.
                patron = Pattern.compile("^[A-Za-z_$][\\w$]*$");
                coincidencia = patron.matcher(palabra[j]);
                
                // Sí coincide se asigna el tipo base si no ha sido declarado antes.
                if (coincidencia.matches()) {
                    
                    // Variable donde se almacena el tipo de la variable que se analiza.
                    String tipoPalabra = "null";
                    
                    // Se verifica si esta es la variable base de la línea, de ser así, se almacena.
                    if (tipoBase && !varBase) {
                        var = palabra[j];
                        tipoPalabra = tipo;
                        varBase = true;
                    }
                    
                    // Bandera indicadora de si se pasa a la siguiente variable.
                    Boolean salto = false;
                    
                    // Variable donde se almacena el tipo de la variable que se analiza.
                    //String tipoPalabra = tipo;
                    
                    // Variable donde se almacena el valor de la variable que se analiza.
                    String valorPalabra = valor;
                    
                    // Se busca si la variable no ha sido registrada antes
                    for (int k = 0; k < variable.size(); k++) {
                        
                        // Si ha sido registrada antes.
                        if (variable.get(k).equals(palabra[j])) {
                            
                            // Se obtiene y almacena el tipo de la variable.
                            tipoPalabra = tipoVar.get(k);
                            
                            // Se analiza si la variable ha sido declarada previamente.
                            if (!tipo.equalsIgnoreCase("null") && !asignacion) {
                                txtError.add("Línea " +numLinea+ ": La variable " + palabra[j] + " ha sido declarada previamente.");

                                error = true;
                                errores = true;
                            }
                            
                            // Se obtiene y almacena el valor de la variable.
                            valorPalabra = valorVar.get(k);
                            
                            // Se indica que la palabra se ha encontrado.
                            salto = true;
                            break;
                        }
                        
                    }
                    
                    // Se verifica de nuevo si esta es la variable de la base de línea pero sin declaración.
                    if (j == 0) {
                        var = palabra[j];
                        tipo = tipoPalabra;
                        varBase = true;
                    }
                    
                    // Se analiza si la variable no ha sido declarada.
                    if (tipoPalabra.equalsIgnoreCase("null")) {
                        txtError.add("Línea " +numLinea+ ": Variable " + palabra[j] + " no declarada.");
                        
                        error = true;
                        errores = true;
                    }
                    
                    // Se analiza si se esta intentando asignar una variable incompatible o no inicializada.
                    if (asignacion) {
                        
                        // Se analiza si el tipo base no es compatible con el tipo que se analiza.
                        if (tipo.equalsIgnoreCase("int") && (tipoPalabra.equalsIgnoreCase("double") || tipoPalabra.equalsIgnoreCase("float"))) {
                            txtError.add("Línea " +numLinea+ ": Incompatibilidad de tipos: " +var+ " es un int y " +palabra[j]+ " es " +tipoPalabra+ ".");

                            error = true;
                            errores = true;
                        }
                        
                        // Se analiza si la variable no se encuentra inicializada.
                        if (valorPalabra.equalsIgnoreCase("null")) {
                            txtError.add("Línea " +numLinea+ ": Variable " + palabra[j] + " no inicializada.");

                            error = true;
                            errores = true;
                        }
                        
                        // Si ningún error fue encontrado, se almacena en stb para después asignar el valor a la variable.
                        if (!error) {
                            stb.append(palabra[j]).append(" ");
                        }
                    }
                    
                    // Se agrega el token.
                    if (numIDE > 0) {
                        // Primero debemos verificar que el token no exista.
                        // t guarda la posición del token a analizar.
                        int t = 0;
                        // tokensReg guarda el total de tokens registrados hasta el momento.
                        int tokensReg = tokens.size();
                        while (t < tokensReg) {
                            
                            // Si el token existe.
                            if (valortokens.get(t).equals(palabra[j])) {
                                // Se guarda el token existente.
                                tokens.add(tokens.get(t));
                                valortokens.add(valortokens.get(t));
                                nuevo = false;
                            
                                break;
                            }
                                
                            t++;
                                
                        }
                            
                        if (nuevo) {
                            // Se guarda el nuevo token.
                            numIDE++;
                            tokens.add("IDE" +numIDE);
                            valortokens.add(palabra[j]);
                        }
                    }
                    // Si no hay ningún token, se registra el primero
                    else {
                        numIDE++;
                        tokens.add("IDE" +numIDE);
                        valortokens.add(palabra[j]);
                    }
                    
                    // Se verifica la siguiente palabra si no es necesario agregar.
                    if (salto) {
                        pos++;
                        continue;
                    }
                    
                    // La variable se registra
                    variable.add(palabra[j]);
                    tipoVar.add(tipoPalabra);
                    valorVar.add(valorPalabra);
                    
                    pos++;
                    
                }
                
            }
            
            // Si la línea tuvo una asignación, esta se agrega en esta fase.
            if (asignacion) {
                
                // Si la línea no tuvo ningún error, se procede a la asignación.
                if (!error) {
                    
                    // Primero debemos encontrar la posición de la variable a asignar.
                    for (int j = 0; j < variable.size(); j++) {
                        
                        // Al encontrar la variable, se le asigna su nuevo valor.
                        if (variable.get(j).equalsIgnoreCase(var)) {
                            valorVar.set(j, stb.toString());
                            break;
                        }
                    }
                }
            }
        }
        
        // Ahora el código se optimiza
        optimizarCodigo();
        
        // Se genera la tripleta
        generarTriplo();
        
        // Se genera el ensamblador
        generarEnsamblador();
        
        return errores;
    }
    
    // Método para obtener las variables.
    public static String[] obtenerVariables() {
        
        // Número de variables registradas.
        int tamanio = variable.size();
        
        // Arreglo que almacena todas las variables.
        String[] variables = new String[tamanio];
        
        // Se recorre el ArrayList de variables.
        for (int i = 0; i < tamanio; i++) {
            variables[i] = variable.get(i);
        }
        
        // Se regresan todas las variables.
        return variables;
    }
    
    // Método para obtener el número de variables.
    public static int obtenerNumVariables() {
        return variable.size();
    }
    
    // Método para obtener el número de errores.
    public static int obtenerNumErrores() {
        return txtError.size();
    }
    
    // Método para obtener el número de tokens.
    public static int obtenerNumTokens() {
        return tokens.size();
    }
    
    // Método para obtener los tipos de variables.
    public static String[] obtenerTipoVar() {
        
        // Número de variables registradas.
        int tamanio = tipoVar.size();
        
        // Arreglo que almacena todas las variables.
        String[] tipos = new String[tamanio];
        
        // Se recorre el ArrayList de variables.
        for (int i = 0; i < tamanio; i++) {
            tipos[i] = tipoVar.get(i);
        }
        
        // Se regresan todas las variables.
        return tipos;
    }
    
    // Método para obtener los valores de las variables.
    public static String[] obtenerValoresVar() {
        
        // Número de variables registradas.
        int tamanio = valorVar.size();
        
        // Arreglo que almacena todas las variables.
        String[] valores = new String[tamanio];
        
        // Se recorre el ArrayList de variables.
        for (int i = 0; i < tamanio; i++) {
            valores[i] = valorVar.get(i);
        }
        
        // Se regresan todas las variables.
        return valores;
    }
    
    // Método para obtener el texto de los errores.
    public static String[] obtenerTxtError() {
        
        // Número de variables registradas.
        int tamanio = txtError.size();
        
        // Arreglo que almacena todas las variables.
        String[] erroresArray = new String[tamanio];
        
        // Se recorre el ArrayList de variables.
        for (int i = 0; i < tamanio; i++) {
            erroresArray[i] = txtError.get(i);
        }
        
        // Se regresan todas las variables.
        return erroresArray;
    }
    
    // Método para obtener los tokens.
    public static String[] obtenerTokens() {
        
        // Número de tokens registrados.
        int tamanio = tokens.size();
        
        // Arreglo que almacena todos los tokens.
        String[] tokensArray = new String[tamanio];
        
        // Se recorre el ArrayList de tokens.
        for (int i = 0; i < tamanio; i++) {
            tokensArray[i] = tokens.get(i);
        }
        
        // Se regresan todas las tokens.
        return tokensArray;
    }
    
    // Método para obtener los valores de los tokens.
    public static String[] obtenerValoresTokens() {
        
        // Número de valores de tokens registrados.
        int tamanio = valortokens.size();
        
        // Arreglo que almacena todas las variables.
        String[] valortokensArray = new String[tamanio];
        
        // Se recorre el ArrayList de variables.
        for (int i = 0; i < tamanio; i++) {
            valortokensArray[i] = valortokens.get(i);
        }
        
        // Se regresan todas las variables.
        return valortokensArray;
    }
    
    // aquí se realiza la optimización de código y tokens.
    public static void optimizarCodigo() {
        
        // aquí se almacenan el texto optimizado.
        StringBuilder stb = new StringBuilder();
        
        // listas que almacenan la optimizacion.
        ArrayList<String> tokensoptimizados = new ArrayList<>();
        ArrayList<String> valoresoptimizados = new ArrayList<>();
        
        // se analizan los tokens registrados.
        int tamanio = tokens.size();
        
        for (int i = 0; i < tamanio; i++) {
            String tokenactual = tokens.get(i);
            String valoractual = valortokens.get(i);
            String tokenanterior = "";
            String valoranterior = "";
            String tokenposterior = "";
            Boolean tokenfinal = true;
            
            // se guarda el token anterior de ser posible.
            if (i > 0) {
                tokenanterior = tokens.get(i-1);
                valoranterior = valortokens.get(i-1);
            }
            
            // se guarda el token posterior de ser posible.
            if (i < tamanio-1) {
                tokenposterior = tokens.get(i+1);
                tokenfinal = false;
            }
            
            // si nos encontramos con un delimitador.
            if (tokenactual.equalsIgnoreCase("DEL")) {
                
                // mientras no sea el tokenfinal token.
                if (!tokenfinal) {
                    
                    // se checa si lo siguiente es un corchete terminal.
                    if (tokenposterior.equalsIgnoreCase("COR2")) {
                        
                        // de ser asi se agrega el delimitador tal cual.
                        stb.append(valoractual);
                        tokensoptimizados.add(tokenactual);
                        valoresoptimizados.add(valoractual);
                    }
                    
                    // si no hay un corchete
                    else {
                        
                        // se agrega el delimitador junto a un salto de linea.
                        stb.append(valoractual+n);
                        tokensoptimizados.add(tokenactual);
                        valoresoptimizados.add(valoractual);
                    }
                }
                
                // si este es el token final.
                else {
                    
                    // se agrega solamente el delimitador.
                    stb.append(valoractual);
                    tokensoptimizados.add(tokenactual);
                    valoresoptimizados.add(valoractual);
                }
            }
            
            // si nos encontramos con una palabra reservada.
            else if (tokenactual.contains("PR")) {
                
                // si el token anterior era un parentesis terminal.
                if (tokenanterior.equalsIgnoreCase("PAR2")) {
                    
                    // se agrega un salto de linea
                    stb.append(n);
                }
                
                // a continuacion se agrega la palabra del token y un espacio.
                stb.append(valoractual+" ");
                tokensoptimizados.add(tokenactual);
                valoresoptimizados.add(valoractual);
                
            }
            
            // si nos encontramos con un corchete inicial.
            else if (tokenactual.equalsIgnoreCase("COR1")) {
                
                // se agrega un salto de linea y el corchete inicial.
                stb.append(n+valoractual);
                tokensoptimizados.add(tokenactual);
                valoresoptimizados.add(valoractual);
            }
            
            // si nos encontramos con un corchete final.
            else if (tokenactual.equalsIgnoreCase("COR2")) {
                
                // se agrega el corchete final y un salto de linea.
                stb.append(valoractual);
                tokensoptimizados.add(tokenactual);
                valoresoptimizados.add(valoractual);
                stb.append(n);
            }
            
            // aqui empieza la optimizacion de la instruccion, detectando una asignacion.
            else if (tokenactual.equalsIgnoreCase("OAS")) {
                
                // se agrega la asignacion.
                stb.append(valoractual);
                tokensoptimizados.add(tokenactual);
                valoresoptimizados.add(valoractual);
                
                // bandera para indicar que la optimizacion se aplicara.
                Boolean optimizacion = false;
                
                // se almacena en esta variable lo ultimo que se le ha asignado.
                String codigotoken = "";
                
                // comienza el primer recorrido de variables.
                for (int j = 0; j < variable.size(); j++) {
                    
                    // si la variable contiene 1 o null se ignora la optimizacion.
                    if (valorVar.get(j).equalsIgnoreCase("1") || valorVar.get(j).equalsIgnoreCase("null")) {
                        continue;
                    }
                    
                    // si se encuentra el valor de la variable que se busca
                    if (valoranterior.equalsIgnoreCase(variable.get(j))) {
                        
                        // se escribe el codigo que la misma contiene.
                        codigotoken = valorVar.get(j);
                        
                        // se termina el ciclo.
                        break;
                    }
                    
                }
                
                // en esta parte se analizan los identificadores para buscar si es necesario sustituir.
                for (int j = 0; j < variable.size(); j++) {
                    
                    // si el codigo tiene una parte similar, se reemplaza.
                    if (codigotoken.contains(valorVar.get(j)) && !valoranterior.equalsIgnoreCase(variable.get(j)) && !valorVar.get(j).equalsIgnoreCase("1 ") && !valorVar.get(j).equalsIgnoreCase("null")) {
                        
                        // se guarda la posicion del caracter anterior.
                        int caracteranterior = codigotoken.indexOf(valorVar.get(j)) - 1;
                        
                        // si se encuentra al principio, se ignora el caracter anterior.
                        if (caracteranterior == -1) {
                            caracteranterior = 0;
                        }
                        
                        // si no se encuentra un espacio antes, se ignora este reemplazo.
                        if (codigotoken.charAt(caracteranterior) != ' ' && caracteranterior != 0) {
                            continue;
                        }
                        
                        // se aplica la optimizacion.
                        codigotoken = codigotoken.replace(valorVar.get(j), variable.get(j)+ " ");
                        
                        // y se activa la bandera de que si ocurrio la optimizacion.
                        optimizacion = true;
                    }
                }
                
                // al si haber optimizacion
                if (optimizacion) {
                    
                    // el codigo se divide por espacios para la busqueda de tokens.
                    String[] tokensdelcodigo = codigotoken.split("\\s");
                    
                    // se recorre el codigo
                    for (int j = 0; j < tokensdelcodigo.length ; j++) {
                        
                        // se recorren todos los tokens
                        for (int k = 0 ; k < tokens.size() ; k ++) {
                            
                            // si esta parte del codigo es un "1" y a continuacion tiene una multiplicacion, se elimina.
                            if (tokensdelcodigo[j].equalsIgnoreCase("1") && (tokensdelcodigo[j+1].equalsIgnoreCase("(") || tokensdelcodigo[j+1].equalsIgnoreCase("*"))) {
                                tokensdelcodigo[j] = "";

                                // si es un asterisco se elimina.
                                if (tokensdelcodigo[j+1].equalsIgnoreCase("*")) {
                                    tokensdelcodigo[j] = "";
                                    j++;
                                }

                                // se termina el ciclo.
                                break;
                            }
                            
                            // si la parte del codigo corresponde a un token.
                            if (tokensdelcodigo[j].equalsIgnoreCase(valortokens.get(k))) {
                                
                                // se almacena la palabra del codigo con su token y valor.
                                tokensoptimizados.add(tokens.get(k));
                                valoresoptimizados.add(valortokens.get(k));
                                
                                // se termina el ciclo
                                break;
                            }
                        }
                    }
                    
                    // se eliminan los espacios.
                    codigotoken = codigotoken.replace(" ", "");
                    stb.append(codigotoken);
                        
                    // y se salta el analisis de toda la linea.
                    while (!tokens.get(i+1).equalsIgnoreCase("DEL")) {
                        i++;
                    }
                    
                }
                
                
            }
            
            // cualquier otro caso, se guarda.
            else {
                stb.append(valoractual);
                tokensoptimizados.add(tokenactual);
                valoresoptimizados.add(valoractual);
            }
            
            
        }
        
        // el codigo optimizado se guarda en las listas
        tokens = tokensoptimizados;
        valortokens = valoresoptimizados;
        
        // y se guarda el codigo optimizado en el archivo
        try (FileWriter fw = new FileWriter("Codigo Optimizado.txt")) {
            fw.write(stb.toString());
        }
        catch (IOException ex) {
            System.out.println("No se pudo guardar el archivo.");
        }
    }
    
    public static void generarTriplo () {
        
        // Indica el numero de instruccion.
        int numeroinstruccion = 1;
        
        // Numero de temporal
        int numerotemporal = 1;
        
        // Numero de condicion
        int numwhile = 1;
        
        // Variables que almacenaran la tripleta
        ArrayList<Integer> numeroln = new ArrayList<>();
        ArrayList<String> objeto = new ArrayList<>();
        ArrayList<String> fuente = new ArrayList<>();
        ArrayList<String> operador = new ArrayList<>();
        ArrayList<Integer> lugardo = new ArrayList<>();
        ArrayList<Integer> lugarwhile = new ArrayList<>();
        ArrayList<Integer> numinsdo = new ArrayList<>();
        
        // se verifican los tokens uno por uno.
        for (int i = 0; i < tokens.size(); i++) {
            
            // cuando es una palabra reservada
            if(tokens.get(i).contains("PR")) {
                
                // si es un "do" se registra.
                if (valortokens.get(i).equalsIgnoreCase("do")) {
                    lugardo.add(numeroinstruccion);
                    numinsdo.add(numeroinstruccion);
                }
                
                // si es un "while".
                else if (valortokens.get(i).equalsIgnoreCase("while")) {
                    
                    // se registra
                    lugarwhile.add(numeroinstruccion);
                    
                    // Se guarda en la tripleta la variable a comprobar
                    numeroln.add(numeroinstruccion);
                    objeto.add("T" +numerotemporal);
                    fuente.add(valortokens.get(i+2));
                    operador.add("=");
                    numeroinstruccion++;
                    
                    // Se analiza con que se debe comparar
                    numeroln.add(numeroinstruccion);
                    objeto.add("T" +numerotemporal);
                    fuente.add(valortokens.get(i+4));
                    operador.add(valortokens.get(i+3));
                    numeroinstruccion++;
                    
                    // Si es verdadero, debe continuar con la siguiente instruccion.
                    int sigIns = numeroinstruccion + 2;
                    numeroln.add(numeroinstruccion);
                    objeto.add("TR" +numwhile);
                    fuente.add("TRUE");
                    operador.add(numinsdo.remove(numinsdo.size()-1)+"");
                    numeroinstruccion++;
                    
                    // Si es falso, se debe regresar al anterior do
                    numeroln.add(numeroinstruccion);
                    objeto.add("TR" +numwhile);
                    fuente.add("FALSE");
                    operador.add(sigIns+"");
                    numeroinstruccion++;
                    numerotemporal++;
                    numwhile++;
                    
                }
                
                else {
                    continue;
                }
                
            }
            
            // Si el token es un operador de asignación.
            if(tokens.get(i).contains("OAS")) {
                
                // Se considera si hay un delimitador despúes de su siguiente token
                if (tokens.get(i+2).equalsIgnoreCase("DEL")) {
                    
                    // De ser así estamos ante una asignación simple y la tripleta.
                    numeroln.add(numeroinstruccion);
                    objeto.add(valortokens.get(i-1));
                    fuente.add(valortokens.get(i+1));
                    operador.add("=");
                    numeroinstruccion++;
                }
                
                // En este caso, tenemos una probable operación matemática.
                else {
                    
                    // Variable a la que se asignará el resultado final.
                    String variableAsig = valortokens.get(i-1);
                    
                    // se almacenan los tokens de la operacion.
                    ArrayList<String> tokensoperacion = new ArrayList<>();
                    
                    // Saltamos el guardado del operador de asignación.
                    i++;
                    
                    while (!tokens.get(i).equalsIgnoreCase("DEL")) {
                        tokensoperacion.add(valortokens.get(i));
                        i++;
                    }
                    
                    // Se va recorriendo la lista hasta que no queden variables por comparar
                    while (tokensoperacion.size() > 1) {
                        
                        // Si hay un parentesis en la operación.
                        if (tokensoperacion.indexOf(")") != -1) {
                            
                            // Se almacenan las posiciones.
                            int posFin = tokensoperacion.indexOf(")");
                            int posIni = posFin - 2;
                            
                            // Se revisa que antes dos tokens anteriores no sean "(".
                            if (!tokensoperacion.get(posIni).equalsIgnoreCase("(")) {
                                
                                // Si hay una multiplicación en la operación.
                                if (tokensoperacion.lastIndexOf("*") != -1) {

                                    // Se almacena la última variable en el triplo y se elimina de la lista.
                                    numeroln.add(numeroinstruccion);
                                    objeto.add("T" +numerotemporal);
                                    fuente.add(tokensoperacion.remove(tokensoperacion.lastIndexOf("*")+1));
                                    operador.add("=");
                                    numeroinstruccion++;

                                    // En el triplo se almacena la operacion realizada.
                                    numeroln.add(numeroinstruccion);
                                    objeto.add("T" +numerotemporal);
                                    fuente.add(tokensoperacion.remove(tokensoperacion.lastIndexOf("*")-1));
                                    operador.add(tokensoperacion.get(tokensoperacion.lastIndexOf("*")));
                                    numeroinstruccion++;

                                    // Se agrega a la lista el triplo realizado.
                                    tokensoperacion.set(tokensoperacion.lastIndexOf("*"),"T" +numerotemporal);

                                    // Se aumenta el contador del triplo
                                    numerotemporal++;

                                    continue;
                                }

                                // Si hay una division en la operación.
                                else if (tokensoperacion.lastIndexOf("/") != -1) {

                                    // Se almacena la última variable en el triplo y se elimina de la lista.
                                    numeroln.add(numeroinstruccion);
                                    objeto.add("T" +numerotemporal);
                                    fuente.add(tokensoperacion.remove(tokensoperacion.lastIndexOf("/")+1));
                                    operador.add("=");
                                    numeroinstruccion++;

                                    // En el triplo se almacena la operacion realizada.
                                    numeroln.add(numeroinstruccion);
                                    objeto.add("T" +numerotemporal);
                                    fuente.add(tokensoperacion.remove(tokensoperacion.lastIndexOf("/")-1));
                                    operador.add(tokensoperacion.get(tokensoperacion.lastIndexOf("/")));
                                    numeroinstruccion++;

                                    // Se agrega a la lista el triplo realizado.
                                    tokensoperacion.set(tokensoperacion.lastIndexOf("/"),"T" +numerotemporal);

                                    // Se aumenta el contador del triplo
                                    numerotemporal++;

                                    continue;
                                }

                                // Si solo quedan sumas y restas.
                                else {

                                    // Se almacena la última variable en el triplo y se elimina de la lista.
                                    numeroln.add(numeroinstruccion);
                                    objeto.add("T" +numerotemporal);
                                    fuente.add(tokensoperacion.remove(tokensoperacion.size()-1));
                                    operador.add("=");
                                    numeroinstruccion++;

                                    // En el triplo se almacena la operacion realizada.
                                    numeroln.add(numeroinstruccion);
                                    objeto.add("T" +numerotemporal);
                                    operador.add(tokensoperacion.remove(tokensoperacion.size()-1));
                                    fuente.add(tokensoperacion.remove(tokensoperacion.size()-1));
                                    numeroinstruccion++;

                                    // Se agrega a la lista el triplo realizado.
                                    tokensoperacion.add("T" +numerotemporal);

                                    // Se aumenta el contador del triplo
                                    numerotemporal++;
                                }
                                
                            }
                            
                            // En este caso, la operación no tiene mas operaciones alrededor
                            else {
                                
                                // Primero debemos comprobar que no estemos en el final del lado derecho.
                                if (posFin < tokensoperacion.size()-1) {
                                    
                                    // Se verifica que haya una suma, resta o parentesis final del lado derecho
                                    if (tokensoperacion.get(posFin+1).equalsIgnoreCase("+") || tokensoperacion.get(posFin+1).equalsIgnoreCase("-") || tokensoperacion.get(posFin+1).equalsIgnoreCase(")")) {
                                        
                                        // De ser así, se elimina el parentesis
                                        tokensoperacion.remove(posFin);
                                    }
                                    
                                    // En cualquier otro caso, se cambia el parentesis por una multiplicación
                                    else {
                                        tokensoperacion.set(posFin, "*");
                                    }
                                        
                                }
                                
                                // Al ser el final, solamente se elimina el parentesis.
                                else {
                                    tokensoperacion.remove(posFin);
                                }
                                
                                // Ahora debemos comprobar que no estemos al principio del lado izquierdo.
                                if (posIni > 0) {
                                    // Se verifica que haya una suma, resta o parentesis del lado izquierdo.
                                    if (tokensoperacion.get(posIni-1).equalsIgnoreCase("+") || tokensoperacion.get(posIni-1).equalsIgnoreCase("-") || tokensoperacion.get(posIni-1).equalsIgnoreCase(")") || tokensoperacion.get(posIni-1).equalsIgnoreCase("(")) {
                                        
                                        // De ser así, se elimina el parentesis
                                        tokensoperacion.remove(posIni);
                                    }
                                    
                                    // En cualquier otro caso, se cambia el parentesis por una multiplicación
                                    else {
                                        tokensoperacion.set(posIni, "*");
                                    }
                                    
                                }
                                
                                // Al ser el principio, solamente se elimina el parentesis.
                                else {
                                    tokensoperacion.remove(posIni);
                                }
                                
                            }
                            
                            continue;
                        }
                        
                        // Si hay una multiplicación en la operación.
                        else if (tokensoperacion.lastIndexOf("*") != -1) {
                            
                            // Se almacena la última variable en el triplo y se elimina de la lista.
                            numeroln.add(numeroinstruccion);
                            objeto.add("T" +numerotemporal);
                            fuente.add(tokensoperacion.remove(tokensoperacion.lastIndexOf("*")+1));
                            operador.add("=");
                            numeroinstruccion++;

                            // En el triplo se almacena la operacion realizada.
                            numeroln.add(numeroinstruccion);
                            objeto.add("T" +numerotemporal);
                            fuente.add(tokensoperacion.remove(tokensoperacion.lastIndexOf("*")-1));
                            operador.add(tokensoperacion.get(tokensoperacion.lastIndexOf("*")));
                            numeroinstruccion++;

                            // Se agrega a la lista el triplo realizado.
                            tokensoperacion.set(tokensoperacion.lastIndexOf("*"),"T" +numerotemporal);
                            
                            // Se aumenta el contador del triplo
                            numerotemporal++;
                            
                            continue;
                        }
                        
                        // Si hay una division en la operación.
                        else if (tokensoperacion.lastIndexOf("/") != -1) {
                            
                            // Se almacena la última variable en el triplo y se elimina de la lista.
                            numeroln.add(numeroinstruccion);
                            objeto.add("T" +numerotemporal);
                            fuente.add(tokensoperacion.remove(tokensoperacion.lastIndexOf("/")+1));
                            operador.add("=");
                            numeroinstruccion++;

                            // En el triplo se almacena la operacion realizada.
                            numeroln.add(numeroinstruccion);
                            objeto.add("T" +numerotemporal);
                            fuente.add(tokensoperacion.remove(tokensoperacion.lastIndexOf("/")-1));
                            operador.add(tokensoperacion.get(tokensoperacion.lastIndexOf("/")));
                            numeroinstruccion++;

                            // Se agrega a la lista el triplo realizado.
                            tokensoperacion.set(tokensoperacion.lastIndexOf("/"),"T" +numerotemporal);
                            
                            // Se aumenta el contador del triplo
                            numerotemporal++;
                            
                            continue;
                        }
                        
                        // Si solo quedan sumas y restas.
                        else {
                            
                            // Se almacena la última variable en el triplo y se elimina de la lista.
                            numeroln.add(numeroinstruccion);
                            objeto.add("T" +numerotemporal);
                            fuente.add(tokensoperacion.remove(tokensoperacion.size()-1));
                            operador.add("=");
                            numeroinstruccion++;

                            // En el triplo se almacena la operacion realizada.
                            numeroln.add(numeroinstruccion);
                            objeto.add("T" +numerotemporal);
                            operador.add(tokensoperacion.remove(tokensoperacion.size()-1));
                            fuente.add(tokensoperacion.remove(tokensoperacion.size()-1));
                            numeroinstruccion++;

                            // Se agrega a la lista el triplo realizado.
                            tokensoperacion.add("T" +numerotemporal);
                            
                            // Se aumenta el contador del triplo
                            numerotemporal++;
                        }
                        
                    }
                    
                    // El resultado final es almacenado en la variable
                    numeroln.add(numeroinstruccion);
                    objeto.add(variableAsig);
                    fuente.add(tokensoperacion.get(0));
                    operador.add("=");
                    numeroinstruccion++;
                    
                }
            }
        }
        
        // Ahora se genera el texto de la tripleta.
        StringBuilder stb = new StringBuilder("\tCódigo objeto\tCódigo fuente\tOperador");
        
        stb.append(n);
        for (int i = 0; i < numeroln.size(); i++) {
            stb.append(numeroln.get(i)).append("\t")
                    .append(objeto.get(i)).append("\t\t")
                    .append(fuente.get(i)).append("\t\t")
                    .append(operador.get(i)).append(n);
        }
        stb.append(numeroln.size()+1).append("\t...\t\t...\t\t...");
        
        // se guarda la tripleta
        tripleta = new tripleta(numeroln, objeto, fuente, operador, lugardo, lugarwhile);
        
        // Se guarda el archivo.
        try (FileWriter fw = new FileWriter("Tripleta.txt")) {
            fw.write(stb.toString());
        }
        catch (IOException ex) {
            System.out.println("No se pudo guardar el archivo.");
        }
    }
    
    public static void generarEnsamblador() {
        
        // Se crean variables referentes a los registros.
        String AH = "";
        String AL = "";
        String CL = "";
        
        // Variables con el numero de ciclo y condicion.
        int numdo = 1;
        int numwhile = 1;
        
        // Se guarda la posición del primer do y while.
        int numinsdo = 0;
        int numinswhile = 0;
        try {
            numinsdo = tripleta.lugardo.remove(0) - 1;
            numinswhile = tripleta.lugarwhile.remove(0) - 1;
        }
        catch (IndexOutOfBoundsException ex) {
            // No hay ciclos.
            numinsdo = -1;
            numinswhile = -1;
        }
        
        // Pila que almacena siempre el ultimo ciclo.
        ArrayList<Integer> ultdo = new ArrayList<>();
        
        // En dado caso de que la pila se vacíe antes, se usa este String.
        String finaldo = "";
        
        // Variable que contendrá el código ensamblador
        StringBuilder stb = new StringBuilder();
        
        // Lo divertido del ensamblador comienza en esta parte.
        for (int i = 0; i < tripleta.numeroln.size(); i++) {
            
            // Se verifica si estamos ante una instruccion do.
            if (i == numinsdo) {
                
                finaldo = "do" + numdo;
                stb.append(finaldo+":"+n);
                ultdo.add(numdo);
                numdo++;
                
                // Se elimina la posicion de este do.
                try {
                    while (numinsdo == tripleta.lugardo.get(0) - 1) {
                        numinsdo = tripleta.lugardo.remove(0) - 1;
                    }
                    numinsdo = tripleta.lugardo.remove(0) - 1;
                }
                catch (IndexOutOfBoundsException ex) {
                    // No hay más ciclos.
                    numinsdo = -1;
                }
            }
            
            // Si estamos ante una instruccion while.
            if (i == numinswhile) {
                
                stb.append("while"+numwhile+":"+n);
                numwhile++;
                
                /*
                // Se limpia lo que habia en AL.
                if (!AL.isEmpty()) {
                    stb.append("\tMOV AL, 0;").append(linea);
                    AL = "";
                }
                */
                
                // Se pasa la variable a comparar a AL.
                stb.append("\tMOV AL, "+tripleta.fuente.get(i)+n);
                AL = tripleta.fuente.get(i);
                i++;
                
                // Ahora se compara lo que tiene AL.
                stb.append("\tCMP AL, "+tripleta.fuente.get(i)+n);
                AL = tripleta.fuente.get(i);
                
                int posciclo = 0;
                
                // Se procede a leer que comparación se esta realizando.
                // Si se trata de un menor.
                if (tripleta.operador.get(i).equalsIgnoreCase("<")) {
                    try {
                        posciclo = ultdo.remove(ultdo.size()-1);
                        stb.append("\tJL do" +posciclo+n);
                    }
                    catch (ArrayIndexOutOfBoundsException ex) {
                        stb.append("\tJL "+finaldo+n);
                        finaldo = "";
                    }
                    
                }
                
                // Si se trata de un menor o igual que.
                else if (tripleta.operador.get(i).equalsIgnoreCase("<=")) {
                    try {
                        posciclo = ultdo.remove(ultdo.size()-1);
                        stb.append("\tJLE do"+posciclo+n);
                    }
                    catch (ArrayIndexOutOfBoundsException ex) {
                        stb.append("\tJLE "+finaldo+n);
                        finaldo = "";
                    }
                }
                
                // Si se trata de un mayor.
                else if (tripleta.operador.get(i).equalsIgnoreCase(">")) {
                    try {
                        posciclo = ultdo.remove(ultdo.size()-1);
                        stb.append("\tJG do"+posciclo+n);
                    }
                    catch (ArrayIndexOutOfBoundsException ex) {
                        stb.append("\tJG "+finaldo+n);
                        finaldo = "";
                    }
                }
                
                // Si se trata de un mayor o igual que.
                else if (tripleta.operador.get(i).equalsIgnoreCase(">=")) {
                    try {
                        posciclo = ultdo.remove(ultdo.size()-1);
                        stb.append("\tJGE do"+posciclo+n);
                    }
                    catch (ArrayIndexOutOfBoundsException ex) {
                        stb.append("\tJGE "+finaldo+n);
                        finaldo = "";
                    }
                }
                
                // Si se trata de un igual que.
                else if (tripleta.operador.get(i).equalsIgnoreCase("==")) {
                    try {
                        posciclo = ultdo.remove(ultdo.size()-1);
                        stb.append("\tJE do"+posciclo+n);
                    }
                    catch (ArrayIndexOutOfBoundsException ex) {
                        stb.append("\tJE "+finaldo+n);
                        finaldo = "";
                    }
                }
                
                // Si se trata de un diferente de.
                else if (tripleta.operador.get(i).equalsIgnoreCase("!=")) {
                    try {
                        posciclo = ultdo.remove(ultdo.size()-1);
                        stb.append("\tJNE do"+posciclo+n);
                    }
                    catch (ArrayIndexOutOfBoundsException ex) {
                        stb.append("\tJNE "+finaldo+n);
                        finaldo = "";
                    }
                }
                
                // Se saltan las dos lineas que contienen la instrucción while
                i = i + 2;
                
                // Se elimina la posicion de este while.
                try {
                    numinswhile = tripleta.lugarwhile.remove(0) - 1;
                }
                catch (IndexOutOfBoundsException ex) {
                    // No hay más ciclos.
                    numinswhile = -1;
                }
                
                // Si a continuación no nos encontramos con un while o un do,
                // se considera que es continuación del ciclo anterior.
                if (i + 1 != numinsdo && i + 1 != numinswhile && i + 1 < tripleta.numeroln.size()) {
                    if (!finaldo.isEmpty()) {
                        stb.append("\tJMP "+finaldo+"iter2"+n+finaldo+"iter2:"+n);
                    }
                    else {
                        stb.append("\tJMP exit"+n+"exit:"+n);
                    }
                }
            }
            
            // Se verifica que el operador es una asignación.
            else if (!tripleta.objeto.get(i).contains("T") && !tripleta.fuente.get(i).contains("T")) {
                
                stb.append("\tMOV "+tripleta.objeto.get(i)+", "+tripleta.fuente.get(i)+n);
                
            }
            
            // Si solamente la columna 2 no contiene una variable temporal, estamos ante una asignación.
            else if (!tripleta.objeto.get(i).contains("T")) {
                
                // Se mueve de AL el resultado a la variable correspondiente.
                stb.append("\tMOV "+tripleta.objeto.get(i)+", AL"+n);
                
                // Se limpia la variable para detectar que lo que almacena BH ya no es importante.
                AL = "";
                
            }
            
            // De no ser ningun caso anterior, estamos ante una operación aritmetica.
            else {
                
                // Si AL se encuentra vacía, se procede a realizar la operación.
                if (AL.isEmpty()) {
                    
                    // Se mueve a AL el primer número.
                    stb.append("\tMOV AL, "+tripleta.fuente.get(i)+n);
                    AL = tripleta.fuente.get(i);
                    
                    // Se mueve al siguiente número.
                    i++;
                    
                    // Si no estamos ante una división, se utiliza el registro AH
                    if (!tripleta.operador.get(i).equalsIgnoreCase("/")) {
                        stb.append("\tMOV AH, "+tripleta.fuente.get(i)+n);
                        AH = tripleta.fuente.get(i);
                    }
                    
                    // Pero si se trata de una división.
                    else {
                        
                        // Se limpia la parte alta de AX si no se encuentra vacía.
                        if (!AH.isEmpty()) {
                            stb.append("\tMOV AH, 0"+n);
                            AH = "";
                        }
                        
                        // Se pasa a CL el divisor.
                        stb.append("\tMOV CL, "+tripleta.fuente.get(i)+n);
                        CL = tripleta.fuente.get(i);
                        
                        // Se realiza la división y se indica los registros con información.
                        stb.append("\tDIV CL"+n);
                        AH = "Residuo";
                        AL = "Resultado";
                    }
                    
                    // Si se trata de una multiplicación.
                    if (tripleta.operador.get(i).equalsIgnoreCase("*")) {
                        stb.append("\tMUL AL, AH"+n);
                        AL = "AL * AH";
                    }
                    
                    // Si se trata de una resta.
                    else if (tripleta.operador.get(i).equalsIgnoreCase("-")) {
                        stb.append("\tSUB AL, AH"+n);
                        AL = "AL - AH";
                    }
                    
                    // Si se trata de una suma.
                    else if (tripleta.operador.get(i).equalsIgnoreCase("+")) {
                        stb.append("\tADD AL, AH"+n);
                        AL = "AL + AH";
                    }
                }
                
                // Si BL tiene guardada información, se utiliza la misma para seguir utilizandose.
                else {
                    
                    // Se salta la instrucción que hace un guardado de dos temporales.
                    i++;
                    
                    // Si no estamos ante una división, se utiliza el registro AH
                    if (!tripleta.operador.get(i).equalsIgnoreCase("/")) {
                        stb.append("\tMOV AH, "+tripleta.fuente.get(i)+n);
                        AH = tripleta.fuente.get(i);
                    }
                    
                    // Pero si se trata de una división.
                    else {
                        
                        // Se limpia la parte alta de AX si no se encuentra vacía.
                        if (!AH.isEmpty()) {
                            stb.append("\tMOV AH, 0"+n);
                            AH = "";
                        }
                        
                        // Se pasa a CL el divisor.
                        stb.append("\tMOV CL, "+tripleta.fuente.get(i)+n);
                        CL = tripleta.fuente.get(i);
                        
                        // Se realiza la división y se indica los registros con información.
                        stb.append("\tDIV CL"+n);
                        AH = "Residuo";
                        AL = "Resultado";
                    }
                    
                    // Si se trata de una multiplicación.
                    if (tripleta.operador.get(i).equalsIgnoreCase("*")) {
                        stb.append("\tMUL AL, AH"+n);
                        AL = "AL * AH";
                    }
                    
                    // Si se trata de una resta.
                    else if (tripleta.operador.get(i).equalsIgnoreCase("-")) {
                        stb.append("\tSUB AL, AH"+n);
                        AL = "AL - AH";
                    }
                    
                    // Si se trata de una suma.
                    else if (tripleta.operador.get(i).equalsIgnoreCase("+")) {
                        stb.append("\tADD AL, AH"+n);
                        AL = "AL + AH";
                    }
                    
                }
                
            }
            
            // Si la siguiente instrucción es un ciclo.
            if (i + 1 == numinsdo) {
                
                // Se escribe un salto hacia el ciclo.
                stb.append("\tJMP do"+numdo+n);
                
            }
            
            // Si la siguiente instrucción es una condicion.
            if (i + 1 == numinswhile) {
                
                // Se escribe un salto hacia el ciclo.
                stb.append("\tJMP while"+numwhile+n);
                
            }
            
            // Si estamos en la ultima linea.
            if (i + 1 == tripleta.numeroln.size()) {
                stb.append("\tend");
            }
            
        }
        
        // Se guarda el archivo final.
        try (FileWriter fw = new FileWriter("Ensamblador.txt")) {
            fw.write(stb.toString());
        }
        catch (IOException ex) {
            System.out.println("No se pudo guardar el archivo.");
        }
    }
    
    // Método para convertir un arreglo a texto.
    public static String arregloATexto (String[] arreglo) {
        StringBuilder stb = new StringBuilder();
        
        for (String texto : arreglo) {
            stb.append(texto);
        }
        
        return stb.toString();
    }
    
}
