package com.peopleapps.security;


import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.peopleapps.dto.auth.AdmTokenDto;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.util.List;
import java.util.UUID;

public class MicroProfileService {

    public static AdmTokenDto generateJWT(PrivateKey key,
                                          String subject,
                                          List<String> groups,
                                          String personKey,
                                          String email,
                                          Long roleId,
                                          String roleName
    ) {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .keyID("acodihueKey")
                .build();

        MicroProfileToken token = new MicroProfileToken();
        token.setAud("peopleApps");
        token.setIss("https://acodihue.peopleapps.dev");  // Must match the expected issues configuration values
        token.setJti(UUID.randomUUID().toString());

        token.setSub(subject);
        token.setUpn(subject);

        token.setIat(System.currentTimeMillis());
        token.setExp(System.currentTimeMillis() + 24 * 60 * 60 * 1000); // 24 hours expiration!

        token.setGroups(groups);
        token.setPersonKey(personKey);
        token.setEmail(email);
        token.setRoleId(roleId);
        token.setRoleName(roleName);

        JWSObject jwsObject = new JWSObject(header, new Payload(token.toJSONString()));

        // Apply the Signing protection
        JWSSigner signer = new RSASSASigner(key);

        //object to return
        AdmTokenDto tokenDto = new AdmTokenDto();


        try {
            jwsObject.sign(signer);
            tokenDto.setToken(jwsObject.serialize());
            tokenDto.setExpire(token.getExp());
        } catch (JOSEException e) {
            e.printStackTrace();
        }

        return tokenDto;
    }

    public PrivateKey readPrivateKey() throws IOException {

        InputStream inputStream = MicroProfileService.class.getResourceAsStream("/privateKey.pem");

        PEMParser pemParser = new PEMParser(new InputStreamReader(inputStream));
        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider(new BouncyCastleProvider());
        Object object = pemParser.readObject();
        KeyPair kp = converter.getKeyPair((PEMKeyPair) object);
        return kp.getPrivate();
    }
}
