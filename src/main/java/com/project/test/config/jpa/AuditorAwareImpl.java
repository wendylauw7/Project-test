package com.project.test.config.jpa;



import com.project.test.util.AuthenticationUtil;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return AuthenticationUtil.getAuthenticationName();
    }


}
