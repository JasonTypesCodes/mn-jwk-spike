package mn.spike.jwk.rsa;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.inject.Singleton;
import javax.naming.ConfigurationException;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;

import io.micronaut.context.annotation.Requires;
import io.micronaut.core.util.StringUtils;
import mn.spike.jwk.config.JwkConfig;
import mn.spike.jwk.config.JwkConfig.RSAConfig;

@Singleton
@Requires(property = "jwk.enabled", value = StringUtils.TRUE)
@Requires(property = "jwk.type", value = JwkConfig.RSA_TYPE)
@Requires(property = "jwk.rsa.auto", notEquals = StringUtils.TRUE)
public class RSAConfigKeyProvider implements RSAKeyProvider {
	private RSAConfig config;
	private Map<String, RSAKey> keyMap;

	public RSAConfigKeyProvider(JwkConfig jwkConfig) throws ConfigurationException {
		this.config = jwkConfig.getRsa();
		this.keyMap = parse(this.config.getKeys());
		if (config.getKeys().isEmpty()) {
			throw new ConfigurationException("No jwk.rsa.keys configured!");
		}
		if (config.getSigningKey() == null) {
			throw new ConfigurationException("jwk.rsa.signingKey is not set.");
		}
		if (!keyMap.containsKey(config.getSigningKey())) {
			throw new ConfigurationException("Unable to locate a private key for configured signing key");
		}
	}

	@Override
	public RSAKey getSigningKey() {
		return keyMap.get(config.getSigningKey());
	}

	@Override
	public List<RSAKey> getAllRsaKeys() {
		return keyMap.values().stream().collect(Collectors.toList());
	}

	private Map<String, RSAKey> parse(Map<String, Object> input) throws ConfigurationException {
		Map<String, RSAKey> result = new HashMap<>(input.size());

		for (Entry<String, Object> inputEntry : input.entrySet()) {
			result.put(inputEntry.getKey(), parse(inputEntry.getKey(), (String) inputEntry.getValue()));
		}

		return result;
	}

	private RSAKey parse(String key, String input) throws ConfigurationException {
		try {
			JWK parsedJwk = JWK.parse(input);
			if (parsedJwk instanceof RSAKey) {
				return (RSAKey) parsedJwk;
			} else {
				throw new ConfigurationException(key + " is not an RSA Key!");
			}
		} catch (ParseException e) {
			e.printStackTrace();
			throw new ConfigurationException("Failed to parse " + key);
		}
	}


}
