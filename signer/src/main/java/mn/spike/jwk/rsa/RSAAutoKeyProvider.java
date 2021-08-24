package mn.spike.jwk.rsa;

import java.util.List;
import java.util.UUID;

import javax.inject.Singleton;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import mn.spike.jwk.config.JwkConfig;
import mn.spike.jwk.exception.JwkException;

@Singleton
@Requires(property = "jwk.enabled", value = StringUtils.TRUE)
@Requires(property = "jwk.type", value = JwkConfig.RSA_TYPE)
@Requires(property = "jwk.rsa.auto", value = StringUtils.TRUE)
public class RSAAutoKeyProvider implements RSAKeyProvider {

	private static final Logger LOG = LoggerFactory.getLogger(RSAAutoKeyProvider.class);

	private RSAKey myKey;

	public RSAAutoKeyProvider() {
		LOG.debug("Initializing RSAAutoKeyProvider...");
		LOG.warn("Using a generated RSA Key for JWT signing...");
		try {
			myKey = new RSAKeyGenerator(2048)
				.algorithm(JWSAlgorithm.RS256)
				.keyUse(KeyUse.SIGNATURE)
				.keyID(generateKid())
				.generate();
		} catch (JOSEException e) {
			String message = "Unable to generate a RSA token";
			LOG.error(message, e);
			throw new JwkException(message, e);
		}
	}

	@Override
	public RSAKey getSigningKey() {
		return myKey;
	}

	@Override
	public List<RSAKey> getAllRsaKeys() {
		return List.of(myKey);
	}

	private String generateKid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
