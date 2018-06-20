package projecte.dsa.com.world_of_eetac_android.Mon;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import projecte.dsa.com.world_of_eetac_android.Objectes.Objeto;
import projecte.dsa.com.world_of_eetac_android.Objectes.ObjetoConsumible;
import projecte.dsa.com.world_of_eetac_android.Objectes.ObjetoEquipable;

public class Jugador {
    private String nickname;
    public int profession; // 1: Guerrero, 2: Arquero, 3: Mago
    public int attack;
    public int defense;
    public int magicAttack;
    @JsonIgnore
    public Objeto[] equipo=new Objeto[6];
    @JsonIgnore
    public Objeto[] inventario = new Objeto[15];
    public Jugador(String nick, int profession){
        this.nickname = nick;
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
    public int getProfession() {
        return profession;
    }
    public Jugador(){}
}
