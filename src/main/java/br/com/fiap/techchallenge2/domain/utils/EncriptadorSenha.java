package br.com.fiap.techchallenge2.domain.utils;

public abstract class EncriptadorSenha
{

    public static String encriptar(String senha) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(senha.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao encriptar a senha", e);
        }
    }

    public static boolean verificarSenha(String senhaInformada, String hashSalvo) {
        String hashInformado = encriptar(senhaInformada);
        return hashInformado.equals(hashSalvo);
    }
}
