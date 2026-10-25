package dev.akhras;
import org.springframework.data.jpa.repository.JpaRepository;
interface Customers extends JpaRepository<Domain.Customer,Long>{}
interface Policies extends JpaRepository<Domain.Policy,Long>{}
interface Claims extends JpaRepository<Domain.Claim,Long>{}
