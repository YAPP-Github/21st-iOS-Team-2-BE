package com.yapp.ios2.fitfty.infrastructure.user.OAuth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplePublicKeyResponse {
    // 애플은 3개의 공개키를 줌
    @JsonProperty("keys")
    public List<MaterialsOfApplePublicKey> keys;

    // 3개의 공개키 중 사용할 키 찾기
    public Optional<MaterialsOfApplePublicKey> getMatchedKeyBy(Object kid, Object alg) {
        return this.keys.stream()
                .filter(key -> key.getKid().equals(kid) && key.getAlg().equals(alg))
                .findFirst();
    }

    static public ApplePublicKeyResponse getApplePublicKeys() {
        // URI 준비
        URI url = URI.create("https://appleid.apple.com/auth/keys");

        // Http Request 발싸
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        ApplePublicKeyResponse result = null;
        try {
            result = objectMapper.readValue(response.getBody().toString(),ApplePublicKeyResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}