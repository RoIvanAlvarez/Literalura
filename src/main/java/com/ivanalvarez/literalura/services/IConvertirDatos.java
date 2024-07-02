package com.ivanalvarez.literalura.services;

public interface IConvertirDatos {
    <T> T obtenerDatos(String json,Class<T> clase);
}
