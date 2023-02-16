package com.yapp.ios2.fitfty.infrastructure.user.OAuth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MaterialsOfApplePublicKey {
    String kty;
    String kid;
    String use;
    String alg;
    String n;
    String e;
}
