package objetos;

import java.io.Serializable;

public class coccion_actual implements Serializable {

    int estado;
    String t_fin_mace;
    String t_inicio_mace;
    double temp_mace;
    double temp_olla;
    String receta_elegida;
    boolean arduinoConectado=false;

    public coccion_actual() {
    }

    public coccion_actual(int estado, String t_fin_mace, String t_inicio_mace, double temp_mace, double temp_olla, String receta_elegida) {
        this.estado = estado;
        this.t_fin_mace = t_fin_mace;
        this.t_inicio_mace = t_inicio_mace;
        this.temp_mace = temp_mace;
        this.temp_olla = temp_olla;
        this.receta_elegida = receta_elegida;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getT_fin_mace() {
        return t_fin_mace;
    }

    public void setT_fin_mace(String t_fin_mace) {
        this.t_fin_mace = t_fin_mace;
    }

    public String getT_inicio_mace() {
        return t_inicio_mace;
    }

    public void setT_inicio_mace(String t_inicio_mace) {
        this.t_inicio_mace = t_inicio_mace;
    }

    public double getTemp_mace() {
        return temp_mace;
    }

    public void setTemp_mace(double temp_mace) {
        this.temp_mace = temp_mace;
    }

    public double getTemp_olla() {
        return temp_olla;
    }

    public void setTemp_olla(double temp_olla) {
        this.temp_olla = temp_olla;
    }

    public String getReceta_elegida() {
        return receta_elegida;
    }

    public void setReceta_elegida(String receta_elegida) {
        this.receta_elegida = receta_elegida;
    }

    public void setArduinoConectado(boolean est){
        arduinoConectado=est;
    }

    public boolean getArduinoConectado(){
        return arduinoConectado;
    }
}


