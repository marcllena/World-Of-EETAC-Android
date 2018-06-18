package projecte.dsa.com.world_of_eetac_android.Mon;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import projecte.dsa.com.world_of_eetac_android.Objectes.Objeto;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

//@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
public class Usuario {
    private String nickname;
    private String password;
    private int level;
    private int profession; // 1: Guerrero, 2: Arquero, 3: Mago
    private int attack;
    private int defense;
    private int magicAttack;
    private int magicDefense;
    //private Objeto[] objetos;

    public String getNickname(){
        return this.nickname;
    }
    public String getPassword(){
        return this.password;
    }
    public void setLevel(){
        this.level++;
    }
    public int getLevel(){
        return this.level;
    }
    public int getProfession() {
        return profession;
    }
    public void setAttack(int attack) {
        this.attack = attack;
    }
    public int getAttack() {
        return attack;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public int getDefense() {
        return defense;
    }
    public void setMagicAttack(int magicAttack) {
        this.magicAttack = magicAttack;
    }
    public int getMagicAttack() {
        return magicAttack;
    }
    public void setMagicDefense(int magicDefense) {
        this.magicDefense = magicDefense;
    }
    public int getMagicDefense() {
        return magicDefense;
    }

    public Usuario()
    {
    }
    public Usuario(String nick, String password){
        this.nickname = nick;
        this.password = password;
    }
    public Usuario(String nick, String password, int profession){
        this.nickname = nick;
        this.password = password;
        this.profession = profession;
        if(getProfession() == 1)
        {
            //Stats iniciales guerrero
        }
        else if(getProfession() ==2){
            //Stats iniciales arquero
        }
        else
        {
            //Stats iniciales mago
        }
    }
/*
    public Objeto[] getObjetos() {
        return objetos;
    }

    public void setObjetos(Objeto[] objetos) {
        this.objetos = objetos;
    }
    */
}
