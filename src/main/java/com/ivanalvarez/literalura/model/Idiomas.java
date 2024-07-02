package com.ivanalvarez.literalura.model;

public enum Idiomas {
    SPANISH("es"),
    ENGLISH("en"),
    FRENCH("fr"),
    PORTUGUESE("pt");

    private String idioma;

    Idiomas (String idioma){
        this.idioma = idioma;
    }

    public static Idiomas fromString(String text) {
        for (Idiomas idioma : Idiomas.values()) {
            if (idioma.idioma.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Ningun idioma encontrado: " + text);
    }
}
