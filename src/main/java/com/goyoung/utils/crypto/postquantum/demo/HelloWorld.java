package com.goyoung.utils.crypto.postquantum.demo;

import org.apache.maven.plugin.MojoExecutionException;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.digests.SHA3Digest;
import org.bouncycastle.pqc.crypto.MessageSigner;
import org.bouncycastle.pqc.crypto.sphincs.*;

import java.security.SecureRandom;

/**
 * Created by gyoung on 10/8/16.
 */
public class HelloWorld {
    public static void main(String[] args) throws MojoExecutionException {

        SPHINCS256KeyPairGenerator generator = new SPHINCS256KeyPairGenerator();
        generator.init(new SPHINCS256KeyGenerationParameters(new SecureRandom(), new SHA3Digest(256)));
        AsymmetricCipherKeyPair kp = generator.generateKeyPair();
        SPHINCSPrivateKeyParameters priv = (SPHINCSPrivateKeyParameters) kp.getPrivate();
        SPHINCSPublicKeyParameters pub = (SPHINCSPublicKeyParameters) kp.getPublic();
        MessageSigner sphincsSigner = new SPHINCS256Signer(new SHA3Digest(256), new SHA3Digest(512));
        sphincsSigner.init(true, priv);

        byte[] msg = "This is a test".getBytes();

        byte[] sig = sphincsSigner.generateSignature(msg);
        String out = new String(sig);

        sphincsSigner.init(false, pub);
        boolean test = sphincsSigner.verifySignature(msg, sig);
        
        System.out.println(out +  "\n" + test);
    }
}
