package com.spring.security.example.Repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.spring.security.example.Entity.Employees;

@Repository
public interface EmployeeRepository extends JpaRepository<Employees, Long>
{
	Employees findByEmail(String email);
	Optional<Employees> findById(Long id);
}
