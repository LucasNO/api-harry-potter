package com.apiharrypotter.repository;

import com.apiharrypotter.entity.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Integer>, JpaSpecificationExecutor<Character> {
}
