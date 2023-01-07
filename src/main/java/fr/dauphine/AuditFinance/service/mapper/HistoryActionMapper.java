package fr.dauphine.AuditFinance.service.mapper;



import fr.dauphine.AuditFinance.domain.dto.ActionDTO;
import fr.dauphine.AuditFinance.domain.dto.HistoryActionDTO;
import fr.dauphine.AuditFinance.domain.entity.HistoryAction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HistoryActionMapper {

    @Mapping(source = "open", target = "open")
    @Mapping(source = "high", target = "high")
    @Mapping(source = "low", target = "low")
    @Mapping(source = "close", target = "close")
    @Mapping(source = "adjustedClose", target = "adjustedClose")
    @Mapping(source = "volume", target = "volume")
    @Mapping(source = "dividendAmount", target = "dividendAmount")
    HistoryAction toHistoryAction(ActionDTO actionDTO);
    
    @Mapping(source = "open", target = "open")
    @Mapping(source = "high", target = "high")
    @Mapping(source = "low", target = "low")
    @Mapping(source = "close", target = "close")
    @Mapping(source = "adjustedClose", target = "adjustedClose")
    @Mapping(source = "volume", target = "volume")
    @Mapping(source = "dividendAmount", target = "dividendAmount")
    HistoryActionDTO toHistoryAction(HistoryAction historyAction);

}