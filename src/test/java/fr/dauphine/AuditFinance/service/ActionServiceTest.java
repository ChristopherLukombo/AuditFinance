package fr.dauphine.AuditFinance.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import fr.dauphine.AuditFinance.dao.HistoryActionRepository;
import fr.dauphine.AuditFinance.domain.dto.HistoryActionDTO;
import fr.dauphine.AuditFinance.domain.entity.HistoryAction;
import fr.dauphine.AuditFinance.service.impl.ActionServiceImpl;
import fr.dauphine.AuditFinance.service.mapper.HistoryActionMapper;


@ExtendWith(MockitoExtension.class)
class ActionServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@Mock
	private HistoryActionRepository historyActionRepository;

	@Mock
	private HistoryActionMapper historyActionMapper;

	@InjectMocks
	private ActionServiceImpl actionService;

	@SuppressWarnings("unchecked")
	@Test
	void should_consult_successfully() {
		// Given
		LinkedHashMap<String, Object> timeSeriesMap = new LinkedHashMap<>();
		timeSeriesMap.put("1. open", "1.0");
		timeSeriesMap.put("2. high", "1.0");
		timeSeriesMap.put("3. low", "1.0");
		timeSeriesMap.put("4. close", "2.0");
		timeSeriesMap.put("5. adjusted close", "1.0");
		timeSeriesMap.put("6. volume", "1");
		timeSeriesMap.put("7. dividend amount", "1.0");
		timeSeriesMap.put("8. split coefficient", "1.0");

		LocalDate now = LocalDate.now();
		String dateInString = now.minus(1L,ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		LinkedHashMap dates = new LinkedHashMap();
		dates.put(dateInString, timeSeriesMap);
		LinkedHashMap body = new LinkedHashMap();
		body.put("Time Series (Daily)", dates);

		final ResponseEntity<LinkedHashMap<String, Object>> map = ResponseEntity.ok().body(body);

		// When
		when(restTemplate.getForEntity(anyString(), ArgumentMatchers.<Class<LinkedHashMap<String, Object>>>any())).thenReturn(map);

		// Then
		assertThat(actionService.consult("IBM", now)).isNotNull();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void should_failed_when_trying_to_consult() {
		// Given
		LinkedHashMap<String, Object> timeSeriesMap = new  LinkedHashMap<>();
		timeSeriesMap.put("1. open", "1L");
		timeSeriesMap.put("2. high", "1.0");
		timeSeriesMap.put("3. low", "1.0");
		timeSeriesMap.put("4. close", "2.0");
		timeSeriesMap.put("5. adjusted close", "1.0");
		timeSeriesMap.put("6. volume", "1");
		timeSeriesMap.put("7. dividend amount", "1.0");
		timeSeriesMap.put("8. split coefficient", "1.0");

		LocalDate now = LocalDate.now();
		String dateInString = now.minus(1L,ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		LinkedHashMap dates = new LinkedHashMap();
		dates.put(dateInString, timeSeriesMap);
		LinkedHashMap body = new LinkedHashMap();
		body.put("Time Series (Daily)", dates);

		final ResponseEntity<LinkedHashMap<String, Object>> map = ResponseEntity.ok().body(body);

		// When
		when(restTemplate.getForEntity(anyString(), ArgumentMatchers.<Class<LinkedHashMap<String, Object>>>any())).thenReturn(map);

		// Then
		assertThatExceptionOfType(Exception.class)
		  .isThrownBy(() -> actionService.consult("IBM", now));
	}

	@Test
	void should_consultActionBetween_successfully() {
		// Given
		LinkedHashMap<String, Object> timeSeriesMap = new  LinkedHashMap<>();
		timeSeriesMap.put("1. open", "1.0");
		timeSeriesMap.put("2. high", "1.0");
		timeSeriesMap.put("3. low", "1.0");
		timeSeriesMap.put("4. close", "2.0");
		timeSeriesMap.put("5. adjusted close", "1.0");
		timeSeriesMap.put("6. volume", "1");
		timeSeriesMap.put("7. dividend amount", "1.0");
		timeSeriesMap.put("8. split coefficient", "1.0");

		LocalDate dateFrom = LocalDate.now();
		LocalDate dateTo = LocalDate.now().plusDays(1L);
		String dateInString = dateFrom.minus(1L,ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		LinkedHashMap dates = new LinkedHashMap();
		dates.put(dateInString, timeSeriesMap);
		LinkedHashMap body = new LinkedHashMap();
		body.put("Time Series (Daily)", dates);

		final ResponseEntity<LinkedHashMap<String, Object>> map = ResponseEntity.ok().body(body);

		// When
		when(restTemplate.getForEntity(anyString(), ArgumentMatchers.<Class<LinkedHashMap<String, Object>>>any())).thenReturn(map);

		// Then
		assertThat(actionService.consultActionBetween("IBM", dateFrom, dateTo)).isNotNull();
		
		verify(restTemplate, times(1)).getForEntity(anyString(), ArgumentMatchers.<Class<LinkedHashMap<String, Object>>>any());
	}

	@Test
	void should_failed_when_trying_to_consultActionBetween() {
		// Given
		LinkedHashMap<String, Object> timeSeriesMap = new LinkedHashMap<>();
		timeSeriesMap.put("1. open", "1L");
		timeSeriesMap.put("2. high", "1.0");
		timeSeriesMap.put("3. low", "1.0");
		timeSeriesMap.put("4. close", "2.0");
		timeSeriesMap.put("5. adjusted close", "1.0");
		timeSeriesMap.put("6. volume", "1");
		timeSeriesMap.put("7. dividend amount", "1.0");
		timeSeriesMap.put("8. split coefficient", "1.0");

		LocalDate dateFrom = LocalDate.now();
		LocalDate dateTo = LocalDate.now().plusDays(1L);
		String dateInString = dateFrom.minus(1L,ChronoUnit.DAYS).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		LinkedHashMap dates = new LinkedHashMap();
		dates.put(dateInString, timeSeriesMap);
		LinkedHashMap body = new LinkedHashMap();
		body.put("Time Series (Daily)", dates);

		final ResponseEntity<LinkedHashMap<String, Object>> map = ResponseEntity.ok().body(body);

		// When
		when(restTemplate.getForEntity(anyString(), ArgumentMatchers.<Class<LinkedHashMap<String, Object>>>any())).thenReturn(map);

		// Then
		assertThatExceptionOfType(Exception.class)
		  .isThrownBy(() -> actionService.consultActionBetween("IBM", dateFrom, dateTo));
		
		verify(restTemplate, times(1)).getForEntity(anyString(), ArgumentMatchers.<Class<LinkedHashMap<String, Object>>>any());
	}
	
	@Test
	void should_returns_historyAction_find_Most_Wanted_successfully() {
		// Given
		HistoryActionDTO historyActionDTO = new HistoryActionDTO();
		historyActionDTO.setId(1L);
		
		HistoryAction historyAction = new HistoryAction();
		historyAction.setId(1L);
		
		// When
		when(historyActionRepository.findMostWanted()).thenReturn(Optional.ofNullable(historyAction));
		when(historyActionMapper.toHistoryAction((HistoryAction) any())).thenReturn(historyActionDTO);
		
		// Then
		assertThat(actionService.findMostWanted()).isNotNull();
		
		verify(historyActionRepository, times(1)).findMostWanted();
		verify(historyActionMapper, times(1)).toHistoryAction((HistoryAction) any());
		
	}
	
	@Test
	void should_returns_emptyOptional_when_trying_to_find_Most_Wanted() {
		// When
		when(historyActionRepository.findMostWanted()).thenReturn(Optional.ofNullable(null));
		
		// Then
		assertThat(actionService.findMostWanted()).isEmpty();
		
		verify(historyActionRepository, times(1)).findMostWanted();
		verify(historyActionMapper, never()).toHistoryAction((HistoryAction) any());
	}
}
