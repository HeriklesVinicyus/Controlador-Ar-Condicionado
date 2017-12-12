/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.heriklesvinicyus.controlearcondicionado.view;

import java.util.Scanner;

/**
 *
 * @author Hérikles
 */
public class Main {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        double numeroPessoas = 0, temperaturaAtual = 0, frio, medio, quente, baixo, media, alto, valoresRegra[];

        System.out.println("Digite o quantidade de pessoas: ");
        numeroPessoas = scan.nextDouble();

        System.out.println("Digite a temperatuda: ");
        temperaturaAtual = scan.nextDouble();

        frio = pertinenciaFrio(temperaturaAtual);
        medio = pertinenciaMedio(temperaturaAtual);
        quente = pertinenciaQuente(temperaturaAtual);

        baixo = pertinenciaBaixo(numeroPessoas);
        media = pertinenciaMedia(numeroPessoas);
        alto = pertinenciaAlto(numeroPessoas);

        double temp[] = {frio, medio, quente, baixo, media, alto};
        valoresRegra = Regras(temp);
        
        System.out.println(defuzzyficacao(valoresRegra));

    }
    
    public static double[] Regras(double[] variaveis) {
        double res[] = new double[3];
        //valocidade baixa 
        res[0] = Double.max(variaveis[0], variaveis[3]);
        //valocidade media
        res[1] = Double.max(Double.min(variaveis[2], variaveis[3]), Double.min(variaveis[0], variaveis[5]));
        //valocidade alta
        res[2] = Double.max(Double.max(variaveis[2], variaveis[5]), Double.min(variaveis[1], variaveis[5]));

        return res;
    }

    public static String defuzzyficacao(double[] variaveis) {

        double soma = 0;
        double divisor = 0;
        
        double centroMassa = 0;

        for (int i = 5; i<=100 ; i+=5) {
            if(isVelocidadeBaixa(i)){
                soma+=(i*variaveis[0]);
                divisor+=variaveis[0];
            }
            if(isVelocidadeMeidaS(i, ((variaveis[1]-40)/10))){
                soma+=(i*variaveis[1]);
                divisor+=variaveis[1];
            }
            if(isVelocidadeMeidaD(i, ((variaveis[1]-50)/10))){
                soma+=(i*variaveis[1]);
                divisor+=variaveis[1];
            }
            if(isVelocidadeAlto(i, (variaveis[2]-65)/10)){
                 soma+=(i*variaveis[2]);
                divisor+=variaveis[2];
            }
        }
        
        centroMassa = soma/divisor;
        
        return centroMassa + " - " + pertinenciaRisco(centroMassa);
    }

    public static double pertinenciaFrio(double temp) {
        if (temp <= 15) {
            return 1;
        } else if (temp > 15 && temp <= 20) {
            return ((temp - 15) / 5);
        } else {
            return 0;
        }
    }

    public static double pertinenciaMedio(double temp) {
        if (temp > 17 && temp <= 22) {
            return ((temp - 17) / 5);
        } else if (temp > 22 && temp <= 27) {
            return 1;
        } else if (temp > 27 && temp <= 32) {
            return ((temp - 27) / 5);
        } else {
            return 0;
        }
    }

    public static double pertinenciaQuente(double temp) {
        if (temp >= 30 && temp < 35) {
            return ((temp - 30) / 5);
        } else if (temp > 35) {
            return 1;
        } else {
            return 0;
        }
    }

    public static double pertinenciaBaixo(double quant) {
        if (quant <= 5) {
            return 1;
        } else if (quant > 5 && quant < 10) {
            return (quant - 5) / (10 - 5);
        } else {
            return 0;
        }
    }

    public static double pertinenciaMedia(double quant) {
        if (quant > 12 && quant <= 17) {
            return ((quant - 12) / (17 - 12));
        } else if (quant > 17 && quant <= 22) {
            return 1;
        } else if (quant > 22 && quant <= 27) {
            return ((quant - 22) / (27 - 22));
        } else {
            return 0;
        }
    }

    public static double pertinenciaAlto(double quant) {
        if (quant >= 30 && quant < 35) {
            return ((quant - 30) / 5);
        } else if (quant > 35) {
            return 1;
        } else {
            return 0;
        }
    }

    public static boolean isVelocidadeBaixa(double aux) {
        return aux <= 30;
    }

    public static boolean isVelocidadeMeidaS(double aux, double valor) {
        return (aux == 35 && valor == 0.5) || (aux >= 40 && aux <= 50);
    }
    public static boolean isVelocidadeMeidaD(double aux, double valor) {
        return (aux == 55 && valor == 0.5);
    }
    public static boolean isVelocidadeAlto(double aux, double valor) {
        return (aux == 60 &&  valor == 0.5) || aux >= 65;
    }

    private static String pertinenciaRisco(double centroMassa) {
        if(centroMassa<=32.5 ){
            return "Velocidade Baixa";
        }else if(centroMassa>32.5 && centroMassa <= 55){
            return "Velocidade Média";
        }else{
            return "Velocidade Alta";
        }
    }
}
