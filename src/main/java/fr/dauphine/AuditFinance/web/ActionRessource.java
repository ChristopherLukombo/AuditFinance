package fr.dauphine.AuditFinance.web;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.dauphine.AuditFinance.domain.dto.ActionDTO;
import fr.dauphine.AuditFinance.domain.dto.HistoryActionDTO;
import fr.dauphine.AuditFinance.service.ActionService;

@RestController
@RequestMapping(path = "/api")
public class ActionRessource {

    private static final Logger log = LoggerFactory.getLogger(ActionRessource.class);

    private final ActionService actionService;

    public ActionRessource(ActionService actionService) {
        this.actionService = actionService;
    }

    @GetMapping(value = "/consultation", params = {"stockKey", "date"})
    public ResponseEntity<ActionDTO> consultAction(
            @RequestParam String stockKey,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {
        log.debug("Request to get action: {} {}", stockKey, date);
        final ActionDTO actionDTO = actionService.consult(stockKey, date);
        return ResponseEntity.ok().body(actionDTO);
    }

    @GetMapping(value = "/consultation/web", params = {"stockKey", "dateFrom", "dateTo"})
    public ResponseEntity<ActionDTO> consultActionBetween(
            @RequestParam String stockKey,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFrom,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateTo) {
        log.debug("Request to get action: {} {}", dateFrom, dateTo);
        final ActionDTO actionDTO = actionService.consultActionBetween(stockKey, dateFrom, dateTo);
        return ResponseEntity.ok().body(actionDTO);
    }
    
    @GetMapping("/consultation/mostWanted")
    public ResponseEntity<HistoryActionDTO> consultMostWanted() {
       log.debug("Request to get most wanted action");
       return actionService.findMostWanted()
    		   .map(ResponseEntity::ok)
    		   .orElse(ResponseEntity.notFound().build());
    }
}
