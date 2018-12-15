package objetos;

import java.io.Serializable;
import java.util.HashMap;

public class recetas implements Serializable {

    double alcohol;
    long color;
    long df;
    long di;
    long ibu;
    String levadura;
    long t_coccion;
    long t_mace;
    long temp_mace;
    HashMap<String,Object> lupulos;
    HashMap<String,Object> maltas;
    String nombre;

    public recetas() {
    }

    public recetas(double alcohol, long color, long df, long di, long ibu, String levadura, long t_coccion, long t_mace, long temp_mace, Object lupulos, Object maltas) {

        this.alcohol = alcohol;
        this.color = color;
        this.df = df;
        this.di = di;
        this.ibu = ibu;
        this.levadura = levadura;
        this.t_coccion = t_coccion;
        this.t_mace = t_mace;
        this.temp_mace = temp_mace;
        this.lupulos = (HashMap<String,Object>)lupulos;
        this.maltas = (HashMap<String,Object>)maltas;
    }

    public double getAlcohol() {
        return alcohol;
    }

    public void setAlcohol(double alcohol) {
        this.alcohol = alcohol;
    }

    public long getColor() {
        return color;
    }

    public void setColor(long color) {
        this.color = color;
    }

    public long getDf() {
        return df;
    }

    public void setDf(long df) {
        this.df = df;
    }

    public long getDi() {
        return di;
    }

    public void setDi(long di) {
        this.di = di;
    }

    public long getIbu() {
        return ibu;
    }

    public void setIbu(long ibu) {
        this.ibu = ibu;
    }

    public String getLevadura() {
        return levadura;
    }

    public void setLevadura(String levadura) {
        this.levadura = levadura;
    }

    public long getT_coccion() {
        return t_coccion;
    }

    public void setT_coccion(long t_coccion) {
        this.t_coccion = t_coccion;
    }

    public long getT_mace() {
        return t_mace;
    }

    public void setT_mace(long t_mace) {
        this.t_mace = t_mace;
    }

    public long getTemp_mace() {
        return temp_mace;
    }

    public void setTemp_mace(long temp_mace) {
        this.temp_mace = temp_mace;
    }

    public HashMap<String,Object> getLupulos() {
        return lupulos;
    }

    public void setLupulos(Object lupulos) {
        this.lupulos =(HashMap<String,Object>) lupulos;
    }

    public HashMap<String,Object> getMaltas() {
        return maltas;
    }

    public void setMaltas(Object maltas) {
        this.maltas =(HashMap<String,Object>) maltas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
