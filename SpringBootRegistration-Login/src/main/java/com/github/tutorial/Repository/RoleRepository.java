package com.github.tutorial.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.github.tutorial.Entities.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long>{
	Roles findByNameRole(String name);
}
