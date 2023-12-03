package teamx.app.backend.models.dto.charts;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChartsDataDTO {
    private List labels;
    private List<DataSetDTO> datasets;
}
