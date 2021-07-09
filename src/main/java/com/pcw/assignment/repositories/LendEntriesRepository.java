package com.pcw.assignment.repositories;

import com.pcw.assignment.models.LendEntry;
import com.pcw.assignment.models.LendEntryKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LendEntriesRepository extends JpaRepository<LendEntry, LendEntryKey> {
}
