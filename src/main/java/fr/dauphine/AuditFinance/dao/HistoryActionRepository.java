package fr.dauphine.AuditFinance.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import fr.dauphine.AuditFinance.domain.entity.HistoryAction;

@Repository
public interface HistoryActionRepository extends JpaRepository<HistoryAction, Long> {
	
	@Query(value = "SELECT h.* FROM history h GROUP BY h.stock_key HAVING COUNT(*) = (SELECT MAX(*) FROM history)", nativeQuery = true)
	Optional<HistoryAction> findMostWanted();
}
