package projecte.dsa.com.world_of_eetac_android.Celes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value=Hierba.class, name="hierba"),
        @JsonSubTypes.Type(value=Cofre.class, name="cofre"),
        @JsonSubTypes.Type(value=Puerta.class, name="puerta"),
        @JsonSubTypes.Type(value=Rio.class, name="Rio"),
})

public abstract class Celda {
    @JsonIgnore
    public abstract int getPisable();
    @JsonIgnore
    public abstract int getInteractuable();
    @JsonIgnore
    public abstract String getSimbolo();
}

