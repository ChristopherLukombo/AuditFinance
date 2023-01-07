package fr.dauphine.AuditFinance.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import fr.dauphine.AuditFinance.dao.HistoryActionRepository;
import fr.dauphine.AuditFinance.domain.dto.ActionDTO;
import fr.dauphine.AuditFinance.domain.dto.HistoryActionDTO;
import fr.dauphine.AuditFinance.domain.entity.HistoryAction;
import fr.dauphine.AuditFinance.service.ActionService;
import fr.dauphine.AuditFinance.service.mapper.HistoryActionMapper;

@Service
@Transactional
public class ActionServiceImpl implements ActionService {

    private static final String URL = "https://www.alphavantage.co";

    private static final Logger log = LoggerFactory.getLogger(ActionServiceImpl.class);

    private final RestTemplate restTemplate;

    private final HistoryActionRepository historyActionRepository;

    private final HistoryActionMapper  historyActionMapper;

    public ActionServiceImpl(
            RestTemplate restTemplate,
            HistoryActionRepository historyActionRepository,
            HistoryActionMapper historyActionMapper) {
        this.restTemplate = restTemplate;
        this.historyActionRepository = historyActionRepository;
        this.historyActionMapper = historyActionMapper;
    }


    /**
     * Consult.
     *
     * @param stockKey the stock key
     * @param date the date
     * @return the action DTO
     */
    @Override
    public ActionDTO consult(String stockKey, LocalDate date) {
        LocalDateTime currentLocalDateTime = LocalDateTime.of(date, LocalTime.MIDNIGHT);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HH:mm");
        String formattedDateTimeTo = currentLocalDateTime.format(dateTimeFormatter);
        log.info("Request to consult action: {} {}", stockKey, date);
        String resourceUrl
                = URL + "/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=IBM&apikey=R9IIXZIDF397M8ZH&time_from=" + formattedDateTimeTo;
        //20220410T0130
        ResponseEntity<LinkedHashMap> response
                = restTemplate.getForEntity(resourceUrl + "/1", LinkedHashMap.class);
        ActionDTO actionDTO = null;
                LinkedHashMap map = response.getBody();
        if (map.get("Time Series (Daily)") instanceof LinkedHashMap) {
            String dateFormat = date.minus(1L,ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            LinkedHashMap<String, Object> timeSeriesMap = (LinkedHashMap<String, Object>) map.get("Time Series (Daily)");

            final LinkedHashMap<String, String> timeSerie = (LinkedHashMap<String, String>) timeSeriesMap.get(dateFormat);
           // ObjectMapper mapper = new ObjectMapper();
           // mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
          //  mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
          //  actionDTO = mapper.convertValue(map, ActionDTO.class);
            actionDTO = new ActionDTO();
            actionDTO.setOpen(Double.parseDouble(timeSerie.get("1. open")));
            actionDTO.setHigh(Double.parseDouble(timeSerie.get("2. high")));
            actionDTO.setLow(Double.parseDouble(timeSerie.get("3. low")));
            actionDTO.setClose(Double.parseDouble(timeSerie.get("4. close")));
            actionDTO.setAdjustedClose(Double.parseDouble(timeSerie.get("5. adjusted close")));
            actionDTO.setVolume(Long.parseLong(timeSerie.get("6. volume")));
            actionDTO.setDividendAmount(Double.parseDouble(timeSerie.get("7. dividend amount")));
            actionDTO.setDividendAmount(Double.parseDouble(timeSerie.get("8. split coefficient")));

        }

        
        final HistoryAction historyAction = historyActionMapper.toHistoryAction(actionDTO);
        historyActionRepository.save(historyAction);

        return actionDTO;
    }

    /**
     * Consult action between.
     *
     * @param stockKey the stock key
     * @param dateFrom the date from
     * @param dateTo the date to
     * @return the action DTO
     */
    @Override
    public ActionDTO consultActionBetween(String stockKey, LocalDate dateFrom, LocalDate dateTo) {
        log.info("Request to consult action: {} {} {}", stockKey, dateFrom, dateTo);
        LocalDateTime currentLocalDateTime = LocalDateTime.of(dateFrom, LocalTime.MIDNIGHT);
        LocalDateTime currentLocalDateTimeTo = LocalDateTime.of(dateTo, LocalTime.MIDNIGHT);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HH:mm");

        String formattedDateTime = currentLocalDateTime.format(dateTimeFormatter);
        String formattedDateTimeTo = currentLocalDateTimeTo.format(dateTimeFormatter);
        String dateFormat = dateFrom.minus(1L,ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String resourceUrl
                = URL + "/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=IBM&apikey=R9IIXZIDF397M8ZH&time_from=" + dateFormat + formattedDateTime + "&time_to=" + formattedDateTimeTo;

        ResponseEntity<LinkedHashMap> response
                = restTemplate.getForEntity(resourceUrl + "/1", LinkedHashMap.class);
        ActionDTO actionDTO = null;
        LinkedHashMap map = response.getBody();
        if (map.get("Time Series (Daily)") instanceof LinkedHashMap) {

            LinkedHashMap<String, Object> timeSeriesMap = (LinkedHashMap<String, Object>) map.get("Time Series (Daily)");

            final LinkedHashMap<String, String> timeSerie = (LinkedHashMap<String, String>) timeSeriesMap.get(dateFormat);

            actionDTO = new ActionDTO();
            actionDTO.setOpen(Double.parseDouble(timeSerie.get("1. open")));
            actionDTO.setHigh(Double.parseDouble(timeSerie.get("2. high")));
            actionDTO.setLow(Double.parseDouble(timeSerie.get("3. low")));
            actionDTO.setClose(Double.parseDouble(timeSerie.get("4. close")));
            actionDTO.setAdjustedClose(Double.parseDouble(timeSerie.get("5. adjusted close")));
            actionDTO.setVolume(Long.parseLong(timeSerie.get("6. volume")));
            actionDTO.setDividendAmount(Double.parseDouble(timeSerie.get("7. dividend amount")));
            actionDTO.setDividendAmount(Double.parseDouble(timeSerie.get("8. split coefficient")));

        }

        final HistoryAction historyAction = historyActionMapper.toHistoryAction(actionDTO);
        historyActionRepository.save(historyAction);

        return actionDTO;
    }

    /**
     * Find most wanted.
     *
     * @return the optional
     */
	@Override
	@Transactional(readOnly = true)
	public Optional<HistoryActionDTO> findMostWanted() {
        return historyActionRepository.findMostWanted()
        		.map(historyActionMapper::toHistoryAction);
	}
}
