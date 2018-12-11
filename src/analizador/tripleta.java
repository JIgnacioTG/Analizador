/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package analizador;

import java.util.ArrayList;

/**
 *
 * @author Gerardo
 */
public class tripleta {
    
    // la tripleta contiene la informacion necesaria para el codigo intermedio.
    // tambien almacena las posiciones donde los ciclos fueron llamados.
    
    ArrayList<Integer> numeroln;
    ArrayList<String> objeto;
    ArrayList<String> fuente;
    ArrayList<String> operador;
    ArrayList<Integer> lugardo;
    ArrayList<Integer> lugarwhile;
    
    public tripleta() {
        numeroln = new ArrayList<>();
        objeto = new ArrayList<>();
        fuente = new ArrayList<>();
        operador = new ArrayList<>();
        lugardo = new ArrayList<>();
        lugarwhile = new ArrayList<>();
    }
    
    public tripleta(ArrayList<Integer> numeroln, ArrayList<String> objeto, ArrayList<String>  fuente, 
            ArrayList<String> operador, ArrayList<Integer> lugardo, ArrayList<Integer> lugarwhile) {
        this.numeroln = numeroln;
        this.objeto = objeto;
        this.fuente = fuente;
        this.operador = operador;
        this.lugardo = lugardo;
        this.lugarwhile = lugarwhile;
    }
    
    public void setTripleta(ArrayList<Integer> numeroln, ArrayList<String> objeto, ArrayList<String>  fuente, 
            ArrayList<String> operador, ArrayList<Integer> lugardo, ArrayList<Integer> lugarwhile) {
        this.numeroln = numeroln;
        this.objeto = objeto;
        this.fuente = fuente;
        this.operador = operador;
        this.lugardo = lugardo;
        this.lugarwhile = lugarwhile;
    }
    
}
