package com.example.backend.combination;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
/** Combination.java definiert, was in der Datenbank gespeichert wird.
 CombinationRepository.java ermöglicht auf diese Daten zuzugreifen */

public interface CombinationRepository
        extends JpaRepository<Combination, Long> {

    /** Liefert alle Kombinationen eines bestimmten Nutzers. */
    List<Combination> findByOwnerId(Long ownerId);
}