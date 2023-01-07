package fr.dauphine.AuditFinance.web;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import fr.dauphine.AuditFinance.domain.dto.ActionDTO;
import fr.dauphine.AuditFinance.domain.dto.HistoryActionDTO;
import fr.dauphine.AuditFinance.service.ActionService;
import fr.dauphine.AuditFinance.utils.TestUtil;

@ExtendWith(MockitoExtension.class)
class ActionRessourceTest {

	private MockMvc mockMvc;

	@Mock
	private ActionService actionService;

	@InjectMocks
	private ActionRessource actionRessource;
	
	@BeforeEach
     void setUp() throws Exception {
		mockMvc = MockMvcBuilders.standaloneSetup(actionRessource).build();
	}
	
	@Test
	void should_response_200_when_consultAction_is_ok() throws Exception {
		// Given
		ActionDTO actionDTO = new ActionDTO();
		actionDTO.setClose(1.9);

		// When
		when(actionService.consult(anyString(), (LocalDate) any())).thenReturn(actionDTO);

		// Then
		mockMvc.perform(get("/api/consultation?stockKey=IBM&date=2022-11-11")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
		
		verify(actionService, times(1)).consult(anyString(), (LocalDate) any());
	}
	
	@Test
	void should_response_400_consultAction_is_badParam() throws Exception {
		// Then
		mockMvc.perform(get("/api/consultation")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isBadRequest());
		
		verify(actionService, never()).consult(anyString(), (LocalDate) any());
	}
	
	@Test
	void should_response_200_consultActionBetween_is_ok() throws Exception {
		// Given
		ActionDTO actionDTO = new ActionDTO();
		actionDTO.setClose(1.9);

		// When
		when(actionService.consultActionBetween(anyString(), (LocalDate) any(), (LocalDate) any())).thenReturn(actionDTO);

		// Then
		mockMvc.perform(get("/api/consultation/web?stockKey=IBM&dateFrom=2022-11-11&dateTo=2022-11-11")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
		
		verify(actionService, times(1)).consultActionBetween(anyString(), (LocalDate) any(), (LocalDate) any());
	}
	
	@Test
	void should_response_400_consultActionBetween_is_badParam() throws Exception {
		// Then
		mockMvc.perform(get("/api/consultation/web")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isBadRequest());
		
		verify(actionService, never()).consultActionBetween(anyString(), (LocalDate) any(), (LocalDate) any());
	}
	
	@Test
	void should_response_200_when_consultMostWanted_is_ok() throws Exception {
		// Given
		HistoryActionDTO historyActionDTO = new HistoryActionDTO();
		historyActionDTO.setClose(1.9);

		// When
		when(actionService.findMostWanted()).thenReturn(Optional.ofNullable(historyActionDTO));

		// Then
		mockMvc.perform(get("/api/consultation/mostWanted")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isOk());
		
		verify(actionService, times(1)).findMostWanted();
	}
	
	@Test
	void should_response_404_when_consultMostWanted_is_ok() throws Exception {
		// Given
		HistoryActionDTO historyActionDTO = null;

		// When
		when(actionService.findMostWanted()).thenReturn(Optional.ofNullable(historyActionDTO));

		// Then
		mockMvc.perform(get("/api/consultation/mostWanted")
				.contentType(TestUtil.APPLICATION_JSON_UTF8))
		        .andExpect(status().isNotFound());
		
		verify(actionService, times(1)).findMostWanted();
	}
}
