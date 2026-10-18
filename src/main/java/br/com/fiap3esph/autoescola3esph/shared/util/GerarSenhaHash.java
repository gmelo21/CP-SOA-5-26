package br.com.fiap3esph.autoescola3esph.shared.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarSenhaHash {
    public static void main(String[] args) {
        String senha = args.length > 0 ? args[0] : "123456";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println(encoder.encode(senha));
    }
}
