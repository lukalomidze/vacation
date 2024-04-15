package pt.ribas.vacation.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ribas.vacation.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{
    Optional<Admin> findByUsername(String username);
}
