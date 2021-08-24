package mn.spike.jwk.config;

import java.util.Objects;
import java.util.Map;
import java.util.HashMap;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("jwk")
public class JwkConfig {
	public static final String RSA_TYPE = "RSA";
	public static final String NONE_TYPE = "None";

	private boolean enabled = false;
	private String type = NONE_TYPE;

	private RSAConfig rsa = null;

	public JwkConfig() {
	}

	public JwkConfig(boolean enabled, String type, RSAConfig rsa) {
		this.enabled = enabled;
		this.type = type;
		this.rsa = rsa;
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public RSAConfig getRsa() {
		return this.rsa;
	}

	public void setRsa(RSAConfig rsa) {
		this.rsa = rsa;
	}

	public JwkConfig enabled(boolean enabled) {
		setEnabled(enabled);
		return this;
	}

	public JwkConfig type(String type) {
		setType(type);
		return this;
	}

	public JwkConfig rsa(RSAConfig rsa) {
		setRsa(rsa);
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof JwkConfig)) {
			return false;
		}
		JwkConfig jwkConfig = (JwkConfig) o;
		return enabled == jwkConfig.enabled && Objects.equals(type, jwkConfig.type) && Objects.equals(rsa, jwkConfig.rsa);
	}

	@Override
	public int hashCode() {
		return Objects.hash(enabled, type, rsa);
	}

	@Override
	public String toString() {
		return "{" +
			" enabled='" + isEnabled() + "'" +
			", type='" + getType() + "'" +
			", rsa='" + getRsa() + "'" +
			"}";
	}

	@ConfigurationProperties("rsa")
	public static class RSAConfig {
		private boolean auto = false;
		private String signingKey = null;
		private Map<String, String> keys = new HashMap<>();

		public RSAConfig() {
		}

		public RSAConfig(boolean auto, String signingKey, Map<String,String> keys) {
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

		public Map<String,String> getKeys() {
			return this.keys;
		}

		public void setKeys(Map<String,String> keys) {
			this.keys = keys;
		}

		public RSAConfig auto(boolean auto) {
			setAuto(auto);
			return this;
		}

		public RSAConfig signingKey(String signingKey) {
			setSigningKey(signingKey);
			return this;
		}

		public RSAConfig keys(Map<String,String> keys) {
			setKeys(keys);
			return this;
		}

		@Override
		public boolean equals(Object o) {
			if (o == this)
				return true;
			if (!(o instanceof RSAConfig)) {
				return false;
			}
			RSAConfig rSAConfig = (RSAConfig) o;
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

}
