package com.example.computershopserver.repository;

import com.example.computershopserver.entity.DetailImport;
import com.example.computershopserver.entity.Imports;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetailImportRepository extends JpaRepository<DetailImport, Long> {
    List<DetailImport> findDetailImportByImportsId(Imports imports);
}
