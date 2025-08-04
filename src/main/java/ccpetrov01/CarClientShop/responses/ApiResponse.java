package ccpetrov01.CarClientShop.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse {
    private String massage;
    private Object data;
}
