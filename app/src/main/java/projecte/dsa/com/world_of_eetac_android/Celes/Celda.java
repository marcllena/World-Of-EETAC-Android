package projecte.dsa.com.world_of_eetac_android.Celes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=Hierba.class, name="Hierba"),
        @JsonSubTypes.Type(value=Cofre.class, name="Cofre"),
        @JsonSubTypes.Type(value=Puerta.class, name="Puerta"),
        @JsonSubTypes.Type(value=Ventana.class, name="Ventana"),
        @JsonSubTypes.Type(value=Trampilla.class, name="Trampilla"),
        @JsonSubTypes.Type(value=Paret.class, name="Paret"),
        @JsonSubTypes.Type(value=Rio.class, name="Rio"),
})

public abstract class Celda {
    @JsonIgnore
    public abstract int getPisablePersonaje();
    @JsonIgnore
    public abstract int getInteractuable();
    @JsonIgnore
    public abstract int getPisableZombie();
    @JsonIgnore
    public abstract String getSimbolo();
}

