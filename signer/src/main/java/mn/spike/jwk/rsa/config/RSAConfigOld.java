package mn.spike.jwk.rsa.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.micronaut.context.annotation.ConfigurationProperties;

// @ConfigurationProperties("rsa")
public class RSAConfigOld {
	private boolean auto = false;
	private String signingKey = null;
	private Map<String, Object> keys = new HashMap<>();

	public RSAConfigOld() {
	}

	public RSAConfigOld(boolean auto, String signingKey, Map<String,Object> keys) {
		this.auto = auto;
		this.signingKey = signingKey;
		this.keys = keys;
	}

	public boolean isAuto() {
		return this.auto;
	}

	public boolean getAuto() {
		return this.auto;
	}

	public void setAuto(boolean auto) {
		this.auto = auto;
	}

	public String getSigningKey() {
		return this.signingKey;
	}

	public void setSigningKey(String signingKey) {
		this.signingKey = signingKey;
	}

	public Map<String,Object> getKeys() {
		return this.keys;
	}

	public void setKeys(Map<String,Object> keys) {
		this.keys = keys;
	}

	public RSAConfigOld auto(boolean auto) {
		setAuto(auto);
		return this;
	}

	public RSAConfigOld signingKey(String signingKey) {
		setSigningKey(signingKey);
		return this;
	}

	public RSAConfigOld keys(Map<String,Object> keys) {
		setKeys(keys);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof RSAConfigOld)) {
			return false;
		}
		RSAConfigOld rSAConfig = (RSAConfigOld) o;
		return auto == rSAConfig.auto && Objects.equals(signingKey, rSAConfig.signingKey) && Objects.equals(keys, rSAConfig.keys);
	}

	@Override
	public int hashCode() {
		return Objects.hash(auto, signingKey, keys);
	}

	@Override
	public String toString() {
		return "{" +
			" auto='" + isAuto() + "'" +
			", signingKey='" + getSigningKey() + "'" +
			", keys='" + getKeys() + "'" +
			"}";
	}

}
