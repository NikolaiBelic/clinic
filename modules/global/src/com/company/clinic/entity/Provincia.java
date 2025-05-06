package com.company.clinic.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;

public enum Provincia implements EnumClass<String> {
    ALAVA("Álava"),
    ALBACETE("Albacete"),
    ALICANTE("Alicante"),
    ALMERIA("Almería"),
    ASTURIAS("Asturias"),
    AVILA("Ávila"),
    BADAJOZ("Badajoz"),
    BARCELONA("Barcelona"),
    BURGOS("Burgos"),
    CACERES("Cáceres"),
    CADIZ("Cádiz"),
    CANTABRIA("Cantabria"),
    CASTELLON("Castellón"),
    CEUTA("Ceuta"),
    CIUDAD_REAL("Ciudad Real"),
    CORDOBA("Córdoba"),
    CUENCA("Cuenca"),
    GERONA("Gerona"),
    GRANADA("Granada"),
    GUADALAJARA("Guadalajara"),
    GUIPUZCOA("Guipúzcoa"),
    HUELVA("Huelva"),
    HUESCA("Huesca"),
    ISLAS_BALEARES("Islas Baleares"),
    JAEN("Jaén"),
    LA_CORUÑA("La Coruña"),
    LA_RIOJA("La Rioja"),
    LAS_PALMAS("Las Palmas"),
    LEON("León"),
    LERIDA("Lérida"),
    LUGO("Lugo"),
    MADRID("Madrid"),
    MALAGA("Málaga"),
    MELILLA("Melilla"),
    MURCIA("Murcia"),
    NAVARRA("Navarra"),
    ORENSE("Orense"),
    PALENCIA("Palencia"),
    PONTEVEDRA("Pontevedra"),
    SALAMANCA("Salamanca"),
    SANTA_CRUZ_DE_TENERIFE("Santa Cruz de Tenerife"),
    SEGOVIA("Segovia"),
    SEVILLA("Sevilla"),
    SORIA("Soria"),
    TARRAGONA("Tarragona"),
    TERUEL("Teruel"),
    TOLEDO("Toledo"),
    VALENCIA("Valencia"),
    VALLADOLID("Valladolid"),
    VIZCAYA("Vizcaya"),
    ZAMORA("Zamora"),
    ZARAGOZA("Zaragoza");

    private String id;

    Provincia(String value) {
        this.id = value;
    }

    @JsonValue
    public String getId() {
        return id;
    }

    @Nullable
    @JsonCreator
    public static Provincia fromId(String id) {
        for (Provincia at : Provincia.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}
