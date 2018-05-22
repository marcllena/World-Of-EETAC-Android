package projecte.dsa.com.world_of_eetac_android.Mon;

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
    private String[] listaAmigos;
    private List<Objeto> inventario;
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
    public void setInventario(List<Objeto> inventario) {
        this.inventario = inventario;
    }
    public List<Objeto> getInventario() {
        return inventario;
    }
    public void setListaAmigos(String[] listaAmigos) {
        this.listaAmigos = listaAmigos;
    }
    public String[] getListaAmigos() {
        return listaAmigos;
    }

    public Usuario()
    {
        this.listaAmigos = new String[50];
        this.inventario = new LinkedList<Objeto>(); //Potser es mes rentable un altre tipus d'estructura de Dades.
    }
    public Usuario(String nick, String password, int profession){
        this.nickname = nick;
        this.password = password;
        this.profession = profession;
        this.listaAmigos = new String[50];
        this.inventario = new LinkedList<Objeto>(); //Potser es mes rentable un altre tipus d'estructura de Dades.
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

    @Override
    public String toString() {
        return "Usuario{" + "nickname='" + nickname + '\'' + ", password='" + password + '\'' + ", level=" + level + ", profession=" + profession + ", attack=" + attack + ", defense=" + defense + ", magicAttack=" + magicAttack + ", magicDefense=" + magicDefense + ", listaAmigos=" + Arrays.toString(listaAmigos) + ", inventario=" + inventario + '}';
    }
}
