package com.awg.gpe.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;

/**
 * Clase útil para ver el resultado del hashing de una contraseña en texto plano.
 * 
 * <p>
 * Solo para desarrollo
 * </p>
 * 
 * @author Pablo Barrientos
 * @version 1.0
 * @since 1.0
 *
 */
public class HashPassword {

    /**
     * @param args Argumentos pasados por consola 
     * @throws IOException En el caso de que haya algún error inesperado
     * @version 1.0
     * @since 1.0
     */
    public static void main(String[] args) throws IOException {
        BCryptPasswordEncoder encoder;
        InputStreamReader stream;
        BufferedReader reader;
        String line;
        String hash;

        encoder = new BCryptPasswordEncoder(5);
        stream = new InputStreamReader(System.in);
        reader = new BufferedReader(stream);

        System.out.println("Enter passwords to be hashed or <ENTER> to quit");

        line = reader.readLine();
        while (StringUtils.hasText(line)) {
            hash = encoder.encode(line);
            System.out.println(hash);
            line = reader.readLine();
        }
    }

}
