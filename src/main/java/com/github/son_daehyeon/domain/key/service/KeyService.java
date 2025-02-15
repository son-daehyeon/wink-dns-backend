package com.github.son_daehyeon.domain.key.service;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Base64;

import org.springframework.stereotype.Service;

import com.github.son_daehyeon.domain.key.dto.response.KeyResponse;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@Service
@RequiredArgsConstructor
public class KeyService {

    @SneakyThrows(NoSuchAlgorithmException.class)
    public KeyResponse generate() {

        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();

        return KeyResponse.builder()
            .privateKey(transferPrivateKey(keyPair))
            .publicKey(transferPublicKey(keyPair))
            .build();
    }

    private String transferPrivateKey(KeyPair keyPair) {

        return "-----BEGIN RSA PRIVATE KEY-----" + '\n'
            + Base64.getMimeEncoder().encodeToString(keyPair.getPrivate().getEncoded()) + '\n'
            + "-----END RSA PRIVATE KEY-----";
    }

    private String transferPublicKey(KeyPair keyPair) {

        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        String type = "ssh-rsa";

        byte[] typeByte = type.getBytes(StandardCharsets.UTF_8);
        byte[] exponent = publicKey.getPublicExponent().toByteArray();
        byte[] modulus = publicKey.getModulus().toByteArray();

        return type + " " + Base64.getEncoder().encodeToString(concat(typeByte,exponent, modulus));
    }

    private byte[] concat(byte[]... arrays) {

        ByteBuffer buffer = ByteBuffer.allocate(Arrays.stream(arrays).mapToInt(arr -> 4 + arr.length).sum());
        Arrays.stream(arrays).forEach(arr -> {
            buffer.putInt(arr.length);
            buffer.put(arr);
        });

        return buffer.array();
    }
}
