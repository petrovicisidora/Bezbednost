package com.pks.pks.security;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;


@NoArgsConstructor
@Getter
@Setter
public class Authority implements GrantedAuthority {

    String name;

    @Override
    public String getAuthority() {
        return name;
    }

}
