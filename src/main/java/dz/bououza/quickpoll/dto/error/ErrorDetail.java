package dz.bououza.quickpoll.dto.error;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ErrorDetail {
    private String title;
    private int status;
    private String detail;
    private long timeStamp;
    private String path;
    private String developerMessage;
}
