package fr.dauphine.AuditFinance.service;

import fr.dauphine.AuditFinance.domain.dto.ActionDTO;
import fr.dauphine.AuditFinance.domain.dto.HistoryActionDTO;

import java.time.LocalDate;
import java.util.Optional;

/**
 * The Interface ActionService.
 */
public interface ActionService {


    /**
     * Consult.
     *
     * @param stockKey the stock key
     * @param date the date
     * @return the action DTO
     */
    ActionDTO consult(String stockKey, LocalDate date);

    /**
     * Consult action between.
     *
     * @param stockKey the stock key
     * @param dateFrom the date from
     * @param dateTo the date to
     * @return the action DTO
     */
    ActionDTO consultActionBetween(String stockKey, LocalDate dateFrom, LocalDate dateTo);
    
    /**
     * Find most wanted.
     *
     * @return the optional
     */
    Optional<HistoryActionDTO> findMostWanted();
    
}
