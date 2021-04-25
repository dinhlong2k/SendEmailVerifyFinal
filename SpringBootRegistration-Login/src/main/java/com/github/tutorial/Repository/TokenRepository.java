package com.github.tutorial.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.tutorial.Entities.VerificationToken;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Long>{
	VerificationToken findByToken(String token);
	VerificationToken save(VerificationToken token);
}
