package mn.spike.jwk.rsa;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.inject.Singleton;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.RSAKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.exceptions.ConfigurationException;
import io.micronaut.core.util.StringUtils;
import mn.spike.jwk.config.JwkConfig;
import mn.spike.jwk.config.JwkConfig.RSAConfig;

@Singleton
@Requires(property = "jwk.enabled", value = StringUtils.TRUE)
@Requires(property = "jwk.type", value = JwkConfig.RSA_TYPE)
@Requires(property = "jwk.rsa.auto", notEquals = StringUtils.TRUE)
public class RSAConfigKeyProvider implements RSAKeyProvider {

	private static final Logger LOG = LoggerFactory.getLogger(RSAConfigKeyProvider.class);

	private RSAConfig config;
	private Map<String, RSAKey> keyMap;

	public RSAConfigKeyProvider(RSAConfig config) {
		LOG.debug("Initializing RSAConfigKeyProvider...");
		this.config = config;
		this.keyMap = parse(this.config.getKeys());
		if (config.getKeys().isEmpty()) {
			throw new ConfigurationException("No jwk.rsa.keys configured!");
		}
		if (config.getSigningKey() == null) {
			throw new ConfigurationException("jwk.rsa.signing-key is not set.");
		}
		if (!keyMap.containsKey(config.getSigningKey())) {
			throw new ConfigurationException("Unable to locate a private key for configured signing key " + config.getSigningKey());
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

	private Map<String, RSAKey> parse(Map<String, String> input) {
		Map<String, RSAKey> result = new HashMap<>(input.size());
		LOG.debug("Looking for keys to parse...");
		for (Entry<String, String> inputEntry : input.entrySet()) {
			LOG.debug("Parsing {}...", inputEntry.getKey());
			result.put(inputEntry.getKey(), parse(inputEntry.getKey(), inputEntry.getValue()));
		}

		return result;
	}

	private RSAKey parse(String key, String input) {
		try {
			JWK parsedJwk = JWK.parse(input);
			if (parsedJwk instanceof RSAKey) {
				return (RSAKey) parsedJwk;
			} else {
				throw new ConfigurationException(key + " is not an RSA Key!");
			}
		} catch (ParseException e) {
			throw new ConfigurationException("Failed to parse " + key, e);
		}
	}


}
