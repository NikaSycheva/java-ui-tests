package otcuda.zvuk.stepik.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StepikAuthModel {
    private String email;
    private String password;
}
