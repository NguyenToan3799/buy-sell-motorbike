package buysellmoto.model.dto;

import buysellmoto.core.ultilities.DateToTimestamp;
import buysellmoto.core.ultilities.TimestampToDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto implements Serializable {

    private Long id;

    @Column(nullable = false)
    private Long showroomStaffId;

    @Column(nullable = false)
    private String showroomStaffName;

    @Column(nullable = false)
    private String originBankName;

    @Column(nullable = false)
    private String originAccountNumber;

    @Column(nullable = false)
    private String originAccountHolder;

    @Column(nullable = false)
    private String targetBankName;

    @Column(nullable = false)
    private String targetAccountNumber;

    @Column(nullable = false)
    private String targetAccountHolder;

    @Column(nullable = false)
    private Double amount;

    private String description;

    @JsonSerialize(using = DateToTimestamp.class)
    @JsonDeserialize(using = TimestampToDate.class)
    private LocalDateTime recordedDate;

    private Long buyRequestId;

    private Long sellRequestId;

}
