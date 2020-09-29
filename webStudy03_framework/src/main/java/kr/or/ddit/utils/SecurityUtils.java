package kr.or.ddit.utils;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import lombok.Builder;
import lombok.Getter;

public class SecurityUtils {
   
   public static enum RSAKey{
      PRIVATEKEY, PUBLICKEY
   }
   
   @Getter
   @Builder
   public static class CipherVO{
      
      private SecretKey secretKey;
      private byte[] iv;
      
      private PrivateKey privateKey;
      private PublicKey publicKey;
      
   }

   //단방향
   public static String encryptSha512(String plain) {
      
      try {
         
         MessageDigest md = MessageDigest.getInstance("SHA-512");
         byte[] input = plain.getBytes();
         byte[] encryted = md.digest(input);
         String encoded = Base64.getEncoder().encodeToString(encryted);
         
         return encoded;
         
      } catch (NoSuchAlgorithmException e) {
         throw new RuntimeException(e);
      }
   }
   
   //RSA 복호화
   public static String decryptRSA(String encoded, RSAKey keyKind, CipherVO cipherVO) {
      try {
         Cipher cipher = Cipher.getInstance("RSA");
         PrivateKey privateKey = cipherVO.getPrivateKey();
         PublicKey publicKey = cipherVO.getPublicKey();
         Key decryptKey = RSAKey.PRIVATEKEY.equals(keyKind)?privateKey:publicKey;
         cipher.init(Cipher.DECRYPT_MODE, decryptKey);
         byte[] decoded = Base64.getDecoder().decode(encoded);
         byte[] decrypted = cipher.doFinal(decoded);
         return new String(decrypted);
      }catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
   
   //양방향 - 비대칭
   public static String encryptRSA(String plain, RSAKey keyKind, Map<String, CipherVO> resultMap) {
      try {
         
         Cipher cipher = Cipher.getInstance("RSA");
         KeyPairGenerator kpGen = KeyPairGenerator.getInstance("RSA");
         KeyPair keyPair = kpGen.generateKeyPair();
         PrivateKey privateKey = keyPair.getPrivate();
         PublicKey publicKey = keyPair.getPublic();
         Key encryptKey = RSAKey.PUBLICKEY.equals(keyKind)?publicKey:privateKey;
         resultMap.put("cipherSpec", CipherVO.builder()
                                          .privateKey(privateKey)
                                          .publicKey(publicKey)
                                          .build());
         
         cipher.init(Cipher.ENCRYPT_MODE, encryptKey);
         byte[] input = plain.getBytes();
         byte[] encrypted = cipher.doFinal(input);
         String encoded = Base64.getEncoder().encodeToString(encrypted);
         
         return encoded;
         
      }catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
   
   //복원 - 평문
   public static String decryptAES(String encoded, CipherVO cipherVO) {
      try {
         
         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
         SecretKey key = cipherVO.getSecretKey();
         byte[] iv = cipherVO.getIv();
         IvParameterSpec ivSpec = new IvParameterSpec(iv);
         cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
         byte[] decoded = Base64.getDecoder().decode(encoded);
         byte[] decrypted = cipher.doFinal(decoded);
         
         return new String(decrypted);
         
      }catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
   
   //양방향 - 대칭
   public static String encryptAES(String plain, Map<String, CipherVO> resultMap) {
      
      try {
         
         Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
         KeyGenerator keyGen = KeyGenerator.getInstance("AES");
         keyGen.init(128);
         SecretKey key = keyGen.generateKey();
         byte[] iv = new byte[16];
         
         resultMap.put("cipherSpec", CipherVO.builder()
                                             .secretKey(key)
                                             .iv(iv)
                                             .build());
         
         SecureRandom random = new SecureRandom();
         random.nextBytes(iv);
         IvParameterSpec ivSpec = new IvParameterSpec(iv);
         cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
         byte[] input = plain.getBytes();
         byte[] encrypted = cipher.doFinal(input);
         String encoded = Base64.getEncoder().encodeToString(encrypted);
         
         return encoded;
         
      }catch(Exception e) {
         throw new RuntimeException(e);
      }
      
   }
}