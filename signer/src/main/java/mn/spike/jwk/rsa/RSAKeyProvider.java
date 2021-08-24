package mn.spike.jwk.rsa;

import java.util.List;

import com.nimbusds.jose.jwk.RSAKey;

public interface RSAKeyProvider {
	public RSAKey getSigningKey();
	public List<RSAKey> getAllRsaKeys();
}
