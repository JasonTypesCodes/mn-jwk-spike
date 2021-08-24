package mn.spike.jwk.rsa;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Named;
import javax.inject.Singleton;

import com.nimbusds.jose.Algorithm;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import io.micronaut.security.token.jwt.endpoints.JwkProvider;
import io.micronaut.security.token.jwt.signature.rsa.RSASignatureGeneratorConfiguration;
import mn.spike.jwk.config.JwkConfig;

@Singleton
@Requires(property = "jwk.enabled", value = StringUtils.TRUE)
@Requires(property = "jwk.type", value = JwkConfig.RSA_TYPE)
@Named("generator")
public class RSAKeyManager implements RSASignatureGeneratorConfiguration, JwkProvider {

	private RSAKeyProvider provider;
	private RSAKey signingKey;

	public RSAKeyManager(RSAKeyProvider provider) {
		this.provider = provider;
		this.signingKey = provider.getSigningKey();
	}

	@Override
	public RSAPublicKey getPublicKey() {
		try {
			return signingKey.toRSAPublicKey();
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public RSAPrivateKey getPrivateKey() {
		try {
			return signingKey.toRSAPrivateKey();
		} catch (JOSEException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public JWSAlgorithm getJwsAlgorithm() {
		Algorithm al = signingKey.getAlgorithm();
		if (al instanceof JWSAlgorithm) {
			return (JWSAlgorithm) al;
		}

		return JWSAlgorithm.parse(al.getName());
	}

	@Override
	public List<JWK> retrieveJsonWebKeys() {
		return provider.getAllRsaKeys().stream().collect(Collectors.toList());
	}

}
