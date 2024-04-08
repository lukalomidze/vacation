package pt.ribas.vacation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.ribas.vacation.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long>{

}
